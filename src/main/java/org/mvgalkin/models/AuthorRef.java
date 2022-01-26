package org.mvgalkin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("books_authors")
@Data
@AllArgsConstructor
public class AuthorRef {
    @Column("author_id")
    Long authorId;
}
