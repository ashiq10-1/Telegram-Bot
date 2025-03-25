package com.pm.telegrambot.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProductCheckerService {

    @Value("${product_url}")
    String productUrl;

    public boolean isProductInStock() throws IOException {
        // Fetch the product page
        Document doc = Jsoup.connect(productUrl).get();

        // Check if "Notify Me" button is present (out of stock)
        Elements notifyMeButton = doc.select("#notifyMe");

        // If "Notify Me" button exists, product is out of stock
        if (!notifyMeButton.isEmpty()) {
            System.out.println("Product is still out of stock.");
            return false;
        }

        // Check if "Add to Cart" button is present when product is in stock
        Elements addToCartButton = doc.select("a.add-to-cart.update_cart_product");

        // Product is available if "Add to Cart" button exists
        if (!addToCartButton.isEmpty()) {
            System.out.println("✅ Product is in stock! 🎉");
            return true;
        }

        // Default: Product not available
        System.out.println("Product availability could not be determined.");
        return false;
    }
}

