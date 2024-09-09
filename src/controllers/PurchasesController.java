package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.DynamicCombobox;
import static models.EmployeesDAO.id_user;
import static models.EmployeesDAO.rol_user;
import models.Products;
import models.ProductsDAO;
import models.Purchases;
import models.PurchasesDAO;
import views.print;
import views.systemView;

public class PurchasesController implements KeyListener, ActionListener, MouseListener {

    private Purchases purchase;
    private PurchasesDAO purchase_dao;
    private systemView views;
    private int getIdSupplier;
    private int item = 0;
    DefaultTableModel model = new DefaultTableModel();
    DefaultTableModel temp;
    //Instanciar el modelo productos
    Products product = new Products();
    ProductsDAO product_dao = new ProductsDAO();
    String rol = rol_user;

    public PurchasesController(Purchases purchase, PurchasesDAO purchase_dao, systemView views) {
        this.purchase = purchase;
        this.purchase_dao = purchase_dao;
        this.views = views;
        
        this.views.btn_add_product_to_buy.addActionListener(this);
        this.views.btn_confirm_purchase.addActionListener(this);
        this.views.btn_remove_purchase.addActionListener(this);
        this.views.btn_new_purchase.addActionListener(this);
               
        this.views.txt_purchase_product_code.addKeyListener(this);
        this.views.txt_purchase_price.addKeyListener(this);
        this.views.jLabelPurchases.addMouseListener(this);
        this.views.jLabelReports.addMouseListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_add_product_to_buy) {
            DynamicCombobox supplier_cmb = (DynamicCombobox) views.cmb_purchase_supplier.getSelectedItem();
            int supplier_id = supplier_cmb.getId();
            if (getIdSupplier == 0) {
                getIdSupplier = supplier_id;
                addProductToBuy();
            } else {
                if (getIdSupplier != supplier_id) {
                    JOptionPane.showMessageDialog(null, "No puedes hacer una misma compra a varios proveedores");
                    cleanFieldsPurchases();
                    calculatePurchase();
                    return;
                }
                addProductToBuy();
            }
        } else if (e.getSource() == views.btn_confirm_purchase) {
            insertPurchase();
        } else if (e.getSource() == views.btn_remove_purchase) {
            if (views.purchases_table.getSelectedRow() != -1) {
                model = (DefaultTableModel) views.purchases_table.getModel();
                model.removeRow(views.purchases_table.getSelectedRow());
                calculatePurchase();
                views.txt_purchase_product_code.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            }
        } else if (e.getSource() == views.btn_new_purchase) {
            cleanTableTemp();
            cleanFieldsPurchases();
        }
    }

        @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.jLabelPurchases) {
            if (rol.equals("Administrador")) {
                views.jTabbedPane1.setSelectedIndex(1);
                cleanTable();
            } else {
                views.jTabbedPane1.setEnabledAt(1, false);
                views.jLabelPurchases.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tienes privilegios de administrador para acceder a esta vista");

            }
        } else if (e.getSource() == views.jLabelReports) {
            views.jTabbedPane1.setSelectedIndex(7);
            cleanTable();
            listPurchases();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == views.txt_purchase_product_code) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (views.txt_purchase_product_code.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Ingresa el codigo del producto a comprar");
                } else {
                    int id = Integer.parseInt(views.txt_purchase_product_code.getText());
                    product = product_dao.searchCode(id);
                    views.txt_purchase_product_name.setText(product.getName());
                    views.txt_purchase_id.setText("" + product.getId());
                    views.txt_purchase_product_amount.requestFocus();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_purchase_price) {
            int quantity;
            double price = 0.0;
            if (views.txt_purchase_product_amount.getText().equals("")) {
                quantity = 1;
                views.txt_purchase_price.setText("" + price);
            } else {
                quantity = Integer.parseInt(views.txt_purchase_product_amount.getText());
                price = Double.parseDouble(views.txt_purchase_price.getText());
                views.txt_purchase_subtotal.setText("" + quantity * price);
            }
        }
    }
    
    
    public void insertPurchase() {
        double total = Double.parseDouble(views.txt_purchase_total_to_pay.getText());
        int employee_id = id_user;
        if (purchase_dao.registerPurchaseQuery(getIdSupplier, employee_id, total)) {
            int purchase_id = purchase_dao.purchaseId();
            for (int i = 0; i < views.purchases_table.getRowCount(); i++) {
                int product_id = Integer.parseInt(views.purchases_table.getValueAt(i, 0).toString());
                int purchase_amount = Integer.parseInt(views.purchases_table.getValueAt(i, 2).toString());
                double purchase_price = Double.parseDouble(views.purchases_table.getValueAt(i, 3).toString());
                double purchase_subtotal = purchase_price * purchase_amount;

                //registrar detalles de la compra
                purchase_dao.registerPurchaseDetailQuery(purchase_id, purchase_price, purchase_amount, purchase_subtotal, product_id);
                //traer la cantidad de productos
                product = product_dao.searchId(product_id);
                int amount = (product.getProduct_quantity() + purchase_amount);

                product_dao.updateStockQuery(amount, product_id);

            }
            cleanTableTemp();
            JOptionPane.showMessageDialog(null, "Compra registrada con exito");
            print print = new print(purchase_id);
            print.setVisible(true);
        }
        
    }

    //metodo para listar las compras realizadas
    public void listPurchases() {
        if (rol.equals("Administrador") || rol.equals("Auxiliar")) {
            List<Purchases> list = purchase_dao.listAllPurchasesQuery();
            model = (DefaultTableModel) views.table_all_purchases.getModel();
            Object[] row = new Object[4];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getSupplier_name_product();
                row[2] = list.get(i).getTotal();
                row[3] = list.get(i).getCreated();
                model.addRow(row);
            }
            views.table_all_purchases.setModel(model);
        }
    }

    //añadir producto a la tabla temporal
    public void addProductToBuy() {
        int amount = Integer.parseInt(views.txt_purchase_product_amount.getText());
        String product_name = views.txt_purchase_product_name.getText();
        double price = Double.parseDouble(views.txt_purchase_price.getText());
        int purchase_id = Integer.parseInt(views.txt_purchase_id.getText());
        String supplier_name = views.cmb_purchase_supplier.getSelectedItem().toString();
        if (amount > 0) {
            temp = (DefaultTableModel) views.purchases_table.getModel();
            for (int i = 0; i < views.purchases_table.getRowCount(); i++) {
                if (views.purchases_table.getValueAt(i, 1).equals(views.txt_purchase_product_name.getText())) {
                    JOptionPane.showMessageDialog(null, "El producto ya esta registrado en la tabla de compras");
                    return;
                }
            }
            ArrayList list = new ArrayList();
            item = 1;
            list.add(item);
            list.add(purchase_id);
            list.add(product_name);
            list.add(amount);
            list.add(price);
            list.add(amount * price);
            list.add(supplier_name);

            Object[] obj = new Object[6];
            obj[0] = list.get(1);
            obj[1] = list.get(2);
            obj[2] = list.get(3);
            obj[3] = list.get(4);
            obj[4] = list.get(5);
            obj[5] = list.get(6);
            temp.addRow(obj);
            views.purchases_table.setModel(temp);
            cleanFieldsPurchases();
            views.cmb_purchase_supplier.setEditable(false);
            views.txt_purchase_product_code.requestFocus();
            calculatePurchase();
        }
    }

    //limpiar campos
    public void cleanFieldsPurchases() {
        views.txt_purchase_product_name.setText("");
        views.txt_purchase_price.setText("");
        views.txt_purchase_product_amount.setText("");
        views.txt_purchase_product_code.setText("");
        views.txt_purchase_subtotal.setText("");
        views.txt_purchase_id.setText("");
        views.txt_purchase_total_to_pay.setText("");
    }

    //calcular total a pagar
    public void calculatePurchase() {
        double total = 0.00;
        int numRow = views.purchases_table.getRowCount();
        for (int i = 0; i < numRow; i++) {
            //pasar el indice de la columna a sumar
            total = total + Double.parseDouble(String.valueOf(views.purchases_table.getValueAt(i, 4)));
        }
        views.txt_purchase_total_to_pay.setText("" + total);
    }

    //limpiar tabla temporal
    public void cleanTableTemp() {
        for (int i = 0; i < temp.getRowCount(); i++) {
            temp.removeRow(i);
            i = i - 1;
        }
    }
    
     //limpiar tabla
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }
}
