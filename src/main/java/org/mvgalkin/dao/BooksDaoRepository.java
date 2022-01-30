package org.mvgalkin.dao;

import org.mvgalkin.models.Book;
import org.mvgalkin.models.BookInfoView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksDaoRepository extends PagingAndSortingRepository<Book, Long> {
    Page<BookInfoView> findByNameLikeIgnoreCase(String partOfName, Pageable pageable);

    Page<BookInfoView> findByAuthors_NameLikeIgnoreCase(String authors_name, Pageable pageable);

    Page<BookInfoView> findByGenres_NameLikeIgnoreCase(String genres_name, Pageable pageable);

    //todo: при улучшение модели безопасности (разделении пользователей на админов и пользователей)
    // разбелить доступ к методам через @PreAuthorize; доступ на редактирование только админам, на чтение всем
}