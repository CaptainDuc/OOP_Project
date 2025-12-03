package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class FlightZone extends JFrame {
    private JTable table;
    private JTextField t;
    Choice c1;

    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456";

    FlightZone() {
        super("Thông Tin Chuyến Bay");
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        Color ACCENT_COLOR = new Color(176, 4, 21);
        Color HEADER_BG = new Color(245, 245, 245);

        // Header Panel
        JLabel flightDetails = new JLabel("THÔNG TIN CHI TIẾT CHUYẾN BAY");
        flightDetails.setHorizontalAlignment(JLabel.CENTER);
        flightDetails.setFont(new Font("Arial", Font.BOLD, 30));
        flightDetails.setForeground(ACCENT_COLOR);
        
        JPanel pHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pHeader.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        pHeader.setBackground(HEADER_BG);
        pHeader.add(flightDetails);
        add(pHeader, BorderLayout.NORTH);


        // Control Panel
        c1 = new Choice();
        c1.setFont(new Font("Arial", Font.PLAIN, 14));
        c1.setPreferredSize(new Dimension(200, 25));

        JLabel flightCodeLabel = new JLabel("Chọn Mã Chuyến Bay:");
        flightCodeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        flightCodeLabel.setForeground(new Color(15, 11, 1));
        
        JButton b = new JButton("HIỂN THỊ CHI TIẾT");
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setBackground(new Color(70, 130, 180));
        b.setForeground(Color.WHITE);

        JPanel pControl = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pControl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pControl.setBackground(Color.WHITE);
        pControl.add(flightCodeLabel);
        pControl.add(c1);
        pControl.add(b);

        // Table Setup
        table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(15, 11, 1));
        table.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1), 
            "Thông tin chuyến bay", 
            0, 
            0, 
            new Font("Arial", Font.BOLD, 15), 
            ACCENT_COLOR
        ));

        // Main Panel Assembly
        JPanel pMain = new JPanel(new BorderLayout(0, 15));
        pMain.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        pMain.setBackground(Color.WHITE);
        pMain.add(pControl, BorderLayout.NORTH);
        pMain.add(scrollPane, BorderLayout.CENTER);
        add(pMain, BorderLayout.CENTER);
        
        // Data Loading & Event Handling
        
        ConnectionClass o = null;
        PreparedStatement pst = null;
        ResultSet r = null;
        try {
            o = new ConnectionClass(DB_USER, DB_PASS);
            String s = "SELECT DISTINCT fcode FROM flight";
            
            pst = o.con.prepareStatement(s);
            r = o.stm.executeQuery(s);
            while (r.next()) {
                c1.add(r.getString("fcode"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải mã chuyến bay: " + e.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
        } finally {
            closeResources(r, o);
        }
        
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConnectionClass c = null;
                PreparedStatement pst = null;
                ResultSet r = null;
                try {
                    String code = c1.getSelectedItem();
                    if (code == null || code.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn mã chuyến bay.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    c = new ConnectionClass(DB_USER, DB_PASS);
                    
                    String s1 = "SELECT fcode AS 'Mã Chuyến', fname AS 'Tên Hãng', sourcee AS 'Nơi Đi', destination AS 'Nơi Đến', journey_date AS 'Ngày Đi', journey_time AS 'Giờ Đi', classname AS 'Hạng Vé', price AS 'Giá Vé' " +
                                 "FROM flight WHERE fcode = ?";
                    pst = c.con.prepareStatement(s1);
                    pst.setString(1, code);
                    r = pst.executeQuery();
                    
                    table.setModel(DbUtils.resultSetToTableModel(r));
                    
                    resizeColumnWidth();
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi truy vấn CSDL: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                } finally {
                    closeResources(r, c, pst);
                }
            }
        });

        if (c1.getItemCount() > 0) {
             b.doClick();
        }
    }
    
    private void resizeColumnWidth() {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(80);
            table.getColumnModel().getColumn(1).setPreferredWidth(120);
            table.getColumnModel().getColumn(2).setPreferredWidth(120);
            table.getColumnModel().getColumn(3).setPreferredWidth(120);
            table.getColumnModel().getColumn(4).setPreferredWidth(90);
            table.getColumnModel().getColumn(5).setPreferredWidth(80);
            table.getColumnModel().getColumn(6).setPreferredWidth(100);
            table.getColumnModel().getColumn(7).setPreferredWidth(100);
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
    
    public static void main(String[] args) {
        new FlightZone().setVisible(true);
    }
}