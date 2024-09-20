//package com.projectsfortraining.bookproject.dao;
//
//import com.projectsfortraining.bookproject.models.Book;
//import com.projectsfortraining.bookproject.models.Person;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class BookDAO {
//
//    private final SessionFactory sessionFactory;
//    @Autowired
//    public BookDAO(SessionFactory sessionFactory){
//
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Transactional
//    public void create(Book newBook){
//        Session session = sessionFactory.getCurrentSession();
//        session.save(newBook);
//    }
//
//    @Transactional(readOnly = true)
//    public Book readOne(int id){
//        Session session = sessionFactory.getCurrentSession();
//        return (Book) session.createQuery("SELECT b FROM Book b WHERE b.bookId=:bookId", Book.class).setParameter("bookId",id);
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<Book> readOne(String name){
//        Session session = sessionFactory.getCurrentSession();
//        return Optional.ofNullable((Book) session.createQuery("SELECT b FROM Book b WHERE b.name=:name", Book.class).setParameter("name", name));
//    }
//
//    @Transactional(readOnly = true)
//    public List<Book> readByPersonId(int personId){
//        Session session = sessionFactory.getCurrentSession();
//        return session.createQuery("SELECT b FROM Book b WHERE b.person.personId=:personId", Book.class).setParameter("personId", personId).getResultList();
//
//    }
//    @Transactional(readOnly = true)
//    public List<Book> readAll(){
//        Session session = sessionFactory.getCurrentSession();
//        return session.createQuery("SELECT b FROM Book b", Book.class).getResultList();
//    }
//    @Transactional
//    public void update(int id, Book updatedBook){
//        Session session = sessionFactory.getCurrentSession();
//        Book book = session.get(Book.class,id);
//        book.setName(updatedBook.getName());
//        book.setAuthor(updatedBook.getAuthor());
//        book.setYearOfRelease(updatedBook.getYearOfRelease());
//        book.setPerson(updatedBook.getPerson());
//    }
//    @Transactional
//    public void appointPerson(int id, int personId){
//        Session session = sessionFactory.getCurrentSession();
//        Book book = session.get(Book.class, id);
//        Person person = session.get(Person.class, personId);
//        book.setPerson(person);
//
//    }
//    @Transactional
//    public void delete(int id){
//        Session session = sessionFactory.getCurrentSession();
//        Book book = session.get(Book.class, id);
//        session.delete(book);
//    }
//}