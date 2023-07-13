package edu.pja.mas.s22460.mp_gui.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Period;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String rating;

    @NotBlank
    @Size(min = 5, max = 500)
    private String comment;


    /**
     * The book associated with the opinion.
     */
    @ManyToOne(optional = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "book_id",nullable = false, updatable = false)
    private Book book;

    public static void displayReviews(Set<Opinion> opinions) {
        for (Opinion opinion : opinions) {
            System.out.println("Rating: " + opinion.getRating());
            System.out.println("Comment: " + opinion.getComment());
            System.out.println("----------------------------------------");
        }
    }

}
