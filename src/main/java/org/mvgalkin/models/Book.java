package org.mvgalkin.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private byte[] cover;

    @Lob
    private byte[] content;

    @ManyToMany(targetEntity = Author.class)
    private Set<Author> authors;

    @ManyToMany(targetEntity = Genre.class)
    private Set<Genre> genres;

}
