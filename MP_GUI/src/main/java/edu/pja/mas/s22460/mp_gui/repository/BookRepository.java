package edu.pja.mas.s22460.mp_gui.repository;

import edu.pja.mas.s22460.mp_gui.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface BookRepository extends CrudRepository<Book, Long>{

}
