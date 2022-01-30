package org.mvgalkin.services;

import org.mvgalkin.models.Book;
import org.mvgalkin.models.BookInfoView;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface LibraryService {
    Iterable<BookInfoView> getBestBooks(int limit);
    Page<BookInfoView> getBooksByPage(int pageNumber, int pageSize);
    Optional<BookInfoView> getBookInfoView(long id);

    Optional<byte[]> getBookContent(long id);
    Optional<byte[]> getBookCover(long id);


    //Заменил, Integer на int, хотя т.к. в БЛ не используется и просто пробрасывается до DAO пока особого смысла не увидел ... надо по профилировать и сравнить
    Page<BookInfoView> findBooksByName(String partOfName, int pageNumber, int pageSize);
    Page<BookInfoView> findBooksByAuthorName(String name, int pageNumber, int pageSize);
    Page<BookInfoView> findBooksByGenre(String genre, int pageNumber, int pageSize);

    Book save(Book book);
    void update(long id, Book book);
    void delete(long id);

    boolean isExists(long id);
}
