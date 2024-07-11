package org.mylog.domain.tag.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mylog.domain.tag.domain.Tag;

@Getter
@Setter
@NoArgsConstructor
public class TagDto {
    private Long id;
    private String name;

    public TagDto(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
