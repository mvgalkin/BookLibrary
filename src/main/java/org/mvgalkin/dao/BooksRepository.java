package org.mvgalkin.dao;

import org.mvgalkin.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends CrudRepository<Book, Long> {
}
