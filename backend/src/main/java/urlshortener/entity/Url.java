package urlshortener.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String originalUrl;

    @Column(unique = true, length = 20)
    private String shortCode;

    @Column(nullable = false)
    private Long clickCount = 0L;

    private LocalDateTime createdAt;
}