package org.mvgalkin.dao;

import org.mvgalkin.models.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenresDaoRepository extends CrudRepository<Genre, Long> {
}
