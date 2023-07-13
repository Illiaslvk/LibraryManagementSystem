package edu.pja.mas.s22460.mp_gui.model;

import edu.pja.mas.s22460.mp_gui.users.Client;
import edu.pja.mas.s22460.mp_gui.utils.ReservationState;
import edu.pja.mas.s22460.mp_gui.validation.MyException;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The start date of the reservation.
     */
    @NotNull
    @Temporal(TemporalType.DATE)
    @Future(message = "Start date should be in the future")
    private LocalDate startDate;

    /**
     * The end date of the reservation.
     */
    @NotNull
    @Temporal(TemporalType.DATE)
    @Future(message = "End date should be in the future")
    private LocalDate endDate;

    /**
     * The book status before the reservation.
     */
    @Min(value = 1, message = "Book status before should be at least 1")
    @Max(value = 5, message = "Book status before should be at most 5")
    private int bookStatusBefore;

    /**
     * The book status after the reservation.
     */
    @Min(value = 1, message = "Book status after should be at least 1")
    @Max(value = 5, message = "Book status after should be at most 5")
    private int bookStatusAfter;

    /**
     * The state of the reservation.
     */
    @Enumerated(EnumType.STRING)
    private ReservationState state;

    /**
     * The book associated with the reservation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    /**
     * The client who made the reservation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "client_id")
    private Client client;


    /**
     * Sets the start date of the reservation.
     * business constraint
     * @param startDate the start date to be set
     * @throws MyException if the start date is null or after the end date
     */
    public void setStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new MyException("Start date cannot be null");
        }
        if (endDate != null && startDate.isAfter(endDate)) {
            throw new MyException("Start date cannot be after the end date");
        }
        this.startDate = startDate;
    }


    /**
     * Sets the end date of the reservation.
     * @param endDate the end date to be set
     * @throws MyException if the end date is null or before the start date
     */
    public void setEndDate(LocalDate endDate) {
        if (endDate == null) {
            throw new MyException("End date cannot be null");
        }
        if (startDate != null && endDate.isBefore(startDate)) {
            throw new MyException("End date cannot be before the start date");
        }
        this.endDate = endDate;
    }

    /**
     * Removes the association between the reservation and the book/client.
     */
    public void remove() {
        if (book != null) {
            Book bk = this.book;
            setBook(null);
            bk.removeReservation(this);
        }
        if (client != null) {
            Client cl = this.client;
            setClient(null);
            cl.removeReservation(this);
        }
    }


}
