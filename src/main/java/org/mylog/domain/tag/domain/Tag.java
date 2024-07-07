package org.mylog.domain.tag.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mylog.domain.post.domain.PostTag;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@Entity
@Table(name = "tags")
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "tag")
    private Set<PostTag> postTags = new HashSet<>();
}
