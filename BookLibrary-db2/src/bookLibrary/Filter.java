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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Filter extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField txtf;
	
	private static final String myname = "root";
	private static final String mypassword = "root@123";
	private static final String url = "jdbc:mysql://localhost:3306/library";
	
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Filter() {
		setTitle("Filter");
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
				"book id", "title", "ISBN", "publish date", "author id", "price"
			}
		));
		
		JRadioButton rdate = new JRadioButton("Publish Date");
		JRadioButton rtitle = new JRadioButton("Title");
		JRadioButton rgenre = new JRadioButton("Genre");
		JRadioButton rnever = new JRadioButton("Never been borrowd&Sold");
		JRadioButton rauthor = new JRadioButton("Author last name");
		
		JLabel lblNewLabel_8 = new JLabel("Count");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_8.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_8.setForeground(new Color(255, 135, 0));
		
		JButton displaybtn = new JButton("Dispaly ");
		displaybtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tblmode = (DefaultTableModel)table.getModel();
				tblmode.setRowCount(0);
				
				try (Connection con = DriverManager.getConnection(url, myname, mypassword)) {
					String query = "SELECT * FROM book";
					String data = txtf.getText(); 
					int year = 0;
					
					if (rnever.isSelected()) {
					    query = "SELECT id, UPPER(title) AS title_upper, ISBN, publish_date, author_id, price "
					          + "FROM book "
					          + "WHERE id NOT IN "
					          + "    (SELECT borrow.book_id "
					          + "     FROM borrow "
					          + "     UNION "
					          + "     SELECT sell.book_id "
					          + "     FROM sell);";
					}

					if (rgenre.isSelected()) {
					    query = "SELECT * "
					            + "FROM book "
					            + "WHERE id IN "
					            + "    (SELECT book_id "
					            + "     FROM genre "
					            + "     WHERE LOWER(book_genre) = LOWER(?));";
					}

					if (rtitle.isSelected()) {
					    query = "SELECT * "
					          + "FROM book "
					          + "WHERE LOWER(title) LIKE LOWER(?);"; 
					}

					if (rdate.isSelected()) {
					    query = "SELECT id, UPPER(title), ISBN, publish_date, author_id, price "
					          + "FROM book "
					          + "WHERE YEAR(publish_date) = ? " 
					          + "ORDER BY publish_date ASC;";
					    
					    year = Integer.parseInt(data);
					}

					if (rauthor.isSelected()) {
					    query = "SELECT b.* "
					          + "FROM book AS b "
					          + "JOIN author AS a ON b.author_ID = a.id "
					          + "WHERE LOWER(a.last_name) = LOWER(?);"; 
					}

					    
				    try (PreparedStatement pstmt = con.prepareStatement(query)) {
				        if (rdate.isSelected()) {
				            pstmt.setInt(1, year);
				        } else if (rgenre.isSelected()) {
				            pstmt.setString(1, data);
				        } else if (rtitle.isSelected()) {
				            pstmt.setString(1, "%" + data + "%"); // Use wildcards for LIKE
				        } else if (rauthor.isSelected()) {
				            pstmt.setString(1, data);
				        }

				        try (ResultSet rst = pstmt.executeQuery()) {
				            while (rst.next()) {
				                String book_id = String.valueOf(rst.getInt("id"));
				                String title = rst.getString(2);
				                String isbn = rst.getString("ISBN");
				                String publish_date = AID.dateToString(rst.getDate("publish_date"));
				                String author_id = String.valueOf(rst.getInt("author_id"));
				                String price = String.valueOf(rst.getInt("price")); // Use getDouble if price is a decimal type

				                String[] row = {book_id, title, isbn, publish_date, author_id, price};        
				                tblmode.addRow(row);
				            }
				            
				        }
				    }
					   
				} 
				catch (Exception e2) 
				{
				    JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}	
	
			}
		});
		displaybtn.setForeground(new Color(0, 0, 51));
		displaybtn.setFont(new Font("Bitstream Charter", Font.BOLD, 14));
		displaybtn.setBackground(new Color(255, 140, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(35,35,51));
		
		JSeparator separator = new JSeparator();
		
		JLabel lblNewLabel_3 = new JLabel("Enter Data:");
		lblNewLabel_3.setForeground(new Color(255, 140, 0));
		lblNewLabel_3.setFont(new Font("C059", Font.BOLD, 16));
		
		txtf = new JTextField();
		txtf.setColumns(10);
		
		
		JSeparator separator_1 = new JSeparator();
		
		JSeparator separator_2 = new JSeparator();
		
		JSeparator separator_2_1 = new JSeparator();
		
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
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_8, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(215)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(separator_2, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
							.addGap(27)
							.addComponent(displaybtn, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
							.addGap(27)
							.addComponent(separator_2_1, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE))
					.addGap(138)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(39))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(202)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(12)
							.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtf, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 752, Short.MAX_VALUE)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 752, Short.MAX_VALUE)
								.addComponent(separator, GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE))
							.addGap(183))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(12, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtf, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
							.addGap(45)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(displaybtn, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
								.addComponent(separator_2_1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 451, GroupLayout.PREFERRED_SIZE)
							.addGap(421)
							.addComponent(lblNewLabel_8, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(50))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(484))))
		);
		
		rdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtf.setText("");
				if(rdate.isSelected()) {
					rgenre.setSelected(false);
					rtitle.setSelected(false);
					rnever.setSelected(false);
					rauthor.setSelected(false);
				}
			}
		});
		
		rnever.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtf.setText("");
				if(rnever.isSelected()) {
					rgenre.setSelected(false);
					rtitle.setSelected(false);
					rdate.setSelected(false);
					rauthor.setSelected(false);
				}
			}
		});
		
		rauthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtf.setText("");
				if(rauthor.isSelected()) {
					rgenre.setSelected(false);
					rtitle.setSelected(false);
					rdate.setSelected(false);
					rnever.setSelected(false);
				}
			}
		});
		
		rgenre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtf.setText("");
				if(rgenre.isSelected()) {
					rtitle.setSelected(false);
					rdate.setSelected(false);
					rnever.setSelected(false);
					rauthor.setSelected(false);
				}
			}
		});
	
		rtitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtf.setText("");
				if(rtitle.isSelected()) {
					rgenre.setSelected(false);
					rdate.setSelected(false);
					rnever.setSelected(false);
					rauthor.setSelected(false);
				}
			}
		});
		rtitle.setForeground(Color.WHITE);
		rtitle.setBackground(new Color(35,35,51));
		
		
		rgenre.setForeground(Color.WHITE);
		rgenre.setBackground(new Color(35,35,51));
		
		
		rdate.setForeground(Color.WHITE);
		rdate.setBackground(new Color(35,35,51));
		
		
		rnever.setForeground(Color.WHITE);
		rnever.setBackground(new Color(35,35,51));
		
		
		rauthor.setForeground(Color.WHITE);
		rauthor.setBackground(new Color(35, 35, 51));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(19)
					.addComponent(rtitle, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
					.addGap(40)
					.addComponent(rgenre, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
					.addGap(35)
					.addComponent(rdate, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
					.addGap(49)
					.addComponent(rauthor, GroupLayout.PREFERRED_SIZE, 116, Short.MAX_VALUE)
					.addGap(31)
					.addComponent(rnever, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(rtitle)
						.addComponent(rnever)
						.addComponent(rauthor)
						.addComponent(rdate)
						.addComponent(rgenre))
					.addContainerGap(11, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
