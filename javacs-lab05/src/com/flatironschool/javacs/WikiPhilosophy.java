package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

public class WikiPhilosophy {
	
	final static WikiFetcher wf = new WikiFetcher();
	
	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 * 
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 * 
	 * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
        // some example code to get you started
	
		
		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		Elements paragraphs = wf.fetchWikipedia(url);
		List<String> visitedLinks = new ArrayList<String>();

		Element firstPara = paragraphs.get(0);
		
		processParagraphs(paragraphs, url, visitedLinks);
		
	}
	
	public static void processParagraphs(Elements paragraphs, String url, List<String> visitedLinks)throws IOException{
			
		String nextLink = getNextLink(paragraphs, url, visitedLinks);
		if(nextLink.equals("https://en.wikipedia.org/wiki/Philosophy")){
				
				printLinks(visitedLinks);	
				System.out.println("You've reached philosophy!");
				return;		
		} 
		if(nextLink == null){
			printLinks(visitedLinks);
			System.out.println("Exiting now");
			return;
		}
		if(nextLink == ""){
			printLinks(visitedLinks);
			System.out.println("Could not find valid link. Exiting now");		
			return;
		}
		
		//System.out.println(nextLink);
		

		else{
		
			Elements nextParagraphs = wf.fetchWikipedia(nextLink);
			processParagraphs(nextParagraphs, nextLink, visitedLinks);
		}
	
	}
	
	public static String getNextLink(Elements paragraphs, String url, List<String> visitedLinks){
	
	
		String nextLink = "";
		Elements links = paragraphs.select("a");
		for(int i = 0; i < links.size(); i++){
			
			boolean isItalics = links.get(i).hasAttr("i");
			
			if(!isItalics){
				String pulledLink = links.get(i).attr("href"); 
				if((pulledLink.contains("/wiki/")) &&  !(pulledLink.contains("Greek")) && !(pulledLink.contains("Latin"))){
				
					nextLink = "https://en.wikipedia.org" + links.get(i).attr("href");
				
					if(visitedLinks.contains(nextLink)){
					
						System.out.println("Oops! First link is a page we have already seen");
						return null;	
					}
					else{
						visitedLinks.add(nextLink);
						break;
					}
				}
			}
			
		}
		
		return nextLink;
	
	}	
	
	public static void printLinks(List<String> visitedLinks){
	
		for(String link: visitedLinks){
			
			System.out.println(link);
		}
	
	}
	
	
	
}



