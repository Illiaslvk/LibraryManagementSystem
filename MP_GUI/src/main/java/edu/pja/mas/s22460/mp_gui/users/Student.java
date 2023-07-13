package edu.pja.mas.s22460.mp_gui.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a student who is a client of the library.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Student extends Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "year_of_study")
    private int yearOfStudy;

    @Column(name = "student_id", unique = true)
    private String studentId;

    private double discount = 0.0; // Static discount value
    private static final double MIN_DISCOUNT = 0.0;
    private static final double MAX_DISCOUNT = 0.2;

    //static
    public void setDiscount(double discount) {
        if (discount < MIN_DISCOUNT || discount > MAX_DISCOUNT) {
            throw new IllegalArgumentException("Discount must be between 0% and 20%.");
        }
    }
}