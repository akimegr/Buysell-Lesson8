package com.shop.prices.services;

import com.shop.engine.models.Discount;

public interface PriceBehavior {
    boolean checkAvailablePrice();
    double setDiscount(Discount discount);
    double buyProduct(double productPrice);
}
