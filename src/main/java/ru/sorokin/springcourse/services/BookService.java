package ru.sorokin.springcourse.services;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sorokin.springcourse.models.Book;
import ru.sorokin.springcourse.models.Person;
import ru.sorokin.springcourse.repositories.BookRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private  final EntityManager em;

    @Autowired
    public BookService(BookRepository bookRepository, EntityManager em) {
        this.bookRepository = bookRepository;
        this.em = em;
    }



    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findById(int id){
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }
    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }
    @Transactional
    public void update(int id,Book book){
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void deleteById(int id){
        bookRepository.deleteById(id);
    }

   public  List<Book> findBookByOwner(Person person) {
        return bookRepository.findBookByOwner(person);
    }

    @Transactional
    public void release(int id){
        Session session = em.unwrap(Session.class);
        session.createQuery("update Book b SET b.owner=null where b.id= :bookId")
                .setParameter("bookId",id).executeUpdate();
    }
    @Transactional
    public void assign(int id,Person selectedPerson){
        Session session = em.unwrap(Session.class);
        session.createQuery("update Book b SET b.owner=:selectedPerson where b.id= :bookId")
                .setParameter("bookId",id).setParameter("selectedPerson",selectedPerson).executeUpdate();
    }

}
