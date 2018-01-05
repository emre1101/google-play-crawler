package com.github.emre1101.gpsc.app;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.emre1101.gpsc.crawler.NPMUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author emreg
 */
public class AppPageParser {

	public static AppPage parse(String packageName, String html) {
		Document doc = Jsoup.parse(html);
		Elements detailsInfo = doc.select(".details-info");
		String title = detailsInfo.select(".document-title").text().trim();
		String developer = detailsInfo.select("span[itemprop=\"name\"]").text();
		String developerId = detailsInfo.select(".primary").attr("href").split("id=")[1];
		String summary = doc.select("meta[name=\"description\"]").attr("content");

		Element mainGenre = detailsInfo.select(".category").first();
		String genreText = mainGenre.text().trim();
		String genreId = mainGenre.attr("href").split("/")[4];

		Elements familyGenre = detailsInfo.select(".category[href*=\"FAMILY\"]");
		String familyGenreText = "undefined";
		String familyGenreId = "undefined";

		if (familyGenre.isEmpty() == false) {
			familyGenreText = familyGenre.text().trim();
			familyGenreId = familyGenre.attr("href").split("/")[4];
		}

		String price = detailsInfo.select("meta[itemprop=price]").attr("content");
		String icon = detailsInfo.select("img.cover-image").attr("src");
		boolean offersIAP = detailsInfo.select(".inapp-msg").isEmpty() == false;
		boolean adSupported = detailsInfo.select(".ads-supported-label-msg").isEmpty() == false;

		Elements additionalInfo = doc.select(".details-section-contents");
		String description = additionalInfo.select("div[itemprop=description] div").text().trim();
		String version = additionalInfo.select("div.content[itemprop=\"softwareVersion\"]").text().trim();
		String updated = additionalInfo.select("div.content[itemprop=\"datePublished\"]").text().trim();
		String androidVersionText = additionalInfo.select("div.content[itemprop=\"operatingSystems\"]").text().trim();
		String androidVersion = normalizeAndroidVersion(androidVersionText);
		String contentRating = additionalInfo.select("div.content[itemprop=\"contentRating\"]").text().trim();
		String size = additionalInfo.select("div.content[itemprop=\"fileSize\"]").text().trim();

		long maxInstalls, minInstalls;
		String installs = "undefined";
		boolean preregister = doc.select(".preregistration-container").isEmpty();
		if (preregister == true) {
			installs = additionalInfo.select("div.content[itemprop=\"numDownloads\"]").text().trim();
			// minInstalls = cleanInt(installs[0]);
			// maxInstalls = cleanInt(installs[1]);
		}

		String developerEmail = additionalInfo.select(".dev-link[href^=\"mailto:\"]").attr("href");
		if (developerEmail != null && developerEmail.length() != 0) {
			developerEmail = developerEmail.split(":")[1];
		} else
			developerEmail = "undefined";

		String developerWebsite = additionalInfo.select(".dev-link[href^=\"http\"]").attr("href");
		if (developerWebsite != null && developerWebsite.length() != 0) {
			developerWebsite = parseDeveloperWebsite(developerWebsite);
		} else
			developerWebsite = "undefined";

		String developerAddress = additionalInfo.select(".physical-address").text().trim();

		Elements ratingBox = doc.select(".rating-box");
		int reviews = cleanInt(ratingBox.select("span.reviews-num").text());

		Elements ratingHistogram = doc.select(".rating-histogram");
		int score5 = cleanInt(ratingHistogram.select(".five .bar-number").text());
		int score4 = cleanInt(ratingHistogram.select(".four .bar-number").text());
		int score3 = cleanInt(ratingHistogram.select(".three .bar-number").text());
		int score2 = cleanInt(ratingHistogram.select(".two .bar-number").text());
		int score1 = cleanInt(ratingHistogram.select(".one .bar-number").text());
		double score = Double.parseDouble(ratingBox.select("div.score").text().replace(',', '.'));

		String video = doc.select(".screenshots span.preview-overlay-container[data-video-url]").attr("data-video-url");
		if (video != null && video.length() > 0) {
			video = video.split("\\?")[0];
		} else
			video = "undefined";

		Elements screenshots = doc.select(".thumbnails .screenshot");
		List<String> screenshotUrls = new ArrayList<String>();
		for (int i = 0; i < screenshots.size(); i++) {
			screenshotUrls.add(screenshots.get(i).attr("src"));
		}
		Elements recentChanges = doc.select(".recent-change");
		List<String> recentChangeList = new ArrayList<String>();
		for (int i = 0; i < recentChanges.size(); i++) {
			recentChangeList.add(recentChanges.get(i).text());
		}
		
		List<Permission> permissions = getPermissions(packageName);

		AppPage appPage = new AppPage();
		appPage.adSupported = adSupported;
		appPage.androidVersion = androidVersion;
		appPage.androidVersionText = androidVersionText;
		appPage.contentRating = contentRating;
		appPage.crawledDate = new Date();
		appPage.description = description;
		appPage.developer = developer;
		appPage.developerAddress = developerAddress;
		appPage.developerWebsite = developerWebsite;
		appPage.developerEmail = developerEmail;
		appPage.developerId = developerId;
		appPage.familyGenreId = familyGenreId;
		appPage.familyGenreText = familyGenreText;
		appPage.genreText = genreText;
		appPage.genreId = genreId;
		appPage.icon = icon;
		appPage.installs = installs;
		appPage.offersIAP = offersIAP;
		appPage.packageName = packageName;
		appPage.permissionList = permissions;
		appPage.price = price;
		appPage.recentChangeList = recentChangeList;
		appPage.reviewsNumber = reviews;
		appPage.score = score;
		appPage.score1 = score1;
		appPage.score2 = score2;
		appPage.score3 = score3;
		appPage.score4 = score4;
		appPage.score5 = score5;
		appPage.screenshotUrls = screenshotUrls;
		appPage.size = size;
		appPage.summary = summary;
		appPage.title = title;
		appPage.updated = updated;
		appPage.version = version;
		appPage.video = video;


		return appPage;
	}

	private static String parseDeveloperWebsite(String url) {
		String website = null;
		StringTokenizer tokenizer = new StringTokenizer(url, "&=");
		while (tokenizer.hasMoreTokens()) {
			String tok = tokenizer.nextToken();
			if (tok.endsWith("?q"))
				website = tokenizer.nextToken();
		}
		return website;
	}

	private static int cleanInt(String number) {
		number = number.replace(".", "");
		number = number.replace(",", "");
		return Integer.parseInt(number);
	}

	private static String normalizeAndroidVersion(String androidVersionText) {
		// let matches = androidVersionText.match(/^([0-9\.]+)[^0-9\.].+/);

		// if (!matches || typeof matches[1] === 'undefined') {
		// return 'VARY';
		// }

		// return matches[1];

		return androidVersionText;
	}

	public static List<Permission> getPermissions(String packageName) {
		HttpGet httpGet = new HttpGet(NPMUtil.getInstance().getUrl() + "/apps/" + packageName + "/permissions");

		try {
			CloseableHttpResponse response;
			CloseableHttpClient client = getClientWithSSL();
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String jsonString = EntityUtils.toString(entity);
				JsonParser jsonParser = new JsonParser();
				JsonObject objectFromString = jsonParser.parse(jsonString).getAsJsonObject();
				// System.out.println("----"+objectFromString.get("results"));
				Permission[] permArr = (new Gson()).fromJson(objectFromString.get("results").toString(), Permission[].class);
				return Arrays.asList(permArr);
			}
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<>();

	}

	private static CloseableHttpClient getClientWithSSL()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		HttpClientBuilder b = HttpClientBuilder.create();
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

			public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				return true;
			}
		}).build();
		b.setSslcontext(sslContext);

		HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
		SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory)
				.build();

		PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		b.setConnectionManager(connMgr);

		CloseableHttpClient client = b.build();
		return client;
	}


}
