package com.pm.telegrambot.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TelegramNotificationService {

    private final String TELEGRAM_BOT_TOKEN = "7157189030:AAGgsRHWd7DB0PMot4_wuE6sQUBnedp8R9M";
    private final String TELEGRAM_CHAT_ID = "793568653";
    private final String TELEGRAM_API_URL = "https://api.telegram.org/bot" + TELEGRAM_BOT_TOKEN + "/sendMessage";

    private final RestTemplate restTemplate;

    public TelegramNotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendNotification(String message) {
        try {
            System.out.println("📡 Preparing to send message to Telegram...");

            // Create the URL with message and chat ID
            String url = TELEGRAM_API_URL + "?chat_id=" + TELEGRAM_CHAT_ID + "&text=" + message;
            System.out.println("🔗 Telegram API URL: " + url);

            // Send the message using RestTemplate
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Log the response
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("✅ Telegram notification sent successfully! Response: " + response.getBody());
            } else {
                System.out.println("❌ Failed to send Telegram notification. Status Code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("❗ Error while sending Telegram notification: " + e.getMessage());
        }
    }
}
