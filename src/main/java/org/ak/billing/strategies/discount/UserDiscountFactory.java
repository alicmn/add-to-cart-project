package org.ak.billing.strategies.discount;

import org.ak.billing.constants.UserTypes;

import java.util.EnumMap;
import java.util.Map;

public class UserDiscountFactory {
    private static final Map<UserTypes, UserDiscountStrategy> strategies = new EnumMap<>(UserTypes.class);

    static {
        strategies.put(UserTypes.AFFILIATE, new AffiliateDiscountStrategy());
        strategies.put(UserTypes.CUSTOMER, new CustomerDiscountStrategy());
        strategies.put(UserTypes.GOLD_CART, new GoldCartDiscountStrategy());
        strategies.put(UserTypes.SILVER_CART, new SilverCartDiscountStrategy());
    }

    public static UserDiscountStrategy getStrategy(UserTypes type) {
        return strategies.getOrDefault(type, new CustomerDiscountStrategy());
    }
}
