package edu.pja.mas.s22460.mp_gui.utils;

public interface IClerk {
    /**
     * Retrieves the number of tasks to do for the clerk.
     * @return The number of tasks to do.
     */
    Integer getNumberOfTasksToDo();

    /**
     * Sets the number of tasks to do for the clerk.
     * @param numberOfTasks The number of tasks to do.
     */
    void setNumberOfTasksToDo(Integer numberOfTasks);
}
