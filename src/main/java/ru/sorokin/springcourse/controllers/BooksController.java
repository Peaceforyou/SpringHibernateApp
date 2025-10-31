package ru.sorokin.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.sorokin.springcourse.models.Book;
import ru.sorokin.springcourse.models.Person;
import ru.sorokin.springcourse.services.BookService;
import ru.sorokin.springcourse.services.PeopleService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BooksController {

    private final PeopleService peopleService;
    private final BookService bookService;
    @Autowired
    public BooksController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

@GetMapping()
public String index(@RequestParam(value = "page", required = false) Integer page,
                    @RequestParam(value = "size", required = false) Integer size,
                    @RequestParam(value = "sort", required = false) String sort,
                    Model model) {

    if (page == null || size == null) {
        //Without pagination
        if (sort != null) {
            model.addAttribute("books", bookService.findAll(Sort.by(sort)));
        } else {
            model.addAttribute("books", bookService.findAll());
        }
    } else {
        //With pagination
        PageRequest pageRequest;
        if (sort != null) {
            pageRequest = PageRequest.of(page, size, Sort.by(sort));
        } else {
            pageRequest = PageRequest.of(page, size);
        }

        List<Book> bookPage = bookService.findAll(pageRequest).getContent();
        model.addAttribute("books", bookPage);

    }

    return "books/index";
}

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book1 = bookService.findById(id);
        if (bookService.isExpired(id)) {
            book1.setExpired(true);
            System.out.println("Поставили то что он просрочен в представление");

        }

        model.addAttribute("book", book1);

        Book book = new Book();
        book.setId(id);
        Optional<Person> bookOwner = Optional.ofNullable(peopleService.findPersonByBookList(book));

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());

        else
            model.addAttribute("people", peopleService.findAll());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book Book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book Book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookService.save(Book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "title", required = false) String title, Model model) {
        List<Book> bookList;
        if (title != null){
            bookList = bookService.findByTitleContainingIgnoreCase(title);
            model.addAttribute("books",bookList);
        }
        else
            bookList = null;
        return "books/search";
    }



}
