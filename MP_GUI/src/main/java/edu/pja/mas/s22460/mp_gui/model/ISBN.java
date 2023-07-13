package edu.pja.mas.s22460.mp_gui.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ISBN {

    @NotBlank
    private String isbn;

    /**
     * The unique number of the ISBN.
     */
    @NotBlank
    @Column(name = "unique_number", nullable = false, unique = true, length = 13)
    @Pattern(regexp = "\\d{13}", message = "Unique number should contain 13 digits")
    private String uniqueNumber;

}

