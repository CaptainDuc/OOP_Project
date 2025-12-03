package AMS;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ViewPassenger extends JFrame {
    JTable t;
    String x[] = {"Username", "Họ và Tên", "Tuổi", "Ngày Sinh", "Địa chỉ", "Điện thoại", "Email", "Quốc tịch", "Giới tính", "Hộ chiếu"};
    String y[][] = new String[20][10];
    int i = 0, j = 0;
    private final Font F_CONTENT = new Font("Arial", Font.PLAIN, 15);

    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456";

    private final Color ACCENT_COLOR = new Color(4, 1, 54);
    private final Color HEADER_BG = new Color(245, 245, 245);

    ViewPassenger() {
        super("Thông Tin Chi Tiết Hành Khách");
        
        setSize(1250, 650);
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JLabel lTitle = new JLabel("DANH SÁCH TẤT CẢ HÀNH KHÁCH", SwingConstants.CENTER);
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
            String g = "SELECT username as Username, namee as Name, age as Age, dob as [Date of Birth], addresss as Address, phone as Phone, email as Email, nationality as Nationality, gender as Gender, passport as Passport FROM passenger";
            
            pst = obj.con.prepareStatement(g);
            rs = pst.executeQuery();
            
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
            t = new JTable(new String[0][x.length], x);
        } finally {
            closeResources(rs, obj, pst);
        }

        if (t != null) {
            t.setFont(F_CONTENT);
            t.setBackground(Color.WHITE);
            t.setForeground(Color.BLACK);
            t.setRowHeight(30);
            
            t.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
            t.getTableHeader().setBackground(ACCENT_COLOR);
            t.getTableHeader().setForeground(Color.WHITE);
            t.getTableHeader().setReorderingAllowed(false);
            
            resizeColumnWidth(t);
            
            JScrollPane s = new JScrollPane(t);
            s.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
            add(s, BorderLayout.CENTER);
        } else {
            add(new JLabel("Lỗi: Không thể tải thông tin hành khách.", SwingConstants.CENTER));
        }
    }

    private void resizeColumnWidth(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS); 
        
        int totalColumns = table.getColumnModel().getColumnCount();
        
        int[] widths = {120, 150, 60, 100, 150, 100, 150, 100, 80, 120}; 
        
        if (totalColumns == 10) { 
            for (int k = 0; k < 10; k++) { 
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
        new ViewPassenger().setVisible(true);
    }
}