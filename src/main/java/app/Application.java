package app;

import java.io.IOException;
import java.util.Collection;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import io.FileHandler;
import model.Event;

public class Application {
	private static final String PPTQ_TEXT = Messages.getString("HTML.PPTQ");
	static String MAIN_URL = Messages.getString("EventLocator.Main");

	public static void main(String[] args) {

		// Read store links
		Collection<String> storeLinks = FileHandler.reader("storeLinks.txt");

		for (String storeUrl : storeLinks) {

			// Visit each one
			// Wait for page to load
			HtmlPage storeHomepage = waitForPageToLoad(storeUrl);

			// Find & click the PPTQ button
			HtmlAnchor PPTQButton = (HtmlAnchor) storeHomepage.getFirstByXPath("//a[text()='" + PPTQ_TEXT + "']");
			try {
				// HtmlPage pptqPage = clickButtonAndWait(PPTQButton);

				// Find out how many results we have
				System.out.println("Other results: " + storeHomepage.getElementsByTagName("dl").size());

				DomNodeList<DomNode> storeTourneys = storeHomepage.getElementById("event-table-content")
						.getChildNodes();
				storeTourneys.forEach(node -> node.asText());

				storeTourneys.stream().filter(node -> node.asText().contains(PPTQ_TEXT)).forEach(node -> {
					System.out.println(node.asText());
					Event event = new Event();
					event.setName(
							((HtmlSpan) node.getByXPath("//*[@class='event-title-name']").get(0)).getTextContent());
					System.out.println("Name: " + event.getName());
				});

				// Create that many Events
				// Fill events with info from each HTMLElement

				// for (int i = 0; i < results; i++) {
				// Event event = new Event();
				// event.setName(((HtmlSpan)
				// storeHomepage.getByXPath("//*[@class='event-title-name']").get(i))
				// .getTextContent());
				// System.out.println("Name: " + event.getName());
				// }
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

			// Wait for result
			// Get the info from the element

		}

		// Google calendar
	}

	private static HtmlPage clickButtonAndWait(HtmlAnchor pPTQButton) {
		// TODO Auto-generated method stub
		HtmlPage afterClick;
		try {
			afterClick = pPTQButton.click();
			afterClick.getWebClient().waitForBackgroundJavaScript(60000);
			return afterClick;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private static HtmlPage waitForPageToLoad(String page_url) {
		HtmlPage page = null;
		try (final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45)) {
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			page = webClient.getPage(page_url);
			webClient.waitForBackgroundJavaScript(120000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// JavaScriptJobManager manager =
		// page.getEnclosingWindow().getJobManager();
		// System.out.println(manager.getJobCount());
		// while (manager.getJobCount() > 0) {
		// System.out.println(manager.getEarliestJob().toString());
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// }
		return page;
	}
}
