package com.shop.prices.services.servicesImpl;

import com.shop.engine.models.Discount;
import com.shop.prices.services.PriceBehavior;

public class PriceBehaviorImpl implements PriceBehavior {
    @Override
    public boolean checkAvailablePrice() {
        return false;
    }

    @Override
    public double setDiscount(Discount discount) {
        return 0;
    }

    @Override
    public double buyProduct(double productPrice) {
        return 0;
    }
}
