package edu.pja.mas.s22460.mp_gui.utils;

public interface IStudent {
    /**
     * Retrieves the year of study of the student.
     * @return The year of study of the student.
     */
    int getYearOfStudy();

    /**
     * Sets the year of study of the student.
     * @param yearOfStudy The year of study of the student.
     */
    void setYearOfStudy(int yearOfStudy);

    /**
     * Retrieves the student ID of the student.
     * @return The student ID of the student.
     */
    String getStudentId();

    /**
     * Sets the student ID of the student.
     * @param studentId The student ID of the student.
     */
    void setStudentId(String studentId);
}