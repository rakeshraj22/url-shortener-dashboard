package   urlshortener.controller;

import   urlshortener.dto.UrlRequest;
import   urlshortener.dto.UrlResponse;
import   urlshortener.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping
@CrossOrigin("*")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/api/url/shorten")
    public UrlResponse shorten(@RequestBody UrlRequest request) {
        return urlService.createShortUrl(request);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

        String originalUrl = urlService.getOriginalUrl(shortCode);

        return ResponseEntity
                .status(302)
                .header(HttpHeaders.LOCATION, originalUrl)
                .build();
    }
}