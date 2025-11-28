package AMS;

import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class FlightJourneyDetails extends JFrame {

    JTable t;
    String x[] = {"Source", "Destination", "Class", "Price", "Flight Code", "Flight Name", "Journey Date", "Journey Time"};
    String y[][] = new String[20][8]; 
    int i = 0, j = 0;
    Font f;
    
    private static final String DB_USER = "sa"; 
    private static final String DB_PASS = "123456"; 

    FlightJourneyDetails(String src, String dst) {
        super("Flight Journey Details");
        setSize(1300, 400);
        setLocation(0, 10);
        f = new Font("Arial", Font.BOLD, 17);

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
            t.setBackground(Color.BLACK);
            t.setForeground(Color.WHITE);
            t.setRowHeight(30);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn chuyến bay: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            t = new JTable(y, x);
        } finally {
            closeResources(rs, o, pst);
        }

        JScrollPane js = new JScrollPane(t);
        add(js);
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
        new FlightJourneyDetails("HANOI", "DUBAI").setVisible(true);
    }
}