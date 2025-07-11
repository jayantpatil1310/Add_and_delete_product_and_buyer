/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 */
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.Vector;

public class DeleteProductForm extends JFrame {
    JTable table;
    DefaultTableModel model;

    public DeleteProductForm() {
        setTitle("Delete Products");
        setSize(600, 300);
        setLayout(null);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Category", "Price", "Quantity", "Description"});
        table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(20, 20, 540, 150);
        add(pane);

        JButton deleteBtn = new JButton("Delete Selected");
        deleteBtn.setBounds(200, 190, 150, 30);
        add(deleteBtn);

        deleteBtn.addActionListener(e -> deleteSelected());

        loadProducts();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadProducts() {
        model.setRowCount(0);
        try (Connection con = DBUtil.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM products");
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("category"));
                row.add(rs.getDouble("price"));
                row.add(rs.getInt("quantity"));
                row.add(rs.getString("description"));
                model.addRow(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?");
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection con = DBUtil.getConnection()) {
                PreparedStatement ps = con.prepareStatement("DELETE FROM products WHERE id = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Deleted!");
                loadProducts();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new DeleteProductForm();
    }
}
