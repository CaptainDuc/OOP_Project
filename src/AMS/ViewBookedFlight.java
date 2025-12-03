package AMS;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ViewBookedFlight extends JFrame {
    JTable t;
    String x[] = {"Mã vé", "Điểm đi", "Điểm đến", "Hạng vé", "Giá vé", "Mã chuyến bay", "Tên chuyến bay", "Ngày bay", "Thời gian bay", "Username", "Họ và tên", "Trạng thái"};
    String y[][] = new String[20][12];
    int i = 0, j = 0;
    Font f;

    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456";

    private final Color ACCENT_COLOR = new Color(4, 1, 54); 
    private final Color HEADER_BG = new Color(245, 245, 245);

    ViewBookedFlight(String username) {
        super("Chi Tiết Chuyến Bay Đã Đặt - User: " + username);
        
        setSize(1350, 600); 
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
        
        f = new Font("Arial", Font.BOLD, 17);

        JLabel lTitle = new JLabel("DANH SÁCH VÉ MÁY BAY ĐÃ ĐẶT", SwingConstants.CENTER);
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

        try {
            obj = new ConnectionClass(DB_USER, DB_PASS);

            String q = "SELECT " +
                       "  t1.tid AS [Ticket ID], t3.sourcee AS Source, t3.destination AS Destination, t3.classname AS [Class Name], t3.price AS Price, " +
                       "  t1.fcode AS [Flight Code], t3.fname AS [Flight Name], t1.journey_date AS [Journey Date], t1.journey_time AS [Journey Time], " +
                       "  t2.username AS Username, t2.namee AS Name, t1.statuss AS Status " +
                       "FROM bookedFlight t1 " +
                       "JOIN passenger t2 ON t1.passenger_passport = t2.passport " +
                       "JOIN flight t3 ON t1.fcode = t3.fcode AND t1.journey_date = t3.journey_date AND t1.journey_time = t3.journey_time AND t1.classname = t3.classname " +
                       "WHERE t2.username = ?";
            
            pst = obj.con.prepareStatement(q);
            pst.setString(1, username);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                if (i >= 20) break;

                y[i][j++] = rs.getString("Ticket ID");
                y[i][j++] = rs.getString("Source");
                y[i][j++] = rs.getString("Destination");
                y[i][j++] = rs.getString("Class Name");
                y[i][j++] = rs.getString("Price");
                y[i][j++] = rs.getString("Flight Code");
                y[i][j++] = rs.getString("Flight Name");
                y[i][j++] = rs.getString("Journey Date");
                y[i][j++] = rs.getString("Journey Time");
                y[i][j++] = rs.getString("Username");
                y[i][j++] = rs.getString("Name");
                y[i][j++] = rs.getString("Status");

                i++;
                j = 0;
            }
            t = new JTable(y, x);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu chuyến bay đã đặt: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            t = new JTable(y, x);
        } finally {
            closeResources(rs, obj, pst); 
        }
        
        // ----------------------- 3. Table Styling -----------------------
        t.setFont(new Font("Arial", Font.PLAIN, 15));
        t.setBackground(Color.WHITE); 
        t.setForeground(Color.BLACK); 
        t.setRowHeight(30);
        
        // Styling Header
        t.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        t.getTableHeader().setBackground(ACCENT_COLOR); 
        t.getTableHeader().setForeground(Color.WHITE); 
        t.getTableHeader().setReorderingAllowed(false);
        
        resizeColumnWidth(t);
        
        JScrollPane s = new JScrollPane(t);
        s.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        add(s, BorderLayout.CENTER);
    }

    private void resizeColumnWidth(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS); 
        
        int totalColumns = table.getColumnModel().getColumnCount();
        
        int[] widths = {80, 100, 100, 80, 100, 80, 120, 100, 100, 120, 150}; 
        
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
        new ViewBookedFlight("duc").setVisible(true); 
    }
}