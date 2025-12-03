package AMS;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class ViewCancelTicket extends JFrame {
    private JTable t;
    private final Font F_CONTENT = new Font("Arial", Font.PLAIN, 15);

    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456";

    private final Color ACCENT_COLOR = new Color(176, 4, 21);
    private final Color HEADER_BG = new Color(245, 245, 245);

    ViewCancelTicket(String username) {
        super("Chi Tiết Vé Máy Bay Đã Hủy - User: " + username);
        
        setSize(1400, 600);
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        
        JLabel lTitle = new JLabel("DANH SÁCH VÉ MÁY BAY ĐÃ HỦY", SwingConstants.CENTER);
        lTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lTitle.setForeground(ACCENT_COLOR);
        
        JPanel pHeader = new JPanel();
        pHeader.setBackground(HEADER_BG);
        pHeader.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        pHeader.add(lTitle);
        add(pHeader, BorderLayout.NORTH);

        
        ConnectionClass obj = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        t = new JTable();

        try {
            obj = new ConnectionClass(DB_USER, DB_PASS);

            String q = "SELECT " +
                       "  t1.tid AS 'Mã Vé', t3.sourcee AS 'Nơi Đi', t3.destination AS 'Nơi Đến', t3.classname AS 'Hạng Vé', t3.price AS 'Giá Vé', " +
                       "  t1.fcode AS 'Mã Chuyến', t3.fname AS 'Tên Hãng', t1.journey_date AS 'Ngày Đi', t1.journey_time AS 'Giờ Đi', " +
                       "  t2.username AS 'Username', t2.namee AS 'Họ Tên Khách', t1.reason AS 'Lý Do Hủy' " +
                       "FROM cancelFlight t1 " +
                       "JOIN passenger t2 ON t1.passenger_passport = t2.passport " +
                       "JOIN flight t3 ON t1.fcode = t3.fcode AND t1.journey_date = t3.journey_date AND t1.journey_time = t3.journey_time AND t1.classname = t3.classname " +
                       "WHERE t2.username = ?";
            
            pst = obj.con.prepareStatement(q);
            pst.setString(1, username);
            rs = pst.executeQuery();

            t.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn chi tiết chuyến bay đã hủy: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            t.setModel(new javax.swing.table.DefaultTableModel(new Object [][] {}, new String [] {"Mã Vé", "Nơi Đi", "Nơi Đến", "Hạng Vé", "Giá Vé", "Mã Chuyến", "Tên Hãng", "Ngày Đi", "Giờ Đi", "Username", "Họ Tên Khách", "Lý Do Hủy"}));
        } finally {
            closeResources(rs, obj, pst);
        }
        
        t.setFont(F_CONTENT);
        t.setBackground(Color.WHITE);
        t.setForeground(Color.BLACK);
        t.setRowHeight(25);

        t.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        t.getTableHeader().setBackground(new Color(4, 1, 54));
        t.getTableHeader().setForeground(Color.WHITE);
        t.getTableHeader().setReorderingAllowed(false);
        
        resizeColumnWidth(t);

        JScrollPane s = new JScrollPane(t);
        s.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
        add(s, BorderLayout.CENTER);
    }
    
    private void resizeColumnWidth(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS); 
        
        int totalColumns = table.getColumnModel().getColumnCount();
        
        int[] widths = {80, 100, 100, 100, 80, 100, 120, 100, 80, 120, 150}; 
        
        if (totalColumns == 12) { 
            for (int k = 0; k < 11; k++) { 
                table.getColumnModel().getColumn(k).setPreferredWidth(widths[k]);
            }
        }
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
        new ViewCancelTicket("duc").setVisible(true);
    }
}