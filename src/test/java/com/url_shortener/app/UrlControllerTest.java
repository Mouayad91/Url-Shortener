package com.url_shortener.app;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.url_shortener.app.service.UrlService;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlService urlService;

    @BeforeEach
    public void setup() {
        String shortUrl = "AbCdEf";
        String originalUrl = "http://google.com";
        given(urlService.getOriginalUrl(shortUrl)).willReturn(originalUrl);
    }

    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
    }

    @Test
    public void testGetShortUrl() throws Exception {
        String shortUrl = "AbCdEf";
        String expectedOriginalUrl = "http://google.com";

        mockMvc.perform(get("/api/" + shortUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(expectedOriginalUrl));
    }

    @Test
    public void deleteExpiredUrls() throws Exception {
        mockMvc.perform(delete("/api/expired"))
               .andExpect(status().isNoContent());
    }


}
