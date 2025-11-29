package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddPassengerDetails extends JFrame implements ActionListener {
    
    // --- Components ---
    JFrame f;
    JButton btSave, btClose;
    
    JTextField tfUsername, tfName, tfAge, tfDOB, tfAddress, tfPhone, tfEmail, tfNationality, tfGender, tfPassport;
    
    private static final int FIELD_WIDTH = 200;
    private static final int FIELD_HEIGHT = 30;
    private static final String DATE_FORMAT = "dd-MM-yyyy";

    public AddPassengerDetails() {
        f = new JFrame("Thêm Chi Tiết Hành Khách");
        f.setLayout(new BorderLayout(10, 10));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // --- 1.Tiêu đề ---
        JLabel lbTitle = new JLabel("THÊM CHI TIẾT HÀNH KHÁCH MỚI", SwingConstants.CENTER);
        lbTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lbTitle.setForeground(new Color(0, 51, 102)); // Màu xanh đậm
        lbTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        f.add(lbTitle, BorderLayout.NORTH);
        
        // --- 2. Form Panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        formPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        tfUsername = new JTextField(15);
        tfName = new JTextField(15);
        tfAge = new JTextField(15);
        tfDOB = new JTextField(15);
        tfAddress = new JTextField(15);
        tfPhone = new JTextField(15);
        tfEmail = new JTextField(15);
        tfNationality = new JTextField(15);
        tfGender = new JTextField(15);
        tfPassport = new JTextField(15);
        
        // --- Thêm các Label và Field vào FormPanel ---
        
        // Cột 1
        addRow(formPanel, gbc, "Username (*):", tfUsername, 0, 0);
        addRow(formPanel, gbc, "Tuổi (*):", tfAge, 0, 1);
        addRow(formPanel, gbc, "Địa chỉ:", tfAddress, 0, 2);
        addRow(formPanel, gbc, "Email ID:", tfEmail, 0, 3);
        addRow(formPanel, gbc, "Giới tính:", tfGender, 0, 4);

        // Cột 2
        addRow(formPanel, gbc, "Tên (*):", tfName, 2, 0);
        addRow(formPanel, gbc, "Ngày sinh (DD-MM-YYYY):", tfDOB, 2, 1);
        addRow(formPanel, gbc, "Điện thoại (*):", tfPhone, 2, 2);
        addRow(formPanel, gbc, "Quốc tịch:", tfNationality, 2, 3);
        addRow(formPanel, gbc, "Số Hộ chiếu (*):", tfPassport, 2, 4);

        f.add(formPanel, BorderLayout.CENTER);
        
        // --- 3. Phần Chức năng ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        
        btSave = new JButton("Lưu");
        btSave.setFont(new Font("Arial", Font.BOLD, 14));
        btSave.setBackground(new Color(51, 153, 51));
        btSave.setForeground(Color.WHITE);
        btSave.setPreferredSize(new Dimension(120, 40));
        btSave.addActionListener(this);

        btClose = new JButton("Đóng");
        btClose.setFont(new Font("Arial", Font.BOLD, 14));
        btClose.setBackground(new Color(204, 51, 51));
        btClose.setForeground(Color.WHITE);
        btClose.setPreferredSize(new Dimension(120, 40));
        btClose.addActionListener(this);

        buttonPanel.add(btSave);
        buttonPanel.add(btClose);
        f.add(buttonPanel, BorderLayout.SOUTH);

        // --- Frame Settings ---
        f.setSize(800, 550);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    // Phương thức trợ giúp để thêm Label và Field vào GridBagLayout
    private void addRow(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField, int gridx, int gridy) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);
        
        gbc.gridx = gridx + 1;
        gbc.gridy = gridy;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(textField, gbc);
    }

    // --- Action Listener ---
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btSave) {
            savePassengerDetails();
        } else if (e.getSource() == btClose) {
            JOptionPane.showMessageDialog(f, "Hủy bỏ");
            f.dispose();
        }
    }
    
    // --- PHƯƠNG THỨC LƯU DỮ LIỆU ĐÃ CẬP NHẬT TÊN BIẾN ---
    private void savePassengerDetails() {
        String username = tfUsername.getText();
        String name = tfName.getText();
        String age = tfAge.getText();
        String dob = tfDOB.getText();
        String address = tfAddress.getText();
        String phone = tfPhone.getText();
        String email = tfEmail.getText();
        String nationality = tfNationality.getText();
        String gender = tfGender.getText();
        String passport = tfPassport.getText();

        if (username.isEmpty() || name.isEmpty() || age.isEmpty() || phone.isEmpty() || passport.isEmpty()) {
            JOptionPane.showMessageDialog(f, "Vui lòng điền đầy đủ các trường bắt buộc (Username, Tên, Tuổi, Điện thoại, Số Hộ chiếu).", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int ageNum = Integer.parseInt(age);
            if (ageNum <= 0) {
                 JOptionPane.showMessageDialog(f, "Tuổi phải là một số nguyên dương.", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                 return;
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(f, "Tuổi phải là một số nguyên hợp lệ.", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!dob.isEmpty()) {
            try {
                new SimpleDateFormat(DATE_FORMAT).parse(dob);
            } catch (ParseException pe) {
                JOptionPane.showMessageDialog(f, "Ngày sinh không đúng định dạng " + DATE_FORMAT + ".", "Lỗi định dạng ngày", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }


        ConnectionClass obj = null;
        PreparedStatement pst = null;
        try {
            obj = new ConnectionClass("sa", "123456"); 

            if (obj.con == null) {
                JOptionPane.showMessageDialog(f, "Lỗi kết nối CSDL.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "INSERT INTO passenger (username, namee, age, dob, addresss, phone, email, nationality, gender, passport) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pst = obj.con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, name);
            pst.setString(3, age);
            pst.setString(4, dob);
            pst.setString(5, address);
            pst.setString(6, phone);
            pst.setString(7, email);
            pst.setString(8, nationality);
            pst.setString(9, gender);
            pst.setString(10, passport);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Thêm chi tiết hành khách thành công!");
                // Xóa form sau khi lưu thành công
                clearForm();
            } else {
                JOptionPane.showMessageDialog(null, "Thêm chi tiết hành khách thất bại");
            }

        } catch (SQLIntegrityConstraintViolationException constraintEx) {
             JOptionPane.showMessageDialog(f, "Lỗi: Username hoặc Passport đã tồn tại.", "Lỗi trùng lặp", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(f, "Lỗi CSDL: " + sqlEx.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            sqlEx.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(f, "Lỗi chung: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pst != null) pst.close();
                if (obj != null && obj.con != null) obj.con.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }
    
    private void clearForm() {
        tfUsername.setText("");
        tfName.setText("");
        tfAge.setText("");
        tfDOB.setText("");
        tfAddress.setText("");
        tfPhone.setText("");
        tfEmail.setText("");
        tfNationality.setText("");
        tfGender.setText("");
        tfPassport.setText("");
    }
    public static void main(String[] args) {
        new AddPassengerDetails();
    }
}