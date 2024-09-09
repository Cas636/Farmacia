package controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static models.EmployeesDAO.address_user;
import static models.EmployeesDAO.email_user;
import static models.EmployeesDAO.full_name_user;
import static models.EmployeesDAO.id_user;
import static models.EmployeesDAO.telephone_user;
import views.systemView;

/**
 *
 * @author JOHAN
 */
public class settingsController implements MouseListener{
    
    private systemView views;
    
    public settingsController(systemView views){
        this.views = views;
        this.views.jLabelProducts.addMouseListener(this);
        this.views.jLabelPurchases.addMouseListener(this);
        this.views.jLabelSales.addMouseListener(this);
        this.views.jLabelCustomers.addMouseListener(this);
        this.views.jLabelEmployees.addMouseListener(this);
        this.views.jLabelSuppliers.addMouseListener(this);
        this.views.jLabelCategories.addMouseListener(this);
        this.views.jLabelReports.addMouseListener(this);
        this.views.jLabelSettings.addMouseListener(this);
        Profile();
    }

    //asignar el perfil del usuario
    public void Profile(){
        this.views.txt_id_profile.setText(""+id_user);
        this.views.txt_name_profile.setText(""+full_name_user);
        this.views.txt_address_profile.setText(""+address_user);
        this.views.txt_telephone_profile.setText(""+telephone_user);
        this.views.txt_email_profile.setText(""+email_user);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == views.jLabelProducts){
            views.jPanelProduct.setBackground(new Color(148,211,196));
        }else if(e.getSource() == views.jLabelPurchases){
            views.jPanelPurchases.setBackground(new Color(148,211,196));
        }else if(e.getSource() == views.jLabelSales){
            views.jPanelSales.setBackground(new Color(148,211,196));
        }else if(e.getSource() == views.jLabelCustomers){
            views.jPanelCustomers.setBackground(new Color(148,211,196));
        }else if(e.getSource() == views.jLabelEmployees){
            views.jPanelEmployees.setBackground(new Color(148,211,196));
        }else if(e.getSource() == views.jLabelSuppliers){
            views.jPanelSuppliers.setBackground(new Color(148,211,196));
        }else if(e.getSource() == views.jLabelCategories){
            views.jPanelCategories.setBackground(new Color(148,211,196));
        }else if(e.getSource() == views.jLabelReports){
            views.jPanelReports.setBackground(new Color(148,211,196));
        }else if(e.getSource() == views.jLabelSettings){
            views.jPanelSettings.setBackground(new Color(148,211,196));
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == views.jLabelProducts){
            views.jPanelProduct.setBackground(new Color(255,255,255));
        }else if(e.getSource() == views.jLabelPurchases){
            views.jPanelPurchases.setBackground(new Color(255,255,255));
        }else if(e.getSource() == views.jLabelSales){
            views.jPanelSales.setBackground(new Color(255,255,255));
        }else if(e.getSource() == views.jLabelCustomers){
            views.jPanelCustomers.setBackground(new Color(255,255,255));
        }else if(e.getSource() == views.jLabelEmployees){
            views.jPanelEmployees.setBackground(new Color(255,255,255));
        }else if(e.getSource() == views.jLabelSuppliers){
            views.jPanelSuppliers.setBackground(new Color(255,255,255));
        }else if(e.getSource() == views.jLabelCategories){
            views.jPanelCategories.setBackground(new Color(255,255,255));
        }else if(e.getSource() == views.jLabelReports){
            views.jPanelReports.setBackground(new Color(255,255,255));
        }else if(e.getSource() == views.jLabelSettings){
            views.jPanelSettings.setBackground(new Color(255,255,255));
        }
    }
    
}
