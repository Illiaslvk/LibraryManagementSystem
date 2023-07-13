package edu.pja.mas.s22460.mp_gui.users;

import edu.pja.mas.s22460.mp_gui.model.Library;
import edu.pja.mas.s22460.mp_gui.utils.EmpType;
import edu.pja.mas.s22460.mp_gui.utils.IClerk;
import edu.pja.mas.s22460.mp_gui.utils.ILibrarian;
import edu.pja.mas.s22460.mp_gui.utils.IManager;
import edu.pja.mas.s22460.mp_gui.validation.BookValidation;
import edu.pja.mas.s22460.mp_gui.validation.MyException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Employee extends Client implements IClerk, ILibrarian, IManager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "apartment_address")
    private String apartmentAddress;

    @ManyToOne
    @JoinColumn(name = "library_id") //nullable = false, updatable = false
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Library library;

    /**
     * The set of employee types associated with the employee.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = EmpType.class)
    @Column(nullable = false)
    private Set<EmpType> empTypes;

    /**
     * The number of tasks to be done by the clerk.
     * Clerk
     */
    @Column(name = "clerkTasks")
    private Integer numberOfTasks;

    /**
     * The experience of the librarian.
     * librarian
     */
    @Column(name = "librarianExperience")
    private String experience;

    /**
     * The salary of the manager.
     * manager
     */
    @Column(name = "managerSalary")
    private Double salary;

    /**
     * Constructs an Employee object with the specified parameters.
     * @param empTypes the set of employee types associated with the employee
     * @param email the email of the employee
     * @param apartmentAddress the apartment address of the employee
     * @param numberOfTasks the number of tasks to be done by the clerk
     * @param experience the experience of the librarian
     * @param salary the salary of the manager
     */
    public Employee(EnumSet<EmpType> empTypes,String email, String apartmentAddress,
                    Integer numberOfTasks,String experience,Double salary){
        setEmpTypes(empTypes);
        setEmail(email);
        setApartmentAddress(apartmentAddress);
        //attributes for CLERK
        if (empTypes.contains(EmpType.CLERK)){
            setNumberOfTasksToDo(numberOfTasks);
        }
        //attributes for LIBRARIAN
        if (empTypes.contains(EmpType.LIBRARIAN)){
            setExperience(experience);
        }
        //attributes for MANAGER
        if (empTypes.contains(EmpType.MANAGER)){
            setSalary(salary);
        }

    }

    //Clerk
    @Override
    public Integer getNumberOfTasksToDo() {
        if(isClerk()) {
            return numberOfTasks;
        }
        throw new MyException("This object does not have property requested");
    }

    @Override
    public void setNumberOfTasksToDo(Integer numberOfTasks) {
        if(isClerk()) {
            if(numberOfTasks == null) {
                throw new MyException("number of tasks cannot be null!!!");
            }
            this.numberOfTasks = numberOfTasks;
            return;
        }
        throw new MyException("This object does not have property requested");
    }

    //Librarian
    @Override
    public String getExperience() {
        if(isLibrarian()){
            return experience;
        }
        throw new MyException("This object does not have property requested");
    }

    @Override
    public void setExperience(String experience) {
        if(isLibrarian()) {
            if(experience == null || experience.isBlank()) {
                throw new MyException("experience cannot be null!!!");
            }
            this.experience = experience;
            return;
        }
        throw new BookValidation("This object does not have property requested");
    }

    //Manager
    @Override
    public Double getSalary() {
        if (isManager()) {
            return salary;
        }
        throw new MyException("This object does not have the 'salary' property.");
    }

    @Override
    public void setSalary(Double salary) throws MyException {
        if (empTypes.contains(EmpType.MANAGER)) {
            this.salary = salary;
        } else {
            throw new MyException("This object does not have the 'salary' property.");
        }
    }

    /**
     * Retrieves the set of employee types associated with the employee.
     * @return the set of employee types
     */
    public Set<EmpType> getEmpTypes() {
        return empTypes;
    }

    /**
     * Sets the employee types associated with the employee.
     * @param empType the set of employee types to be set
     * @throws MyException if the employee types set is empty
     */
    private void setEmpTypes(EnumSet<EmpType> empType) {
        //should contain at least 1 type
        if (empType == null || empType.size() == 0){
            throw new MyException("Emp types cannot be empty!");
        }
        if(empTypes == null) {
            empTypes = EnumSet.copyOf(empType);
        }
    }

    //Dynamic
    private boolean isLibrarian() {
        return empTypes.contains(EmpType.LIBRARIAN);
    }

    private boolean isClerk() {
        return empTypes.contains(EmpType.CLERK);
    }

    private boolean isManager() {
        return empTypes.contains(EmpType.MANAGER);
    }

    /**
     * Makes the employee become a librarian with the specified experience.
     * @param experience the experience of the librarian
     */
    public void becomeLibrarian(String experience) {
        if (isLibrarian()) {
            return;
        }
        if (isClerk()) {
            numberOfTasks = null;
        }
        if (isManager()) {
            salary = null;
        }
        empTypes.add(EmpType.LIBRARIAN);
        setExperience(experience);
    }

    /**
     * Makes the employee become a clerk with the specified number of tasks.
     * @param numberOfTasks the number of tasks to be done by the clerk
     */
    public void becomeClerk(Integer numberOfTasks) {
        if (isClerk()) {
            return;
        }
        if (isLibrarian()) {
            experience = null;
        }
        if (isManager()) {
            salary = null;
        }
        empTypes.add(EmpType.CLERK);
        setNumberOfTasks(numberOfTasks);
    }

    /**
     * Makes the employee become a manager with the specified salary.
     * @param salary the salary of the manager
     */
    public void becomeManager(double salary) {
        if (isManager()) {
            return;
        }
        if (isLibrarian()) {
            experience = null;
        }
        if (isClerk()) {
            numberOfTasks = null;
        }
        empTypes.add(EmpType.MANAGER);
        setSalary(salary);
    }

    /**
     * Removes the clerk role from the employee.
     */
    public void stopBeingClerk(){
        this.numberOfTasks = null;
        empTypes.remove(EmpType.CLERK);
    }

    /**
     * Removes the librarian role from the employee.
     */
    public void stopBeingLibrarian(){
        this.experience = null;
        empTypes.remove(EmpType.LIBRARIAN);
    }

    /**
     * Removes the manager role from the employee.
     */
    public void stopBeingManager(){
        this.salary = null;
        empTypes.remove(EmpType.MANAGER);
    }

    /**
     * Displays the list of employees associated with the given apartment address.
     * If the apartment address is not set, it prints an appropriate message and returns.
     * It iterates over the employees in the library and finds the employees whose apartment address matches the given address.
     * If matching employees are found, it prints their names. Otherwise, it prints a message indicating that no employees were found.
     */
    public void displayEmployeeList() {
        if (apartmentAddress == null || apartmentAddress.isBlank()) {
            System.out.println("Apartment address is not set");
            return;
        }

        Set<Employee> employees = library.getEmployees();
        Set<Employee> matchingEmployees = new HashSet<>();

        for (Employee employee : employees) {
            if (apartmentAddress.equalsIgnoreCase(employee.getApartmentAddress())) {
                matchingEmployees.add(employee);
            }
        }

        if (matchingEmployees.isEmpty()) {
            System.out.println("No employees found for the given apartment address");
        } else {
            System.out.println("Employees for the apartment address: " + apartmentAddress);
            for (Employee employee : matchingEmployees) {
                System.out.println(employee.getName());
            }
        }
    }

}

