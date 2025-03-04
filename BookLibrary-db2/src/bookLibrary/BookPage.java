package bookLibrary;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BookPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookPage frame = new BookPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BookPage() {
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
				Filter frame = new Filter();
				frame.setVisible(true);
				dispose();
			}
		});
	
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("image/photo_2024-10-22_13-48-42 (1).jpg"));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		
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
				Book frame = new Book();
				frame.setVisible(true);
				dispose();
			}
		});
		
		JLabel lblAddMember_1 = new JLabel("Manage Books");
		lblAddMember_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddMember_1.setFont(new Font("DejaVu Sans", Font.BOLD, 23));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 330, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addGap(75)
					.addComponent(lblAddMember_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(65))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 104, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap(33, Short.MAX_VALUE)
					.addComponent(lblAddMember_1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(27))
		);
		panel_1.setLayout(gl_panel_1);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(286)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(277))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(286)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
					.addGap(277))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(84)
					.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
					.addGap(75))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 224, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
					.addGap(8))
		);
		
		JLabel lblAddMember = new JLabel("Filter Books");
		lblAddMember.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddMember.setFont(new Font("DejaVu Sans", Font.BOLD, 23));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGap(75)
					.addComponent(lblAddMember, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(65))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(33, Short.MAX_VALUE)
					.addComponent(lblAddMember, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(27))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
