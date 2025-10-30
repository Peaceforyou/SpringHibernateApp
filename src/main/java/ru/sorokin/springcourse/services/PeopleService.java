package ru.sorokin.springcourse.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sorokin.springcourse.models.Book;
import ru.sorokin.springcourse.models.Person;
import ru.sorokin.springcourse.repositories.BookRepository;
import ru.sorokin.springcourse.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
   private final PeopleRepository peopleRepository;
   private final BookRepository bookRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BookRepository bookRepository) {
        this.peopleRepository = peopleRepository;
        this.bookRepository = bookRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findById(int id){
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public  void update(int id,Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public Person findPersonByBookList(Book book){
        return peopleRepository.findPersonByBookList(book);
    }


}
