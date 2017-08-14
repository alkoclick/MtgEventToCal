package model;

public class Event {

	private String name;
	private String organizer;
	private String format;
	private String link;
	private String date;

	public Event() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganizer() {
		return organizer;
	}

	public void setOrganizer(String organizer) {
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String toString() {
		return ("Name : " + name + " Date : " + date + " Organizer : " + organizer + " Format : " + format);
	}
}
