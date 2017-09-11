package app;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class Interface extends JFrame {
	
	private final JButton pptqButton = new JButton();
	private final JButton gpButton = new JButton();
	private final JButton deleteButton = new JButton();
	private final JButton insertEventsButton = new JButton();
	
	
	
	
	public Interface() {
		super();
		this.setTitle("EventLocator");
		this.getContentPane().setLayout(null);
		this.setBounds(1000, 500, 1000, 440);
		this.add(pptqButton());
		this.add(deleteButton());
		this.add(gpButton());
		this.add(insertEventsButton());
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
	}
	
	private JButton pptqButton() {
		pptqButton.setText("PPTQ Events");
		pptqButton.setBounds(100, 100, 200, 30);
		pptqButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(pptqButton, "PPTQ Success");
			}
		});
		return pptqButton;
	}
	
	private JButton gpButton() {
		gpButton.setText(" GP Events");
		gpButton.setBounds(40, 40, 100, 30);
		gpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(gpButton, "GP Success");
			}
		});
		return gpButton;
	}
	
	private JButton deleteButton() {
		deleteButton.setText("Delete all future events");
		deleteButton.setBounds(200, 200, 500, 30);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(deleteButton, "Deleted all the previous events");
			}
		});
		return deleteButton;
	}
	
	
	private JButton insertEventsButton() {
		insertEventsButton.setText("Insert All events of the chosen type");
		insertEventsButton.setBounds(150, 150, 100, 30);
		insertEventsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(insertEventsButton, "All events are being inserted. Please Wait.");
				
			}
		});
		return insertEventsButton;
	}
	
}