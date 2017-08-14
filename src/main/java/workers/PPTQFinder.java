package workers;

import java.util.stream.Collectors;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import app.Messages;
import model.Event;
import util.MainMemory;

public class PPTQFinder implements Runnable {
	private static final String PPTQ_TEXT = Messages.getString("HTML.PPTQ");
	private String url;

	public PPTQFinder(String url) {
		this.url = url;
	}

	@Override
	public void run() {
		// Visit each one
		// Wait for page to load
		System.out.println(url);
		HtmlPage storeHomepage = waitForPageToLoad(url);

		try {
			DomNodeList<DomNode> storeTourneys = storeHomepage.getElementById("event-table-content").getChildNodes();
			storeTourneys.forEach(node -> node.asText());

			// Keep only PPTQs
			storeTourneys.stream().filter(node -> node.asText().contains(PPTQ_TEXT)).forEach(node -> {
				Event event = parseEvent(node);
				MainMemory.allEvents.put(event.hashCode(), event);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets an HTML DomNode item, and parses its contents to return an event
	 * 
	 * @param node
	 *            The node to parse
	 * @return Event with all the information we were able to extract
	 */
	private Event parseEvent(DomNode node) {
		Event event = new Event();

		event.setDate(node.getChildNodes().get(1).getChildNodes().get(1).getTextContent());

		event.setName(node.getChildNodes().get(3).getFirstChild().getTextContent());

		event.setOrganizer(
				node.getChildNodes().get(5).getChildNodes().stream().map(childNode -> childNode.getTextContent())
						.filter(line -> !line.isEmpty()).collect(Collectors.toList()));

		event.setFormat(node.getChildNodes().get(7).getTextContent());

		return event;
	}

	private HtmlPage waitForPageToLoad(String page_url) {
		HtmlPage page = null;
		try (final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45)) {
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			page = webClient.getPage(page_url);
			webClient.waitForBackgroundJavaScript(120000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

}
