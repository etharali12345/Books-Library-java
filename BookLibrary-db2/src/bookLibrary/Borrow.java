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


public class Borrow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField bookidf;
	private JTextField memidf;
	private JTextField bordatef;
	private JTextField retdatef;
	private JTextField textField_6;
	private int selectedId;
	private static final String myname = "root";
	private static final String mypassword = "root@123";
	private static final String url = "jdbc:mysql://localhost:3306/library";
	private JTextField searchf;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Borrow() {
	
		setTitle("Borrow");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 1100, 750);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bookidf.setText("");
				memidf.setText("");
				bordatef.setText("");
				retdatef.setText("*optional");
				
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
                    
                	String idString = table.getValueAt(selectedRow, 0).toString();
                	selectedId = Integer.parseInt(idString);
                	bookidf.setText(table.getValueAt(selectedRow, 1).toString());
                	memidf.setText(table.getValueAt(selectedRow, 3).toString());
                	bordatef.setText(table.getValueAt(selectedRow, 6).toString());
                	
                	Object value = table.getValueAt(selectedRow, 7);
                	if(value == null) {
                		retdatef.setText("");	
                	}
                	else {
                		retdatef.setText(value.toString());
                	}
                
                }
			}
		});
		table.setBackground(SystemColor.window);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"borrow id", "book id", "title", "member id", "first name", "last name", "borrow date", "return date"
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
				try (Connection con = DriverManager.getConnection(url, myname, mypassword)) {
					DefaultTableModel tblmode = (DefaultTableModel)table.getModel();
					tblmode.setRowCount(0);
					String query;
				    if (!searchf.getText().isEmpty()) {
				    	query = "SELECT bor.id AS borrow_id, bk.id AS book_id, bk.title, mem.id AS member_id, " +
				    	        "mem.first_name, mem.last_name, bor.borrow_date, bor.return_date " +
				    	        "FROM borrow bor " +
				    	        "JOIN book bk ON bor.book_id = bk.id " +
				    	        "JOIN member mem ON bor.member_id = mem.id " +
				    	        "WHERE YEAR(bor.borrow_date) = ? " +
				    	        "ORDER BY bor.borrow_date;";

				    	int year = Integer.parseInt(searchf.getText());
				    			
				        try (PreparedStatement pstmt = con.prepareStatement(query)) {
			    
				            pstmt.setInt(1, year);
				            
				            try (ResultSet rst = pstmt.executeQuery()) {
				            	
				                while (rst.next()) {
				                    String borrow_id = String.valueOf(rst.getInt(1));
						            String book_id = String.valueOf(rst.getInt(2));
						            String book_title = rst.getString(3);
						            String mem_id = String.valueOf(rst.getInt(4));
						            String first = rst.getString(5);
						            String last = rst.getString(6);
						            String borrow_date = AID.dateToString(rst.getDate("borrow_date"));
						            String return_date = AID.dateToString(rst.getDate("return_date"));
						     
						            
						        	String[] row = {borrow_id, book_id,book_title, mem_id,first, last, borrow_date, return_date};        
						            
						            tblmode.addRow(row);
						            searchf.setText("");
				                }
				            }
				        }
				    	
				    }//if
				    else {
						query = "SELECT bor.id AS borrow_id, bk.id AS book_id, bk.title, mem.id AS member_id, mem.first_name, mem.last_name, bor.borrow_date, bor.return_date " +
							    "FROM borrow bor " +
							    "JOIN book bk ON bor.book_id = bk.id " +
							    "JOIN member mem ON bor.member_id = mem.id " +
							    "ORDER BY bor.borrow_date;";
					    	    
					    try (PreparedStatement pstmt = con.prepareStatement(query);
					         ResultSet rst = pstmt.executeQuery()) {
					        while (rst.next()) 
					        {
					            String borrow_id = String.valueOf(rst.getInt(1));
					            String book_id = String.valueOf(rst.getInt(2));
					            String book_title = rst.getString(3);
					            String mem_id = String.valueOf(rst.getInt(4));
					            String first = rst.getString(5);
					            String last = rst.getString(6);
					            String borrow_date = AID.dateToString(rst.getDate("borrow_date"));
					            String return_date = AID.dateToString(rst.getDate("return_date"));
					     
					            
					        	String[] row = {borrow_id, book_id,book_title, mem_id,first, last, borrow_date, return_date};        
					            
					            tblmode.addRow(row);
					            searchf.setText("");
					        }//while
					    }//else
					    
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
		
		searchf = new JTextField();
	
		searchf.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("*year");
		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
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
								.addGap(97)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
										.addGap(81))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(displaybtn, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(searchf, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblNewLabel_1)
										.addContainerGap())))
							.addComponent(lblNewLabel_8, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 664, GroupLayout.PREFERRED_SIZE)
							.addGap(445)
							.addComponent(lblNewLabel_8, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(62)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(displaybtn, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(searchf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_1)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 485, GroupLayout.PREFERRED_SIZE)
							.addGap(101)
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
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Borrow Date");
		lblNewLabel_3_1_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_1_1.setFont(new Font("C059", Font.BOLD, 16));
		
		bordatef = new JTextField();
		bordatef.setColumns(10);
		
		JLabel lblNewLabel_3_1_2 = new JLabel("Return Date");
		lblNewLabel_3_1_2.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_1_2.setFont(new Font("C059", Font.BOLD, 16));
		
		retdatef = new JTextField();
		retdatef.setToolTipText("");
		retdatef.setColumns(10);
		retdatef.setText("*optional");
		
		JLabel lblNewLabel_3_2_1_1 = new JLabel("Book ID");
		lblNewLabel_3_2_1_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_2_1_1.setFont(new Font("C059", Font.BOLD, 16));
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		
		JButton addbtn = new JButton("ADD");
		addbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try(Connection con = DriverManager.getConnection(url, myname, mypassword)) {
					String query;
					int i;
					int bookid =Integer.parseInt(bookidf.getText()); 
					int memid =Integer.parseInt(memidf.getText());
					Date bordate = AID.stringToDate(bordatef.getText()) ;
					String retdatetxt = retdatef.getText();
					
					query = "INSERT INTO borrow ( book_id, member_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
					try (PreparedStatement pstmt = con.prepareStatement(query)){
		            pstmt.setInt(1, bookid);
		            pstmt.setInt(2, memid);
		            pstmt.setDate(3, bordate);
		            
		            if(!retdatetxt.equals("*optional") && !retdatetxt.equals("")) {
		            	Date retdate = AID.stringToDate(retdatetxt);
		            	pstmt.setDate(4, retdate);     	
		            }
		            else
		            {
		            	pstmt.setNull(4, java.sql.Types.DATE);
		            }
		       
					i = pstmt.executeUpdate();
		           	if(i > 0)	
		           		JOptionPane.showMessageDialog(null, "Borrow Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				catch(Exception e2){
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				finally
				{
					bookidf.setText("");
					memidf.setText("");
					bordatef.setText("");
					retdatef.setText("*optional");
				}
			}
		});
		addbtn.setFont(new Font("Bitstream Charter", Font.BOLD, 14));
		addbtn.setForeground(new Color(0, 0, 51));
		addbtn.setBackground(new Color(255, 140, 0));
		
		JButton updatebtn = new JButton("UPDATE");
		updatebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try(Connection con = DriverManager.getConnection(url, myname, mypassword)) {
					String query;
					int i;
					int bookid =Integer.parseInt(bookidf.getText()); 
					int memid =Integer.parseInt(memidf.getText());
					Date bordate = AID.stringToDate(bordatef.getText()) ;
					String retdatetxt = retdatef.getText();
					
					query = "UPDATE borrow SET book_id = ?, member_id = ?, borrow_date = ?, return_date = ? WHERE id = ?";
					try (PreparedStatement pstmt = con.prepareStatement(query)){
		            pstmt.setInt(1, bookid);
		            pstmt.setInt(2, memid);
		            pstmt.setDate(3, bordate);
		            
		            if(!retdatetxt.equals("*optional") && !retdatetxt.equals("")) {
		            	Date retdate = AID.stringToDate(retdatetxt);
		            	pstmt.setDate(4, retdate);     	
		            }
		            else
		            {
		            	pstmt.setNull(4, java.sql.Types.DATE);
		            }
		       
		            pstmt.setInt(5, selectedId);
		            
					i = pstmt.executeUpdate();
		           	if(i > 0)	
		           		JOptionPane.showMessageDialog(null, "Borrow updated Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				catch(Exception e2){
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				finally
				{
					bookidf.setText("");
					memidf.setText("");
					bordatef.setText("");
					retdatef.setText("*optional");
					
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
				
				try(Connection con = DriverManager.getConnection(url, myname, mypassword)) {
					String query;
					int i;
					query = "DELETE FROM borrow WHERE id = ?";
					try (PreparedStatement pstmt = con.prepareStatement(query)){
			            pstmt.setInt(1, selectedId);
			       
						i = pstmt.executeUpdate();
			           	if(i > 0) {	
			           		JOptionPane.showMessageDialog(null, "Borrow deleted Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
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
					bookidf.setText("");
					memidf.setText("");
					bordatef.setText("");
					retdatef.setText("*optional");
					
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
								.addComponent(bookidf, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
								.addComponent(memidf, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
								.addComponent(bordatef, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_3_1_2, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(retdatef, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
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
					.addComponent(bookidf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(memidf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(bordatef, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_3_1_2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(retdatef, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(addbtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(updatebtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(deletebtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(284)
					.addComponent(lblNewLabel_3_2_1_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(textField_6, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
