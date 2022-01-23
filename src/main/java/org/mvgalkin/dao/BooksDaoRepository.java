package org.mvgalkin.dao;

import org.mvgalkin.models.Book;
import org.springframework.data.repository.CrudRepository;

public interface BooksDaoRepository extends CrudRepository<Book, Long> {
    /* по идее должно работать так и даже через Page<BookInfo>, но IDE выдает предупреждение, что неправильная проекция ..., поэтому разделим на 2 репозитория
    Page<BookInfoView> findByInfo_NameIsLike(String partOfName, Pageable pageable);
    Page<BookInfoView> findByInfo_Authors_NameLike(String authors_name, Pageable pageable);
    Page<BookInfoView> findByInfo_Genres_NameLike(String genres_name, Pageable pageable);
     */
}