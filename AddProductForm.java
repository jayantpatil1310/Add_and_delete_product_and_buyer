/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 */
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.sql.*;

public class AddProductForm extends JFrame {
    JTextField name, category, price, quantity, description;
    JButton addButton;

    public AddProductForm() {
        setTitle("Add Product");
        setSize(400, 300);
        setLayout(null);

        name = new JTextField(); name.setBounds(150, 20, 200, 25); add(name);
        category = new JTextField(); category.setBounds(150, 50, 200, 25); add(category);
        price = new JTextField(); price.setBounds(150, 80, 200, 25); add(price);
        quantity = new JTextField(); quantity.setBounds(150, 110, 200, 25); add(quantity);
        description = new JTextField(); description.setBounds(150, 140, 200, 25); add(description);

        addButton = new JButton("Add Product");
        addButton.setBounds(150, 180, 150, 30); add(addButton);

        JLabel[] labels = {
            new JLabel("Name"), new JLabel("Category"),
            new JLabel("Price"), new JLabel("Quantity"), new JLabel("Description")
        };
        int y = 20;
        for (JLabel l : labels) {
            l.setBounds(50, y, 100, 25);
            add(l); y += 30;
        }

        addButton.addActionListener((ActionEvent e) -> {
            try (Connection con = DBUtil.getConnection()) {
                String sql = "INSERT INTO products (name, category, price, quantity, description) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, name.getText());
                ps.setString(2, category.getText());
                ps.setDouble(3, Double.parseDouble(price.getText()));
                ps.setInt(4, Integer.parseInt(quantity.getText()));
                ps.setString(5, description.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(AddProductForm.this, "Product added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(AddProductForm.this, "Error: " + ex.getMessage());
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

public static void main(String[] args) {
    new AddProductForm();
    }
}
