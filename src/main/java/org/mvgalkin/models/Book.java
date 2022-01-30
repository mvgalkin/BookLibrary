package org.mvgalkin.models;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "books")
public class Book implements BookInfoView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    //todo: вынести в отдельный класс BookImage и добавить туда информацию о mimeType (которая поступает от клиента при загрузке документа)
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] cover;

    //todo: вынести в отдельный класс BookFile и добавить туда информацию о mimeType и о названии файла(для того, чтобы при скачивани пользователю генерировалось нормальное название)
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] content;

    // Изменение Set<> на List<> (или Collection<>) не подходит, т.к. есть ограничения Hibernate,
    // если требуется заменить на коллекции, то чтобы заработало придется сильно усложнять логику приложения,
    // подробнее о проблеме: https://www.baeldung.com/java-hibernate-multiplebagfetchexception
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    // Изменение Set<> на List<> (или Collection<>) не подходит, т.к. есть ограничения Hibernate,
    // если требуется заменить на коллекции, то чтобы заработало придется сильно усложнять логику приложения,
    // подробнее о проблеме: https://www.baeldung.com/java-hibernate-multiplebagfetchexception
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "books_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;
}
