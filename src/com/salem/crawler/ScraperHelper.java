package com.salem.crawler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScraperHelper {
	
	private List<String> urlsList = new LinkedList<String>();
	private Document htmlDoc;
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) "
			+ "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36";
	
	/**
	 * Use jsoup libraries to establish a connection
	 * Checks the response, then if succcessful gathers all the necessary info
	 * 
	 * 
	 * @param url
	 *       -Holds the value of the URL to visit
	 *       
	 * @return
	 * 
	 */
	public boolean scrape(String url)
	{
		try
		{
			//establish a connection with the url using a bot as a normal web browser
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDoc = connection.get();
			this.htmlDoc = htmlDoc;
			
		
			if(connection.response().statusCode() == 200)
			{
				System.out.println("Received web page " + url);
			}
			if(!connection.response().contentType().contains("text/html"))
			{
				System.out.println("Failed! Retrieved wrong data type, not HTML!");
				return false;
			}
			//
			Elements urlsOnPage = htmlDoc.select("a[href]");
			System.out.println("There are " + urlsOnPage.size() + " links that have been found!" );
			
			//read each element
			for(Element urls : urlsOnPage )
			{
				this.urlsList.add(urls.absUrl("href"));
			}
			return true;
		}
		catch(IOException e)
		{
			//There was no successful HTTP request
			return false;
		}
	}
	
	/**
	 * Searches in the body of the HTML document,
	 * Only called after a successful crawl
	 * 
	 * @param word
	 *       -The word/string to search for
	 * 
	 * @return
	 *       -True or false depending on if a match was found
	 *
	 */
	public boolean searchForWord(String word)
	{
		if(this.htmlDoc == null)
		{
			System.out.println("Error! method scrape() was not called!");
			return false;
		}
		System.out.println("Now searching for the word " + word);
		String body = this.htmlDoc.body().text();
		return body.toLowerCase().contains(word.toLowerCase());
		
	}
	
	public List<String> getURL()
	{
		return this.urlsList;
	}

}
