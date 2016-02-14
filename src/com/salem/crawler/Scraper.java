package com.salem.crawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Scraper {
	//sets the max pages allowed to search
	private static final int MAX_PAGES_TO_SEARCH = 25;
	//HashSets allow only unique objects, so we won't ever search the
	//same page twice
	private Set<String> pagesVisited = new HashSet<String>();
	//We will be appending URLs so I am using linked lists 
	private List<String> pagesToVisit = new LinkedList<String>();
	
	
	/**
	 * 
	 * This method creates helpers that make an HTTP request
	 * then parse the web page
	 * 
	 * @param url
	 *       -The starting point, and holds urls
	 * @param searchWord
	 * 	     -Holds the word/string a user is searching for
	 */
	public void search(String url, String searchWord)
	{
		while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH)
		{
			String currentUrl;
			ScraperHelper helper = new ScraperHelper();
			if(this.pagesToVisit.isEmpty())
			{
				currentUrl = url;
				this.pagesVisited.add(url);
				
			}
			else
			{
				currentUrl = this.nextUrl();
			}
			helper.scrape(currentUrl);
			boolean check = helper.searchForWord(searchWord);
			
			if(check)
			{
				System.out.println(String.format("Suceess, we found the word %s at %s", searchWord, currentUrl));
				break;
			}
			this.pagesToVisit.addAll(helper.getURL());
		}
		System.out.println(String.format("Finished,  %s web pages have been visited", this.pagesVisited.size()));
	}

	/** 
     * Returns the next URL in the order that each URL is found
     * Also does a check through each URL making sure all are unique 
     * 
     * @return
     * 
	 */
	private String nextUrl()
	{
		String nextUrl;
		do
		{
			//Remove the next URL and assign it to nextUrl
			nextUrl = this.pagesToVisit.remove(0);
		}while(this.pagesVisited.contains(nextUrl));
		//add nextUrl into our HashSet
		this.pagesVisited.add(nextUrl);
		return nextUrl;
	}

}
