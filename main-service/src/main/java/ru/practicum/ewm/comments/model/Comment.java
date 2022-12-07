package ru.practicum.ewm.comments.model;

import lombok.*;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.utils.State;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * <b>Комментарий.</b><br>
 * Возможность оставлять комментарии к событиям и модерировать их.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long id;
    @Column(nullable = false, length = 1000)
    private String text;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "published_on")
    private LocalDateTime publishedOn; //дата публикации
    @Column(name = "anonymous")
    @Builder.Default
    private Boolean anonymous = false; //checkbox видимости имени
    @Column(name = "state", nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private State state;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", author=" + author.getId() +
                ", event=" + event.getId() +
                ", createdOn=" + createdOn +
                ", publishedOn=" + publishedOn +
                ", anonymous=" + anonymous +
                ", state=" + state +
                '}';
    }
}
