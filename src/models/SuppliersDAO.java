package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class SuppliersDAO {
    //instanciar la conexión

    connectionMySQL cn = new connectionMySQL();
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;

    //registrar proveedor
    public boolean registerSuppliersQuery(Suppliers supplier) {
        String query = "INSERT INTO suppliers (name, description, address, telephone, email, city, created, updated)"
                + " VALUES(?,?,?,?,?,?,?,?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getAddress());
            pst.setString(4, supplier.getTelephone());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setTimestamp(8, datetime);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el proveedor: " + e);
            return false;
        }
    }

    //listar proveedor
    public List listSupplierQuery(String value) {
        List<Suppliers> list_suppliers = new ArrayList();
        String query = "SELECT * FROM suppliers";
        String query_search_supplier = "SELECT * FROM suppliers WHERE name LIKE '%" + value + "%'";
        try {
            conn = cn.getConnection();
            if (value.equalsIgnoreCase("")) {
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            } else {
                pst = conn.prepareStatement(query_search_supplier);
                rs = pst.executeQuery();
            }
            while (rs.next()) {
                Suppliers supplier = new Suppliers();
                supplier.setId(rs.getInt("id"));
                supplier.setName(rs.getString("name"));
                supplier.setDescription(rs.getString("Description"));
                supplier.setAddress(rs.getString("address"));
                supplier.setTelephone(rs.getString("telephone"));
                supplier.setEmail(rs.getString("email"));
                supplier.setCity(rs.getString("city"));
                list_suppliers.add(supplier);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list_suppliers;
    }

    //modificar proveedor
    public boolean updateSupplierQuery(Suppliers supplier) {
        String query = "UPDATE suppliers SET name = ?, description = ?, address = ?, telephone = ?, email = ?, city = ?, updated = ?"
                + "WHERE id = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getAddress());
            pst.setString(4, supplier.getTelephone());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setInt(8, supplier.getId());
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar los datos del proveedor: " + e);
            return false;
        }
    }

    //eliminar proveedor
    public boolean deleteSupplierQuery(int id) {
        String query = "DELETE FROM suppliers WHERE id = " + id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el proveedor porque tiene realción con otra tabla: " + e);
            return false;
        }

    }

}
