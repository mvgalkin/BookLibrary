package org.mvgalkin.dao;

import org.mvgalkin.models.Book;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao{

    private DataSource dataSource;

    public void BookDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Book> getAll() {
        /*
        String query = "select * from books b inner join books_authors ba on b.id=ba.book_id inner join authors a on ba.author_id=a.id inner join books_genres bg on b.id=bg.book_id inner join genres g on bg.genre_id=g.id";
        List<Book> bookList = new ArrayList<Book>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            Book book = new Book();
            Long prevId = -1L;
            while(rs.next()){
                book.setId(rs.getLong("id"));
                book.setName(rs.getString("name"));
                book.setCover(rs.getBytes("cover"));
                book.setContent(rs.getBytes("content"));
                book.setGenres();
                bookList.add(book);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bookList;
         */
        return null;
    }

    @Override
    public List<Book> findByName(String name) {
        return null;
    }

    @Override
    public List<Book> findByAuthor(String name) {
        return null;
    }

    @Override
    public List<Book> findByGenre(String name) {
        return null;
    }

    @Override
    public Book save(Book book) {
        return null;
    }

    @Override
    public void update(long id, Book book) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }
}