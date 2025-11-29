package AMS;

import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class FlightJourneyDetails extends JFrame {

    JTable t;
    String x[] = {"Điểm Đi", "Điểm Đến", "Hạng", "Giá", "Mã Chuyến Bay", "Tên Hãng", "Ngày Đi", "Giờ Đi"}; 
    String y[][] = new String[20][8];
    int i = 0, j = 0;
    Font f;
    
    private static final String DB_USER = "sa"; 
    private static final String DB_PASS = "123456"; 

    FlightJourneyDetails(String src, String dst) {
        super("Chi Tiết Hành Trình Bay");
        setSize(1300, 450);
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        f = new Font("Arial", Font.PLAIN, 16);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("KẾT QUẢ TÌM KIẾM CHUYẾN BAY TỪ " + src.toUpperCase() + " ĐẾN " + dst.toUpperCase());
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setForeground(new Color(103, 3, 173));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(240, 240, 240));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        ConnectionClass o = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            o = new ConnectionClass(DB_USER, DB_PASS);
            
            String q = "SELECT sourcee, destination, classname, price, fcode, fname, journey_date, journey_time " +
                       "FROM flight WHERE sourcee = ? AND destination = ?";
            
            pst = o.con.prepareStatement(q);
            pst.setString(1, src);
            pst.setString(2, dst);
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                if (i >= 20) break;
                
                y[i][j++] = rs.getString("sourcee");
                y[i][j++] = rs.getString("destination");
                y[i][j++] = rs.getString("classname");
                y[i][j++] = rs.getString("price");
                y[i][j++] = rs.getString("fcode");
                y[i][j++] = rs.getString("fname");
                y[i][j++] = rs.getString("journey_date");
                y[i][j++] = rs.getString("journey_time");
                
                i++;
                j = 0;
            }
            
            t = new JTable(y, x);
            t.setFont(f);
            
            t.setRowHeight(30);
            t.getTableHeader().setFont(new Font("Arial", Font.BOLD, 17));
            t.getTableHeader().setBackground(new Color(230, 2, 10));
            t.getTableHeader().setForeground(Color.WHITE);
            t.setGridColor(new Color(200, 200, 200));
            t.setBackground(Color.WHITE);
            t.setForeground(Color.BLACK);


        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn chuyến bay: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            t = new JTable(y, x); 
        } finally {
            closeResources(rs, o, pst);
        }

        JScrollPane js = new JScrollPane(t);
        add(js, BorderLayout.CENTER);
        
        setVisible(true);
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
        new FlightJourneyDetails("HANOI", "DUBAI");
    }
}