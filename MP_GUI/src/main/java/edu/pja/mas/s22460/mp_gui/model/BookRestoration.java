package edu.pja.mas.s22460.mp_gui.model;

import edu.pja.mas.s22460.mp_gui.validation.MyException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "book_restoration")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRestoration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "restoration_cost")
    private int restorationCost;

    @Column(name = "restoration_time")
    private LocalTime restorationTime;

    /**
     * The map of books involved in the restoration.
     */
    @ManyToMany // Will be loaded from the database only when it is explicitly accessed
    @JoinTable(
            name = "book_restoration_books",
            joinColumns = @JoinColumn(name = "restoration_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private final Map<Integer, Book> books = new HashMap<>();


    /**
     * Adds a book to the book restoration process.
     * @param book the book to be added
     * @throws MyException if the book argument is null
     */
    public void addBook(Book book) {
        if (book == null) {
            throw new MyException("Book cannot be null");
        }
        books.put(book.getId().intValue(), book);
        book.getBookRestorations().add(this);
    }

    public static void introduceBookForRestoration(BookRestoration restoration, Book book) {
        if (restoration == null || book == null) {
            throw new MyException("Restoration and book cannot be null");
        }
        restoration.addBook(book);
        System.out.println("Book introduced for restoration: " + book.getName());
    }

    public static void removeBookFromRestoration(BookRestoration restoration, Book book) {
        if (restoration == null || book == null) {
            throw new MyException("Restoration and book cannot be null");
        }
        restoration.getBooks().remove(book.getId().intValue());
        book.getBookRestorations().remove(restoration);
        System.out.println("Book removed from restoration: " + book.getName());
    }

}
