package org.mylog.domain.series.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mylog.domain.blog.domain.Blog;
import org.mylog.domain.post.domain.Post;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
@Table(name = "series")
@NoArgsConstructor
@AllArgsConstructor
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "series_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    @OneToMany(mappedBy = "series")
    private List<Post> posts = new ArrayList<>();
}
