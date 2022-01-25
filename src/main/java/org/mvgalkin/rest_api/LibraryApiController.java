package org.mvgalkin.rest_api;

import org.mvgalkin.models.Book;
import org.mvgalkin.models.BookInfo;
import org.mvgalkin.services.LibraryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/api")
public class LibraryApiController {

    private final LibraryService libraryService;

    public LibraryApiController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/best_books")
    public @ResponseBody
    Iterable<BookInfo> getBooksByPages(
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
    Page<BookInfo> getBooksByPages(
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
    ResponseEntity<BookInfo> getBookInfo(
            @PathVariable(value = "id") Long id
    ){
        var book = libraryService.getBookInfo(id);
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
                return ResponseEntity.ok().body(bookContent.get());
            }
        }
    }

    @GetMapping("/books/find/name/{name}")
    public @ResponseBody
    ResponseEntity<Page<BookInfo>> findBooksByName(
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
    ResponseEntity<Page<BookInfo>> findBooksByAuthorName(
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
    ResponseEntity<Page<BookInfo>> findBooksByGenre(
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
}

