package bookLibrary;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class Login extends JFrame {	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField_1;
	
	private static final String myname = "root";
	private static final String mypassword = "root@123";
	private static final String url = "jdbc:mysql://localhost:3306/library";
	
	public Login() {
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 748, 472);
		//Panel
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JLabel loginLabel = new JLabel("LOGIN");
		loginLabel.setForeground(new Color(255, 153, 51));
		loginLabel.setBounds(435, 42, 239, 49);
		loginLabel.setFont(new Font("Century Schoolbook L", Font.BOLD, 28));
		loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		usernameField = new JTextField();
		usernameField.setBounds(442, 163, 224, 28);
		usernameField.setColumns(10);
		
		JButton loginButton = new JButton("login");
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				loginButton.setBackground(new Color(255, 140, 0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				loginButton.setBackground(new Color(35, 35, 51));
			}
		});
		loginButton.setFont(new Font("Dialog", Font.BOLD, 17));
		loginButton.setForeground(new Color(255, 153, 51));
		loginButton.setBackground(new Color(35, 35, 51));
		loginButton.setBounds(487, 367, 135, 32);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				char[] passwordChars = passwordField_1.getPassword();
				String password = new String(passwordChars);

				String query = "SELECT username FROM admin WHERE username = ? AND password = ?";

				try (Connection con = DriverManager.getConnection(url, myname, mypassword);
				     PreparedStatement pstmt = con.prepareStatement(query)) {
				     
				    // Set parameters for the query
				    pstmt.setString(1, username);
				    pstmt.setString(2, password);

				    try (ResultSet rst = pstmt.executeQuery()) {
				        if (rst.next()) { 
				            Home frame = new Home();
				            frame.setVisible(true);
				            dispose(); 
				        } else {
				            JOptionPane.showMessageDialog(null, "Incorrect username or password", "Error", JOptionPane.ERROR_MESSAGE);
				            usernameField.setText("");
				            passwordField_1.setText("");
				        }
				    }

				} catch (Exception e1) {
				    JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(442, 229, 224, 28);
		contentPane.setLayout(null);
		contentPane.add(loginLabel);
		contentPane.add(loginButton);
		contentPane.add(usernameField);
		contentPane.add(passwordField_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(35, 35, 51));
		panel.setBounds(0, 0, 362, 440);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon("image/photo_2024-10-22_13-48-42 (1).jpg"));
		lblNewLabel.setBounds(41, 107, 283, 226);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("username");
		lblNewLabel_1.setForeground(new Color(102, 102, 102));
		lblNewLabel_1.setBounds(444, 143, 99, 17);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("password");
		lblNewLabel_1_1.setForeground(new Color(102, 102, 102));
		lblNewLabel_1_1.setBounds(444, 210, 99, 17);
		contentPane.add(lblNewLabel_1_1);
	}
}