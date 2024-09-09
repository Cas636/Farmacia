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
import models.CategoriesDAO;
import models.DynamicCombobox;
import static models.EmployeesDAO.rol_user;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import views.systemView;

public class CategoriesController implements ActionListener, MouseListener, KeyListener {

    private Categories category;
    private CategoriesDAO categories_dao;
    private systemView views;
    String rol = rol_user;

    DefaultTableModel model = new DefaultTableModel();

    public CategoriesController(Categories category, CategoriesDAO categories_dao, systemView views) {
        this.category = category;
        this.categories_dao = categories_dao;
        this.views = views;
        this.views.btn_register_category.addActionListener(this);
        this.views.btn_update_category.addActionListener(this);
        this.views.btn_delete_category.addActionListener(this);
        this.views.btn_cancel_category.addActionListener(this);
        this.views.categories_table.addMouseListener(this);
        this.views.txt_search_category.addKeyListener(this);
        this.views.jLabelCategories.addMouseListener(this);
        getCategoryName();
        AutoCompleteDecorator.decorate(views.cmb_product_category);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == views.btn_register_category) {
            if (views.txt_category_name.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                //realizar inserción
                category.setName(views.txt_category_name.getText().trim());
                if (categories_dao.registerCategoryQuery(category)) {
                    cleanTable();
                    cleanFields();
                    listAllCategories();
                    JOptionPane.showMessageDialog(null, "Categoria registrado con exito");
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar la categoria");
                }
            }
        } else if (e.getSource() == views.btn_update_category) {
            if (views.txt_category_id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
            } else {
                //verificar si los campos estan vacios
                if (views.txt_category_id.getText().equals("")
                        || views.txt_category_name.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
                } else {
                    //realizar inserción
                    category.setId(Integer.parseInt(views.txt_category_id.getText()));
                    category.setName(views.txt_category_name.getText().trim());
                    if (categories_dao.updateCategoryQuery(category)) {
                        cleanTable();
                        cleanFields();
                        listAllCategories();
                        views.btn_register_category.setEnabled(true);
                        JOptionPane.showMessageDialog(null, "Datos modificados exitosamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar la categoria");
                    }
                }
            }
        } else if (e.getSource() == views.btn_delete_category) {
            int row = views.categories_table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar una categoria para eliminar");
            } else {
                int id = Integer.parseInt(views.categories_table.getValueAt(row, 0).toString());
                int question = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres eliminar esta categoria?");
                if (question == 0 && categories_dao.deleteCategoryQuery(id) != false) {
                    cleanFields();
                    cleanTable();
                    views.btn_register_category.setEnabled(true);
                    listAllCategories();
                    JOptionPane.showMessageDialog(null, "Categoria eliminada con exito");
                }
            }
        } else if (e.getSource() == views.btn_cancel_category) {
            cleanFields();
            views.btn_register_category.setEnabled(true);
        }
    }

    public void listAllCategories() {
        if (rol.equals("Administrador")) {

            List<Categories> list = categories_dao.listCategoryQuery(views.txt_search_category.getText());
            model = (DefaultTableModel) views.categories_table.getModel();
            Object[] row = new Object[2];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId();
                row[1] = list.get(i).getName();
                model.addRow(row);
            }
            views.categories_table.setModel(model);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == views.categories_table) {
            int row = views.categories_table.rowAtPoint(e.getPoint());
            views.txt_category_id.setText(views.categories_table.getValueAt(row, 0).toString());
            views.txt_category_name.setText(views.categories_table.getValueAt(row, 1).toString());
            //deshabilitar
            views.btn_register_category.setEnabled(false);
        } else if (e.getSource() == views.jLabelCategories) {
            if (rol.equals("Administrador")) {
                views.jTabbedPane1.setSelectedIndex(6);
                cleanFields();
                cleanTable();
                listAllCategories();
            } else {
                views.jTabbedPane1.setEnabledAt(6,false);
                views.jLabelCategories.setEnabled(false);
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
        if (e.getSource() == views.txt_search_category) {
            cleanTable();
            listAllCategories();
        }
    }

    //Limpiar campos
    public void cleanFields() {
        views.txt_category_id.setText("");
        views.txt_category_name.setText("");
    }

    //limpiar tabla
    public void cleanTable() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.removeRow(i);
            i = i - 1;
        }
    }
    
    //metodo para mostrar el nombre de las categorias
    public void getCategoryName(){
        List<Categories> list = categories_dao.listCategoryQuery(views.txt_search_category.getText());
        for(int i = 0; i < list.size(); i++){
            int id = list.get(i).getId();
            String name = list.get(i).getName();
            views.cmb_product_category.addItem(new DynamicCombobox(id,name));
        }
    }

}
