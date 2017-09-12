package app;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.google.api.services.calendar.Calendar;

import io.FileHandler;
import io.Messages;

public class Interface extends JFrame {

	// Messages
	private static final String DELETE_ERROR = Messages.getString("Delete.Error");
	private static final String DELETE_SUCCESS = Messages.getString("Delete.Success");
	private static final String INSERT_SUCCESS = Messages.getString("Insert.Success");
	private static final String INSERT_ERROR = Messages.getString("Insert.Error");
	private static final String INSTRUCTIONS_LINK = Messages.getString("Instructions");

	// Buttons
	private final JButton deleteButton = new JButton("Delete all future events");
	private final JButton insertEventsButton = new JButton("Insert all future events");
	private final JButton documentation = new JButton("How do I use this?");

	// Calendar ID
	private final JLabel calendarIDLabel = new JLabel("Calendar ID:");
	private final JTextArea calendarIDTextArea = new JTextArea(
			FileHandler.reader(Messages.getString("File.Calendar")).get(0));
	// Store links
	private final JLabel linkLabel = new JLabel("Link:");
	private final JTextArea linkTextArea = new JTextArea(FileHandler.reader(Messages.getString("File.Stores")).get(0));

	public Interface() {
		super();
		// Settings and setup
		this.setSize(600, 300);
		this.setTitle("Event Locator to Calendar");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Content
		this.getContentPane().setLayout(new BorderLayout());
		this.add(deleteButton(), BorderLayout.LINE_END);
		this.add(insertEventsButton(), BorderLayout.LINE_START);
		this.add(documentation, BorderLayout.PAGE_END);
		this.add(boxFunction(), BorderLayout.PAGE_START);

		documentation.addActionListener(e -> {
			try {
				Desktop.getDesktop().browse(new URL(INSTRUCTIONS_LINK).toURI());
			} catch (IOException | URISyntaxException e1) {
				e1.printStackTrace();
			}
		});

		this.setVisible(true);
	}

	private JButton deleteButton() {
		deleteButton.addActionListener(e -> {
			CalendarUtils.CALENDAR_ID = calendarIDTextArea.getText();
			Calendar service;
			try {
				service = CalendarApi.getCalendarService();
				CalendarUtils.deleteAllFutureEvents(service);
				JOptionPane.showMessageDialog(deleteButton, DELETE_SUCCESS);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(deleteButton, DELETE_ERROR);
				e1.printStackTrace();
			}
		});
		return deleteButton;
	}

	private JButton insertEventsButton() {
		insertEventsButton.addActionListener(e -> {
			CalendarUtils.CALENDAR_ID = calendarIDTextArea.getText();
			Calendar service;
			try {
				service = CalendarApi.getCalendarService();
				WebscrapingUtils.fetchStoreLinks(linkTextArea.getText());
				WebscrapingUtils.createEventsInMemory();
				CalendarUtils.insertAllEventsFromMemory(service);
				JOptionPane.showMessageDialog(insertEventsButton, INSERT_SUCCESS);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(insertEventsButton, INSERT_ERROR);
				e1.printStackTrace();
			}
		});
		return insertEventsButton;
	}

	private JPanel boxFunction() {
		JPanel calendarIdFlowPanel = new JPanel();
		calendarIdFlowPanel.add(calendarIDLabel);
		calendarIdFlowPanel.add(calendarIDTextArea);

		JPanel storeLinkFlowPanel = new JPanel();
		storeLinkFlowPanel.add(linkLabel);
		storeLinkFlowPanel.add(linkTextArea);

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
		northPanel.add(calendarIdFlowPanel);
		northPanel.add(storeLinkFlowPanel);

		return northPanel;
	}

}