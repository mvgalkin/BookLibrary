package org.mvgalkin.dao;

import org.mvgalkin.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BooksDaoRepository extends CrudRepository<Book, Long> {
}