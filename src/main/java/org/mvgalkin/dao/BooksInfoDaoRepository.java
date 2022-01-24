/*
package org.mvgalkin.dao;

import org.mvgalkin.models.BookInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BooksInfoDaoRepository extends PagingAndSortingRepository<BookInfo, Long> {
    Page<BookInfo> findByNameIsLike(String partOfName, Pageable pageable);
    Page<BookInfo> findByAuthors_NameLike(String authors_name, Pageable pageable);
    Page<BookInfo> findByGenres_NameLike(String genres_name, Pageable pageable);
}
*/