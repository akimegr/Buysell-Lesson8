package com.shop.engine.userServices;

import com.shop.engine.repositories.FranchiseRepository;
import com.shop.engine.repositories.ProductRepository;
import com.shop.engine.userServices.services.ParsingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ParsingServiceImpl implements ParsingService {
    @Autowired
    FranchiseRepository franchiseRepository;

    @Autowired
    ProductRepository productRepository;

    private String linkProduct;
    private String titleProduct;
    private String descriptionProduct;
    private String priceProduct;
    private String cityProduct;
    private String dateOfCreatedProduct;



    @Override
    public String getLinkProduct() throws IOException {
        Document doc = Jsoup.connect("https://auto.kufar.by/").get();
        Elements postTitleElements = doc.getElementsByClass("styles_wrapper__eefVX");
        String href = "";
        for (Element element : postTitleElements) {
            Document first = Jsoup.connect(element.attr("href")).get();
            if(franchiseRepository.findByLink(element.attr("href"))==null
                && productRepository.findByTitle(first.title()).size()==0) {
                href = element.attr("href");
                break;
            }
        }



        linkProduct = href;
        return linkProduct;
    }

    @Override
    public String getTitleProduct(Document first) {
        titleProduct = first.title();
        return titleProduct;
    }

    @Override
    public String getDescriptionProduct(Document first) {
        Elements description = first.getElementsByAttributeValue("itemprop", "description");
        descriptionProduct= description.get(0).text();
        return descriptionProduct;
    }

    @Override
    public String getPriceProduct(Document first) {
        Elements priceElements = first.getElementsByClass("styles_main__PU1v4");
        priceProduct = priceElements.get(0).text();
        return priceProduct;
    }

    @Override
    public String getCityProduct(Document first) {
        Elements cityElements = first.getElementsByClass("styles_region__xM0OY");
        cityProduct = cityElements.get(0).text();
        return cityProduct;
    }

    @Override
    public LocalDateTime getDateOfCreatedProduct() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        dateOfCreatedProduct = dtf.format(now);
        return now;
    }

}
