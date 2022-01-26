package org.mvgalkin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("books_genres")
@Data
@AllArgsConstructor
public class GenreRef {
    @Column("genre_id")
    Long genreId;
}
