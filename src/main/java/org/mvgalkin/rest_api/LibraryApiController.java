package org.mvgalkin.rest_api;

import org.mvgalkin.models.Book;
import org.mvgalkin.models.BookInfoView;
import org.mvgalkin.services.LibraryService;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController()
@RequestMapping("/api")
public class LibraryApiController {

    private final LibraryService libraryService;

    public LibraryApiController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/books")
    public @ResponseBody
    ResponseEntity<List<Book>> getAll(){
        //запуск БЛ
        return ResponseEntity.ok().body(libraryService.getAll());
    }
/*
    @GetMapping("/books/find/name/{name}")
    public @ResponseBody
    ResponseEntity<List<Book>> findBooksByName(
            @PathVariable(value = "name") String name
    ){
        //контроль входных параметров (требований нет, поэтому проверка чисто на адекватность)
        if (name == null || name.isBlank()) {
            return ResponseEntity.ok().build();
        }

        //запуск БЛ
        return ResponseEntity.ok().body(libraryService.findBooksByName(name));
    }

    @GetMapping("/books/find/author/{name}")
    public @ResponseBody
    ResponseEntity<List<Book>> findBooksByAuthor(
            @PathVariable(value = "name") String name
    ){
        //контроль входных параметров (требований нет, поэтому проверка чисто на адекватность)
        if (name == null || name.isBlank()) {
            return ResponseEntity.ok().build();
        }

        //запуск БЛ
        return ResponseEntity.ok().body(libraryService.findBooksByAuthor(name));
    }

    @GetMapping("/books/find/genre/{name}")
    public @ResponseBody
    ResponseEntity<List<Book>> findBooksByGenre(
            @PathVariable(value = "name") String name
    ){
        //контроль входных параметров (требований нет, поэтому проверка чисто на адекватность)
        if (name == null || name.isBlank()) {
            return ResponseEntity.ok().build();
        }

        //запуск БЛ
        return ResponseEntity.ok().body(libraryService.findBooksByGenre(name));
    }

 */
/*
    @GetMapping("/best_books")
    public @ResponseBody
    Iterable<BookInfoView> getBestBooks(
            @RequestParam(value = "count", defaultValue = "10") Integer limit
    ){
        //контроль входных параметров (требований нет, поэтому проверка чисто на адекватность)
        if (limit<1) {
            limit = 10;
        } else if (limit>25) {
            limit = 25;
        }

        //запуск БЛ
        return libraryService.getBestBooks(limit);
    }

    @GetMapping("/books")
    public @ResponseBody
    Page<BookInfoView> getBooksByPages(
            @RequestParam(value = "pagenumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pagesize", defaultValue = "20") Integer pageSize
    ){
        //контроль входных параметров (требований нет, поэтому проверка чисто на адекватность)
        if (pageNumber<0) pageNumber = 0;
        if (pageSize<4) pageSize = 4;

        //запуск БЛ
        return libraryService.getBooksByPage(pageNumber, pageSize);
    }

    @GetMapping("/books/{id}/info")
    public @ResponseBody
    ResponseEntity<BookInfoView> getBookInfoView(
            @PathVariable(value = "id") Long id
    ){
        var book = libraryService.getBookInfoView(id);
        if (book.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(book.get());
        }
    }

    @GetMapping("/books/{id}/content")
    public @ResponseBody
    ResponseEntity<byte[]> getBookContent(
            @PathVariable(value = "id") Long id
    ){
        var bookContent = libraryService.getBookContent(id);
        if (bookContent == null) {
            return ResponseEntity.notFound().build();
        } else {
            if (bookContent.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                var content = bookContent.get();
                return ResponseEntity.ok().contentLength(content.length).body(content);
            }
        }
    }

    @GetMapping("/books/find/name/{name}")
    public @ResponseBody
    ResponseEntity<Page<BookInfoView>> findBooksByName(
            @PathVariable(value = "name") String name,
            @RequestParam(value = "pagenumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pagesize", defaultValue = "20") Integer pageSize
    ){
        //контроль входных параметров (требований нет, поэтому проверка чисто на адекватность)
        if (name == null || name.isBlank()) {
            return ResponseEntity.ok().build();
        }
        if (pageNumber<0) pageNumber = 0;
        if (pageSize<4) pageSize = 4;

        //запуск БЛ
        return ResponseEntity.ok().body(libraryService.findBooksByName(name, pageNumber, pageSize));
    }

    @GetMapping("/books/find/author/{name}")
    public @ResponseBody
    ResponseEntity<Page<BookInfoView>> findBooksByAuthorName(
            @PathVariable(value = "name") String name,
            @RequestParam(value = "pagenumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pagesize", defaultValue = "20") Integer pageSize
    ){
        //контроль входных параметров (требований нет, поэтому проверка чисто на адекватность)
        if (name == null || name.isBlank()) {
            return ResponseEntity.ok().build();
        }
        if (pageNumber<0) pageNumber = 0;
        if (pageSize<4) pageSize = 4;

        //запуск БЛ
        return ResponseEntity.ok().body(libraryService.findBooksByAuthorName(name, pageNumber, pageSize));
    }

    @GetMapping("/books/find/genre/{genre}")
    public @ResponseBody
    ResponseEntity<Page<BookInfoView>> findBooksByGenre(
            @PathVariable(value = "genre") String genre,
            @RequestParam(value = "pagenumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pagesize", defaultValue = "20") Integer pageSize
    ){
        //контроль входных параметров (требований нет, поэтому проверка чисто на адекватность)
        if (genre == null || genre.isBlank()) {
            return ResponseEntity.ok().build();
        }
        if (pageNumber<0) pageNumber = 0;
        if (pageSize<4) pageSize = 4;

        //запуск БЛ
        return ResponseEntity.ok().body(libraryService.findBooksByGenre(genre, pageNumber, pageSize));
    }

    @PostMapping("/books")
    public ResponseEntity<?> addBook(@RequestBody Book book){
       if (book.getId() == null) {book.setId(0L);}
       book.getAuthors().forEach(author -> {
           if (author.getId()==null) {
               author.setId(0L);
           }
       });

        book.getGenres().forEach(genre -> {
            if (genre.getId()==null) {
                genre.setId(0L);
            }
        });

        Book savedBook = libraryService.save(book);
        return ResponseEntity.ok().body(savedBook);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable("id")long id, @RequestBody Book book){
        if (libraryService.isExists(id)) {
            libraryService.update(id, book);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") long id){
        if (libraryService.isExists(id)) {
            libraryService.delete(id);
            return ResponseEntity.ok().body("Book has been deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return ResponseEntity.ok().body(currentUserName);
        }
        return ResponseEntity.ok().body("Anonymous");
    }

 */
}