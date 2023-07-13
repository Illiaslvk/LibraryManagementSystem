package edu.pja.mas.s22460.mp_gui.model;

import edu.pja.mas.s22460.mp_gui.users.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "library")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false) //used to specify that the name field in the Library should not be nullable in the database
    private String name;

    private String address;

    /**
     * The set of books in the library.
     */
    @OneToMany(mappedBy = "library", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Book> books = new HashSet<>();

    @OneToMany(mappedBy = "library", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Employee> employees = new HashSet<>();

    /**
     * Adds a book to the library.
     * @param book the book to be added
     */
    public void addBook(Book book) {
        books.add(book);
        book.setLibrary(this);
        System.out.println("Book added to the library.");
    }

    /**
     * Removes a book from the library.
     * @param book the book to be removed
     */
    public void removeBook(Book book) {
        book.setLibrary(null);
        books.remove(book);
        System.out.println("Book removed from the library.");
    }

}

