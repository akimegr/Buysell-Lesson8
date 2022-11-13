package com.shop.engine.userServices.services;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.time.LocalDateTime;

public interface ParsingService {
    String getLinkProduct() throws IOException;
    String getTitleProduct(Document first);
    String getDescriptionProduct(Document first);
    String getPriceProduct(Document first);
    String getCityProduct(Document first);
    LocalDateTime getDateOfCreatedProduct();
}
