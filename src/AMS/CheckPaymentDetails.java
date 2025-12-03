package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class CheckPaymentDetails extends JFrame {
    
    JTextField textField;
    JTable table;
    JLabel sector;
    
    private static final String DB_USER = "sa"; 
    private static final String DB_PASS = "123456"; 

    public CheckPaymentDetails() {
        initialize();
    }

    private void initialize() {
        setTitle("Chi Tiết Thanh Toán");
        getContentPane().setBackground(Color.WHITE);
        setSize(960, 500);
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        setLayout(new BorderLayout(10, 10)); 

        sector = new JLabel("KIỂM TRA CHI TIẾT THANH TOÁN");
        sector.setForeground(new Color(0, 0, 139));
        sector.setFont(new Font("Arial", Font.BOLD, 30));
        sector.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(sector);
        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        contentPanel.setBackground(Color.WHITE);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        inputPanel.setBackground(Color.WHITE);
        
        JLabel Fcode = new JLabel("Nhập Username:"); 
        Fcode.setFont(new Font("Arial", Font.BOLD, 16));
        Fcode.setForeground(new Color(103, 3, 173));
        
        textField = new JTextField(15); 
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton Show = new JButton("HIỂN THỊ"); 
        Show.setFont(new Font("Arial", Font.BOLD, 14));
        Show.setBackground(new Color(230, 2, 10));
        Show.setForeground(Color.WHITE);

        inputPanel.add(Fcode);
        inputPanel.add(textField);
        inputPanel.add(Show);
        
        contentPanel.add(inputPanel, BorderLayout.NORTH);

        table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(103, 3, 173));
        table.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(contentPanel, BorderLayout.CENTER);

        Show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                String u = textField.getText().trim();
                
                if (u.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập Username.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                ConnectionClass o = null;
                PreparedStatement pst = null;
                ResultSet rs = null;
                try {
                    o = new ConnectionClass(DB_USER, DB_PASS);
                    
                    String s = "SELECT t1.tid AS 'Mã Vé', t3.price AS 'Giá', t1.journey_date AS 'Ngày Đi', t1.journey_time AS 'Giờ Đi', t2.username AS 'Username', t1.statuss AS 'Trạng Thái' " +
                                "FROM bookedFlight t1 " +
                                "JOIN passenger t2 ON t1.passenger_passport = t2.passport " +
                                "JOIN flight t3 ON t1.fcode = t3.fcode AND t1.classname = t3.classname AND t1.journey_date = t3.journey_date AND t1.journey_time = t3.journey_time " +
                                "WHERE t2.username = ? AND t1.statuss = 'success'";
                    
                    pst = o.con.prepareStatement(s);
                    pst.setString(1, u);
                    rs = pst.executeQuery();
                    
                    if (!rs.isBeforeFirst()) { 
                        JOptionPane.showMessageDialog(null, "Không tìm thấy chi tiết thanh toán thành công cho Username này.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        table.setModel(DbUtils.resultSetToTableModel(rs));
                        return;
                    }
                    
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    fixColumnWidths(); 
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi truy vấn cơ sở dữ liệu: " + e.getMessage(), "Lỗi CSDL", JOptionPane.ERROR_MESSAGE);
                } finally {
                    closeResources(rs, o, pst);
                }
            }
        });
        
        setVisible(true);
    }
    
    private void fixColumnWidths() {
        if (table.getModel().getColumnCount() >= 6) {
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
            table.getColumnModel().getColumn(1).setPreferredWidth(80);
            table.getColumnModel().getColumn(2).setPreferredWidth(120);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
            table.getColumnModel().getColumn(4).setPreferredWidth(120);
            table.getColumnModel().getColumn(5).setPreferredWidth(100);
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
            if (obj != null && obj.con != null) {
                obj.con.close(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CheckPaymentDetails();
    }
}