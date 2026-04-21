package   urlshortener.controller;

import   urlshortener.entity.Url;
import   urlshortener.repository.UrlRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin("*")
public class AnalyticsController {

    private final UrlRepository urlRepository;

    public AnalyticsController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @GetMapping("/summary")
    public Map<String, Object> summary() {

        List<Url> all = urlRepository.findAll();

        long totalUrls = all.size();

        long totalClicks = all.stream()
                .mapToLong(Url::getClickCount)
                .sum();

        Map<String, Object> data = new HashMap<>();
        data.put("totalUrls", totalUrls);
        data.put("totalClicks", totalClicks);

        return data;
    }

    @GetMapping("/top-links")
    public List<Url> topLinks() {
        return urlRepository.findTop5ByOrderByClickCountDesc();
    }

    @GetMapping("/recent")
    public List<Url> recent() {
        return urlRepository.findTop10ByOrderByCreatedAtDesc();
    }
}