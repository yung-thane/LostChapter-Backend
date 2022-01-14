package com.revature.lostchapterbackend.booktests;

import com.revature.lostchapterbackend.dao.BookDAO;
import com.revature.lostchapterbackend.dao.GenreDAO;
import com.revature.lostchapterbackend.dto.AddOrUpdateBookDTO;
import com.revature.lostchapterbackend.exceptions.*;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.Genre;
import com.revature.lostchapterbackend.service.BookService;
import com.revature.lostchapterbackend.utility.ValidateBookUtil;
import com.revature.lostchapterbackend.utility.ValidateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;


public class BookServiceTests {

    @InjectMocks
    private BookService bs;

    @Mock
    private ValidateBookUtil vu;

    @Mock
    private GenreDAO gd;


    @Mock
    private BookDAO bd;

    @BeforeEach
    public void setUp() {
        this.vu = new ValidateBookUtil();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllBook_positive(){

        Genre g = new Genre();
        g.setGenre("Fiction");

        Book book1 = new Book("23242526","booktest1","synposis",
                "author",g,4,1997,"edition",
                "publisher",false,0.99,10,"image");
        book1.setBookId(1);

        Book book2 = new Book("76777879","booktest2","synposis2",
                "author2",g,4,1997,"edition2",
                "publisher2",false,0.99,10,"image2");
        book2.setBookId(2);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        when(bd.findAll()).thenReturn(books);

        bs = new BookService(bd);

        Book expected1 = new Book("23242526","booktest1","synposis",
                "author",g,4,1997,"edition",
                "publisher",false,0.99,10,"image");
        expected1.setBookId(1);

        Book expected2 = new Book("76777879","booktest2","synposis2",
                "author2",g,4,1997,"edition2",
                "publisher2",false,0.99,10,"image2");
        expected2.setBookId(2);

        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(expected1);
        expectedBooks.add(expected2);

        List<Book> actual = bs.getAllBooks();

        Assertions.assertEquals(expectedBooks,actual);
    }

    @Test
    void getBookById_positive() throws BookNotFoundException {
        Genre g = new Genre();
        g.setGenre("Fiction");

        when(bd.findById(1)).thenReturn(Optional.of(new Book("23242526", "booktest1", "synposis",
                "author", g, 4, 1997, "edition",
                "publisher", false, 0.99, 10, "image")));

        bs = new BookService(bd);

        Book actual = bs.getBookById("1");

        Assertions.assertEquals(new Book("23242526", "booktest1", "synposis",
                "author", g, 4, 1997, "edition",
                "publisher", false, 0.99, 10, "image"),actual);
    }

    @Test
    void getBookByGenreId_positive(){
        Genre g = new Genre();
        g.setGenre("Fiction");

        Book book1 = new Book("23242526","booktest1","synposis",
                "author",g,4,1997,"edition",
                "publisher",false,0.99,10,"image");
        book1.setBookId(1);

        Book book2 = new Book("76777879","booktest2","synposis2",
                "author2",g,4,1997,"edition2",
                "publisher2",false,0.99,10,"image2");
        book2.setBookId(2);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        when(bd.getByGenreId(1)).thenReturn(books);

        bs = new BookService(bd);

        Book expectedBook1 = new Book("23242526","booktest1","synposis",
                "author",g,4,1997,"edition",
                "publisher",false,0.99,10,"image");
        expectedBook1.setBookId(1);

        Book expectedBook2 = new Book("76777879","booktest2","synposis2",
                "author2",g,4,1997,"edition2",
                "publisher2",false,0.99,10,"image2");
        expectedBook2.setBookId(2);

        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(expectedBook1);
        expectedBooks.add(expectedBook2);

        List<Book> actual = bs.getBooksByGenreId("1");

        Assertions.assertEquals(expectedBooks,actual);
    }

    @Test
    void getBookByKeyword_positive(){
        Genre g = new Genre();
        g.setGenre("Fiction");

        Book book1 = new Book("23242526","booktest1","synposis",
                "author",g,4,1997,"edition",
                "publisher",false,0.99,10,"image");
        book1.setBookId(1);

        Book book2 = new Book("76777879","booktest2","synposis2",
                "author2",g,4,1997,"edition2",
                "publisher2",false,0.99,10,"image2");
        book2.setBookId(2);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        when(bd.findBybookNameIgnoreCaseContaining("book")).thenReturn(books);

        bs = new BookService(bd);

        Book expectedBook1 = new Book("23242526","booktest1","synposis",
                "author",g,4,1997,"edition",
                "publisher",false,0.99,10,"image");
        expectedBook1.setBookId(1);

        Book expectedBook2 = new Book("76777879","booktest2","synposis2",
                "author2",g,4,1997,"edition2",
                "publisher2",false,0.99,10,"image2");
        expectedBook2.setBookId(2);

        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(expectedBook1);
        expectedBooks.add(expectedBook2);

        List<Book> actual = bs.getBooksByKeyword("book");

        Assertions.assertEquals(expectedBooks,actual);
    }

    @Test
    void getBookBySales_positive(){
        Genre g = new Genre();
        g.setGenre("Fiction");

        Book book1 = new Book("23242526","booktest1","synposis",
                "author",g,4,1997,"edition",
                "publisher",true,0.99,10,"image");
        book1.setBookId(1);

        Book book2 = new Book("76777879","booktest2","synposis2",
                "author2",g,4,1997,"edition2",
                "publisher2",false,0.99,10,"image2");
        book2.setBookId(2);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        when(bd.findBysaleIsActiveTrue()).thenReturn(books);

        bs = new BookService(bd);

        Book expectedBook1 = new Book("23242526","booktest1","synposis",
                "author",g,4,1997,"edition",
                "publisher",true,0.99,10,"image");
        expectedBook1.setBookId(1);

        Book expectedBook2 = new Book("76777879","booktest2","synposis2",
                "author2",g,4,1997,"edition2",
                "publisher2",false,0.99,10,"image2");
        expectedBook2.setBookId(2);

        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(expectedBook1);
        expectedBooks.add(expectedBook2);

        List<Book> actual = bs.getBooksBySale();

        Assertions.assertEquals(expectedBooks,actual);
    }

//    @Test
//    void addBook_positive() throws SynopsisInputException,
//            SaleDiscountRateException, ISBNAlreadyExists,
//            GenreNotFoundException {
//
//        Genre g = new Genre();
//        g.setGenre("Fiction");
//        g.setId(1);
//
//        Book addBook = new Book("23242526","booktest1","synposis",
//                "author",g,4,1997,"edition",
//                "publisher",true,0.89,10,"image");
//
//        when(bd.saveAndFlush(addBook)).thenReturn(new Book("23242526","booktest1","synposis",
//                "author",g,4,1997,"edition",
//                "publisher",true,0.89,10,"image"));
//
//        bs = new BookService(bd);
//
//        AddOrUpdateBookDTO dto = new AddOrUpdateBookDTO("23242526","booktest1","synposis",
//                "author",1,4,1997,"edition",
//                "publisher",true,0.89,10,"image");
//
//        when(gd.findById(1)).thenReturn(Optional.of(new Genre(1, "Fiction")));
//
//        when(vu.validateBookInput(any(AddOrUpdateBookDTO.class))).thenReturn(Optional.empty());
//        //vu.validateBookInput(dto);
//
////               // when(jobService.listJobs(any(Person.class))).thenReturn(Stream.empty());
//
//        Book addedBook = new Book("23242526","booktest1","synposis",
//                "author",g,4,1997,"edition",
//                "publisher",true,0.89,10,"image");
//
//        Book expectedBook = bs.addBook(dto);
//
//        Assertions.assertEquals(addedBook,expectedBook);
//    }

//    @Test
//    void addBookISBNIsEmpty_negative() throws SynopsisInputException,
//            SaleDiscountRateException, ISBNAlreadyExists, GenreNotFoundException {
//
//        AddOrUpdateBookDTO dto = new AddOrUpdateBookDTO("23242526","booktest1","synposis",
//                "author",1,4,1997,"edition",
//                "publisher",true,0.89,10,"image");
//
//        Genre g = gd.findById(dto.getGenre()).get();
//        AddOrUpdateBookDTO dto1 = new AddOrUpdateBookDTO("23242526","booktest1","synposis",
//                "author", g, 4,1997,"edition",
//                "publisher",true,0.89,10,"image");
//
//        vu.validateBookInput(dto1);
//
//        Assertions.assertThrows(GenreNotFoundException.class, () -> {
//            bs.addBook(dto1);
//        });
//    }
}
