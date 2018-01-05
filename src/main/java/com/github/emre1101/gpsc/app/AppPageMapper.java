package com.github.emre1101.gpsc.app;

import org.bson.Document;

public class AppPageMapper {

	public static Document toDocument(AppPage page){
		 Document doc = new Document("packageName", page.packageName)
	                .append("title", page.title)
	                .append("developer", page.developer)
	                .append("developerId", page.developerId)
	                .append("developerEmail", page.developerEmail)
	                .append("developerWebsite", page.developerWebsite)
	                .append("developerAddress", page.developerAddress)
	                .append("summary", page.summary)
	                .append("genreText", page.genreText)
	                .append("genreId", page.genreId)
	                .append("familyGenreText", page.familyGenreText)
	                .append("familyGenreId", page.familyGenreId)
	                .append("createDate", page.crawledDate)
	                .append("price", page.price)
	                .append("icon", page.icon)
	                .append("offersIAP", page.offersIAP)
	                .append("adSupported", page.adSupported)
	                .append("description", page.description)
	                .append("version", page.version)
	                .append("updated", page.updated)
	                .append("androidVersionText", page.androidVersionText)
	                .append("androidVersion", page.androidVersion)
	                .append("contentRating", page.contentRating)
	                .append("size", page.size)
	                .append("installs", page.installs)
	                .append("reviewsNumber", page.reviewsNumber)
	                .append("score5", page.score5)
	                .append("score4", page.score4)
	                .append("score3", page.score3)
	                .append("score2", page.score2)
	                .append("score1", page.score1)
	                .append("score", page.score)
	                .append("video", page.video)
	                .append("screenshotUrls", page.screenshotUrls)
	                .append("recentChangeList", page.recentChangeList)
         			.append("permissionList", page.permissionList);
		 
		return doc;
		
	}
	
	
}
