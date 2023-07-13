package edu.pja.mas.s22460.mp_gui.model;

import edu.pja.mas.s22460.mp_gui.users.Client;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "card_number")
    private int cardNumber;

    @Column(name = "cvv")
    private int cvv;

    @Column(name = "date_expire")
    private LocalDate dateExpire;

    /**
     * The client who made the payment.
     */
    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "client_id")
    private Client client;

}
