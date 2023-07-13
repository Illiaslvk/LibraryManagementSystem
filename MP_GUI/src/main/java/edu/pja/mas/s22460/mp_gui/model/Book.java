package edu.pja.mas.s22460.mp_gui.model;


import edu.pja.mas.s22460.mp_gui.utils.BookGenre;
import edu.pja.mas.s22460.mp_gui.validation.MyException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Book {
    /**
     * The unique identifier of the book.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="book_name")
    private String name;

    @Embedded
    @JoinColumn(name = "isbn_id", nullable = false, referencedColumnName = "id")
    @NotNull
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ISBN isbn;

    @ElementCollection //collection of embedded values
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<String> authors = new HashSet<>();

    @Enumerated(EnumType.STRING) // Store as string
    @Column(name = "book_genre")
    private BookGenre bookGenre;

    private double price = 30.0;

    public static double minPrice = 9.99;

    private String edition;

    @Temporal(TemporalType.DATE)
    private LocalDate yearOfWriting;

    /**
     * The set of opinions about the book.
     * Composition: Opinion
     * CascadeTypeALL ensures that the Opinion entity is created, updated, and deleted along with the Book entity.
     */
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Opinion> opinions = new HashSet<>();

    /**
     * The set of book restorations involving the book.
     * Qualified: BookRestoration
     */
    @ManyToMany(mappedBy = "books")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<BookRestoration> bookRestorations = new HashSet<>();

    /**
     * The library where the book is located.
     */
    @ManyToOne
    @JoinColumn(name = "library_id") //nullable = false, updatable = false
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Library library;

    /**
     * The set of reservations made for the book.
     * When a Reservation entity is no longer referenced by its Book entity, it should be automatically removed from the database (orphanRemoval = true).
     */
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Reservation> reservations = new HashSet<>();


    /**
     * Adds a reservation for the book.
     * @param reservation the reservation to be added
     * @throws IllegalArgumentException if the reservation argument is null
     */
    public void addReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("The reservation argument cannot be null.");
        }

        if (!reservations.contains(reservation)) {
            this.reservations.add(reservation);
        }
    }

    /**
     * Removes a reservation for the book.
     * @param reservation the reservation to be removed
     * @throws IllegalArgumentException if the reservation argument is null
     */
    public void removeReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("The reservation argument cannot be null.");
        }

        if (this.reservations.contains(reservation)) {
            this.reservations.remove(reservation);
            reservation.remove();
        }
    }

    public static Set<Book> orderBooksByName(Set<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getName))
                .collect(Collectors.toSet());
    }


    // Calculate number of books not returned
    public int calculateNumberOfBooksNotReturned() {
        return reservations.size();
    }

    // Introduce book for restoration
    public void introduceBookForRestoration(BookRestoration restoration) {
        restoration.addBook(this);
        System.out.println("Book introduced for restoration");
    }

    // Calculate restoration cost
    public int calculateCost() {
        int restorationCost = 0;
        for (BookRestoration restoration : bookRestorations) {
            restorationCost += restoration.getRestorationCost();
        }
        return restorationCost;
    }

    // dynamic
    public void setPrice(double price) {
        double maxAllowedIncrease = this.price / 10; // Allowing a maximum increase of 10%
        double maxPrice = this.price + maxAllowedIncrease;

        if (price < minPrice) {
            throw new MyException("Book price cannot be smaller than 10");
        } else if (price > maxPrice) {
            throw new MyException("Price can not be increased by more than 10%");
        }

        this.price = price;
    }

}



