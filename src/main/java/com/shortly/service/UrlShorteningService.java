package com.shortly.service;

import com.shortly.domain.UrlMapping;
import com.shortly.repository.UrlMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UrlShorteningService {
    private final UrlMappingRepository urlMappingRepository;
    private final Random random = new Random();

    private static final Logger logger = LoggerFactory.getLogger(UrlShorteningService.class);

    @Autowired
    public UrlShorteningService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public UrlMapping createShortUrl(UrlMapping urlMapping) {
        // 1. Генерация уникального короткого кода
        String uniqueShortCode;
        do {
            uniqueShortCode = generateRandomShortCode();
        } while (urlMappingRepository.findByShortCode(uniqueShortCode).isPresent());

        // 2. Сохранение новой пары короткого и длинного URL-адресов в базе данных
        urlMapping.setShortCode(uniqueShortCode);
        urlMappingRepository.save(urlMapping);

        // 3. Логирование
        UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
        logger.info("URL сохранен в базе данных: {}", savedUrlMapping);
        return savedUrlMapping;

    }

    public Optional<UrlMapping> getLongUrlByShortCode(String shortCode) {
        // 1. Поиск записи UrlMapping по короткому коду в базе данных
        return urlMappingRepository.findByShortCode(shortCode);
    }

    private String generateRandomShortCode() {
        int codeLength = 6;
        StringBuilder shortCodeBuilder = new StringBuilder(codeLength);
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < codeLength; i++) {
            shortCodeBuilder.append(chars.charAt(random.nextInt(chars.length())));
        }
        return shortCodeBuilder.toString();
    }
}
