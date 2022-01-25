package org.mvgalkin.services;

import org.mvgalkin.models.Book;
import org.mvgalkin.models.BookInfoView;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface LibraryService {
    Iterable<BookInfoView> getBestBooks(Integer limit);
    Page<BookInfoView> getBooksByPage(Integer pageNumber, Integer pageSize);
    Optional<BookInfoView> getBookInfoView(long id);

    Optional<byte[]> getBookContent(long id);

    Page<BookInfoView> findBooksByName(String partOfName, Integer pageNumber, Integer pageSize);
    Page<BookInfoView> findBooksByAuthorName(String name, Integer pageNumber, Integer pageSize);
    Page<BookInfoView> findBooksByGenre(String genre, Integer pageNumber, Integer pageSize);

    Book save(Book book);
    void update(long id, Book book);
    void delete(long id);
    boolean isExists(long id);
}
