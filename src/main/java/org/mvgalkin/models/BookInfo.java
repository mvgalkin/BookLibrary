package org.mvgalkin.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BookInfo {

    @Column(nullable = false)
    private String name;

    @Lob
    private byte[] cover;

    @ManyToMany(mappedBy = "books")
    private Set<Author> authors;

    @ManyToMany(mappedBy = "books")
    private Set<Genre> genres;
}
