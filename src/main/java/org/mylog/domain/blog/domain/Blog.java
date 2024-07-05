package org.mylog.domain.blog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mylog.domain.post.domain.Post;
import org.mylog.domain.series.domain.Series;
import org.mylog.domain.user.domain.User;

import java.util.List;

@Getter
@Builder
@Entity
@Table(name = "blogs")
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long blogId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 200)
    private String intro;

    @Column(nullable = false, name = "profile_img")
    private String profileImg;

    @Column(length = 50)
    private String email;

    private String github;

    @Column(nullable = false, name = "is_deleted")
    private Boolean isDeleted;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Post> postList;

    @OneToMany(mappedBy = "blog")
    private List<Series> seriesList;

}
