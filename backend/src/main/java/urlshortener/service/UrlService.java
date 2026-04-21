package   urlshortener.service;

import   urlshortener.dto.UrlRequest;
import   urlshortener.dto.UrlResponse;
import   urlshortener.entity.Url;
import   urlshortener.repository.UrlRepository;
import   urlshortener.util.Base62Encoder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final StringRedisTemplate redisTemplate;

    public UrlService(UrlRepository urlRepository,
                      StringRedisTemplate redisTemplate) {
        this.urlRepository = urlRepository;
        this.redisTemplate = redisTemplate;
    }

    public UrlResponse createShortUrl(UrlRequest request) {

        Url url = Url.builder()
                .originalUrl(request.getOriginalUrl())
                .clickCount(0L)
                .createdAt(LocalDateTime.now())
                .build();

        url = urlRepository.save(url);

        String shortCode = Base62Encoder.encode(url.getId());

        url.setShortCode(shortCode);
        urlRepository.save(url);

        redisTemplate.opsForValue()
                .set(shortCode, url.getOriginalUrl(), 1, TimeUnit.DAYS);

        return new UrlResponse(
                url.getOriginalUrl(),
                shortCode,
                "http://localhost:8080/" + shortCode
        );
    }

    public String getOriginalUrl(String shortCode) {

        String cachedUrl =
                redisTemplate.opsForValue().get(shortCode);

        if (cachedUrl != null) {
            Url url = urlRepository.findByShortCode(shortCode).orElseThrow();
            url.setClickCount(url.getClickCount() + 1);
            urlRepository.save(url);

            return cachedUrl;
        }

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        url.setClickCount(url.getClickCount() + 1);
        urlRepository.save(url);

        redisTemplate.opsForValue()
                .set(shortCode, url.getOriginalUrl(), 1, TimeUnit.DAYS);

        return url.getOriginalUrl();
    }
}