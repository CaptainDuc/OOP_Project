package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class CancelTicket extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14;
    JButton bt1, bt2;
    JPanel p1, p2, p3;
    JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9, tf10, tf11;
    Font f, f1;
    Choice c1;
    String currentUsername;

    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456";

    public CancelTicket(String username) {
        super("Cancel airlines flight");
        this.currentUsername = username;
        setLocation(50, 10);
        setSize(1100, 650);
        f = new Font("Arial", Font.BOLD, 25);
        f1 = new Font("Arial", Font.BOLD, 18);

        c1 = new Choice();

        ConnectionClass o = null;
        ResultSet r = null;
        try {
            o = new ConnectionClass(DB_USER, DB_PASS);
            
            String q = "SELECT DISTINCT tid FROM bookedflight WHERE statuss = 'success' AND username = ?";
            PreparedStatement pst = o.con.prepareStatement(q);
            pst.setString(1, currentUsername);
            r = pst.executeQuery();
            
            while (r.next()) {
                c1.add(r.getString("tid"));
            }
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(r, o);
        }

        l1 = new JLabel("Cancel your flight ticket");
        l2 = new JLabel("Ticket ID");
        l3 = new JLabel("Source");
        l4 = new JLabel("Destination");
        l5 = new JLabel("Class");
        l6 = new JLabel("Price");
        l7 = new JLabel("Flight Code");
        l8 = new JLabel("Flight Name");
        l9 = new JLabel("Journey Date");
        l10 = new JLabel("Journey Time");
        l11 = new JLabel("Username");
        l13 = new JLabel("Name");
        l14 = new JLabel("Reason");

        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        tf4 = new JTextField();
        tf5 = new JTextField();
        tf6 = new JTextField();
        tf7 = new JTextField();
        tf8 = new JTextField();
        tf9 = new JTextField();
        tf10 = new JTextField();
        tf11 = new JTextField();

        tf1.setEditable(false);
        tf2.setEditable(false);
        tf3.setEditable(false);
        tf4.setEditable(false);
        tf5.setEditable(false);
        tf6.setEditable(false);
        tf7.setEditable(false);
        tf8.setEditable(false);
        tf9.setEditable(false);
        tf10.setEditable(false);

        bt1 = new JButton("Cancel Flight");
        bt2 = new JButton("Back");

        bt1.addActionListener(this);
        bt2.addActionListener(this);

        l1.setHorizontalAlignment(JLabel.CENTER);
        l1.setForeground(new java.awt.Color(176, 4, 21));
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
        l13.setFont(f1);
        l14.setFont(f1);

        tf1.setFont(f1);
        tf2.setFont(f1);
        tf3.setFont(f1);
        tf4.setFont(f1);
        tf5.setFont(f1);
        tf6.setFont(f1);
        tf7.setFont(f1);
        tf8.setFont(f1);
        tf9.setFont(f1);
        tf10.setFont(f1);
        tf11.setFont(f1);

        bt1.setFont(f1);
        bt2.setFont(f1);

        l2.setForeground(new java.awt.Color(20, 2, 117));
        l3.setForeground(new java.awt.Color(20, 2, 117));
        l4.setForeground(new java.awt.Color(20, 2, 117));
        l5.setForeground(new java.awt.Color(20, 2, 117));
        l6.setForeground(new java.awt.Color(20, 2, 117));
        l7.setForeground(new java.awt.Color(20, 2, 117));
        l8.setForeground(new java.awt.Color(20, 2, 117));
        l9.setForeground(new java.awt.Color(20, 2, 117));
        l10.setForeground(new java.awt.Color(20, 2, 117));
        l11.setForeground(new java.awt.Color(20, 2, 117));
        l13.setForeground(new java.awt.Color(20, 2, 117));
        l14.setForeground(new java.awt.Color(20, 2, 120));

        bt1.setBackground(new java.awt.Color(176, 4, 21));
        bt2.setBackground(Color.BLACK);

        bt1.setForeground(Color.WHITE);
        bt2.setForeground(new java.awt.Color(230, 225, 225));

        p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 1, 10, 10));
        p1.add(l1);

        p2 = new JPanel();
        p2.setLayout(new GridLayout(13, 2, 10, 10));
        p2.add(l2);
        p2.add(c1);
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
        p2.add(l13);
        p2.add(tf10);
        p2.add(l14);
        p2.add(tf11);
        p2.add(bt1);
        p2.add(bt2);

        p3 = new JPanel();
        p3.setLayout(new GridLayout(1, 1, 10, 10));

        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource(""));
        Image img1 = img.getImage().getScaledInstance(600, 350, Image.SCALE_SMOOTH);
        ImageIcon ic1 = new ImageIcon(img1);
        l12 = new JLabel(ic1);
        p3.add(l12);

        setLayout(new BorderLayout(10, 10));
        add(p1, "North");
        add(p2, "Center");
        add(p3, "West");

        c1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                ConnectionClass o = null;
                PreparedStatement pst = null;
                ResultSet rs = null;
                try {
                    o = new ConnectionClass(DB_USER, DB_PASS);
                    String tid = c1.getSelectedItem();
                    
                    String q = "SELECT * FROM bookedflight WHERE tid = ?";
                    pst = o.con.prepareStatement(q);
                    pst.setString(1, tid);
                    rs = pst.executeQuery();
                    
                    if (rs.next()) {
                        tf1.setText(rs.getString("sourcee"));
                        tf2.setText(rs.getString("destination"));
                        tf3.setText(rs.getString("classname"));
                        tf4.setText(rs.getString("price"));
                        tf5.setText(rs.getString("fcode"));
                        tf6.setText(rs.getString("fname"));
                        tf7.setText(rs.getString("journey_date"));
                        tf8.setText(rs.getString("journey_time"));
                        tf9.setText(rs.getString("username"));
                        tf10.setText(rs.getString("namee"));
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
            String tid = c1.getSelectedItem();
            String sourcee = tf1.getText();
            String destination = tf2.getText();
            String username = tf9.getText();
            String class_name = tf3.getText();
            String price = tf4.getText();
            String fcode = tf5.getText();
            String fname = tf6.getText();
            String journey_date = tf7.getText();
            String journey_time = tf8.getText();
            String namee = tf10.getText();
            String reason = tf11.getText();

            if (tid == null || tid.isEmpty() || reason.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn Ticket ID và nhập lý do hủy.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ConnectionClass o = null;
            PreparedStatement pst1 = null;
            PreparedStatement pst2 = null;
            try {
                o = new ConnectionClass(DB_USER, DB_PASS);
                
                String q1 = "INSERT INTO cancelFlight (tid, sourcee, destination, classname, price, fcode, fname, journey_date, journey_time, username, namee, reason) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pst1 = o.con.prepareStatement(q1);
                pst1.setString(1, tid);
                pst1.setString(2, sourcee);
                pst1.setString(3, destination);
                pst1.setString(4, class_name);
                pst1.setString(5, price);
                pst1.setString(6, fcode);
                pst1.setString(7, fname);
                pst1.setString(8, journey_date);
                pst1.setString(9, journey_time);
                pst1.setString(10, username);
                pst1.setString(11, namee);
                pst1.setString(12, reason);
                
                int aa = pst1.executeUpdate();
                
                if (aa == 1) {
                    String q2 = "DELETE FROM bookedFlight WHERE tid = ?";
                    pst2 = o.con.prepareStatement(q2);
                    pst2.setString(1, tid);
                    
                    int deletedRows = pst2.executeUpdate();
                    
                    if (deletedRows == 1) {
                        JOptionPane.showMessageDialog(null, "Đã hủy vé thành công!");
                        this.dispose(); 
                        new CancelTicket(currentUsername).setVisible(true); 
                    } else {
                        JOptionPane.showMessageDialog(null, "Hủy vé thất bại: Không thể xóa thông tin đặt vé.", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Hủy vé thất bại: Không thể lưu trữ chi tiết đặt vé.", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi trong quá trình hủy: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (pst1 != null) pst1.close();
                    if (pst2 != null) pst2.close();
                    if (o != null && o.con != null) o.con.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
         if (e.getSource() == bt2) {
            this.setVisible(false);
        }
    }

//    public static void main(String[] args) {
//        new CancelTicket("UserEx").setVisible(true);
//    }
}