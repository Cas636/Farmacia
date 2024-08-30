package controllers;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
