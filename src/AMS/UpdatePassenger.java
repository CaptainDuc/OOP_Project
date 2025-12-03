package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class UpdatePassenger extends JFrame implements ActionListener {

    private final Font F_TITLE = new Font("Arial", Font.BOLD, 28);
    private final Font F_LABEL = new Font("Arial", Font.BOLD, 16);
    private final Font F_FIELD = new Font("Arial", Font.PLAIN, 16);
    
    private final Color ACCENT_COLOR = new Color(4, 1, 54);
    private final Color BUTTON_COLOR = new Color(70, 130, 180); 
    private final Color HEADER_BG = new Color(245, 245, 245); 

    Choice c;
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11;
    JButton bt1, bt2;
    JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9;
    JPanel p1, p2;

    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456";

    UpdatePassenger() {
        super("Cập Nhật Thông Tin Hành Khách");
        
        setSize(700, 650); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        c = new Choice();
        loadUsernames();

        l1 = new JLabel("CẬP NHẬT THÔNG TIN HÀNH KHÁCH");
        l2 = new JLabel("Tên đăng nhập (Username)");
        l3 = new JLabel("Họ và Tên");
        l4 = new JLabel("Tuổi");
        l5 = new JLabel("Ngày sinh (DD-MM-YYYY)");
        l6 = new JLabel("Địa chỉ");
        l7 = new JLabel("Điện thoại");
        l8 = new JLabel("Email");
        l9 = new JLabel("Quốc tịch");
        l10 = new JLabel("Giới tính");
        l11 = new JLabel("Số Hộ chiếu");

        tf8 = new JTextField();
        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        tf4 = new JTextField();
        tf5 = new JTextField();
        tf6 = new JTextField();
        tf7 = new JTextField();
        tf9 = new JTextField();
        
        bt1 = new JButton("Cập Nhật");
        bt2 = new JButton("Quay Lại");
        
        l1.setHorizontalAlignment(JLabel.CENTER);
        bt1.addActionListener(this);
        bt2.addActionListener(this);

        l1.setFont(F_TITLE);
        l1.setForeground(ACCENT_COLOR);
        
        JLabel[] labels = {l2, l3, l4, l5, l6, l7, l8, l9, l10, l11};
        for (JLabel l : labels) {
            l.setFont(F_LABEL);
        }
        
        JTextField[] fields = {tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9};
        for (JTextField tf : fields) {
            tf.setFont(F_FIELD);
        }
        c.setFont(F_FIELD);
        
        bt1.setFont(F_LABEL);
        bt2.setFont(F_LABEL);
        bt1.setBackground(BUTTON_COLOR);
        bt1.setForeground(Color.WHITE);
        bt2.setBackground(Color.GRAY);
        bt2.setForeground(Color.WHITE);
        
        p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.CENTER));
        p1.setBackground(HEADER_BG);
        p1.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        p1.add(l1);
        
        p2 = new JPanel();
        p2.setLayout(new GridLayout(11, 2, 15, 15));
        p2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        p2.setBackground(Color.WHITE);

        p2.add(l2); p2.add(c);
        p2.add(l3); p2.add(tf1);
        p2.add(l4); p2.add(tf2);
        p2.add(l5); p2.add(tf3);
        p2.add(l6); p2.add(tf4);
        p2.add(l7); p2.add(tf5);
        p2.add(l8); p2.add(tf6);
        p2.add(l9); p2.add(tf7);
        p2.add(l10); p2.add(tf8);
        p2.add(l11); p2.add(tf9);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(bt1);
        buttonPanel.add(bt2);
        
        p2.add(new JLabel("")); 
        p2.add(buttonPanel); 
        
        JPanel pCenterContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pCenterContainer.setBackground(Color.WHITE);
        pCenterContainer.add(p2);


        setLayout(new BorderLayout(10, 10));
        add(p1, BorderLayout.NORTH);
        add(pCenterContainer, BorderLayout.CENTER);
        
        c.addItemListener(new ItemListener() {
             @Override
             public void itemStateChanged(ItemEvent e) {
                 if (e.getStateChange() == ItemEvent.SELECTED) {
                     loadPassengerDetails(c.getSelectedItem());
                 }
             }
         });
        
        if (c.getItemCount() > 0) {
            loadPassengerDetails(c.getItem(0));
        }
    }
    
    private void loadUsernames() {
        ConnectionClass o = null;
        PreparedStatement pst = null;
        ResultSet r = null;
        try {
            o = new ConnectionClass(DB_USER, DB_PASS);
            String q = "SELECT username FROM passenger";
            
            pst = o.con.prepareStatement(q);
            r = o.stm.executeQuery(q);
            while (r.next()) {
                c.add(r.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách người dùng: " + e.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeResources(r, o);
        }
    }
    
    private void loadPassengerDetails(String username) {
        ConnectionClass o = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            o = new ConnectionClass(DB_USER, DB_PASS);

            String q = "SELECT namee, age, dob, addresss, phone, email, nationality, gender, passport FROM passenger WHERE username = ?";
            pst = o.con.prepareStatement(q);
            pst.setString(1, username);
            rs = pst.executeQuery();

            if (rs.next()) {
                tf1.setText(rs.getString("namee"));
                tf2.setText(rs.getString("age"));
                tf3.setText(rs.getString("dob"));
                tf4.setText(rs.getString("addresss"));
                tf5.setText(rs.getString("phone"));
                tf6.setText(rs.getString("email"));
                tf7.setText(rs.getString("nationality"));
                tf8.setText(rs.getString("gender"));
                tf9.setText(rs.getString("passport"));
            } else {
                tf1.setText(""); tf2.setText(""); tf3.setText(""); tf4.setText(""); tf5.setText("");
                tf6.setText(""); tf7.setText(""); tf8.setText(""); tf9.setText("");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải chi tiết hành khách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeResources(rs, o, pst);
        }
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
            
            if (username == null || username.isEmpty() || name.isEmpty() || age.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn Tên đăng nhập và điền đầy đủ các thông tin bắt buộc (Họ tên, Tuổi, Điện thoại).", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
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

    public static void main(String[] args) {
        new UpdatePassenger().setVisible(true);
    }
}