package org.mvgalkin.services;

import org.mvgalkin.dao.BooksDaoRepository;
import org.mvgalkin.models.Book;
import org.mvgalkin.models.BookInfo;
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
    public List<BookInfo> getBestBooks(Integer limit) {
        Iterable<Book> allBooksSource = booksRepository.findAll();
        List<BookInfo> bestBooks = new ArrayList<>();
        if (booksRepository.count()<=limit) {
            allBooksSource.forEach(b -> bestBooks.add(b.getInfo()));
            return bestBooks;
        } else {
            allBooksSource.forEach(b -> bestBooks.add(b.getInfo()));
            return getRandomElements(bestBooks, limit);
        }
    }


    public List<BookInfo> getRandomElements(List<BookInfo> list, int totalItems)
    {
        Random rand = new Random();

        List<BookInfo> newList = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {

            int randomIndex = rand.nextInt(list.size());

            newList.add(list.get(randomIndex));

            list.remove(randomIndex);
        }
        return newList;
    }

    @Override
    public Page<BookInfo> getBooksByPage(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return booksRepository.findAll(pageable).map(Book::getInfo);

    }

    @Override
    public Optional<BookInfo> getBookInfo(long id) {
        return booksRepository.findById(id).map(Book::getInfo);
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
    public Page<BookInfo> findBooksByName(String partOfName, Integer pageNumber, Integer pageSize) {
        partOfName = "%"+partOfName+"%";
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> page = booksRepository.findByInfo_NameLikeIgnoreCase(partOfName, pageable);
        return page.map(Book::getInfo);
    }

    @Override
    public Page<BookInfo> findBooksByAuthorName(String name, Integer pageNumber, Integer pageSize) {
        name = "%"+name+"%";
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return booksRepository.findByInfo_Authors_NameLikeIgnoreCase(name, pageable).map(Book::getInfo);
    }

    @Override
    public Page<BookInfo> findBooksByGenre(String genre, Integer pageNumber, Integer pageSize) {
        genre = "%"+genre+"%";
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return booksRepository.findByInfo_Genres_NameLikeIgnoreCase(genre, pageable).map(Book::getInfo);
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
}
