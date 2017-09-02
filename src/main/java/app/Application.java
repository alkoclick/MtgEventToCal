package app;

import java.io.IOException;

import com.google.api.services.calendar.Calendar;

import io.Messages;

public class Application {
	static String MAIN_URL = Messages.getString("EventLocator.Main");

	public static void main(String[] args) {
		try {
			// Fetch store links from Greek Judges
			WebscrapingUtils.fetchStoreLinks(args);

			// For each store link, create a PPTQ event in MainMem
			WebscrapingUtils.createEventsInMemory();

			Calendar service = CalendarApi.getCalendarService();
			CalendarUtils.deleteAllFutureEvents(service);
			CalendarUtils.insertAllEventsFromMemory(service);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
