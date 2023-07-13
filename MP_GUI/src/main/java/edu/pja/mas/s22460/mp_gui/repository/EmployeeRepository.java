package edu.pja.mas.s22460.mp_gui.repository;

import edu.pja.mas.s22460.mp_gui.users.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
