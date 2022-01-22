package org.mvgalkin.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
