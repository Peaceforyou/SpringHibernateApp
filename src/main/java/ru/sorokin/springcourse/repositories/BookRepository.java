package ru.sorokin.springcourse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sorokin.springcourse.models.Book;
import ru.sorokin.springcourse.models.Person;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    public List<Book> findBookByOwner(Person person);
    public Page<Book> findAll(Pageable Book);
    public List<Book> findAll(Sort field);

    public List<Book> findByTitleContainingIgnoreCase(String titleLike);
}
