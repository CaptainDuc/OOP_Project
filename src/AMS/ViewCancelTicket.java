package AMS;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class ViewCancelTicket extends JFrame {
    JTable t;
    String x[] = {"Ticket ID", "Source", "Destination", "Class Name", "Price", "Flight Code", "Flight Name", "Journey Date", "Journey Time", "Username", "Name", "Reason"};
    Font f;

    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456";

    ViewCancelTicket(String username) {
        super("All Cancel Flight Details");
        setSize(1300, 400);
        setLocation(0, 10);
        f = new Font("Arial", Font.BOLD, 16);
        
        ConnectionClass obj = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            obj = new ConnectionClass(DB_USER, DB_PASS);

            String q = "SELECT tid as [Ticket ID], sourcee as Source, destination as Destination, classname as [Class Name], price as Price, fcode as [Flight Code], fname as [Flight Name], journey_date as [Journey Date], journey_time as [Journey Time], username as [Username], namee as Name, reason as Reason "
                    + "FROM cancelFlight WHERE username = ?";
            pst = obj.con.prepareStatement(q);
            pst.setString(1, username);
            rs = pst.executeQuery();

            t = new JTable();
            t.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn chi tiết chuyến bay đã hủy: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            t = new JTable(new String[0][x.length], x);
        } finally {
            closeResources(rs, obj, pst);
        }
        
        t.setFont(f);
        t.setBackground(Color.BLACK);
        t.setForeground(Color.WHITE);
        t.setRowHeight(25);

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

//    public static void main(String[] args) {
//        new ViewCancelTicket("UserEx");
//    }
}