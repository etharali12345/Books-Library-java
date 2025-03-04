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


public class Sell extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField bookidf;
	private JTextField memidf;
	private JTextField selldf;
	private JTextField textField_6;
	private JTextField qf;


	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Sell() {
		setTitle("SELL");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 1100, 750);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(35, 35, 51));
		contentPane.setForeground(new Color(35, 35, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		table.setBackground(SystemColor.window);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"sell id", "book id", "title", "member id", "first name", "last name", "sell date", "qauntity"
			}
		));
		
		JLabel lblNewLabel_8 = new JLabel("Count");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_8.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_8.setForeground(new Color(255, 135, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(35, 35, 51));
		
		JButton displaybtn = new JButton("Dispaly ");
		displaybtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tblmode = (DefaultTableModel)table.getModel();
				tblmode.setRowCount(0);
				String query;
				try (Connection con = DriverManager.getConnection(AID.url, AID.myname, AID.mypassword)) {
					query = "SELECT s.id AS sell_id, bk.id AS book_id, bk.title, mem.id AS member_id, mem.first_name, mem.last_name, s.sell_date, s.quantity " +
						    "FROM sell s " +
						    "JOIN book bk ON s.book_id = bk.id " +
						    "JOIN member mem ON s.member_id = mem.id " +
						    "ORDER BY s.sell_date;";
				    	    
				    try (PreparedStatement pstmt = con.prepareStatement(query);
				         ResultSet rst = pstmt.executeQuery()) {
				        while (rst.next()) 
				        {
				            String sell_id = String.valueOf(rst.getInt(1));
				            String book_id = String.valueOf(rst.getInt(2));
				            String book_title = rst.getString(3);
				            String mem_id = String.valueOf(rst.getInt(4));
				            String first = rst.getString(5);
				            String last = rst.getString(6);
				            String sell_date = AID.dateToString(rst.getDate(7));
				            String quantity = String.valueOf(rst.getInt(8));
				            
				        	String[] row = {sell_id, book_id,book_title, mem_id,first, last, sell_date, quantity};        
				            
				            tblmode.addRow(row);
				        }//while
				    }//try
				} catch (Exception e2) {
				    JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} 
			}
		});
		displaybtn.setHorizontalAlignment(SwingConstants.LEADING);
		displaybtn.setForeground(new Color(0, 0, 51));
		displaybtn.setFont(new Font("Bitstream Charter", Font.BOLD, 14));
		displaybtn.setBackground(new Color(255, 140, 0));
		
		JLabel lblNewLabel = new JLabel("H");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Home frame = new Home();
				frame.setVisible(true);
				dispose();
			}
		});
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("C059", Font.BOLD, 17));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(12)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
								.addGap(96)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
										.addGap(81))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(displaybtn, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
										.addContainerGap())))
							.addComponent(lblNewLabel_8, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(23)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 664, GroupLayout.PREFERRED_SIZE)
							.addGap(434)
							.addComponent(lblNewLabel_8, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(62)
							.addComponent(displaybtn, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 485, GroupLayout.PREFERRED_SIZE)
							.addGap(104)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
					.addGap(50))
		);
		
		JLabel lblNewLabel_3_1 = new JLabel("Book ID");
		lblNewLabel_3_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_1.setFont(new Font("C059", Font.BOLD, 16));
		
		bookidf = new JTextField();
		bookidf.setColumns(10);
		
		JLabel lblNewLabel_3_2 = new JLabel("Member ID");
		lblNewLabel_3_2.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_2.setFont(new Font("C059", Font.BOLD, 16));
		
		memidf = new JTextField();
		memidf.setColumns(10);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Sell Date");
		lblNewLabel_3_1_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_1_1.setFont(new Font("C059", Font.BOLD, 16));
		
		selldf = new JTextField();
		selldf.setColumns(10);
		
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
					
					int bookid =Integer.parseInt(bookidf.getText()); 
					int memid =Integer.parseInt(memidf.getText());
					Date selldate = AID.stringToDate(selldf.getText()) ;
					int quantity = Integer.parseInt(qf.getText());
					
					
					query = "INSERT INTO sell (book_id, member_id, sell_date,quantity) VALUES (?, ?, ?, ?)";
					try (PreparedStatement pstmt = con.prepareStatement(query)){
					pstmt.setInt(1, bookid);
		            pstmt.setInt(2, memid);
		            pstmt.setDate(3, selldate);
		            pstmt.setInt(4, quantity);
		                   
					i = pstmt.executeUpdate();
		           	if(i > 0)	
		           		JOptionPane.showMessageDialog(null, "Sell Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				catch(Exception e2){
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				finally
				{
					bookidf.setText("");
					memidf.setText("");
					selldf.setText("");
				}
				
				
			}
		});
		addbtn.setFont(new Font("Bitstream Charter", Font.BOLD, 14));
		addbtn.setForeground(new Color(0, 0, 51));
		addbtn.setBackground(new Color(255, 140, 0));
		
		JLabel lblNewLabel_3_1_1_1 = new JLabel("Quantity");
		lblNewLabel_3_1_1_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_1_1_1.setFont(new Font("C059", Font.BOLD, 16));
		
		qf = new JTextField();
		qf.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(30)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
								.addComponent(bookidf, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
								.addComponent(memidf, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
								.addComponent(selldf, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3_1_1_1, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
								.addComponent(qf, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(addbtn, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_3_2_1_1, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(textField_6, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
							.addGap(1)))
					.addGap(43))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(87)
					.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(bookidf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(memidf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(selldf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_3_1_1_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(qf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(addbtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(591)
					.addComponent(lblNewLabel_3_2_1_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(textField_6, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
