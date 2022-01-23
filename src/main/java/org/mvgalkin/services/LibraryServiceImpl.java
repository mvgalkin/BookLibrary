package org.mvgalkin.services;

import org.mvgalkin.dao.BooksDaoRepository;
import org.mvgalkin.dao.BooksInfoDaoRepository;
import org.mvgalkin.models.Book;
import org.mvgalkin.models.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final BooksInfoDaoRepository booksInfoRepository;

    public LibraryServiceImpl(BooksInfoDaoRepository booksInfoRepository, BooksDaoRepository booksRepository){
        this.booksRepository = booksRepository;
        this.booksInfoRepository = booksInfoRepository;
    }

    @Override
    public Iterable<BookInfo> getBestBooks(Integer limit) {
        if (booksInfoRepository.count()<=limit) {
            return booksInfoRepository.findAll();
        } else {
            Iterable<BookInfo> allBooksSource = booksInfoRepository.findAll();
            List<BookInfo> allBooks = new ArrayList<>();
            allBooksSource.forEach(allBooks::add);
            return getRandomElements(allBooks, limit);
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
        return null;
        /*
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return booksInfoRepository.findAll(pageable);

         */
    }

    @Override
    public Optional<BookInfo> getBookInfo(long id) {
        return booksInfoRepository.findById(id);
    }

    @Override
    public Optional<byte[]> getBookContent(long id) {
        Optional<Book> book = booksRepository.findById(id);
        if (book.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(book.get().getContent());
    }

    @Override
    public Page<BookInfo> FindBooksByName(String partOfName, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return booksInfoRepository.findByNameIsLike(partOfName, pageable);
    }

    @Override
    public Page<BookInfo> FindBooksByAuthorName(String name, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return booksInfoRepository.findByAuthors_NameLike(name, pageable);
    }

    @Override
    public Page<BookInfo> FindBooksByGenre(String genre, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return booksInfoRepository.findByGenres_NameLike(genre, pageable);
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
