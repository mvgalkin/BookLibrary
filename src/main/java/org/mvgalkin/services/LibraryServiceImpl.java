package org.mvgalkin.services;

import org.mvgalkin.dao.BooksDaoRepository;
import org.mvgalkin.models.Book;
import org.mvgalkin.models.BookInfoView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BooksDaoRepository booksRepository;

    public LibraryServiceImpl(BooksDaoRepository booksRepository) {
        this.booksRepository = booksRepository;
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
        return booksRepository.save(book);
    }

    @Override
    public void update(long id, Book book) {
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
}
