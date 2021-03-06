package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class MTGEvent {

	private String name;
	private List<String> organizer = new ArrayList<>();
	private String format;
	private String link;
	private String type;
	private String country;
	private Date date;

	public MTGEvent() {
	}

	public Event toGoogleEvent() {
		Event event = new Event();
		event.setSummary(type + " " + country + " @ " + organizer.get(0));
		event.setDescription("Format: " + format + "\n\n" + link);
		event.setLocation(organizer.stream().collect(Collectors.joining(",")));

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 11);
		event.setStart(new EventDateTime().setDateTime(new DateTime(calendar.getTime())));
		calendar.add(Calendar.HOUR_OF_DAY, 8);
		event.setEnd(new EventDateTime().setDateTime(new DateTime(calendar.getTime())));
		return event;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getOrganizer() {
		return organizer;
	}

	public void setOrganizer(List<String> organizer) {
		this.organizer = organizer;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return ("Name : " + name + " Date : " + date + " Organizer : " + organizer + " Format : " + format);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
