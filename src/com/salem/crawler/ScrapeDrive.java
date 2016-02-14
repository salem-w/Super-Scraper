package com.salem.crawler;
import java.util.Scanner;

public class ScrapeDrive {

	/**
	 * Prompts user for search term and website to search in
	 * Begins running program
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
	Scraper scrape = new Scraper();
	System.out.println("Enter a search word or phrase");
	String inputSearchWord = scan.next();
	System.out.println("Enter the website to search in including http://");
	String webPage = scan.next();
	scrape.search(webPage, inputSearchWord);
	scan.close();

	}

}
