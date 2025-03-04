package bookLibrary;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Member extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField firstf;
	private JTextField lastf;
	private JTextField emailf;
	private JTextField phonef;
	private JTextField birthf;
	private JTextField textField_6;
	private int selectedId;
	private JTextField searchf;
	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public Member() {
		setTitle("Member");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 1100, 750);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				firstf.setText("");
				lastf.setText("");
				emailf.setText("");
				phonef.setText("");
				birthf.setText("");
				selectedId = -1;
			}
		});
		contentPane.setBackground(new Color(35, 35, 51));
		contentPane.setForeground(new Color(35, 35, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Retrieve data from the selected row
                	String idString = table.getValueAt(selectedRow, 0).toString();
                	selectedId = Integer.parseInt(idString);
                	firstf.setText(table.getValueAt(selectedRow, 1).toString());
                	lastf.setText(table.getValueAt(selectedRow, 2).toString());
                	emailf.setText(table.getValueAt(selectedRow, 3).toString());
                	phonef.setText(table.getValueAt(selectedRow, 4).toString());
                	birthf.setText(table.getValueAt(selectedRow, 5).toString());
			    } 
			}
		});
		table.setBackground(SystemColor.window);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"member id", "first name", "last name", "email", "phone", "date of birth"
			}
		));
		
		JLabel lblNewLabel_8 = new JLabel("Count");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_8.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_8.setForeground(new Color(255, 135, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(35, 35, 51));
		
		JLabel lblNewLabel = new JLabel("");
		
		JButton displaybtn = new JButton("Dispaly ");
		displaybtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection con = DriverManager.getConnection(AID.url, AID.myname, AID.mypassword)) {
					DefaultTableModel tblmode = (DefaultTableModel)table.getModel();
					tblmode.setRowCount(0);
					String query;
			
				    if (!searchf.getText().isEmpty()) {
				    	
				    	query = "SELECT * FROM member WHERE LOWER(first_name) LIKE LOWER(?) OR LOWER(last_name) LIKE LOWER(?)";;
				        
				    	String s = searchf.getText();
				        
				        try (PreparedStatement pstmt = con.prepareStatement(query)) {
			    
				            pstmt.setString(1, "%" +s+ "%");
				            pstmt.setString(2, "%" +s+ "%");
				            
				            try (ResultSet rst = pstmt.executeQuery()) {
				            	
				                while (rst.next()) {
				                	String id = String.valueOf(rst.getInt("id"));
				                	String first = rst.getString("first_name");
									String last = rst.getString("last_name");
									String email = rst.getString("email");
									String phone = rst.getString("phone");
									String birth = AID.dateToString(rst.getDate("date_of_birth"));
									
									String[] row = {id, first,last,email,phone,birth};

				                    tblmode.addRow(row);
				                }
				            }
				        }

				    }//if
				    else {
				        query = "SELECT * FROM member";
				        
				        try (PreparedStatement pstmt = con.prepareStatement(query);
				             ResultSet rst = pstmt.executeQuery()) {       
				            while (rst.next()) {
				            	String id = String.valueOf(rst.getInt("id"));
								String first = rst.getString("first_name");
								String last = rst.getString("last_name");
								String email = rst.getString("email");
								String phone = rst.getString("phone");
								String birth = AID.dateToString(rst.getDate("date_of_birth"));
								
								String[] row = {id,first,last,email,phone,birth};
								
								tblmode.addRow(row);
				            }
				        }
				    }//else
				   
				} //last try
			catch (Exception e2) {
				    JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			finally
			{
				searchf.setText("");
			}
				
			}
		});
		displaybtn.setHorizontalAlignment(SwingConstants.LEADING);
		displaybtn.setForeground(new Color(0, 0, 51));
		displaybtn.setFont(new Font("Bitstream Charter", Font.BOLD, 14));
		displaybtn.setBackground(new Color(255, 140, 0));
		
		JLabel lblNewLabel_1 = new JLabel("H");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Home frame = new Home();
				frame.setVisible(true);
				dispose();
			}
		});
		lblNewLabel_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("C059", Font.BOLD, 17));
		
		searchf = new JTextField();
		searchf.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("*name");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(11)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
								.addGap(60)
								.addComponent(lblNewLabel)
								.addGap(37)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
										.addGap(81))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(displaybtn, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(searchf, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
										.addGap(448))))
							.addComponent(lblNewLabel_8, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel_1, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(230)
					.addComponent(lblNewLabel)
					.addGap(392)
					.addComponent(lblNewLabel_8, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(62)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(displaybtn, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(searchf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_1_1)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 485, GroupLayout.PREFERRED_SIZE)
					.addGap(106)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 664, GroupLayout.PREFERRED_SIZE))
		);
		
		JLabel lblNewLabel_3_1 = new JLabel("First name");
		lblNewLabel_3_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_1.setFont(new Font("C059", Font.BOLD, 16));
		
		firstf = new JTextField();
		firstf.setColumns(10);
		
		JLabel lblNewLabel_3_2 = new JLabel("Last name");
		lblNewLabel_3_2.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_2.setFont(new Font("C059", Font.BOLD, 16));
		
		lastf = new JTextField();
		lastf.setColumns(10);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Email");
		lblNewLabel_3_1_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_1_1.setFont(new Font("C059", Font.BOLD, 16));
		
		emailf = new JTextField();
		emailf.setColumns(10);
		
		JLabel lblNewLabel_3_1_2 = new JLabel("Phone");
		lblNewLabel_3_1_2.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_1_2.setFont(new Font("C059", Font.BOLD, 16));
		
		phonef = new JTextField();
		phonef.setColumns(10);
		
		JLabel lblNewLabel_3_2_1 = new JLabel("Date of birth");
		lblNewLabel_3_2_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_2_1.setFont(new Font("C059", Font.BOLD, 16));
		
		birthf = new JTextField();
		birthf.setColumns(10);
		
		JLabel lblNewLabel_3_2_1_1 = new JLabel("Book ID");
		lblNewLabel_3_2_1_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_2_1_1.setFont(new Font("C059", Font.BOLD, 16));
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		
		JButton addbtn = new JButton("ADD");
		addbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try(Connection con = DriverManager.getConnection(AID.url, AID.myname, AID.mypassword)) {
					String query;
					int i;
					String first = firstf.getText();
					String last = lastf.getText();
					String email = emailf.getText();
					String phone = phonef.getText();
					Date birth = AID.stringToDate(birthf.getText());
					
					query = "INSERT INTO member ( first_name, last_name, email, phone, date_of_birth) VALUES (?,?,?,?,?)";
					try (PreparedStatement pstmt = con.prepareStatement(query)){
						pstmt.setString(1, first);
			            pstmt.setString(2, last);
			            pstmt.setString(3, email);
			            pstmt.setString(4, phone);
			            pstmt.setDate(5, birth);
			            
						i = pstmt.executeUpdate();
			           	if(i > 0) {	
			           		JOptionPane.showMessageDialog(null, "Member added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
			           	}
			           	else
						{
							JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					
				}
				catch(Exception e2){
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				finally
				{
					firstf.setText("");
					lastf.setText("");
					emailf.setText("");
					phonef.setText("");
					birthf.setText("");	
				}
			}
		});
		addbtn.setFont(new Font("Bitstream Charter", Font.BOLD, 14));
		addbtn.setForeground(new Color(0, 0, 51));
		addbtn.setBackground(new Color(255, 140, 0));
		
		JButton updatebtn = new JButton("UPDATE");
		updatebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try(Connection con = DriverManager.getConnection(AID.url, AID.myname, AID.mypassword)) {
					String query;
					int i;
					String first = firstf.getText();
					String last = lastf.getText();
					String email = emailf.getText();
					String phone = phonef.getText();
					Date birth = AID.stringToDate(birthf.getText());
					query = "UPDATE member SET first_name = ?, last_name = ?, email = ?, phone = ?, date_of_birth = ? WHERE id = ?";
					try (PreparedStatement pstmt = con.prepareStatement(query)){
						pstmt.setString(1, first);
			            pstmt.setString(2, last);
			            pstmt.setString(3, email);
			            pstmt.setString(4, phone);
			            pstmt.setDate(5, birth);
			            pstmt.setInt(6, selectedId);
			            
						i = pstmt.executeUpdate();
			           	if(i > 0) {	
			           		JOptionPane.showMessageDialog(null, "Member updated Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
			           	}
			           	else
			           	{
			           		JOptionPane.showMessageDialog(null, "Error occur", "Error", JOptionPane.ERROR_MESSAGE);
			           	}
					}
				}
				catch(Exception e2){
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				finally
				{
					firstf.setText("");
					lastf.setText("");
					emailf.setText("");
					phonef.setText("");
					birthf.setText("");	
					
					selectedId = -1;
				}
				
			}
		});
		updatebtn.setFont(new Font("Bitstream Charter", Font.BOLD, 14));
		updatebtn.setForeground(new Color(0, 0, 51));
		updatebtn.setBackground(new Color(255, 140, 0));
		
		JButton deletebtn = new JButton("DELETE");
		deletebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try(Connection con = DriverManager.getConnection(AID.url, AID.myname, AID.mypassword)) {
					String query;
					int i;
					
					query = "DELETE FROM member WHERE id = ? ";
					try (PreparedStatement pstmt = con.prepareStatement(query)){
						pstmt.setInt(1, selectedId);
						
						i = pstmt.executeUpdate();
			           	if(i > 0) {	
			           		JOptionPane.showMessageDialog(null, "Member deleted Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
			           	}
			           	else
			           	{
			           		JOptionPane.showMessageDialog(null, "Error occur", "Error", JOptionPane.ERROR_MESSAGE);
			           	}
					}
				}
				catch(Exception e2){
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				finally
				{
					firstf.setText("");
					lastf.setText("");
					emailf.setText("");
					phonef.setText("");
					birthf.setText("");
					
					selectedId = -1;
				}
					
			}
		});
		deletebtn.setFont(new Font("Bitstream Charter", Font.BOLD, 14));
		deletebtn.setForeground(new Color(0, 0, 51));
		deletebtn.setBackground(new Color(255, 140, 0));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(30)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
								.addComponent(firstf, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
								.addComponent(lastf, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
								.addComponent(emailf, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_3_1_2, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(phonef, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3_2_1, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(birthf, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3_2_1_1, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(textField_6, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(updatebtn, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(deletebtn, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(addbtn, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
							.addGap(1)))
					.addGap(43))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(87)
					.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(firstf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(lastf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(emailf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_3_1_2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(phonef, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addComponent(lblNewLabel_3_2_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(birthf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(addbtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(updatebtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(deletebtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(220)
					.addComponent(lblNewLabel_3_2_1_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(textField_6, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
