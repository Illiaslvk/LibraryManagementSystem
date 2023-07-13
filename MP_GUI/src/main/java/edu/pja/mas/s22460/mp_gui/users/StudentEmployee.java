package edu.pja.mas.s22460.mp_gui.users;

import edu.pja.mas.s22460.mp_gui.utils.IStudent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StudentEmployee extends Employee implements IStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "hourly_wage")
    private double hourlyWage;

    /**
     * Retrieves the year of study of the student employee.
     * @return The year of study.
     */
    @Override
    public int getYearOfStudy() {
        return 0;
    }

    /**
     * Sets the year of study of the student employee.
     * @param yearOfStudy The year of study to set.
     */
    @Override
    public void setYearOfStudy(int yearOfStudy) {

    }

    /**
     * Retrieves the student ID of the student employee.
     * @return The student ID.
     */
    @Override
    public String getStudentId() {
        return null;
    }

    /**
     * Sets the student ID of the student employee.
     * @param studentId The student ID to set.
     */
    @Override
    public void setStudentId(String studentId) {

    }

}
