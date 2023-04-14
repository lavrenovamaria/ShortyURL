package com.shortly.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortly.domain.UrlMapping;
import com.shortly.service.UrlShorteningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UrlShorteningController.class)
public class UrlShorteningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UrlShorteningService urlShorteningService;

    private UrlMapping testUrlMapping;

    @BeforeEach
    void setUp() {
        testUrlMapping = new UrlMapping();
        testUrlMapping.setShortCode("test123");
        testUrlMapping.setLongUrl("https://www.example.com");
    }

    @Test
    void shouldCreateShortUrl() throws Exception {
        when(urlShorteningService.createShortUrl(any(UrlMapping.class))).thenReturn(testUrlMapping);

        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUrlMapping)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortCode").value("test123"))
                .andExpect(jsonPath("$.longUrl").value("https://www.example.com"));
    }

    @Test
    void shouldRedirectToLongUrl() throws Exception {
        when(urlShorteningService.getLongUrlByShortCode("test123")).thenReturn(Optional.of(testUrlMapping));

        mockMvc.perform(get("/{shortCode}", "test123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("https://www.example.com"));
    }

    @Test
    void shouldReturnNotFoundForInvalidShortCode() throws Exception {
        when(urlShorteningService.getLongUrlByShortCode("invalidCode")).thenReturn(Optional.empty());

        mockMvc.perform(get("/{shortCode}", "invalidCode"))
                .andExpect(status().isNotFound());
    }
}

