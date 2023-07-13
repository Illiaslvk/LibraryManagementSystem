package edu.pja.mas.s22460.mp_gui.repository;

import edu.pja.mas.s22460.mp_gui.model.Book;
import edu.pja.mas.s22460.mp_gui.model.Library;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface LibraryRepository extends CrudRepository<Library, Long> {

    /**
     * Retrieves a library by its ID along with its associated books.
     * @param id the ID of the library
     * @return an optional containing the library with the specified ID, or an empty optional if not found
     */
    @Query("from Library as r left join fetch r.books where r.id = :id")
    Optional<Library> findById(@Param("id") Long id);

    @Query("SELECT b FROM Library r JOIN r.books b WHERE r.id = :id ORDER BY b.name")
    Set<Book> getBooksSortedByName(@Param("id") Long id);

}
