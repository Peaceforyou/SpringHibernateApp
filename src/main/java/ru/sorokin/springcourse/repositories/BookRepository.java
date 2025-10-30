package ru.sorokin.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sorokin.springcourse.models.Book;
import ru.sorokin.springcourse.models.Person;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    public List<Book> findBookByOwner(Person person);
}
