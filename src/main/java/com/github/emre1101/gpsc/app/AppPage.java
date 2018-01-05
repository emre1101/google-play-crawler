package com.github.emre1101.gpsc.app;

import java.util.Date;
import java.util.List;

/**
 * @author emreg
 */
public class AppPage {

	String packageName;
	String title;
	String developer;
	String developerId;
	String summary;
	String genreText;
	String genreId;
	String familyGenreText;
	String familyGenreId;
	Date crawledDate;

	String price;
	String icon;
	boolean offersIAP;
	boolean adSupported;

	String description;
	String version;
	String updated;
	String androidVersionText;
	String androidVersion;
	String contentRating;
	String size;
	String installs;

	String developerEmail;
	String developerWebsite;
	String developerAddress;

	int reviewsNumber;

	int score5;
	int score4;
	int score3;
	int score2;
	int score1;
	double score;

	String video;
	List<String> screenshotUrls;
	List<String> recentChangeList;
	List<Permission> permissionList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getDeveloperId() {
		return developerId;
	}

	public void setDeveloperId(String developerId) {
		this.developerId = developerId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getGenreText() {
		return genreText;
	}

	public void setGenreText(String genreText) {
		this.genreText = genreText;
	}

	public String getGenreId() {
		return genreId;
	}

	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}

	public String getFamilyGenreText() {
		return familyGenreText;
	}

	public void setFamilyGenreText(String familyGenreText) {
		this.familyGenreText = familyGenreText;
	}

	public String getFamilyGenreId() {
		return familyGenreId;
	}

	public void setFamilyGenreId(String familyGenreId) {
		this.familyGenreId = familyGenreId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isOffersIAP() {
		return offersIAP;
	}

	public void setOffersIAP(boolean offersIAP) {
		this.offersIAP = offersIAP;
	}

	public boolean isAdSupported() {
		return adSupported;
	}

	public void setAdSupported(boolean adSupported) {
		this.adSupported = adSupported;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getAndroidVersionText() {
		return androidVersionText;
	}

	public void setAndroidVersionText(String androidVersionText) {
		this.androidVersionText = androidVersionText;
	}

	public String getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}

	public String getContentRating() {
		return contentRating;
	}

	public void setContentRating(String contentRating) {
		this.contentRating = contentRating;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getInstalls() {
		return installs;
	}

	public void setInstalls(String installs) {
		this.installs = installs;
	}

	public String getDeveloperEmail() {
		return developerEmail;
	}

	public void setDeveloperEmail(String developerEmail) {
		this.developerEmail = developerEmail;
	}

	public String getDeveloperWebsite() {
		return developerWebsite;
	}

	public void setDeveloperWebsite(String developerWebsite) {
		this.developerWebsite = developerWebsite;
	}

	public String getDeveloperAddress() {
		return developerAddress;
	}

	public void setDeveloperAddress(String developerAddress) {
		this.developerAddress = developerAddress;
	}

	public int getReviewsNumber() {
		return reviewsNumber;
	}

	public void setReviewsNumber(int reviews) {
		this.reviewsNumber = reviews;
	}

	public int getScore5() {
		return score5;
	}

	public void setScore5(int score5) {
		this.score5 = score5;
	}

	public int getScore4() {
		return score4;
	}

	public void setScore4(int score4) {
		this.score4 = score4;
	}

	public int getScore3() {
		return score3;
	}

	public void setScore3(int score3) {
		this.score3 = score3;
	}

	public int getScore2() {
		return score2;
	}

	public void setScore2(int score2) {
		this.score2 = score2;
	}

	public int getScore1() {
		return score1;
	}

	public void setScore1(int score1) {
		this.score1 = score1;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public List<String> getScreenshotUrls() {
		return screenshotUrls;
	}

	public void setScreenshotUrls(List<String> screenshotUrls) {
		this.screenshotUrls = screenshotUrls;
	}

	public List<String> getRecentChangeList() {
		return recentChangeList;
	}

	public void setRecentChangeList(List<String> recentChangeList) {
		this.recentChangeList = recentChangeList;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Date getCrawledDate() {
		return crawledDate;
	}

	public void setCrawledDate(Date crawledDate) {
		this.crawledDate = crawledDate;
	}

	@Override
	public String toString() {
		return "AppPage [packageName=" + packageName + ", title=" + title + ", developer=" + developer
				+ ", developerId=" + developerId + ", summary=" + summary + ", genreText=" + genreText + ", genreId="
				+ genreId + ", familyGenreText=" + familyGenreText + ", familyGenreId=" + familyGenreId
				+ ", createDate=" + crawledDate + ", price=" + price + ", icon=" + icon + ", offersIAP=" + offersIAP
				+ ", adSupported=" + adSupported + ", description=" + description + ", version=" + version
				+ ", updated=" + updated + ", androidVersionText=" + androidVersionText + ", androidVersion="
				+ androidVersion + ", contentRating=" + contentRating + ", size=" + size + ", installs=" + installs
				+ ", developerEmail=" + developerEmail + ", developerWebsite=" + developerWebsite
				+ ", developerAddress=" + developerAddress + ", reviewsNumber=" + reviewsNumber + ", score5=" + score5
				+ ", score4=" + score4 + ", score3=" + score3 + ", score2=" + score2 + ", score1=" + score1 + ", score="
				+ score + ", video=" + video + ", screenshotUrls=" + screenshotUrls + ", recentChangeList="
				+ recentChangeList + ", getTitle()=" + getTitle() + ", getDeveloper()=" + getDeveloper()
				+ ", getDeveloperId()=" + getDeveloperId() + ", getSummary()=" + getSummary() + ", getGenreText()="
				+ getGenreText() + ", getGenreId()=" + getGenreId() + ", getFamilyGenreText()=" + getFamilyGenreText()
				+ ", getFamilyGenreId()=" + getFamilyGenreId() + ", getPrice()=" + getPrice() + ", getIcon()="
				+ getIcon() + ", isOffersIAP()=" + isOffersIAP() + ", isAdSupported()=" + isAdSupported()
				+ ", getDescription()=" + getDescription() + ", getVersion()=" + getVersion() + ", getUpdated()="
				+ getUpdated() + ", getAndroidVersionText()=" + getAndroidVersionText() + ", getAndroidVersion()="
				+ getAndroidVersion() + ", getContentRating()=" + getContentRating() + ", getSize()=" + getSize()
				+ ", getInstalls()=" + getInstalls() + ", getDeveloperEmail()=" + getDeveloperEmail()
				+ ", getDeveloperWebsite()=" + getDeveloperWebsite() + ", getDeveloperAddress()="
				+ getDeveloperAddress() + ", getReviewsNumber()=" + getReviewsNumber() + ", getScore5()=" + getScore5()
				+ ", getScore4()=" + getScore4() + ", getScore3()=" + getScore3() + ", getScore2()=" + getScore2()
				+ ", getScore1()=" + getScore1() + ", getScore()=" + getScore() + ", getVideo()=" + getVideo()
				+ ", getScreenshotUrls()=" + getScreenshotUrls() + ", getRecentChangeList()=" + getRecentChangeList()
				+ ", getPackageName()=" + getPackageName() + ", getCreateDate()=" + getCrawledDate() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
