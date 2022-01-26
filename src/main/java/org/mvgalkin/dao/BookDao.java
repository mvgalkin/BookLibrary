package org.mvgalkin.dao;

import org.mvgalkin.models.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAll();
    List<Book> findByName(String name);
    List<Book> findByAuthor(String name);
    List<Book> findByGenre(String name);

    Book save(Book book);
    void update(long id, Book book);
    void deleteById(Long id);
    boolean existsById(Long id);
}