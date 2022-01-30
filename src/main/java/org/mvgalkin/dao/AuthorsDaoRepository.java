package org.mvgalkin.dao;

import org.mvgalkin.models.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorsDaoRepository extends CrudRepository<Author, Long> {
    //todo: при улучшение модели безопасности (разделении пользователей на админов и пользователей)
    // разбелить доступ к методам через @PreAuthorize; доступ на редактирование только админам, на чтение всем
}
