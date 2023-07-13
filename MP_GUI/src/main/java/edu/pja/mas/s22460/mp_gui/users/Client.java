package edu.pja.mas.s22460.mp_gui.users;

import edu.pja.mas.s22460.mp_gui.model.Payment;
import edu.pja.mas.s22460.mp_gui.model.Reservation;
import edu.pja.mas.s22460.mp_gui.validation.MyException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @Min(0)
    private int age;

    @Past
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "\\d{9}")
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * The set of reservations made by the client.
     */
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();

    /**
     * The payment information of the client.
     */
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Payment payment;



    /**
     * Adds a reservation to the client's set of reservations.
     * BAG/HISTORY delete remove the check for uniqueness
     * @param reservation the reservation to be added
     * @throws MyException if the reservation is null or does not pertain to this client
     */
    public void addReservation(Reservation reservation) {
        if (reservation == null) {
            throw new MyException("Reservation cannot be null");
        } else if (reservation.getClient() != this) {
            throw new MyException("This Reservation does not pertain to this customer.");
        }
        this.reservations.add(reservation);
    }

    /**
     * Removes a reservation from the client's set of reservations.
     * @param reservation the reservation to be removed
     * @throws MyException if the reservation is null or if the client has not made a reservation for the specified book
     */
    public void removeReservation(Reservation reservation) {
        if (reservation == null) {
            throw new MyException("Reservation argument can not be null");
        } else if (!reservations.contains(reservation)) {
            throw new IllegalArgumentException("The customer has not made a Reservation for this book.");
        }else{
            this.reservations.remove(reservation);
            reservation.remove();
        }
    }


}




