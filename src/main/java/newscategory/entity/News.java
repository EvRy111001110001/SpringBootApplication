package newscategory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "news")
public class News {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "title")
        private String title;

        @Column(name = "text")
        private String text;

        @ManyToOne
        @JoinColumn(name = "category_id")// внешний ключ
        private Category category;

        @CreationTimestamp
        @Column(name = "creation_time")
        private Instant date;
}
