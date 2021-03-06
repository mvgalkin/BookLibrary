package org.mvgalkin.rest_api;

import org.mvgalkin.models.Book;
import org.mvgalkin.models.BookInfoView;
import org.mvgalkin.services.LibraryService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


//todo: Улучшение: разбить на 3(4) контроллера LibraryApiController, ImagesApiController, AuthApiController, (BooksContentController)
@CrossOrigin("*")
@RestController()
@RequestMapping("/api")
public class LibraryApiController {

    private final LibraryService libraryService;

    public LibraryApiController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/best_books")
    public @ResponseBody
    Iterable<BookInfoView> getBestBooks(
            @RequestParam(value = "count", defaultValue = "10") int limit
    ) {
        //контроль входных параметров (требований нет, поэтому проверка чисто на адекватность)
        if (limit < 1) {
            limit = 10;
        } else if (limit > 25) {
            limit = 25;
        }

        //запуск БЛ
        return libraryService.getBestBooks(limit);
    }

    @GetMapping("/books")
    public @ResponseBody
    Page<BookInfoView> getBooksByPages(
            @RequestParam(value = "pagenumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pagesize", defaultValue = "20") int pageSize
    ) {
        //контроль входных параметров (требований нет, поэтому проверка чисто на адекватность)
        if (pageNumber < 0) pageNumber = 0;
        if (pageSize < 4) pageSize = 4;

        //запуск БЛ
        return libraryService.getBooksByPage(pageNumber, pageSize);
    }

    @GetMapping("/books/{id}/info")
    public @ResponseBody
    ResponseEntity<BookInfoView> getBookInfoView(
            @PathVariable(value = "id") Long id
    ) {
        var book = libraryService.getBookInfoView(id);
        if (book.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(book.get());
        }
    }

    @GetMapping("/books/find/name/{name}")
    public @ResponseBody
    ResponseEntity<Page<BookInfoView>> findBooksByName(
            @PathVariable(value = "name") String name,
            @RequestParam(value = "pagenumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pagesize", defaultValue = "20") int pageSize
    ) {
        //контроль входных параметров (требований нет, поэтому проверка чисто на адекватность)
        if (name == null || name.isBlank()) {
            return ResponseEntity.ok().build();
        }
        if (pageNumber < 0) pageNumber = 0;
        if (pageSize < 4) pageSize = 4;

        //запуск БЛ
        return ResponseEntity.ok().body(libraryService.findBooksByName(name, pageNumber, pageSize));
    }

    @GetMapping("/books/find/author/{name}")
    public @ResponseBody
    ResponseEntity<Page<BookInfoView>> findBooksByAuthorName(
            @PathVariable(value = "name") String name,
            @RequestParam(value = "pagenumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pagesize", defaultValue = "20") int pageSize
    ) {
        //контроль входных параметров (требований нет, поэтому проверка чисто на адекватность)
        if (name == null || name.isBlank()) {
            return ResponseEntity.ok().build();
        }
        if (pageNumber < 0) pageNumber = 0;
        if (pageSize < 4) pageSize = 4;

        //запуск БЛ
        return ResponseEntity.ok().body(libraryService.findBooksByAuthorName(name, pageNumber, pageSize));
    }

    @GetMapping("/books/find/genre/{genre}")
    public @ResponseBody
    ResponseEntity<Page<BookInfoView>> findBooksByGenre(
            @PathVariable(value = "genre") String genre,
            @RequestParam(value = "pagenumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pagesize", defaultValue = "20") int pageSize
    ) {
        //контроль входных параметров (требований нет, поэтому проверка чисто на адекватность)
        if (genre == null || genre.isBlank()) {
            return ResponseEntity.ok().build();
        }
        if (pageNumber < 0) pageNumber = 0;
        if (pageSize < 4) pageSize = 4;

        //запуск БЛ
        return ResponseEntity.ok().body(libraryService.findBooksByGenre(genre, pageNumber, pageSize));
    }

    @PostMapping(value = "/books")
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        if (book.getId() == null) {
            book.setId(0L);
        }
        book.getAuthors().forEach(author -> {
            if (author.getId() == null) {
                author.setId(0L);
            }
        });

        book.getGenres().forEach(genre -> {
            if (genre.getId() == null) {
                genre.setId(0L);
            }
        });

        Book savedBook = libraryService.save(book);
        return ResponseEntity.ok().body(savedBook);
    }

    @PostMapping(value = "/books_and_content",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addBookWithContent(
            @RequestPart Book book,
            @RequestPart MultipartFile cover,
            @RequestPart MultipartFile content
    ) throws IOException {
        //LoggerFactory.getLogger("LibraryApiController").error(String.format("book=%s",book));
        //LoggerFactory.getLogger("LibraryApiController").error(String.format("cover=%s",cover));
        //LoggerFactory.getLogger("LibraryApiController").error(String.format("content=%s",content));

        if (book.getId() == null) {
            book.setId(0L);
        }
        book.setCover(cover.getBytes());
        book.setContent(content.getBytes());
        book.getAuthors().forEach(author -> {
            if (author.getId() == null) {
                author.setId(0L);
            }
        });

        book.getGenres().forEach(genre -> {
            if (genre.getId() == null) {
                genre.setId(0L);
            }
        });

        Book savedBook = libraryService.save(book);
        return ResponseEntity.ok().body(savedBook);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable("id") long id, @RequestBody Book book) {
        if (libraryService.isExists(id)) {
            libraryService.update(id, book);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") long id) {
        if (libraryService.isExists(id)) {
            libraryService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return ResponseEntity.ok().body(currentUserName);
        }
        return ResponseEntity.ok().body("Anonymous");
    }

    @GetMapping(
            value = "/image/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<byte[]> getCover(
            @PathVariable(value = "id") Long id
    ) {
        var bookCover = libraryService.getBookCover(id);
        if (bookCover == null) {
            return ResponseEntity.notFound().build();
        } else {
            if (bookCover.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                var content = bookCover.get();
                return ResponseEntity.ok().contentLength(content.length).body(content);
            }
        }
    }

    @GetMapping(
            value = "/books/{id}/content",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    ResponseEntity<byte[]> downloadBookContent(
            @PathVariable(value = "id") Long id
    ) {
        var bookContent = libraryService.getBookContent(id);
        if (bookContent == null) {
            return ResponseEntity.notFound().build();
        } else {
            if (bookContent.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                var content = bookContent.get();

                return ResponseEntity.ok().contentLength(content.length).header("Content-Disposition", "attachment; filename=filename.pdf").body(content);
            }
        }
    }

    @GetMapping(
            value = "/books/{id}/show",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody
    ResponseEntity<byte[]> showBookContent(
            @PathVariable(value = "id") Long id
    ) {
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
}

