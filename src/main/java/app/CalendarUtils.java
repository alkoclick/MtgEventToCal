package app;

import java.io.IOException;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;
import com.vaadin.ui.Notification;

import util.MainMemory;

public class CalendarUtils {
	private static final String EVENT_INSERT = "Inserting event ";
	private static final String EVENT_DELETE = "Deleted all events";
	public static String CALENDAR_ID = "";

	public static void deleteAllFutureEvents(Calendar service) {
		DateTime now = new DateTime(System.currentTimeMillis());
		Events futureEvents = new Events();
		try {
			futureEvents = service.events().list(CALENDAR_ID).setTimeMin(now).execute();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		futureEvents.getItems().forEach(event -> {
			try {
				service.events().delete(CALENDAR_ID, event.getId()).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Notification.show(EVENT_DELETE);
	}

	public static void insertAllEventsFromMemory(Calendar service) {
		MainMemory.allEvents.values().forEach(event -> {
			try {
				System.out.println(EVENT_INSERT);
				service.events().insert(CALENDAR_ID, event.toGoogleEvent()).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
