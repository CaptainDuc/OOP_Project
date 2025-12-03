package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class CancelTicket extends JFrame implements ActionListener {
    
    JLabel l1, l12;
    JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9, tf10, tf11;
    JButton bt1, bt2;
    JPanel p1, p2, p3;
    Font f, f1;
    Choice c1;
    String currentUsername;

    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456";

    public CancelTicket(String username) {
        super("Hủy Vé Chuyến Bay");
        this.currentUsername = username;
        
        setSize(1100, 650); 
        setLocationRelativeTo(null); 
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        f = new Font("Arial", Font.BOLD, 28);
        f1 = new Font("Arial", Font.PLAIN, 16);
        Font f_label = new Font("Arial", Font.BOLD, 16);
        Color ACCENT_COLOR = new Color(176, 4, 21);

        c1 = new Choice();
        c1.setFont(f1);

        ConnectionClass o = null;
        ResultSet r = null;
        try {
            o = new ConnectionClass(DB_USER, DB_PASS);
            
                String q = "SELECT DISTINCT t1.tid FROM bookedflight t1 " +
               "JOIN passenger t2 ON t1.passenger_passport = t2.passport " +
               "WHERE t1.statuss = 'success' AND t2.username = ?"; 
                PreparedStatement pst = o.con.prepareStatement(q);
                pst.setString(1, currentUsername);
                r = pst.executeQuery();
            
            while (r.next()) {
                c1.add(r.getString("tid"));
            }
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải danh sách vé: " + e.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeResources(r, o);
        }

        l1 = new JLabel("HỦY VÉ MÁY BAY ĐÃ ĐẶT");
        l1.setHorizontalAlignment(JLabel.CENTER);
        l1.setForeground(ACCENT_COLOR);
        l1.setFont(f);

        p1 = new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.CENTER));
        p1.setBackground(new Color(240, 240, 240));
        p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p1.add(l1);

        p2 = new JPanel();
        p2.setLayout(new GridLayout(13, 2, 10, 15)); 
        p2.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        p2.setBackground(Color.WHITE);

        String[] labels = {"Mã Vé (Ticket ID)", "Điểm Đi (Source)", "Điểm Đến (Destination)", "Hạng Vé (Class)", 
                           "Giá (Price)", "Mã Chuyến Bay", "Tên Hãng Bay", "Ngày Đi", 
                           "Giờ Đi", "Username", "Họ Tên Khách", "Lý Do Hủy"};
                           
        JTextField[] textFields = {tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9, tf10, tf11};
        
        for (int k = 0; k < textFields.length; k++) {
            textFields[k] = new JTextField();
            textFields[k].setFont(f1);
            if (k < 10) { 
                textFields[k].setEditable(false);
                textFields[k].setBackground(new Color(245, 245, 245));
            } else {
                textFields[k].setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        }
        
        tf1 = textFields[0]; tf2 = textFields[1]; tf3 = textFields[2]; tf4 = textFields[3]; 
        tf5 = textFields[4]; tf6 = textFields[5]; tf7 = textFields[6]; tf8 = textFields[7]; 
        tf9 = textFields[8]; tf10 = textFields[9]; tf11 = textFields[10];

        p2.add(createLabel(labels[0], f_label, new Color(20, 2, 117)));
        p2.add(c1);
        
        for (int k = 1; k < 11; k++) {
            p2.add(createLabel(labels[k], f_label, new Color(20, 2, 117)));
            p2.add(textFields[k - 1]);
        }
        
        p2.add(createLabel(labels[11], f_label, new Color(20, 2, 117)));
        p2.add(tf11);

        bt1 = new JButton("HỦY VÉ");
        bt2 = new JButton("QUAY LẠI");

        bt1.setFont(f_label);
        bt2.setFont(f_label);
        bt1.setBackground(ACCENT_COLOR);
        bt2.setBackground(new Color(50, 50, 50)); 
        bt1.setForeground(Color.WHITE);
        bt2.setForeground(Color.WHITE);

        bt1.addActionListener(this);
        bt2.addActionListener(this);

        p2.add(bt1);
        p2.add(bt2);

        p3 = new JPanel();
        p3.setLayout(new BorderLayout());
        p3.setBackground(Color.WHITE);

        try {
            ImageIcon img = new ImageIcon(ClassLoader.getSystemResource(""));
            Image img1 = img.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH);
            ImageIcon ic1 = new ImageIcon(img1);
            l12 = new JLabel(ic1);
        } catch (Exception ex) {
            l12 = new JLabel("<html><div style='text-align: center;'>[Chỗ đặt Hình Ảnh]<br>Vé máy bay</div></html>", SwingConstants.CENTER);
            l12.setFont(new Font("Arial", Font.ITALIC, 14));
            l12.setPreferredSize(new Dimension(400, 500));
        }
        p3.add(l12, BorderLayout.CENTER);

        // ----------------- 5. Final Assembly -----------------
        setLayout(new BorderLayout(15, 15));
        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.CENTER);
        add(p3, BorderLayout.WEST); 
        
        // ----------------- 6. Mouse Listener cho Choice (c1) -----------------
        c1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                fillTicketDetails();
            }
        });
        
        if (c1.getItemCount() > 0) {
            fillTicketDetails();
        }
    }
    
    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }
    
    private void fillTicketDetails() {
        ConnectionClass o = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            o = new ConnectionClass(DB_USER, DB_PASS);
            String tid = c1.getSelectedItem();
            
            String q = "SELECT " +
                        "t1.fcode, t1.classname, t1.journey_date, t1.journey_time, t1.passenger_passport, " +
                        "t2.sourcee, t2.destination, t2.price, t2.fname, " +
                        "t3.username, t3.namee " +
                        "FROM bookedFlight t1 " +
                        "JOIN flight t2 ON t1.fcode = t2.fcode AND t1.classname = t2.classname AND t1.journey_date = t2.journey_date AND t1.journey_time = t2.journey_time " +
                        "JOIN passenger t3 ON t1.passenger_passport = t3.passport " +
                        "WHERE t1.tid = ?";
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
            
            else {
            tf1.setText(""); tf2.setText(""); tf3.setText(""); tf4.setText(""); tf5.setText("");
            tf6.setText(""); tf7.setText(""); tf8.setText(""); tf9.setText(""); tf10.setText("");
            }
        }catch (Exception ex) {
            ex.printStackTrace();
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
            String tid = c1.getSelectedItem();
            String reason = tf11.getText();

            if (tid == null || tid.isEmpty() || reason.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn mã vé và nhập lý do hủy.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ConnectionClass o = null;
            PreparedStatement pst1 = null;
            PreparedStatement pst2 = null;
            
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
            String passenger_passport = null; 
            ResultSet rsPassport = null;
            try {
                o = new ConnectionClass(DB_USER, DB_PASS);
                o.con.setAutoCommit(false);

                String qPassport = "SELECT fcode, classname, journey_date, journey_time, passenger_passport FROM bookedFlight WHERE tid = ?";
                PreparedStatement pstPassport = o.con.prepareStatement(qPassport);
                pstPassport.setString(1, tid);
                rsPassport = pstPassport.executeQuery();

                if (rsPassport.next()) {
                    fcode = rsPassport.getString("fcode");
                    class_name = rsPassport.getString("classname");
                    journey_date = rsPassport.getString("journey_date");
                    journey_time = rsPassport.getString("journey_time");
                    passenger_passport = rsPassport.getString("passenger_passport");
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy chi tiết vé để hủy.", "Lỗi Dữ Liệu", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                pstPassport.close();
                
                String q1 = "INSERT INTO cancelFlight (tid, fcode, classname, journey_date, journey_time, passenger_passport, reason) VALUES(?, ?, ?, ?, ?, ?, ?)";                pst1 = o.con.prepareStatement(q1);
                pst1.setString(1, tid);
                pst1.setString(2, fcode);
                pst1.setString(3, class_name);
                pst1.setString(4, journey_date);
                pst1.setString(5, journey_time);
                pst1.setString(6, passenger_passport);
                pst1.setString(7, reason);
                
                int insertCount = pst1.executeUpdate();
                
                if (insertCount == 1) {
                    String q2 = "DELETE FROM bookedFlight WHERE tid = ?";
                    pst2 = o.con.prepareStatement(q2);
                    pst2.setString(1, tid);
                    
                    int deletedRows = pst2.executeUpdate();
                    
                    if (deletedRows == 1) {
                        o.con.commit();
                        JOptionPane.showMessageDialog(null, "Đã hủy vé thành công. Vui lòng kiểm tra email để biết chi tiết hoàn tiền.", "Thành Công", JOptionPane.INFORMATION_MESSAGE);
                        
                        this.dispose(); 
                        new CancelTicket(currentUsername).setVisible(true); 
                    } else {
                        o.con.rollback();
                        JOptionPane.showMessageDialog(null, "Hủy vé thất bại: Không thể xóa thông tin đặt vé. Giao dịch đã bị hoàn tác.", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    o.con.rollback();
                    JOptionPane.showMessageDialog(null, "Hủy vé thất bại: Không thể lưu trữ chi tiết hủy vé. Giao dịch đã bị hoàn tác.", "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                try {
                    if (o != null && o.con != null) o.con.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi trong quá trình hủy (SQL): " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi không xác định trong quá trình hủy: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new CancelTicket("duc").setVisible(true);
    }
}