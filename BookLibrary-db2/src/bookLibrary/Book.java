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
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Book extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField titlef;
	private JTextField isbnf;
	private JTextField publishf;
	private JTextField authorf;
	private JTextField pricef;
	private JTextField textField_6;
	private JTextField genref;
	private JTextField countf;
	private JTextField sumf;
	
	private static final String myname = "root";
	private static final String mypassword = "root@123";
	private static final String url = "jdbc:mysql://localhost:3306/library";
	private int selectedId;
	private JTextField searchf;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Book() {
		setTitle("Book Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 1100, 750);
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				titlef.setText("");
				isbnf.setText("");
				publishf.setText("");
				authorf.setText("");
				pricef.setText("");
				genref.setText("");
				
				selectedId = -1;;
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
                	titlef.setText(table.getValueAt(selectedRow, 1).toString());
                	isbnf.setText(table.getValueAt(selectedRow, 2).toString());
                	publishf.setText(table.getValueAt(selectedRow, 3).toString());
                	authorf.setText(table.getValueAt(selectedRow, 4).toString());
                	pricef.setText(table.getValueAt(selectedRow, 5).toString());
                	
					String query = "SELECT book_genre FROM genre WHERE book_id = ?";
			        
			        try (Connection conn = DriverManager.getConnection(url, myname, mypassword);
			             PreparedStatement pstmt = conn.prepareStatement(query)) {

			            pstmt.setInt(1, selectedId);

			            ResultSet rs = pstmt.executeQuery();

			            StringBuilder genres = new StringBuilder();

			            // Iterate through all results to collect genres
			            while (rs.next()) {
			                if (genres.length() > 0) {
			                    genres.append(", "); // Add comma separator between genres
			                }
			                genres.append(rs.getString("book_genre"));
			            }

			            // Set the collected genres in the genref field or clear it if no genres were found
			            genref.setText(genres.length() > 0 ? genres.toString() : "");


			        } catch (Exception ex) {
			        	JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
				"Book_id", "Book_title", "ISN", "Publish_date", "Author_id", "Price"
			}
		));
		
		JLabel lblNewLabel_8 = new JLabel("Count");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_8.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_8.setForeground(new Color(255, 135, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(35, 35, 51));
		
		JLabel lblNewLabel = new JLabel("");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(35, 35, 51));
		
		JButton displaybtn = new JButton("Dispaly ");
		displaybtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection con = DriverManager.getConnection(url, myname, mypassword)) {
					DefaultTableModel tblmode = (DefaultTableModel)table.getModel();
					tblmode.setRowCount(0);
					String query;
			
				    if (!searchf.getText().isEmpty()) {
				        query = "SELECT * FROM book WHERE LOWER(title) LIKE LOWER(?)";
				        String s = searchf.getText();
				        
				        try (PreparedStatement pstmt = con.prepareStatement(query)) {
			    
				            pstmt.setString(1, "%" +s+ "%");
				            
				            try (ResultSet rst = pstmt.executeQuery()) {
				            	
				                // Process the result set
				                while (rst.next()) {
				                    String book_id = String.valueOf(rst.getInt("id"));
				                    String title = rst.getString("title");
				                    String isbn = rst.getString("ISBN");
				                    String publish_date = AID.dateToString(rst.getDate("publish_date"));
				                    String author_id = String.valueOf(rst.getInt("author_id"));
				                    String price = String.valueOf(rst.getInt("price"));

				                    String[] row = {book_id, title, isbn, publish_date, author_id, price};        
				                    tblmode.addRow(row);
				                }
				            }
				        }
				        
				        query = "SELECT COUNT(*), SUM(price) FROM book WHERE LOWER(title) LIKE LOWER(?)";
				        try (PreparedStatement pstmt = con.prepareStatement(query)) {
				            pstmt.setString(1, "%" + s + "%"); 

				            try (ResultSet rst = pstmt.executeQuery()) {
				                if (rst.next()) { // Check if there is a result
				                    String count = String.valueOf(rst.getInt(1));
				                    String sum = String.valueOf(rst.getInt(2));
				                    countf.setText(count);
				                    sumf.setText(sum);
				                } else {
				                    countf.setText("0");
				                    sumf.setText("0");
				                }
				            } 
				        }

				    }//if
				    else {
				        query = "SELECT * FROM book";
				        
				        try (PreparedStatement pstmt = con.prepareStatement(query);
				             ResultSet rst = pstmt.executeQuery()) {       
				            while (rst.next()) {
				                String book_id = String.valueOf(rst.getInt("id"));
				                String title = rst.getString("title");
				                String isbn = rst.getString("ISBN");
				                String publish_date = AID.dateToString(rst.getDate("publish_date"));
				                String author_id = String.valueOf(rst.getInt("author_id"));
				                String price = String.valueOf(rst.getInt("price"));

				                String[] row = {book_id, title, isbn, publish_date, author_id, price};        
				                tblmode.addRow(row);
				            }
				        }
				        query = "SELECT COUNT(*), SUM(price) FROM book";
					    try (PreparedStatement pstmt = con.prepareStatement(query);
					         ResultSet rst = pstmt.executeQuery()) {
					        rst.next();
					        String count = String.valueOf(rst.getInt(1));
					        String sum = String.valueOf(rst.getInt(2));
					        countf.setText(count);
					        sumf.setText(sum);
					        }//try
				    }//else

				   
				} //last try
				catch (Exception e2) 
				{
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
		
		JLabel lblNewLabel_1_1 = new JLabel("*title");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_8, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(12)
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
							.addGap(59)
							.addComponent(lblNewLabel)
							.addGap(37)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE))
									.addGap(81))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(displaybtn, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(searchf, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(230)
									.addComponent(lblNewLabel))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(2)
									.addComponent(panel, GroupLayout.PREFERRED_SIZE, 664, GroupLayout.PREFERRED_SIZE)))
							.addGap(455)
							.addComponent(lblNewLabel_8, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(62)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(displaybtn, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
											.addComponent(searchf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_1_1)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 485, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
					.addGap(50))
		);
		
		JLabel lblNewLabel_3_1_2_1 = new JLabel("COUNT");
		lblNewLabel_3_1_2_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3_1_2_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_1_2_1.setFont(new Font("C059", Font.BOLD, 16));
		
		JLabel lblNewLabel_3_1_2_1_1 = new JLabel("SUM");
		lblNewLabel_3_1_2_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_3_1_2_1_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_1_2_1_1.setFont(new Font("C059", Font.BOLD, 16));
		
		countf = new JTextField();
		countf.setColumns(10);
		
		sumf = new JTextField();
		sumf.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(200)
					.addComponent(lblNewLabel_3_1_2_1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(countf, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addGap(34)
					.addComponent(lblNewLabel_3_1_2_1_1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(sumf, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(89, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addContainerGap(20, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(sumf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel_3_1_2_1_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_3_1_2_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addComponent(countf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))
					.addGap(52))
		);
		gl_panel_1.linkSize(SwingConstants.HORIZONTAL, new Component[] {countf, sumf});
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblNewLabel_3_1 = new JLabel("Book title");
		lblNewLabel_3_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_1.setFont(new Font("C059", Font.BOLD, 16));
		
		titlef = new JTextField();
		titlef.setColumns(10);
		
		JLabel lblNewLabel_3_2 = new JLabel("ISBN");
		lblNewLabel_3_2.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_2.setFont(new Font("C059", Font.BOLD, 16));
		
		isbnf = new JTextField();
		isbnf.setColumns(10);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Publish date");
		lblNewLabel_3_1_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_1_1.setFont(new Font("C059", Font.BOLD, 16));
		
		publishf = new JTextField();
		publishf.setColumns(10);
		
		JLabel lblNewLabel_3_1_2 = new JLabel("Author ID");
		lblNewLabel_3_1_2.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_1_2.setFont(new Font("C059", Font.BOLD, 16));
		
		authorf = new JTextField();
		authorf.setColumns(10);
		
		JLabel lblNewLabel_3_2_1 = new JLabel("Price");
		lblNewLabel_3_2_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_2_1.setFont(new Font("C059", Font.BOLD, 16));
		
		pricef = new JTextField();
		pricef.setColumns(10);
		
		JLabel lblNewLabel_3_2_1_1 = new JLabel("Book ID");
		lblNewLabel_3_2_1_1.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_2_1_1.setFont(new Font("C059", Font.BOLD, 16));
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		
		JLabel lblNewLabel_3_2_1_2 = new JLabel("Genre");
		lblNewLabel_3_2_1_2.setForeground(new Color(255, 140, 0));
		lblNewLabel_3_2_1_2.setFont(new Font("C059", Font.BOLD, 16));
		
		genref = new JTextField();
		genref.setColumns(10);
		
		JButton addbtn = new JButton("ADD");
		addbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try(Connection con = DriverManager.getConnection(url, myname, mypassword)) {
					String query;
					int i;
					String title = titlef.getText();
					String isbn = isbnf.getText();
					int author_id = Integer.parseInt(authorf.getText());
					int price = Integer.parseInt(pricef.getText());
					Date date = AID.stringToDate(publishf.getText());
					String [] genre = genref.getText().split(",");
					
					query = "INSERT INTO book ( title, isbn, author_id, price, publish_date) VALUES (?,?,?,?,?)";
					try (PreparedStatement pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
					    pstmt.setString(1, title);
					    pstmt.setString(2, isbn);
					    pstmt.setInt(3, author_id);
					    pstmt.setInt(4, price);
					    pstmt.setDate(5, date);

					    i = pstmt.executeUpdate();

					    if (i <= 0) {
					        JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
					        return;
					    } 

					    // Retrieve the auto-generated book id
					    int bookId = 0;
					    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					        if (generatedKeys.next()) {
					            bookId = generatedKeys.getInt(1); // Get the auto-incremented id
					        }
					    }

					    // Check if genre field is empty
					    if (genref.getText().isEmpty()) {
					        JOptionPane.showMessageDialog(null, "Book added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
					        return;
					    }

					    // Insert genres with the retrieved bookId
					    for (String g : genre) {
					        query = "INSERT INTO genre (book_id, book_genre) VALUES (?, ?)";
					        try (PreparedStatement pstmt1 = con.prepareStatement(query)) {
					            pstmt1.setInt(1, bookId); // Use the retrieved book_id
					            pstmt1.setString(2, g);
					            int j = pstmt1.executeUpdate();

					            if (j <= 0) {
					                JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
					                return;
					            }
					        }
					    }
					    
					    JOptionPane.showMessageDialog(null, "Book added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
					}
				}//try1
				catch(Exception e2){
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				finally
				{
					titlef.setText("");
					isbnf.setText("");
					publishf.setText("");
					authorf.setText("");
					pricef.setText("");
					genref.setText("");
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
					String title = titlef.getText();
					String isbn = isbnf.getText();
					int author_id = Integer.parseInt(authorf.getText());
					int price = Integer.parseInt(pricef.getText());
					Date date = AID.stringToDate(publishf.getText());
					String [] genre = genref.getText().split(",");
					
					query = "UPDATE book SET title = ?, isbn = ?, author_id = ?, price = ?, publish_date = ? WHERE id = ?";
					try (PreparedStatement pstmt = con.prepareStatement(query)){
						pstmt.setString(1, title);
			            pstmt.setString(2, isbn);
			            pstmt.setInt(3, author_id);
			            pstmt.setInt(4, price);
			            pstmt.setDate(5, date);
			            pstmt.setInt(6, selectedId);
			            
			            i = pstmt.executeUpdate();
			            
			            if(i <= 0) {
			            	JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
			            	return;
			            } 
			            if(genref.getText().isEmpty()) {
			            	JOptionPane.showMessageDialog(null, "Book updated Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
			            	return;
			            } 
			            else {
			            	query = "DELETE FROM genre WHERE book_id = ? ";
			            	try (PreparedStatement pstmt1 = con.prepareStatement(query)){
			            		pstmt1.setInt(1, selectedId);
			            		pstmt1.executeUpdate();
			            	}

				            for (String g : genre) {				            	
							    query = "INSERT INTO genre (book_id, book_genre) VALUES (?, ?)";
							    try (PreparedStatement pstmt1 = con.prepareStatement(query)){
									pstmt1.setInt(1, selectedId);
									pstmt1.setString(2, g);
									int j = pstmt1.executeUpdate();
									if(j <= 0)
									{
										JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
										return;
									}
							    }//try	    
							}//for
			            }//else
			              
			           	JOptionPane.showMessageDialog(null, "Book updated Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
					}//try2
				}//try1
				catch(Exception e2){
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				finally
				{
					titlef.setText("");
					isbnf.setText("");
					publishf.setText("");
					authorf.setText("");
					pricef.setText("");
					genref.setText("");
									
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
					query = "DELETE FROM book WHERE id = ? ";
					try (PreparedStatement pstmt = con.prepareStatement(query)){
						pstmt.setInt(1, selectedId);
						 
						i = pstmt.executeUpdate();
			           	if(i > 0) {	
			           		JOptionPane.showMessageDialog(null, "Book deleted Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
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
					
					titlef.setText("");
					isbnf.setText("");
					publishf.setText("");
					authorf.setText("");
					pricef.setText("");
					genref.setText("");
					
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
								.addComponent(titlef, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
								.addComponent(isbnf, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
								.addComponent(publishf, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_3_1_2, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(authorf, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3_2_1, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(pricef, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3_2_1_1, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(textField_6, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(lblNewLabel_3_2_1_2, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(genref, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
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
					.addComponent(titlef, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_3_2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(isbnf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(publishf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_3_1_2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(authorf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(7)
					.addComponent(lblNewLabel_3_2_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(pricef, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_3_2_1_2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(genref, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(addbtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(updatebtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(deletebtn, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(157)
					.addComponent(lblNewLabel_3_2_1_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(textField_6, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
