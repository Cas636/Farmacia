package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import models.Employees;
import models.EmployeesDAO;
import views.loginView;
import views.systemView;

public class LoginController implements ActionListener {

    private Employees employee;
    private EmployeesDAO employees_dao;
    private loginView login_view;

    public LoginController(Employees employee, EmployeesDAO employees_dao, loginView login_view) {
        this.employee = employee;
        this.employees_dao = employees_dao;
        this.login_view = login_view;
        this.login_view.btn_enter.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Obtener los datos de la vista
        String user = login_view.txt_username.getText().trim();
        String pass = String.valueOf(login_view.txt_password.getPassword());
        if (e.getSource() == login_view.btn_enter) {
            //validar que los campos no esten vacios
            if (!user.equals("") || !pass.equals("")) {
                //pasar los parametros al metodo login
                employee = employees_dao.loginQuery(user, pass);
                //verificar la existencia del usuario
                if (employee.getUsername() != null) {
                    if (employee.getRol().equals("Administrador")) {
                        systemView admin = new systemView();
                        admin.setVisible(true);
                    } else {
                        systemView aux = new systemView();
                        aux.setVisible(true);
                    }
                    this.login_view.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o Contrasseña incorrecto");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }

        }
    }

}
