/*
VideoParser for the main task
 */

package com.artyom;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class VideoParser {

    private static String URL = "https://www.udemy.com/course/learn-flutter-dart-to-build-ios-android-apps/";

    private static Map<String, String> sortByDuration(final Map<String, String> myMap) {
        return myMap.entrySet()
                .stream()
                .sorted((Map.Entry.<String, String>comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public static void main(String[] args) throws IOException {

        Map<String,String> myMap = new HashMap<String, String>();

        ArrayList<String> Items = new ArrayList<String>();
        ArrayList<String> Items2 = new ArrayList<String>();
        ArrayList<String> Items3 = new ArrayList<String>();

        try {
            Document doc = Jsoup.connect(URL).get();

            String title = doc.title();
            System.out.println(title);

            System.out.println("\nGetting info about first 5 videos... \n");

            Elements video_titles = doc.select("div.lecture-container")
                    .select("div.left-content")
                    .select("div.title");

            Elements video_annotation = doc.select("div.lecture-container")
                    .select("div.left-content")
                    .select("div.description");

            Elements video_duration = doc.select("div.lecture-container")
                    .select("div.details")
                    .select("span.content-summary");

            for (Element titles : video_titles.subList(0, 5)) {
                String TITLE = titles.text();
                Items.add(TITLE);
            }

            for (Element annotation : video_annotation.subList(0, 5)) {
                String ANNOTATION = annotation.text();
                Items2.add(ANNOTATION);
            }

            for (Element duration : video_duration.subList(0, 5)) {
                String DURATION = duration.text();
                Items3.add(DURATION);
            }

            for (int i = 0; i < Items.size(); i++) {
                System.out.println("Title: " + Items.get(i));
                System.out.println("Annotation: " + Items2.get(i));
                System.out.println("Duration: " + Items3.get(i) + "\n");
                myMap.put(Items.get(i), Items3.get(i));
            }

            final Map<String, String> resultMap = sortByDuration(myMap);
            System.out.println("Sorted videos by duration: \n" + resultMap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
