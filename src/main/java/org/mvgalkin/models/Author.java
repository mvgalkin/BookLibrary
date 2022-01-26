package org.mvgalkin.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("authors")
@Data
public class Author {
    @Id
    private Long id;

    private String name;
}
