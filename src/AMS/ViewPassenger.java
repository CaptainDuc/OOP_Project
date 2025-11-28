package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ViewPassenger extends JFrame {
    JTable t;
    String x[] = {"Username", "Name", "Age", "Date of Birth", "Address", "Phone", "Email", "Nationality", "Gender", "Passport"};
    String y[][] = new String[20][10];
    int i = 0, j = 0;
    Font f;

    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456";

    ViewPassenger() {
        super("All Passenger Details");
        setSize(1200, 600);
        setLocation(50, 50);
        f = new Font("Arial", Font.BOLD, 16);

        ConnectionClass obj = null;
        ResultSet rs = null;

        try {
            obj = new ConnectionClass(DB_USER, DB_PASS);
            String g = "SELECT username as Username, namee as Name, age as Age, dob as [Date of Birth], addresss as Address, phone as Phone, email as Email, nationality as Nationality, gender as Gender, passport as Passport FROM passenger";
            rs = obj.stm.executeQuery(g);

            while (rs.next()) {
                if (i >= 20) break;

                y[i][j++] = rs.getString("Username");
                y[i][j++] = rs.getString("Name"); 
                y[i][j++] = rs.getString("Age");
                y[i][j++] = rs.getString("Date of Birth");
                y[i][j++] = rs.getString("Address");
                y[i][j++] = rs.getString("Phone");
                y[i][j++] = rs.getString("Email");
                y[i][j++] = rs.getString("Nationality");
                y[i][j++] = rs.getString("Gender");
                y[i][j++] = rs.getString("Passport");
                
                i++;
                j = 0;
            }
            t = new JTable(y, x);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn chi tiết hành khách: " + ex.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
            t = null;
        } finally {
            closeResources(rs, obj);
        }

        if (t != null) {
            t.setFont(f);
            t.setBackground(Color.BLACK);
            t.setForeground(Color.WHITE);
            t.setRowHeight(25);
            
            JScrollPane s = new JScrollPane(t);
            add(s);
        } else {
            add(new JLabel("Lỗi: Không thể tải thông tin hành khách."));
        }
        setVisible(true);
    }

    private void closeResources(ResultSet rs, ConnectionClass obj) {
        try {
            if (rs != null) rs.close();
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
//        new ViewPassenger();
//    }
}