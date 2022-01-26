package org.mvgalkin.dao;

import org.mvgalkin.models.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorsRepository extends CrudRepository<Author, Long> {
}
