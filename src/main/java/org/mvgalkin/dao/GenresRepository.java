package org.mvgalkin.dao;

import org.mvgalkin.models.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenresRepository extends CrudRepository<Genre, Long> {
}
