package everything;

import edu.pja.mas.s22460.mp_gui.users.Employee;
import edu.pja.mas.s22460.mp_gui.utils.EmpType;
import edu.pja.mas.s22460.mp_gui.validation.MyException;
import org.junit.jupiter.api.Test;
import java.util.EnumSet;
import static org.junit.jupiter.api.Assertions.*;
public class overAndDynamic {
    @Test
    public void testBecomeLibrarian() {
        Employee employee = Employee.builder()
                .empTypes(EnumSet.of(EmpType.CLERK))
                .numberOfTasks(5)
                .build();

        employee.becomeLibrarian("2 years of experience");

        assertTrue(employee.getEmpTypes().contains(EmpType.LIBRARIAN));
        assertNull(employee.getNumberOfTasksToDo());
        assertEquals("2 years of experience", employee.getExperience());
    }

    @Test
    public void testBecomeClerk() {
        Employee employee1 = Employee.builder()
                .empTypes(EnumSet.of(EmpType.LIBRARIAN))
                .experience("3 years of experience")
                .build();

        employee1.becomeClerk(7);

        assertTrue(employee1.getEmpTypes().contains(EmpType.CLERK));
        assertNull(employee1.getExperience());
        assertEquals(7, employee1.getNumberOfTasksToDo());
    }

    @Test
    public void testBecomeManager() {
        Employee employee2 = Employee.builder()
                .empTypes(EnumSet.of(EmpType.LIBRARIAN))
                .experience("3 years of experience")
                .build();

        employee2.becomeManager(5000.0);

        assertTrue(employee2.getEmpTypes().contains(EmpType.MANAGER));
        assertNull(employee2.getExperience());
        assertEquals(5000.0, employee2.getSalary(), 0.01);
    }

    @Test
    public void testStopBeingClerk() {
        Employee employee3 = Employee.builder()
                .empTypes(EnumSet.of(EmpType.CLERK, EmpType.LIBRARIAN))
                .numberOfTasks(5)
                .experience("3 years of experience")
                .build();

        employee3.stopBeingClerk();

        assertFalse(employee3.getEmpTypes().contains(EmpType.CLERK));
        assertEquals("3 years of experience", employee3.getExperience());
    }

    @Test
    public void testStopBeingLibrarian() {
        Employee employee4 = Employee.builder()
                .empTypes(EnumSet.of(EmpType.CLERK, EmpType.LIBRARIAN))
                .numberOfTasks(5)
                .experience("3 years of experience")
                .build();

        employee4.stopBeingLibrarian();

        assertFalse(employee4.getEmpTypes().contains(EmpType.LIBRARIAN));
        assertEquals(5, employee4.getNumberOfTasksToDo());
    }

    @Test
    public void testStopBeingManager() {
        Employee employee5 = Employee.builder()
                .empTypes(EnumSet.of(EmpType.MANAGER))
                .salary(5000.0)
                .build();

        employee5.stopBeingManager();

        assertFalse(employee5.getEmpTypes().contains(EmpType.MANAGER));
    }
}
