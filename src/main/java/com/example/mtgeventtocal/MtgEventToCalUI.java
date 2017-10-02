package com.example.mtgeventtocal;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.google.api.services.calendar.Calendar;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import app.CalendarApi;
import app.CalendarUtils;
import app.WebscrapingUtils;
import io.Messages;
import util.MainMemory;

@Theme("MtgEventToCal")
@Push
public class MtgEventToCalUI extends UI {
	private static ProgressBar bar;
	private static AtomicInteger completedTasks;
	public static MtgEventToCalUI ACTIVE;
	private static final int MAX_THREADS = Messages.getInt("Threads.Max");
	private static final String BOT_EMAIL = "alexbotcentral@gmail.com";
	private TextArea linksField;
	private TextField idField;
	private Button insertEvents;
	private Button deleteEvents;
	private Calendar service = null;

	@Override
	protected void init(VaadinRequest request) {
		ACTIVE = this;
		idField = new TextField(
				"Insert calendar ID here - make sure to provide 'Make changes to events' permissions to " + BOT_EMAIL);
		linksField = new TextArea(
				"Insert stores links here - each store in one line (it's ok if the link is too long and wraps around)");
		linksField.setWidth(100, Unit.PERCENTAGE);
		insertEvents = new Button("Insert future events");
		deleteEvents = new Button("Clear future events");
		bar = new ProgressBar();

		completedTasks = new AtomicInteger();
		try {
			service = CalendarApi.getCalendarService();
		} catch (IOException e1) {
			Notification.show("Error authenticating credentials");
			e1.printStackTrace();
		}

		VerticalLayout vert = new VerticalLayout(idField, linksField, insertEvents, deleteEvents);

		vert.setWidth(100, Unit.PERCENTAGE);
		setContent(vert);

		insertEvents.addClickListener(e -> {
			MainMemory.allEvents.clear();
			bar.setIndeterminate(false);
			CalendarUtils.CALENDAR_ID = idField.getValue();
			// https://stackoverflow.com/questions/454908/split-java-string-by-new-line
			MainMemory.storeLinks
					.addAll(Arrays.asList(linksField.getValue().split("\\R")).stream().collect(Collectors.toSet()));
			access(() -> {
				vert.removeComponent(insertEvents);
				vert.addComponent(bar, 2);
			});
			Notification.show("Started scanning the selected stores for PPTQs using " + MAX_THREADS + " threads");
			new Thread(() -> {
				WebscrapingUtils.addEventsFromLinks();
				access(() -> {
					vert.removeComponent(bar);
					vert.addComponent(insertEvents, 2);
				});
				CalendarUtils.insertAllEventsFromMemory(service);
			}).start();
		});
		deleteEvents.addClickListener(e -> {
			if (showWarningsForMissingFields())
				return;
			bar.setIndeterminate(true);
			setContent(bar);
			CalendarUtils.CALENDAR_ID = idField.getValue();
			CalendarUtils.deleteAllFutureEvents(service);
			setContent(vert);
		});
	}

	/**
	 * Shows a notification message if there is information missing in the id or
	 * links field
	 * 
	 * @return true if a warning was shown
	 */
	public boolean showWarningsForMissingFields() {
		if (idField.getValue().isEmpty()) {
			Notification.show("Please fill in the calendar ID field");
		} else if (idField.getValue().isEmpty()) {
			Notification.show("Please add at least one store link");
		} else {
			return false;
		}
		return true;
	}

	public static void incrementProgress() {
		ACTIVE.access(() -> bar.setValue((float) completedTasks.incrementAndGet() / MainMemory.storeLinks.size()));
	}

}
