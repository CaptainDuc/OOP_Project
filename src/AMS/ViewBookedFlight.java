package AMS;

import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class ViewBookedFlight extends JFrame {
    JTable t;
    String x[] = {"Ticket ID", "Source", "Destination", "Class", "Price", "Flight Code", "Flight Name", "Journey Date", "Journey Time", "Username", "Name", "Status"};
    String y[][] = new String[20][12];
    int i = 0, j = 0;
    Font f;

    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456";

    ViewBookedFlight(String username) {
        super("Flight Journey Details");
        setSize(1300, 400);
        setLocation(0, 10);
        f = new Font("Arial", Font.BOLD, 17);

        ConnectionClass obj = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            obj = new ConnectionClass(DB_USER, DB_PASS);

            String q = "SELECT tid as [Ticket ID], sourcee as Source, destination as Destination, classname as [Class Name], price as Price, fcode as [Flight Code], fname as [Flight Name], journey_date as [Journey Date], journey_time as [Journey Time], username as [Username], namee as Name, statuss as Status "
                    + "FROM bookedFlight WHERE username = ?";
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
        
        t.setFont(f);
        t.setBackground(Color.BLACK);
        t.setForeground(Color.WHITE);
        t.setRowHeight(30);
        
        JScrollPane s = new JScrollPane(t);
        add(s);
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
        new ViewBookedFlight("UserEx");
    }
}