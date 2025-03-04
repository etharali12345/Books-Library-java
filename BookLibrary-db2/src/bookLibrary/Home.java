package bookLibrary;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Home extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Home() {
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 903, 524);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(35, 35, 51));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				panel.setBackground(new Color(255,135,0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				panel.setBackground(new Color(238,238,238));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				Member frame = new Member();
				frame.setVisible(true);
				dispose();
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				panel_1.setBackground(new Color(255,135,0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				panel_1.setBackground(new Color(238,238,238));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				Sell frame = new Sell();
				frame.setVisible(true);
				dispose();
			}
		});
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BookPage frame = new BookPage();
				frame.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				panel_1_1.setBackground(new Color(255,135,0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				panel_1_1.setBackground(new Color(238,238,238));
			}
		});
		
		JPanel panel_2 = new JPanel();
		panel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				panel_2.setBackground(new Color(255,135,0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				panel_2.setBackground(new Color(238,238,238));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				Borrow frame = new Borrow();
				frame.setVisible(true);
				dispose();
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("image/photo_2024-10-22_13-48-42 (1).jpg"));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_1_2 = new JPanel();
		panel_1_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				panel_1_2.setBackground(new Color(255,135,0));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				panel_1_2.setBackground(new Color(238,238,238));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				Author frame = new Author();
				frame.setVisible(true);
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(36)
					.addComponent(panel_1_1, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
					.addGap(29)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 141, Short.MAX_VALUE)
					.addGap(31)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 141, Short.MAX_VALUE)
					.addGap(29)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
					.addGap(28)
					.addComponent(panel_1_2, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
					.addGap(30))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(84)
					.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
					.addGap(75))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 211, Short.MAX_VALUE)
					.addGap(41)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel_1_2, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel_1_1, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
								.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
							.addGap(11))))
		);
		
		JLabel lblBorrow_1_3 = new JLabel("Author");
		lblBorrow_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblBorrow_1_3.setFont(new Font("DejaVu Sans", Font.BOLD, 23));
		
		JLabel lblBorrow_1_7 = new JLabel("");
		lblBorrow_1_7.setIcon(new ImageIcon("image/pngtree-open-book-with-quill-pen-and-inkwell-vintage-illustration-png-image_13333724.png"));
		lblBorrow_1_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblBorrow_1_7.setFont(new Font("C059", Font.BOLD, 19));
		GroupLayout gl_panel_1_2 = new GroupLayout(panel_1_2);
		gl_panel_1_2.setHorizontalGroup(
			gl_panel_1_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1_2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBorrow_1_3, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
						.addComponent(lblBorrow_1_7, GroupLayout.PREFERRED_SIZE, 122, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1_2.setVerticalGroup(
			gl_panel_1_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1_2.createSequentialGroup()
					.addGap(24)
					.addComponent(lblBorrow_1_7, GroupLayout.PREFERRED_SIZE, 108, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblBorrow_1_3, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel_1_2.setLayout(gl_panel_1_2);
		
		JLabel lblBorrow_1_2 = new JLabel("Sell");
		lblBorrow_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblBorrow_1_2.setFont(new Font("DejaVu Sans", Font.BOLD, 23));
		
		JLabel lblBorrow_1_6 = new JLabel("");
		lblBorrow_1_6.setIcon(new ImageIcon("image/4.png"));
		lblBorrow_1_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblBorrow_1_6.setFont(new Font("C059", Font.BOLD, 19));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBorrow_1_2, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
						.addComponent(lblBorrow_1_6, GroupLayout.PREFERRED_SIZE, 122, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(25)
					.addComponent(lblBorrow_1_6, GroupLayout.PREFERRED_SIZE, 108, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblBorrow_1_2, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblBorrow_1_1 = new JLabel("Borrow");
		lblBorrow_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblBorrow_1_1.setFont(new Font("DejaVu Sans", Font.BOLD, 23));
		
		JLabel lblBorrow_1_5 = new JLabel("");
		lblBorrow_1_5.setIcon(new ImageIcon("image/3.png"));
		lblBorrow_1_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblBorrow_1_5.setFont(new Font("C059", Font.BOLD, 19));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblBorrow_1_1, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
							.addGap(7))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblBorrow_1_5, GroupLayout.PREFERRED_SIZE, 127, Short.MAX_VALUE)
							.addGap(7))))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(25)
					.addComponent(lblBorrow_1_5, GroupLayout.PREFERRED_SIZE, 108, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblBorrow_1_1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		
		JLabel lblBorrow = new JLabel("Book");
		lblBorrow.setHorizontalAlignment(SwingConstants.CENTER);
		lblBorrow.setFont(new Font("DejaVu Sans", Font.BOLD, 23));
		
		JLabel lblBorrow_1 = new JLabel("");
		lblBorrow_1.setIcon(new ImageIcon("image/1 (2).png"));
		lblBorrow_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblBorrow_1.setFont(new Font("C059", Font.BOLD, 19));
		GroupLayout gl_panel_1_1 = new GroupLayout(panel_1_1);
		gl_panel_1_1.setHorizontalGroup(
			gl_panel_1_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_1_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBorrow_1, GroupLayout.PREFERRED_SIZE, 122, Short.MAX_VALUE)
						.addComponent(lblBorrow, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1_1.setVerticalGroup(
			gl_panel_1_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1_1.createSequentialGroup()
					.addGap(24)
					.addComponent(lblBorrow_1, GroupLayout.PREFERRED_SIZE, 108, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblBorrow, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel_1_1.setLayout(gl_panel_1_1);
		
		JLabel lblAddMember = new JLabel("Member");
		lblAddMember.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddMember.setFont(new Font("DejaVu Sans", Font.BOLD, 23));
		
		JLabel lblBorrow_1_4 = new JLabel("");
		lblBorrow_1_4.setIcon(new ImageIcon("image/2.png"));
		lblBorrow_1_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblBorrow_1_4.setFont(new Font("C059", Font.BOLD, 19));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblAddMember, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(7))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblBorrow_1_4, GroupLayout.PREFERRED_SIZE, 127, Short.MAX_VALUE)
							.addGap(7))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(24)
					.addComponent(lblBorrow_1_4, GroupLayout.PREFERRED_SIZE, 108, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblAddMember, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
