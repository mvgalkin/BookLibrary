package org.mvgalkin.services;

import org.mvgalkin.dao.AuthorsDaoRepository;
import org.mvgalkin.dao.BooksDaoRepository;
import org.mvgalkin.dao.GenresDaoRepository;
import org.mvgalkin.models.Author;
import org.mvgalkin.models.Book;
import org.mvgalkin.models.BookInfoView;
import org.mvgalkin.models.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class LibraryServiceImpl implements LibraryService {

    private final BooksDaoRepository booksRepository;
    private final AuthorsDaoRepository authorsRepository;
    private final GenresDaoRepository genresRepository;

    public LibraryServiceImpl(BooksDaoRepository booksRepository, AuthorsDaoRepository authorsRepository, GenresDaoRepository genresRepository) {
        this.booksRepository = booksRepository;
        this.authorsRepository = authorsRepository;
        this.genresRepository = genresRepository;
    }

    @Override
    public List<BookInfoView> getBestBooks(Integer limit) {
        Iterable<Book> allBooksSource = booksRepository.findAll();
        List<BookInfoView> bestBooks = new ArrayList<>();
        if (booksRepository.count()<=limit) {
            allBooksSource.forEach(bestBooks::add);
            return bestBooks;
        } else {
            allBooksSource.forEach(bestBooks::add);
            return getRandomElements(bestBooks, limit);
        }
    }


    public List<BookInfoView> getRandomElements(List<BookInfoView> list, int totalItems)
    {
        Random rand = new Random();

        List<BookInfoView> newList = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {

            int randomIndex = rand.nextInt(list.size());

            newList.add(list.get(randomIndex));

            list.remove(randomIndex);
        }
        return newList;
    }

    @Override
    public Page<BookInfoView> getBooksByPage(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return booksRepository.findAll(pageable).map(book -> book);

    }

    @Override
    public Optional<BookInfoView> getBookInfoView(long id) {
        return booksRepository.findById(id).map(book -> book);
    }

    @Override
    public Optional<byte[]> getBookContent(long id) {
        Optional<Book> book = booksRepository.findById(id);
        if (book.isEmpty()) {
            return null;
        } else {
           var content = book.get().getContent();
           if (content==null) {
               return Optional.empty();
           } else {
               return Optional.of(book.get().getContent());
           }
        }
    }

    @Override
    public Page<BookInfoView> findBooksByName(String partOfName, Integer pageNumber, Integer pageSize) {
        partOfName = "%"+partOfName+"%";
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return booksRepository.findByNameLikeIgnoreCase(partOfName, pageable);
    }

    @Override
    public Page<BookInfoView> findBooksByAuthorName(String name, Integer pageNumber, Integer pageSize) {
        name = "%"+name+"%";
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return booksRepository.findByAuthors_NameLikeIgnoreCase(name, pageable);
    }

    @Override
    public Page<BookInfoView> findBooksByGenre(String genre, Integer pageNumber, Integer pageSize) {
        genre = "%"+genre+"%";
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return booksRepository.findByGenres_NameLikeIgnoreCase(genre, pageable);
    }

    @Override
    public Book save(Book book) {
        var savedAuthors = new HashSet<Author>();
        authorsRepository.saveAll(book.getAuthors()).forEach(savedAuthors::add);
        var savedGenres = new HashSet<Genre>();
        genresRepository.saveAll(book.getGenres()).forEach(savedGenres::add);
        book.setAuthors(savedAuthors);
        book.setGenres(savedGenres);
        return booksRepository.save(book);
    }

    @Override
    public void update(long id, Book book) {
        var savedAuthors = new HashSet<Author>();
        authorsRepository.saveAll(book.getAuthors()).forEach(savedAuthors::add);
        var savedGenres = new HashSet<Genre>();
        genresRepository.saveAll(book.getGenres()).forEach(savedGenres::add);
        book.setAuthors(savedAuthors);
        book.setGenres(savedGenres);
        booksRepository.save(book);
    }

    @Override
    public void delete(long id) {
        booksRepository.deleteById(id);
    }

    @Override
    public boolean isExists(long id) {
        return booksRepository.existsById(id);
    }

    @Override
    public Optional<byte[]> getBookCover(long id) {
        Optional<Book> book = booksRepository.findById(id);
        if (book.isEmpty()) {
            return null;
        } else {
            var content = book.get().getCover();
            if (content==null) {
                return Optional.empty();
            } else {
                return Optional.of(book.get().getCover());
            }
        }
    }
}
