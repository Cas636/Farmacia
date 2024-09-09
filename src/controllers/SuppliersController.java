package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Categories;
import models.DynamicCombobox;
import static models.EmployeesDAO.rol_user;
import models.Suppliers;
import models.SuppliersDAO;
import views.systemView;

public class SuppliersController implements ActionListener, MouseListener, KeyListener {

    private Suppliers suppliers;
    private SuppliersDAO suppliers_dao;
    private systemView views;
    String rol = rol_user;
    DefaultTableModel model = new DefaultTableModel();

    public SuppliersController(Suppliers suppliers, SuppliersDAO suppliers_dao, systemView views) {
        this.suppliers = suppliers;
        this.suppliers_dao = suppliers_dao;
        this.views = views;
        //boton registrar proveedor
        this.views.btn_register_supplier.addActionListener(this);
        //boton modificar proveedor
        this.views.btn_update_supplier.addActionListener(this);
        //boton eliminar proveedor
        this.views.btn_delete_supplier.addActionListener(this);
        //boton cancelar
        this.views.btn_cancel_supplier.addActionListener(this);

        this.views.suppliers_table.addMouseListener(this);
        this.views.txt_search_supplier.addKeyListener(this);
        this.views.jLabelSuppliers.addMouseListener(this);
        getSupplierName();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_supplier) {
            if (views.txt_supplier_name.getText().equals("")
                    || views.txt_supplier_description.getText().equals("")
                    || views.txt_supplier_address.getText().equals("")
                    || views.txt_supplier_telephone.getText().equals("")
                    || views.txt_supplier_email.getText().equals("")
                    || views.cmb_supplier_city.getSelectedItem().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                //realizar inserción
                suppliers.setName(views.txt_supplier_name.getText().trim());
                suppliers.setDescription(views.txt_supplier_description.getText().trim());
                suppliers.setAddress(views.txt_supplier_address.getText().trim());
                suppliers.setTelephone(views.txt_supplier_telephone.getText().trim());
                suppliers.setEmail(views.txt_supplier_email.getText().trim());
                suppliers.setCity(views.cmb_supplier_city.getSelectedItem().toString());
                if (suppliers_dao.registerSuppliersQuery(suppliers)) {
                    cleanTable();
                    cleanFields();
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null, "Proveedor registrado con exito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el proveedor");
                }

            }
        } else if (e.getSource() == views.btn_update_supplier) {
            if (views.txt_supplier_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                //verificar si los campos estan vacios

                if (views.txt_supplier_name.getText().equals("")
                        || views.txt_supplier_address.getText().equals("")
                        || views.txt_supplier_telephone.getText().equals("")
                        || views.txt_supplier_email.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    //realizar inserción
                    suppliers.setName(views.txt_supplier_name.getText().trim());
                    suppliers.setDescription(views.txt_supplier_description.getText().trim());
                    suppliers.setAddress(views.txt_supplier_address.getText().trim());
                    suppliers.setTelephone(views.txt_supplier_telephone.getText().trim());
                    suppliers.setEmail(views.txt_supplier_email.getText().trim());
                    suppliers.setCity(views.cmb_supplier_city.getSelectedItem().toString());
                    suppliers.setId(Integer.parseInt(views.txt_supplier_id.getText()));
                    if (suppliers_dao.updateSupplierQuery(suppliers)) {
                        cleanTable();
                        cleanFields();
                        listAllSuppliers();
                        views.btn_register_supplier.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Datos modificados exitosamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el proveedor");
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_supplier) {
            int row = views.suppliers_table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un proveedor para eliminar");
            } else {
                int id = Integer.parseInt(views.suppliers_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar este proveedor?");
                if (question == 0 && suppliers_dao.deleteSupplierQuery(id) != false) {
                    cleanFields();
                    cleanTable();
                    views.btn_register_supplier.setEnabled(true);
                    listAllSuppliers();
                    JOptionPane.showMessageDialog(null, "Proveedor eliminado con exito");
                }
            }
        } else if (e.getSource() == views.btn_cancel_supplier) {
            cleanFields();
            views.btn_register_supplier.setEnabled(true);
        }
    }

    public void listAllSuppliers() {
        if (rol.equals("Administrador")) {
            List<Suppliers> list = suppliers_dao.listSupplierQuery(views.txt_search_supplier.getText());
            model = (DefaultTableModel) views.suppliers_table.getModel();
            Object[] row = new Object[7];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                row[2] = list.get(i).getDescription();
                row[3] = list.get(i).getAddress();
                row[4] = list.get(i).getTelephone();
                row[5] = list.get(i).getEmail();
                row[6] = list.get(i).getCity();
                model.addRow(row);
            }
            views.suppliers_table.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.suppliers_table) {
            int row = views.suppliers_table.rowAtPoint(e.getPoint());
            views.txt_supplier_id.setText(views.suppliers_table.getValueAt(row, 0).toString());
            views.txt_supplier_name.setText(views.suppliers_table.getValueAt(row, 1).toString());
            views.txt_supplier_description.setText(views.suppliers_table.getValueAt(row, 2).toString());
            views.txt_supplier_address.setText(views.suppliers_table.getValueAt(row, 3).toString());
            views.txt_supplier_telephone.setText(views.suppliers_table.getValueAt(row, 4).toString());
            views.txt_supplier_email.setText(views.suppliers_table.getValueAt(row, 5).toString());
            views.cmb_supplier_city.setSelectedItem(views.suppliers_table.getValueAt(row, 6).toString());

            //deshabilitar boton
            views.txt_supplier_id.setEditable(false);
            views.btn_register_supplier.setEnabled(false);

        } else if (e.getSource() == views.jLabelSuppliers) {
            if (rol.equals("Administrador")) {
                views.jTabbedPane1.setSelectedIndex(5);
                cleanFields();
                cleanTable();
                listAllSuppliers();
            } else {
                views.jTabbedPane1.setEnabledAt(5, false);
                views.jLabelSuppliers.setEnabled(false);
                JOptionPane.showMessageDialog(null, "No tienes privilegios de administrador para acceder a esta vista");
            }
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

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == views.txt_search_supplier) {
            cleanTable();
            listAllSuppliers();
        }
    }

    //Limpiar campos
    public void cleanFields() {
        views.txt_supplier_id.setText("");
        views.txt_supplier_id.setEditable(true);
        views.txt_supplier_name.setText("");
        views.txt_supplier_description.setText("");
        views.txt_supplier_address.setText("");
        views.txt_supplier_telephone.setText("");
        views.txt_supplier_email.setText("");
        views.cmb_supplier_city.setSelectedIndex(0);
    }

    //limpiar tabla
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }
    //metodo para mostrar el nombre del proveedor

    public void getSupplierName() {
        List<Suppliers> list = suppliers_dao.listSupplierQuery(views.txt_search_supplier.getText());
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId();
            String name = list.get(i).getName();
            views.cmb_purchase_supplier.addItem(new DynamicCombobox(id, name));
        }
    }
}
