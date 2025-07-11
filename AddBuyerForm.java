/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 */
import javax.swing.*;
import java.sql.*;

public class AddBuyerForm extends JFrame {
    JTextField name, email, phone, address;
    JButton addButton;

    public AddBuyerForm() {
        setTitle("Add Buyer");
        setSize(400, 300);
        setLayout(null);

        name = new JTextField(); name.setBounds(150, 20, 200, 25); add(name);
        email = new JTextField(); email.setBounds(150, 50, 200, 25); add(email);
        phone = new JTextField(); phone.setBounds(150, 80, 200, 25); add(phone);
        address = new JTextField(); address.setBounds(150, 110, 200, 25); add(address);

        addButton = new JButton("Add Buyer");
        addButton.setBounds(150, 160, 150, 30); add(addButton);

        JLabel[] labels = {
            new JLabel("Name"), new JLabel("Email"),
            new JLabel("Phone"), new JLabel("Address")
        };
        int y = 20;
        for (JLabel l : labels) {
            l.setBounds(50, y, 100, 25);
            add(l); y += 30;
        }

        addButton.addActionListener(e -> {
            try (Connection con = DBUtil.getConnection()) {
                String sql = "INSERT INTO buyers (name, email, phone, address) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, name.getText());
                ps.setString(2, email.getText());
                ps.setString(3, phone.getText());
                ps.setString(4, address.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Buyer added!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AddBuyerForm();
    }
}
