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
import models.Customers;
import models.CustomersDAO;
import views.systemView;

public class CustomersController implements ActionListener, MouseListener, KeyListener {

    private Customers customer;
    private CustomersDAO customer_dao;
    private systemView views;
    DefaultTableModel model = new DefaultTableModel();

    public CustomersController(Customers customer, CustomersDAO customer_dao, systemView views) {
        this.customer = customer;
        this.customer_dao = customer_dao;
        this.views = views;
        //boton registrar cliente
        this.views.btn_register_customer.addActionListener(this);
        //boton modificar cliente
        this.views.btn_update_customer.addActionListener(this);
        //boton eliminar cliente
        this.views.btn_delete_customer.addActionListener(this);
        //boton cancelar
        this.views.btn_cancel_customer.addActionListener(this);

        this.views.jLabelCustomers.addMouseListener(this);

        //buscador
        this.views.txt_search_customer.addKeyListener(this);
        this.views.customers_table.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_customer) {
            //verificar si los campos estan vacios
            if (views.txt_customer_id.getText().equals("")
                    || views.txt_customer_fullname.getText().equals("")
                    || views.txt_customer_address.getText().equals("")
                    || views.txt_customer_telephone.getText().equals("")
                    || views.txt_customer_email.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                //realizar inserción
                customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
                customer.setFull_name(views.txt_customer_fullname.getText().trim());
                customer.setAddress(views.txt_customer_address.getText().trim());
                customer.setTelephone(views.txt_customer_telephone.getText().trim());
                customer.setEmail(views.txt_customer_email.getText().trim());
                if (customer_dao.registerCustomerQuery(customer)) {
                    cleanTable();
                    cleanFields();
                    listAllCustomers();
                    JOptionPane.showMessageDialog(null, "Cliente registrado con exito");

                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar el cliente");
                }

            }
        } else if (e.getSource() == views.btn_update_customer) {
            if (views.txt_customer_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                //verificar si los campos estan vacios
                if (views.txt_customer_id.getText().equals("")
                        || views.txt_customer_fullname.getText().equals("")
                        || views.txt_customer_address.getText().equals("")
                        || views.txt_customer_telephone.getText().equals("")
                        || views.txt_customer_email.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    //realizar inserción
                    customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
                    customer.setFull_name(views.txt_customer_fullname.getText().trim());
                    customer.setAddress(views.txt_customer_address.getText().trim());
                    customer.setTelephone(views.txt_customer_telephone.getText().trim());
                    customer.setEmail(views.txt_customer_email.getText().trim());
                    if (customer_dao.updateCustomerQuery(customer)) {
                        cleanTable();
                        cleanFields();
                        listAllCustomers();
                        views.btn_register_customer.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Datos modificados exitosamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar el cliente");
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_customer) {
            int row = views.customers_table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un cliente para eliminar");
            } else {
                int id = Integer.parseInt(views.customers_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar este cliente?");
                if (question == 0 && customer_dao.deleteCustomerQuery(id) != false) {
                    cleanFields();
                    cleanTable();
                    views.btn_register_customer.setEnabled(true);
                    listAllCustomers();
                    JOptionPane.showMessageDialog(null, "Cliente eliminado con exito");
                }
            }
        } else if (e.getSource() == views.btn_cancel_customer) {
            cleanFields();
            views.btn_register_customer.setEnabled(true);
        }
    }

    public void listAllCustomers() {
        List<Customers> list = customer_dao.listCustomerQuery(views.txt_search_customer.getText());
        model = (DefaultTableModel) views.customers_table.getModel();
        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getFull_name();
            row[2] = list.get(i).getAddress();
            row[3] = list.get(i).getTelephone();
            row[4] = list.get(i).getEmail();
            model.addRow(row);
        }
        views.customers_table.setModel(model);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.customers_table) {
            int row = views.customers_table.rowAtPoint(e.getPoint());
            views.txt_customer_id.setText(views.customers_table.getValueAt(row, 0).toString());
            views.txt_customer_fullname.setText(views.customers_table.getValueAt(row, 1).toString());
            views.txt_customer_address.setText(views.customers_table.getValueAt(row, 2).toString());
            views.txt_customer_telephone.setText(views.customers_table.getValueAt(row, 3).toString());
            views.txt_customer_email.setText(views.customers_table.getValueAt(row, 4).toString());

            //deshabilitar
            views.txt_customer_id.setEditable(false);
            views.btn_register_customer.setEnabled(false);
        } else if (e.getSource() == views.jLabelCustomers) {
            views.jTabbedPane1.setSelectedIndex(3);
            cleanFields();
            cleanTable();
            listAllCustomers();
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
        if (e.getSource() == views.txt_search_customer) {
            cleanTable();
            listAllCustomers();
        }
    }

    //Limpiar campos
    public void cleanFields() {
        views.txt_customer_id.setText("");
        views.txt_customer_id.setEditable(true);
        views.txt_customer_fullname.setText("");
        views.txt_customer_address.setText("");
        views.txt_customer_telephone.setText("");
        views.txt_customer_email.setText("");
    }

    //limpiar tabla
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }

    }

}
