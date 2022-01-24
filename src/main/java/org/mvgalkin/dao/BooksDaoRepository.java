package org.mvgalkin.dao;

import org.mvgalkin.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksDaoRepository extends PagingAndSortingRepository<Book, Long> {
    Page<Book> findByInfo_NameLikeIgnoreCase(String partOfName, Pageable pageable);
    Page<Book> findByInfo_Authors_NameLikeIgnoreCase(String authors_name, Pageable pageable);
    Page<Book> findByInfo_Genres_NameLikeIgnoreCase(String genres_name, Pageable pageable);
}