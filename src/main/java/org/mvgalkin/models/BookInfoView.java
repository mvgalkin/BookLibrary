package org.mvgalkin.models;

import java.util.Set;

public interface BookInfoView {
    Long getId();

    String getName();

    //todo: после вынесения в отдельный класс информации об обложке - исключить из данного класса, предоставление клиенту будет через api/image/{id}
    byte[] getCover();

    Set<Author> getAuthors();

    Set<Genre> getGenres();
}
