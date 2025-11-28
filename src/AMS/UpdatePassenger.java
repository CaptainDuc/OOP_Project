package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class UpdatePassenger extends JFrame implements ActionListener {

    Font f, f1;
    Choice c;
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12;
    JButton bt1, bt2;
    JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9;
    JPanel p1, p2, p3;

    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456";

    UpdatePassenger() {
        super("Update Passenger");
        setLocation(450, 10);
        setSize(740, 600);

        f = new Font("Arial", Font.BOLD, 25);
        f1 = new Font("Arial", Font.BOLD, 18);

        c = new Choice();

        ConnectionClass o = null;
        ResultSet r = null;
        try {
            o = new ConnectionClass(DB_USER, DB_PASS);
            String q = "SELECT username FROM passenger";
            r = o.stm.executeQuery(q);
            while (r.next()) {
                c.add(r.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(r, o);
        }

        l1 = new JLabel("Update Passenger Details");
        l2 = new JLabel("Username");
        l3 = new JLabel("Name");
        l4 = new JLabel("Age");
        l5 = new JLabel("Date of birth (DD-MM-YYYY)");
        l6 = new JLabel("Address");
        l7 = new JLabel("Phone");
        l8 = new JLabel("Email");
        l9 = new JLabel("Nationality");
        l10 = new JLabel("Gender");
        l11 = new JLabel("Passport");

        tf8 = new JTextField();
        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        tf4 = new JTextField();
        tf5 = new JTextField();
        tf6 = new JTextField();
        tf7 = new JTextField();
        tf9 = new JTextField();
        
        bt1 = new JButton("Update");
        bt2 = new JButton("Back");
        
        l1.setHorizontalAlignment(JLabel.CENTER);
        bt1.addActionListener(this);
        bt2.addActionListener(this);

        l1.setFont(f);
        l2.setFont(f1);
        l3.setFont(f1);
        l4.setFont(f1);
        l5.setFont(f1);
        l6.setFont(f1);
        l7.setFont(f1);
        l8.setFont(f1);
        l9.setFont(f1);
        l10.setFont(f1);
        l11.setFont(f1);
        c.setFont(f1);
        tf1.setFont(f1);
        tf2.setFont(f1);
        tf3.setFont(f1);
        tf4.setFont(f1);
        tf5.setFont(f1);
        tf6.setFont(f1);
        tf7.setFont(f1);
        tf8.setFont(f1);
        tf9.setFont(f1);
        bt1.setFont(f1);
        bt2.setFont(f1);

        p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 1, 10, 10));
        p1.add(l1);
        
        p2 = new JPanel();
        p2.setLayout(new GridLayout(11, 2, 10, 10));
        p2.add(l2);
        p2.add(c);
        p2.add(l3);
        p2.add(tf1);
        p2.add(l4);
        p2.add(tf2);
        p2.add(l5);
        p2.add(tf3);
        p2.add(l6);
        p2.add(tf4);
        p2.add(l7);
        p2.add(tf5);
        p2.add(l8);
        p2.add(tf6);
        p2.add(l9);
        p2.add(tf7);
        p2.add(l10);
        p2.add(tf8);
        p2.add(l11);
        p2.add(tf9);
        p2.add(bt1);
        p2.add(bt2);

        p3 = new JPanel();
        p3.setLayout(new GridLayout(1, 1, 10, 10));

        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource(""));
        Image img1 = img.getImage().getScaledInstance(300, 500, Image.SCALE_SMOOTH);
        ImageIcon ic1 = new ImageIcon(img1);
        l12 = new JLabel(ic1);
        p3.add(l12);

        setLayout(new BorderLayout(10, 10));
        add(p1, "North");
        add(p2, "Center");
        add(p3, "West");
        
        c.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                ConnectionClass o = null;
                PreparedStatement pst = null;
                ResultSet rs = null;
                try {
                    o = new ConnectionClass(DB_USER, DB_PASS);
                    String username = c.getSelectedItem();

                    String q = "SELECT * FROM passenger WHERE username = ?";
                    pst = o.con.prepareStatement(q);
                    pst.setString(1, username);
                    rs = pst.executeQuery();

                    if (rs.next()) {
                        tf1.setText(rs.getString("name"));
                        tf2.setText(rs.getString("age"));
                        tf3.setText(rs.getString("dob"));
                        tf4.setText(rs.getString("address"));
                        tf5.setText(rs.getString("phone"));
                        tf6.setText(rs.getString("email"));
                        tf7.setText(rs.getString("nationality"));
                        tf8.setText(rs.getString("gender"));
                        tf9.setText(rs.getString("passport"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    closeResources(rs, o, pst);
                }
            }
        });
    }

    private void closeResources(ResultSet rs, ConnectionClass obj) {
        closeResources(rs, obj, null);
    }

    private void closeResources(ResultSet rs, ConnectionClass obj, PreparedStatement pst) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pst != null) pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (obj != null && obj.con != null) obj.con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt1) {

            String username = c.getSelectedItem();
            String name = tf1.getText();
            String age = tf2.getText();
            String dob = tf3.getText();
            String address = tf4.getText();
            String phone = tf5.getText();
            String email = tf6.getText();
            String nationality = tf7.getText();
            String gender = tf8.getText();
            String passport = tf9.getText();
            
            if (name.isEmpty() || age.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ các thông tin bắt buộc.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ConnectionClass obj = null;
            PreparedStatement pst = null;
            try {
                obj = new ConnectionClass(DB_USER, DB_PASS);
                
                String q1 = "UPDATE passenger SET namee = ?, age = ?, dob = ?, addresss = ?, phone = ?, email = ?, nationality = ?, gender = ?, passport = ? WHERE username = ?";
                
                pst = obj.con.prepareStatement(q1);
                pst.setString(1, name);
                pst.setString(2, age);
                pst.setString(3, dob);
                pst.setString(4, address);
                pst.setString(5, phone);
                pst.setString(6, email);
                pst.setString(7, nationality);
                pst.setString(8, gender);
                pst.setString(9, passport);
                pst.setString(10, username);

                int a = pst.executeUpdate();

                if (a == 1) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại. Vui lòng kiểm tra lại thông tin.", "Lỗi Cập nhật", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } finally {
                closeResources(null, obj, pst);
            }
        }
        if (e.getSource() == bt2) {
            int result = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                this.setVisible(false);
            }
        }
    }

//    public static void main(String[] args) {
//        new UpdatePassenger().setVisible(true);
//    }
}