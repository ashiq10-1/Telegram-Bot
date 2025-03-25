package com.pm.telegrambot.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ProductAvailabilityScheduler {

    private final ProductCheckerService productCheckerService;
    private final TelegramNotificationService telegramNotificationService;

    public ProductAvailabilityScheduler(ProductCheckerService productCheckerService, TelegramNotificationService telegramNotificationService) {
        this.productCheckerService = productCheckerService;
        this.telegramNotificationService = telegramNotificationService;
    }

    @Scheduled(fixedRate = 60000) // Check every 60 seconds
    public void checkProductAvailability() {
        try {
            if (productCheckerService.isProductInStock()) {
                String productUrl = productCheckerService.productUrl;
                String message = "🎉 The product is available! Buy now:\n" + productUrl;

                System.out.println("✅ Product is in stock! 🎉");

                // Send Telegram notification every 60 seconds if in stock
                System.out.println("📢 Sending notification...");
                telegramNotificationService.sendNotification(message);
                System.out.println("✅ Notification sent successfully!");

            } else {
                System.out.println("❗ Product is still out of stock.");
            }
        } catch (IOException e) {
            System.err.println("❌ Error while checking product availability: " + e.getMessage());
        }
    }
}
