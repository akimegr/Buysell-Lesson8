package com.shop.engine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws IOException {
        final String PATH_FOR_IMG = "C:/Users/akime/OneDrive/Рабочий стол/5 sem/courses/test/archiv/FranschiseImages";


        Document doc = Jsoup.connect("https://auto.kufar.by/").get();
        Elements postTitleElements = doc.getElementsByClass("styles_wrapper__eefVX");
//        postTitleElements.forEach(postTitleElement -> System.out.println(postTitleElement.attr("href")));
        //3 картинки
        String href = postTitleElements.get(4).attr("href").toString();
        Document first = Jsoup.connect(href).get();
        String name = first.title();
        Elements description = first.getElementsByAttributeValue("itemprop", "description");
        String descriptionString = description.get(0).text();
        Elements priceElements = first.getElementsByClass("styles_main__PU1v4");
        String priceString = priceElements.get(0).text();


        Elements cityElements = first.getElementsByClass("styles_region__xM0OY");
        String city = priceElements.get(0).text();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String date = dtf.format(now);


//
//        Elements imgs = first.getElementsByClass("styles_slide__image__lc2v_");
//        for (Element img:imgs) {
//            String imagine = img.absUrl("src");
//            String[] fragments = imagine.split("\\/");
//            String fileName = fragments[fragments.length - 1].replace(":" , "").replace("?", "");
//            try {
//                URL url = new URL(imagine);
//                InputStream in = url.openStream();
//                OutputStream out = new BufferedOutputStream(new FileOutputStream(PATH_FOR_IMG + fileName));
//                for (int b; (b = in.read()) != -1; ) {
//                    out.write(b);
//                }
//                out.close();
//                in.close();
//            }
//            catch (Exception exception) {
//                exception.printStackTrace();
//            }
//        }




    }
}
