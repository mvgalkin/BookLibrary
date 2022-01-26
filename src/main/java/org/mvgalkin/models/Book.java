package org.mvgalkin.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Table("books")
@Data
public class Book implements BookInfoView {
    @Id
    private Long id;

    private String name;

/*
    private byte[] cover;

    private byte[] content;
 */

    @MappedCollection(idColumn = "book_id")
    private Set<AuthorRef> authors = new HashSet<>();

    public void addAuthor(AuthorRef author) {
        authors.add(new AuthorRef(author.getAuthorId()));
    }

    @MappedCollection(idColumn = "book_id")
    private Set<GenreRef> genres = new HashSet<>();

    public void addGenre(GenreRef genre) {
        genres.add(new GenreRef(genre.getGenreId()));
    }
}
