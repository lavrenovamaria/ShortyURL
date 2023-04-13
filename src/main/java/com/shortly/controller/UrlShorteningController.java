package com.shortly.controller;

import com.shortly.domain.UrlMapping;
import com.shortly.service.UrlShorteningService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UrlShorteningController {
    private final UrlShorteningService urlShorteningService;

    @Autowired
    public UrlShorteningController(UrlShorteningService urlShorteningService) {
        this.urlShorteningService = urlShorteningService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlMapping> shortenUrl(@RequestBody UrlMapping urlMapping) {
        UrlMapping shortUrlMapping = urlShorteningService.createShortUrl(urlMapping);
        return new ResponseEntity<>(shortUrlMapping, HttpStatus.CREATED);
    }

    @GetMapping("/{shortCode}")
    public void redirectToLongUrl(HttpServletResponse response, @PathVariable String shortCode) throws IOException, URISyntaxException {
        Optional<UrlMapping> urlMapping = urlShorteningService.getLongUrlByShortCode(shortCode);
        if (urlMapping.isPresent()) {
            response.sendRedirect(urlMapping.get().getLongUrl());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "The short URL does not exist.");
        }
    }

    @GetMapping("/")
    public ResponseEntity<Resource> serveIndexFile() {
        Resource indexFile = new ClassPathResource("static/index.html");
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(indexFile);
    }
}
