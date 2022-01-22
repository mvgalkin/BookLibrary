package org.mvgalkin.services;

import org.mvgalkin.models.Book;
import org.mvgalkin.models.BookInfo;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.Set;

public interface LibraryService {
    Set<BookInfo> getBestBooks(Integer limit);
    Page<BookInfo> getBooksByPage(Integer pageNumber, Integer pageSize);
    Optional<BookInfo> getBookInfo(long id);

    byte[] getBookContent(long id);

    Book save(Book product);
    void update(long id, Book product);
    void delete(long id);
}
