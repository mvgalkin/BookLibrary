package org.mvgalkin.dao;

import org.mvgalkin.models.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorsDaoRepository extends CrudRepository<Author, Long> {
}
