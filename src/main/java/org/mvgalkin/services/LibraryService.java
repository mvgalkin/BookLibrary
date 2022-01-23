package org.mvgalkin.services;

import org.mvgalkin.models.Book;
import org.mvgalkin.models.BookInfo;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface LibraryService {
    Iterable<BookInfo> getBestBooks(Integer limit);
    Page<BookInfo> getBooksByPage(Integer pageNumber, Integer pageSize);
    Optional<BookInfo> getBookInfo(long id);

    Optional<byte[]> getBookContent(long id);

    Page<BookInfo> FindBooksByName(String partOfName, Integer pageNumber, Integer pageSize);
    Page<BookInfo> FindBooksByAuthorName(String name, Integer pageNumber, Integer pageSize);
    Page<BookInfo> FindBooksByGenre(String genre, Integer pageNumber, Integer pageSize);

    Book save(Book book);
    void update(long id, Book book);
    void delete(long id);
}
