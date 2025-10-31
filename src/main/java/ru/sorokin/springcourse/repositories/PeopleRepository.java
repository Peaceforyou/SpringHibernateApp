package ru.sorokin.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sorokin.springcourse.models.Book;
import ru.sorokin.springcourse.models.Person;


@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
    public Person findPersonByBookList(Book book);
    public Person findPersonByFullName(String fullName);
}
