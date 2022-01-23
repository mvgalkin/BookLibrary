package org.mvgalkin.controllers;

import org.mvgalkin.models.BookInfo;
import org.mvgalkin.services.LibraryService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/*
@RestController("/api")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }


    @GetMapping("/books")
    public @ResponseBody
    Page<BookInfo> getBooksByPages(
            @RequestParam(value = "pagenumber", required = true, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pagesize", required = true, defaultValue = "20") Integer pageSize
    ){
        return libraryService.getBooksByPage(pageNumber, pageSize);
    }
}

*/