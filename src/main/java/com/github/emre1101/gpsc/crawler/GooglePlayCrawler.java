package com.github.emre1101.gpsc.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.http.Header;

import com.github.emre1101.gpsc.app.AppPageMapper;
import com.github.emre1101.gpsc.app.AppPageParser;
import com.github.emre1101.gpsc.data.MongoDBUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @author emreg
 */
public class GooglePlayCrawler extends WebCrawler {

    private static final Pattern FILTER_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png|js|css)$");

    public static Map<String,String> apps = new ConcurrentHashMap<String, String>();

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        if (FILTER_EXTENSIONS.matcher(href).matches()) {
            return false;
        }
        boolean isHrefCheckOK =  href.startsWith("https://play.google.com/store/apps/details?id=");
        String packageName = parsePackageName(href);
        boolean isNewPackage = packageName!= null && !apps.containsKey(packageName);
        if(isNewPackage && isHrefCheckOK){
            apps.put(packageName,packageName);
            return true;
        }
        return false;
    }

    @Override
    public void visit(Page page) {
        int docid = page.getWebURL().getDocid();
        String url = page.getWebURL().getURL();
        String domain = page.getWebURL().getDomain();
        String path = page.getWebURL().getPath();
        String subDomain = page.getWebURL().getSubDomain();
        String parentUrl = page.getWebURL().getParentUrl();
        String anchor = page.getWebURL().getAnchor();

        String packageName = parsePackageName(url);


        logger.debug("Package '{}'", packageName);
        logger.debug("Docid: {}", docid);
        logger.info("URL: {}", url);
        logger.debug("Domain: '{}'", domain);
        logger.debug("Sub-domain: '{}'", subDomain);
        logger.debug("Path: '{}'", path);
        logger.debug("Parent page: {}", parentUrl);
        logger.debug("Anchor text: {}", anchor);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            logger.debug("Text length: {}", text.length());
            logger.debug("Html length: {}", html.length());
            logger.debug("Number of outgoing links: {}", links.size());
            MongoDBUtil.getInstance().insert(AppPageMapper.toDocument(AppPageParser.parse(packageName,html)));
        }

        Header[] responseHeaders = page.getFetchResponseHeaders();
        if (responseHeaders != null) {
            logger.debug("Response headers:");
            for (Header header : responseHeaders) {
                logger.debug("\t{}: {}", header.getName(), header.getValue());
            }
        }

        logger.debug("=============");
    }

    private String parsePackageName(String url){
        String packageName = null;
        StringTokenizer tokenizer = new StringTokenizer(url,"&=");
        while(tokenizer.hasMoreTokens()){
            String tok = tokenizer.nextToken();
            if(tok.endsWith("?id"))
                packageName = tokenizer.nextToken();
        }
        return packageName;
    }
}
