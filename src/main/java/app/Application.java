package app;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import util.MainMemory;
import workers.PPTQFinder;

public class Application {
	static String MAIN_URL = Messages.getString("EventLocator.Main");

	public static void main(String[] args) {
		try {
			Document doc = Jsoup.connect(args[0]).get();
			MainMemory.storeLinks.putAll(doc.getElementsByClass("entry-content").get(0).getElementsByTag("p").stream()
					.filter(el -> el.text().contains("http:"))
					.collect(Collectors.toConcurrentMap(Element::hashCode, Element::text)));

			runOnExecutor(MainMemory.storeLinks.values().stream().map(link -> new PPTQFinder(link))
					.collect(Collectors.toSet()), 8, 5);

			MainMemory.allEvents.values().forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Google calendar upload
	}

	protected static void runOnExecutor(Collection<Runnable> runnables, int maxThreads, long timeout) {
		// Over 4 threads hit connection reset from server
		ExecutorService executor = Executors.newFixedThreadPool(maxThreads);
		runnables.forEach(runnable -> executor.execute(runnable));

		executor.shutdown();
		try {
			executor.awaitTermination(timeout, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
