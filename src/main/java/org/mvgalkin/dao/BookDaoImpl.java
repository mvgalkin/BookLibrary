package org.mvgalkin.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mvgalkin.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Locale;

@Repository
public class BookDaoImpl implements BookDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Book> getAll() {
        @SuppressWarnings("unchecked")
        TypedQuery<Book> query = sessionFactory.getCurrentSession().createQuery("from Book fetch all properties ");
        return query.getResultList();
    }

    @Override
    public List<Book> findByName(String name) {
        @SuppressWarnings("unchecked")
        TypedQuery<Book> query = sessionFactory.getCurrentSession().createQuery("from Book fetch all properties where upper(name) like :name");
        query.setParameter("name", name.toUpperCase());
        return query.getResultList();
    }

    @Override
    public List<Book> findByAuthor(String name) {
        @SuppressWarnings("unchecked")
        TypedQuery<Book> query = sessionFactory.getCurrentSession().createQuery("select b from Book as b inner join b.authors as a inner join b.genres as g where upper(a.name) like :name");
        query.setParameter("name", name.toUpperCase());
        return query.getResultList();
    }

    @Override
    public List<Book> findByGenre(String name) {
        @SuppressWarnings("unchecked")
        TypedQuery<Book> query = sessionFactory.getCurrentSession().createQuery("from Book b inner join b.authors a join b.genres g where upper(g.name) like :name");
        query.setParameter("name", name.toUpperCase());
        return query.getResultList();
    }

    public Book getById(Long id){
        return sessionFactory.getCurrentSession().get(Book.class, id);
    }

    @Override
    public Book save(Book book) {
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(book);
        sessionFactory.getCurrentSession().getTransaction().commit();
        return book;
    }

    @Override
    public void update(long id, Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Book book2 = session.byId(Book.class).load(id);
        book2.setName(book.getName());
        book2.setCover(book.getCover());
        book2.setContent(book.getContent());
        //todo: по идее можно добавить логику обновления авторов ... но для этого надо сопоставить до и после ... и выделить тех которые удаляются и тех которые добавляются
        session.flush();
        session.getTransaction().commit();
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Book book = session.byId(Book.class).load(id);
        session.delete(book);
        session.getTransaction().commit();
    }

    @Override
    public boolean existsById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.byId(Book.class).load(id);
        return book != null;
    }
}