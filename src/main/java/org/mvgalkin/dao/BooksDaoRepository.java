package org.mvgalkin.dao;

import org.mvgalkin.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BooksDaoRepository extends PagingAndSortingRepository<Book, Long> {
    Page<Book> findByName(Pageable pageable, String bookName);
    //Page<Book> findByAuthors(Pageable pageable, String authorName);
    //Page<Book> findByGenres(Pageable pageable, String genreName);
}
