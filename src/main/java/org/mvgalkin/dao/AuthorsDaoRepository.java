package org.mvgalkin.dao;

import org.mvgalkin.models.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorsDaoRepository extends CrudRepository<Author, Long> {
}
