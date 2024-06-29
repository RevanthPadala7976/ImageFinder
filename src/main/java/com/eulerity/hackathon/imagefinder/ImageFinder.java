package com.eulerity.hackathon.imagefinder;

// Import necessary libraries
import java.io.IOException; // Exception for input-output errors
import java.io.PrintWriter; // Class to print formatted representations of objects
import java.util.Collections; // Utility class for collection operations
import java.util.HashMap;
import java.util.HashSet; // Implementation of the set interface
import java.util.Map;
import java.util.Set; // Interface for a collection of unique elements
import java.util.concurrent.ExecutorService; // Interface for managing a pool of threads
import java.util.concurrent.Executors; // Utility class to create thread pools
import java.util.concurrent.TimeUnit; // Represents time durations

import javax.servlet.ServletException; // Exception for servlet erros
import javax.servlet.annotation.WebServlet; // Annotation to define a servlet
import javax.servlet.http.HttpServlet; // Base class for HTTP servlets
import javax.servlet.http.HttpServletRequest; // Represents HTTP request
import javax.servlet.http.HttpServletResponse; // Represents HTTP response

import org.jsoup.Jsoup; // Jsoup library for HTML parsing and web scraping
import org.jsoup.nodes.Document; // Represents the HTML document
import org.jsoup.nodes.Element; // Represents an HTML element
import org.jsoup.select.Elements; // Represents a list of HTML elements

import com.google.gson.Gson; // Library to convert Java objects to JSON

@WebServlet("/main") // Define the servlet endpoint
public class ImageFinder extends HttpServlet {

    private static final long serialVersionUID = 1L; //Serializable class version indetifier 

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

			// Get the URL parameter from the request
        String startUrl = req.getParameter("url");
        if (startUrl == null || startUrl.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL is required");
            return;
        }

        // Log the start of the request
        System.out.println("Starting crawl for URL: " + startUrl);

				// Initialize thread-safe collections to keep track of visited URLs and found images
        Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<>());
        Set<String> imageUrls = Collections.synchronizedSet(new HashSet<>());
				Set<String> faviconUrls = Collections.synchronizedSet(new HashSet<>());
        ExecutorService executorService = Executors.newFixedThreadPool(10); // Create a pool of 10 threads

        try {
						// Start crawling from the provided URL
            crawlImages(startUrl, visitedUrls, imageUrls, faviconUrls, executorService);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while crawling URL: " + e.getMessage());
            return;
        } finally {
						// Shutdown the executor service and wait for the threads to finish
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(10, TimeUnit.MINUTES)) {
                    System.out.println("Crawler did not finish in 10 minutes");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Interrupted while waiting for crawler to finish");
                return;
            }
        }

				// Combine image URLs  and Favicon URLS into a single response
				Map<String, Set<String>> resultMap = new HashMap<>();
				resultMap.put("ImageURLs", imageUrls);
				resultMap.put("FavIconsURLs", faviconUrls);

				// Send the collected image URLs as a JSON
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String jsonResponse = new Gson().toJson(resultMap);
        System.out.println("Returning JSON response: " + jsonResponse);
        out.print(jsonResponse);
        out.flush();
    }

		// Method to start crawling from the given URL
    private void crawlImages(String startUrl, Set<String> visitedUrls, Set<String> imageUrls, Set<String> faviconUrls, ExecutorService executorService) {
        crawlPage(startUrl, visitedUrls, imageUrls, faviconUrls, executorService);
    }

		// Recursive method to crawl a page and its linked pages within the same domain
    private void crawlPage(String url, Set<String> visitedUrls, Set<String> imageUrls, Set<String> faviconUrls, ExecutorService executorService) {
        if (visitedUrls.contains(url) || !url.startsWith("http")) {
            return;
        }

        executorService.submit(() -> {
            try {
                visitedUrls.add(url); // Mark the URL as visited 
                Document doc = Jsoup.connect(url).get(); // Fetch the HTML document
                Elements images = doc.select("img"); // Select all image elements
                for (Element img : images) {
                    String src = img.absUrl("src"); // Get the absolute URl of the image
                    if (!src.isEmpty()) {
                        imageUrls.add(src); // Add the image URL to the set
                    }
                }

								// Collect Favicons
								Elements favicons = doc.select("link[rel~=(?i)^(shortcut|icon|shortcut icon)$]");
								for (Element favicon : favicons){
									String href = favicon.absUrl("href");
									if(!href.isEmpty()){
										faviconUrls.add(href);
									}
								}
                Elements links = doc.select("a[href]"); // Select all link elements 
                for (Element link : links) {
                    String absUrl = link.absUrl("href"); // Get the absolute URL of the link 
                    if (absUrl.startsWith(url)) {
                        crawlPage(absUrl, visitedUrls, imageUrls, faviconUrls, executorService); // Recursively crawl the linked page
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
