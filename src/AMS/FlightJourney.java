package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class FlightJourney extends JFrame implements ActionListener {

    JFrame f;
    JLabel l1, l2, l3;
    JButton b, b2;
    Choice c1, c2;

    private static final String DB_USER = "sa"; 
    private static final String DB_PASS = "123456"; 

    FlightJourney() {
        f = new JFrame("Tìm Kiếm Chuyến Bay");
        f.setSize(500, 300);
        f.setLocationRelativeTo(null);
        
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        f.setLayout(new BorderLayout(15, 15)); 
        f.getContentPane().setBackground(Color.WHITE);

        Font F_LABEL = new Font("Arial", Font.BOLD, 18);
        Font F_CHOICE = new Font("Arial", Font.PLAIN, 16);
        Color ACCENT_COLOR = new Color(103, 3, 173);

        l1 = new JLabel("CHỌN TUYẾN BAY");
        l1.setFont(new Font("Arial", Font.BOLD, 25));
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        l1.setForeground(new Color(230, 2, 10));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        titlePanel.add(l1);
        f.add(titlePanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        inputPanel.setBackground(Color.WHITE);

        l2 = new JLabel("Điểm Đi (Source)");
        l2.setForeground(ACCENT_COLOR);
        l2.setFont(F_LABEL);
        
        l3 = new JLabel("Điểm Đến (Destination)");
        l3.setForeground(ACCENT_COLOR);
        l3.setFont(F_LABEL);

        c1 = new Choice();
        c2 = new Choice();
        c1.setFont(F_CHOICE);
        c2.setFont(F_CHOICE);

        loadSourceData();
        
        c1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    populateDestinationChoice(c1.getSelectedItem());
                }
            }
        });
        
        if (c1.getItemCount() > 0) {
            populateDestinationChoice(c1.getSelectedItem());
        }

        inputPanel.add(l2);
        inputPanel.add(c1);
        inputPanel.add(l3);
        inputPanel.add(c2);
        
        f.add(inputPanel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        b = new JButton("Tìm Kiếm");
        b.setFont(F_LABEL);
        b.setBackground(ACCENT_COLOR);
        b.setForeground(Color.WHITE);
        b.addActionListener(this);
        buttonPanel.add(b);

        b2 = new JButton("Đóng");
        b2.setFont(F_LABEL);
        b2.setBackground(Color.RED);
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        buttonPanel.add(b2);
        
        f.add(buttonPanel, BorderLayout.SOUTH);

        f.setVisible(true);
    }
    
    private void loadSourceData() {
        ConnectionClass obj = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            obj = new ConnectionClass(DB_USER, DB_PASS);
            String q = "SELECT DISTINCT sourcee FROM flight";
            pst = obj.con.prepareStatement(q);
            rs = obj.stm.executeQuery(q);
            while (rs.next()) {
                c1.add(rs.getString("sourcee"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(f, "Lỗi khi tải Điểm Đi: " + e.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeSQLResources(rs, null, obj);
        }
    }

    private void populateDestinationChoice(String selectedSource) {
        c2.removeAll(); 
        
        if (selectedSource == null || selectedSource.isEmpty() || "KHÔNG CÓ CHUYẾN BAY".equals(selectedSource)) {
            c2.add("Chọn Điểm Đi");
            return;
        }

        ConnectionClass obj = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            obj = new ConnectionClass(DB_USER, DB_PASS);
            
            String q = "SELECT DISTINCT destination FROM flight WHERE sourcee = ?";
            pst = obj.con.prepareStatement(q);
            pst.setString(1, selectedSource);
            rs = pst.executeQuery();
            
            boolean found = false;
            while (rs.next()) {
                String destination = rs.getString("destination");
                if (!destination.equals(selectedSource)) {
                    c2.add(destination);
                    found = true;
                }
            }
            
            if (!found) {
                c2.add("KHÔNG CÓ CHUYẾN BAY"); 
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSQLResources(rs, pst, obj);
        }
    }

    private void closeSQLResources(ResultSet rs, Statement stm, ConnectionClass obj) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (stm != null) stm.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { 
            if (obj != null && obj.con != null) obj.con.close(); 
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b2) {
            f.dispose(); 
        } 
        if (e.getSource() == b) {
            String src = c1.getSelectedItem();
            String dst = c2.getSelectedItem();
            
            if (src == null || dst == null || dst.equals("KHÔNG CÓ CHUYẾN BAY") || dst.equals("Chọn Điểm Đi")) {
                JOptionPane.showMessageDialog(f, "Vui lòng chọn Điểm Đi và Điểm Đến hợp lệ.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (src.equals(dst)) {
                JOptionPane.showMessageDialog(f, "Điểm Đi và Điểm Đến không thể trùng nhau.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            f.dispose();
            new FlightJourneyDetails(src, dst); 
        }
    }

    public static void main(String[] args) {
        new FlightJourney();
    }
}