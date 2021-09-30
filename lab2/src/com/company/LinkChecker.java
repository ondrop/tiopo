package com.company;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

public class LinkChecker {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Error");
            return;
        }

        ArrayList<String> allLinks = new ArrayList<String>();
        try {
            PrintWriter brokenLinksWriter = new PrintWriter("../../../data/broker-links.txt");
            PrintWriter noBrokenLinksWriter = new PrintWriter("../../../data/no-broken-links.txt");
            URL url = new URL(args[0]);
            allLinks.add(url.toString());
            boolean isBrokenLink = false;
            for (int i = 0; i < allLinks.toArray().length; i++) {
                String link = allLinks.get(i);
                isBrokenLink = checkLinks(link, allLinks);
                if (i == 0) {
                    continue;
                }

                if (isBrokenLink) {
                    brokenLinksWriter.append(link).append("\n");
                } else {
                    noBrokenLinksWriter.append(link).append("\n");
                }
            }

            brokenLinksWriter.flush();
            noBrokenLinksWriter.flush();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return;
        }

        System.out.println("Received " + (allLinks.toArray().length - 1)  + " links");
    }

    private static boolean checkLinks(String receivedUrl, ArrayList<String> allLinks) {
        final String[] linkAttributes = {"href", "icon", "manifest", "poster", "src", "cite", "action"};

        try {
            Connection.Response response = Jsoup.connect(receivedUrl).timeout(6000).execute();
            if (response.statusCode() != 200) {
                return true;
            }

            URL baseUrl = new URL(allLinks.get(0));
            URL currUrl = new URL(receivedUrl);
            if (!baseUrl.getHost().equals(currUrl.getHost()) || baseUrl.getPort() != currUrl.getPort()) {
                return false;
            }

            Document doc = response.parse();
            Elements pageLinks = doc.select("*[href], *[icon], *[manifest], *[poster], *[src], *[cite], *[action]");
            for (Element pageLink : pageLinks) {
                for (String linkAttr : linkAttributes) {
                    String link = pageLink.absUrl(linkAttr);
                    if (!link.isEmpty() && !allLinks.contains(link)) {
                        allLinks.add(link);
                    }
                }
            }
        } catch (Exception exception) {
            return true;
        }

        return false;
    }
}
