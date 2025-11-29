package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class BookFlight extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l13;
    JButton bt1, bt2;
    JPanel p1, p2, p3;
    JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7; 
    Font f, f1;
    Choice c1, c3, c6; 
    Choice c2; // Username
    private String currentUsername;
    
    private static final String DB_USER = "sa"; 
    private static final String DB_PASS = "123456"; 

    public BookFlight(String username) {
        super("Book Airlines VietNam Flight");
        this.currentUsername = username;
        setLocation(50, 10);
        setSize(1100, 650);
        f = new Font("Arial", Font.BOLD, 25);
        f1 = new Font("Arial", Font.BOLD, 14);
        
        c1 = new Choice();
        c3 = new Choice();
        c6 = new Choice();
        c2 = new Choice();

        tf6 = new JTextField();
        tf7 = new JTextField();

        ConnectionClass obj = null;
        ResultSet r = null;
        try {
            obj = new ConnectionClass(DB_USER, DB_PASS);
            String g = "select distinct sourcee from flight";
            r = obj.stm.executeQuery(g);
            while (r.next()) {
                c1.add(r.getString("sourcee"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeResources(r, obj);
        }

        c2.add(this.currentUsername);
        
        obj = null;
        r = null;
        try {
            obj = new ConnectionClass(DB_USER, DB_PASS);
            
            String q = "SELECT namee FROM passenger WHERE username=?";
            PreparedStatement pst = obj.con.prepareStatement(q);
            pst.setString(1, this.currentUsername);
            r = pst.executeQuery();
            
            if (r.next()) {
                tf5 = new JTextField(r.getString("namee"));
            } else {
                tf5 = new JTextField("Name Not Found");
            }
            pst.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            tf5 = new JTextField("Error Loading Name");
        } finally {
            closeResources(r, obj);
        }
        
        l1 = new JLabel("Book Airlines VietNam Flight");
        l2 = new JLabel("Ticket Id");
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

        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        tf4 = new JTextField();
        
        tf1.setEditable(false);
        tf2.setEditable(false);
        tf3.setEditable(false);
        tf4.setEditable(false);
        tf5.setEditable(false);
        tf6.setEditable(false);
        tf7.setEditable(false);
        
        Random ra = new Random();
        tf1.setText("" + Math.abs(ra.nextInt() % 10000000));
        tf1.setForeground(Color.RED);
        
        bt1 = new JButton("Book Flight");
        bt2 = new JButton("Back");
        
        bt1.addActionListener(this);
        bt2.addActionListener(this);
        
        l1.setHorizontalAlignment(JLabel.CENTER);
        l1.setForeground(new java.awt.Color(232, 2, 125));
        
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
        c1.setFont(f1);
        c2.setFont(f1);
        c3.setFont(f1);
        c6.setFont(f1);
        tf1.setFont(f1);
        tf2.setFont(f1);
        tf3.setFont(f1);
        tf4.setFont(f1);
        tf5.setFont(f1);
        tf6.setFont(f1);
        tf7.setFont(f1);
        bt1.setFont(f1);
        bt2.setFont(f1);
        l2.setForeground(new java.awt.Color(103, 3, 173));
        l3.setForeground(new java.awt.Color(103, 3, 173));
        l4.setForeground(new java.awt.Color(103, 3, 173));
        l5.setForeground(new java.awt.Color(103, 3, 173));
        l6.setForeground(new java.awt.Color(103, 3, 173));
        l7.setForeground(new java.awt.Color(103, 3, 173));
        l8.setForeground(new java.awt.Color(103, 3, 173));
        l9.setForeground(new java.awt.Color(103, 3, 173));
        l10.setForeground(new java.awt.Color(103, 3, 173));
        l11.setForeground(new java.awt.Color(103, 3, 173));
        l13.setForeground(new java.awt.Color(103, 3, 173));
        
        bt1.setBackground(new java.awt.Color(230, 2, 10));
        bt2.setForeground(Color.white);
        bt1.setForeground(Color.white);
        bt2.setBackground(new java.awt.Color(230, 2, 10));

        p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 1, 10, 10));
        p1.add(l1);
        
        p2 = new JPanel();
        p2.setLayout(new GridLayout(12, 2, 10, 10));
        
        p2.add(l2); p2.add(tf1);
        p2.add(l3); p2.add(c1);
        p2.add(l4); p2.add(c6);
        p2.add(l5); p2.add(c3);
        p2.add(l6); p2.add(tf6);
        p2.add(l7); p2.add(tf7);
        p2.add(l8); p2.add(tf2);
        p2.add(l9); p2.add(tf3);
        p2.add(l10); p2.add(tf4);
        p2.add(l11); p2.add(c2);
        p2.add(l13); p2.add(tf5);
        p2.add(bt1);
        p2.add(bt2);
        
        p3 = new JPanel();
        p3.setLayout(new GridLayout(1, 1, 10, 10));
        
        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("")); 
        Image img1 = img.getImage().getScaledInstance(620, 470, Image.SCALE_SMOOTH);
        ImageIcon ic1 = new ImageIcon(img1);
        JLabel l12 = new JLabel(ic1);
        p3.add(l12);
        
        setLayout(new BorderLayout(10, 10));
        add(p1, "North");
        add(p2, "Center");
        add(p3, "West");

        // --- Mouse Listener Logic (Lọc tuần tự) ---

        // 1. Chọn Source (c1) -> Load Destination (c6)
        c1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                c6.removeAll();
                c3.removeAll();
                tf6.setText("");
                tf7.setText("");
                tf2.setText("");
                tf3.setText("");
                tf4.setText("");
                
                ConnectionClass o = null;
                PreparedStatement pst = null;
                ResultSet rss = null;
                try {
                    o = new ConnectionClass(DB_USER, DB_PASS);
                    String source = c1.getSelectedItem();
                    if (source == null || source.isEmpty()) return;
                    
                    String q1 = "SELECT DISTINCT destination FROM flight WHERE sourcee=?";
                    pst = o.con.prepareStatement(q1);
                    pst.setString(1, source);
                    rss = pst.executeQuery();
                    while (rss.next()) {
                        c6.add(rss.getString("Destination"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    closeResources(rss, o, pst);
                }
            }
        });
        
        // 2. Chọn Destination (c6) -> Load Class (c3)
        c6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                c3.removeAll();
                tf6.setText("");
                tf7.setText("");
                tf2.setText("");
                tf3.setText("");
                tf4.setText("");
                
                ConnectionClass o = null;
                PreparedStatement pst = null;
                ResultSet rss = null;
                try {
                    o = new ConnectionClass(DB_USER, DB_PASS);
                    String source = c1.getSelectedItem();
                    String destination = c6.getSelectedItem();
                    if (source == null || destination == null || source.isEmpty() || destination.isEmpty()) return;
                    
                    String q1 = "SELECT DISTINCT classname FROM flight WHERE sourcee=? AND destination=?";
                    pst = o.con.prepareStatement(q1);
                    pst.setString(1, source);
                    pst.setString(2, destination);
                    rss = pst.executeQuery();
                    while (rss.next()) {
                        c3.add(rss.getString("classname"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    closeResources(rss, o, pst);
                }
            }
        });
        
        // 3. Chọn Class (c3) -> TỰ ĐỘNG ĐIỀN Price (tf6), Flight Code (tf7) và Details (tf2, tf3, tf4)
        c3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                tf6.setText("");
                tf7.setText("");
                tf2.setText("");
                tf3.setText("");
                tf4.setText("");
                
                ConnectionClass o = null;
                PreparedStatement pst = null;
                ResultSet rss = null;
                
                String source = c1.getSelectedItem();
                String destination = c6.getSelectedItem();
                String classname = c3.getSelectedItem();
                
                if (source == null || destination == null || classname == null || source.isEmpty() || destination.isEmpty() || classname.isEmpty()) {
                    return;
                }

                try {
                    o = new ConnectionClass(DB_USER, DB_PASS);

                    String q1 = "SELECT TOP 1 price, fcode, fname, journey_date, journey_time FROM flight WHERE sourcee=? AND destination=? AND classname=?";
                    
                    pst = o.con.prepareStatement(q1);
                    pst.setString(1, source);
                    pst.setString(2, destination);
                    pst.setString(3, classname);
                    rss = pst.executeQuery();
                    
                    if (rss.next()) {
                        // Tự động điền các trường output
                        tf6.setText(rss.getString("price")); 
                        tf7.setText(rss.getString("fcode")); 
                        tf2.setText(rss.getString("fname"));
                        tf3.setText(rss.getString("journey_date"));
                        tf4.setText(rss.getString("journey_time"));
                    } else {
                        tf6.setText("N/A");
                        tf7.setText("N/A");
                        tf2.setText("Không tìm thấy");
                        tf3.setText("N/A");
                        tf4.setText("N/A");
                        JOptionPane.showMessageDialog(null, "Không tìm thấy chuyến bay nào khớp với các lựa chọn này.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    tf6.setText("Lỗi");
                    tf7.setText("Lỗi");
                    tf2.setText("Lỗi");
                    tf3.setText("Lỗi");
                    tf4.setText("Lỗi");
                } finally {
                    closeResources(rss, o, pst);
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
            String tid = tf1.getText();
            String source = c1.getSelectedItem();
            String destination = c6.getSelectedItem();
            String classname = c3.getSelectedItem();
            
            String price = tf6.getText(); 
            String fcode = tf7.getText(); 
            
            String fname = tf2.getText();
            String journey_date = tf3.getText();
            String journey_time = tf4.getText();
            String username = c2.getSelectedItem();
            String name = tf5.getText();
            String status = "Success";

            if (source == null || destination == null || classname == null || 
                source.isEmpty() || destination.isEmpty() || classname.isEmpty() || 
                price.isEmpty() || price.equals("N/A") || fcode.isEmpty() || fcode.equals("N/A") ||
                fname.isEmpty() || fname.equals("Không tìm thấy") || journey_date.isEmpty() || journey_time.isEmpty()) 
            {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn đầy đủ chi tiết chuyến bay hợp lệ trước khi đặt.");
                return;
            }

            ConnectionClass obj = null;
            PreparedStatement pst = null;
            try {
                obj = new ConnectionClass(DB_USER, DB_PASS);
                
                String q1 = "INSERT INTO bookedFlight (tid, sourcee, destination, classname, price, fcode, fname, journey_date, journey_time, username, namee, statuss) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                pst = obj.con.prepareStatement(q1);
                pst.setString(1, tid);
                pst.setString(2, source);
                pst.setString(3, destination);
                pst.setString(4, classname);
                pst.setString(5, price);
                pst.setString(6, fcode);
                pst.setString(7, fname);
                pst.setString(8, journey_date);
                pst.setString(9, journey_time);
                pst.setString(10, username);
                pst.setString(11, name);
                pst.setString(12, status);

                int a = pst.executeUpdate();
                
                if (a == 1) {
                    JOptionPane.showMessageDialog(null, "Chuyến bay của bạn đã được đặt thành công!");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Đặt chuyến bay thất bại.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi đặt chuyến bay: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            } finally {
                closeResources(null, obj, pst);
            }
        }
        
        if (e.getSource() == bt2) {

            this.dispose(); 
        }
    }

    public static void main(String[] args) {
        new BookFlight("UserEx").setVisible(true);
    }
}