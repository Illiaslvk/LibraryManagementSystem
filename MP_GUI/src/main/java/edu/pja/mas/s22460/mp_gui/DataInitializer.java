package edu.pja.mas.s22460.mp_gui;

import edu.pja.mas.s22460.mp_gui.model.*;
import edu.pja.mas.s22460.mp_gui.repository.*;
//import edu.pja.mas.s22460.mp_gui.repository.ISBNRepository;
import edu.pja.mas.s22460.mp_gui.users.Client;
import edu.pja.mas.s22460.mp_gui.users.Employee;
import edu.pja.mas.s22460.mp_gui.users.Student;
import edu.pja.mas.s22460.mp_gui.users.StudentEmployee;
import edu.pja.mas.s22460.mp_gui.utils.BookGenre;
import edu.pja.mas.s22460.mp_gui.utils.EmpType;
import edu.pja.mas.s22460.mp_gui.utils.ReservationState;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final ReservationRepository reservationRepository;
    private final OpinionRepository opinionRepository;
    private final BookRestorationRepository bookRestorationRepository;
    private final PaymentRepository paymentRepository;
    private final StudentEmployeeRepository studentEmployeeRepository;
    private final StudentRepository studentRepository;

    @EventListener
    public void atStart(ContextRefreshedEvent ev) {
        System.out.println("Context refreshed");

        ISBN isbn1 = new ISBN();
        isbn1.setIsbn("ISBN");
        isbn1.setUniqueNumber("1231231231231");

        ISBN isbn2 = new ISBN();
        isbn2.setIsbn("ISBN");
        isbn2.setUniqueNumber("2231231231231");

        ISBN isbn3 = new ISBN();
        isbn3.setIsbn("ISBN");
        isbn3.setUniqueNumber("3231231231231");


        Library library1 = new Library();
        library1.setName("Central Library");

        Library library2 = new Library();
        library2.setName("Local Library");

        Library library3 = new Library();
        library3.setName("Small Library");

        libraryRepository.save(library1);
        libraryRepository.save(library2);
        libraryRepository.save(library3);

        // Book 1
        Book book1 = new Book();
        book1.setName("One Piece");
        book1.setBookGenre(BookGenre.FANTASY);
        book1.setPrice(10.0);
        book1.setEdition("asd");
        book1.setYearOfWriting(LocalDate.of(1999, 9, 12));
        book1.setIsbn(isbn1);
        Set<String> authors1 = new HashSet<>();
        authors1.add("A1");
        authors1.add("A2");
        book1.setAuthors(authors1);
        book1.setLibrary(library1);
        bookRepository.save(book1);

        // Book 2
        Book book2 = new Book();
        book2.setName("Harry Potter and the Philosopher's Stone");
        book2.setBookGenre(BookGenre.FANTASY);
        book2.setPrice(15.0);
        book2.setEdition("xyz");
        book2.setYearOfWriting(LocalDate.of(1997, 6, 26));
        book2.setIsbn(isbn2);
        Set<String> authors2 = new HashSet<>();
        authors2.add("J.K. Rowling");
        book2.setAuthors(authors2);
        book2.setLibrary(library2);
        bookRepository.save(book2);

        // Book 3
        Book book3 = new Book();
        book3.setName("To Kill a Mockingbird");
        book3.setBookGenre(BookGenre.DRAMA);
        book3.setPrice(20.0);
        book3.setEdition("abc");
        book3.setYearOfWriting(LocalDate.of(1960, 7, 11));
        book3.setIsbn(isbn3);
        Set<String> authors3 = new HashSet<>();
        authors3.add("Harper Lee");
        book3.setAuthors(authors3);
        book3.setLibrary(library3);
        bookRepository.save(book3);

        // Book 4
        Book book4 = new Book();
        book4.setName("The Great Gatsby");
        book4.setBookGenre(BookGenre.HISTORY);
        book4.setPrice(25.0);
        book4.setEdition("def");
        book4.setYearOfWriting(LocalDate.of(1925, 4, 10));
        ISBN isbn4 = new ISBN();
        isbn4.setIsbn("ISBN");
        isbn4.setUniqueNumber("4231231231231");
        book4.setIsbn(isbn4);
        Set<String> authors4 = new HashSet<>();
        authors4.add("F. Scott Fitzgerald");
        book4.setAuthors(authors4);
        book4.setLibrary(library1);
        bookRepository.save(book4);

        // Book 5
        Book book5 = new Book();
        book5.setName("SMTH1984");
        book5.setBookGenre(BookGenre.SCIENCE_FICTION);
        book5.setPrice(30.0);
        book5.setEdition("ghi");
        book5.setYearOfWriting(LocalDate.of(1949, 6, 8));
        ISBN isbn5 = new ISBN();
        isbn5.setIsbn("ISBN");
        isbn5.setUniqueNumber("5231231231231");
        book5.setIsbn(isbn5);
        Set<String> authors5 = new HashSet<>();
        authors5.add("George Orwell");
        book5.setAuthors(authors5);
        book5.setLibrary(library1);
        bookRepository.save(book5);

        //Client
        // Create a new client
        Client client1 = new Client();
        client1.setName("John Doe");
        client1.setAge(30);
        client1.setDateOfBirth(LocalDate.of(1992, 5, 15));
        client1.setPhoneNumber("123456789");
        clientRepository.save(client1);

        // overlapping
        EnumSet<EmpType> types = EnumSet.of(EmpType.LIBRARIAN,EmpType.MANAGER);

        Employee employee1 = Employee.builder()
                .name("Sarah")
                .dateOfBirth(LocalDate.of(2020,6,6))
                .age(19)
                .phoneNumber("123456789")
                .email("assad@gmail.com")
                .apartmentAddress("23 street7")
                .empTypes(types)
                .experience("3 year")
                .salary(2000.00)
                .build();

        employeeRepository.save(employee1);


        //Dynamic
        EnumSet<EmpType> types2= EnumSet.of(EmpType.LIBRARIAN);
        Employee employee2 = Employee.builder()
                .name("SSS")
                .dateOfBirth(LocalDate.of(2021,6,6))
                .age(53)
                .phoneNumber("123456789")
                .email("assad@gmail.com")
                .apartmentAddress("street7")
                .empTypes(types2)
                .experience("1 year")
                .build();

        employee2.becomeClerk(15);
        employeeRepository.save(employee2);



        //Reservation || With an Attribute

        ISBN isbnForReservation = new ISBN();
        isbnForReservation.setIsbn("ISBN");
        isbnForReservation.setUniqueNumber("1223334444555");

        Book bookForReservation = new Book();
        bookForReservation.setName("Pancake");
        bookForReservation.setBookGenre(BookGenre.HORROR);
        bookForReservation.setPrice(10.0);
        bookForReservation.setEdition("ghj");
        //bookForReservation.setYearOfWriting(LocalDate.of(2012, 9, 12));
        bookForReservation.setIsbn(isbnForReservation);
        Set<String> authorForReservation = new HashSet<>();
        authorForReservation.add("A1");
        bookForReservation.setAuthors(authorForReservation);
        bookForReservation.setLibrary(library3);
        bookRepository.save(bookForReservation);

        Client clientForReservation = new Client();
        clientForReservation.setName("TestReservation");
        clientForReservation.setAge(30);
        clientForReservation.setDateOfBirth(LocalDate.of(2001, 5, 15));
        clientForReservation.setPhoneNumber("987653213");
        clientRepository.save(clientForReservation);

        Reservation reservation = Reservation.builder()
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(7))
                .bookStatusBefore(3)
                .bookStatusAfter(4)
                .state(ReservationState.CONFIRMED)
                .build();

        reservation.setStartDate(LocalDate.of(2023,6,25));
        reservation.setEndDate(LocalDate.of(2023,6,30));
        reservation.setBook(bookForReservation);
        reservation.setClient(clientForReservation);

        reservationRepository.save(reservation);


        // Composition
        Opinion opinion1 = Opinion.builder()
                .rating("5 stars")
                .comment("Great book!")
                .book(book1)
                .build();
        opinionRepository.save(opinion1);

        Opinion opinion2 = Opinion.builder()
                .rating("4 stars")
                .comment("Enjoyed reading it.")
                .book(book2)
                .build();
        opinionRepository.save(opinion2);

        //Qualified
        ISBN isbnForRestoration = new ISBN();
        isbnForRestoration.setIsbn("ISBN");
        isbnForRestoration.setUniqueNumber("5555534444555");

        Book bookForRestoration = new Book();
        bookForRestoration.setName("restorationTest");
        bookForRestoration.setBookGenre(BookGenre.STUDY);
        bookForRestoration.setPrice(10.0);
        bookForRestoration.setEdition("res");
        //bookForReservation.setYearOfWriting(LocalDate.of(2012, 9, 12));
        bookForRestoration.setIsbn(isbnForRestoration);
        Set<String> authorForRestoration = new HashSet<>();
        authorForRestoration.add("Rest Test");
        bookForRestoration.setAuthors(authorForRestoration);
        bookForRestoration.setLibrary(library3);
        bookRepository.save(bookForRestoration);

        //BookRestoration
        BookRestoration bookRestoration1 = BookRestoration.builder()
                .restorationCost(50)
                .restorationTime(LocalTime.of(9, 0))
                .build();
        bookRestoration1.addBook(bookForRestoration);
        bookRestorationRepository.save(bookRestoration1);

        //MultiInheritance
        EnumSet<EmpType> types3 = EnumSet.of(EmpType.CLERK);
        StudentEmployee studentEmployee = StudentEmployee.builder()
                .name("StudentEmployee")
                .age(17)
                .phoneNumber("576576576")
                .email("studentEmployee@gmail.com")
                .apartmentAddress("library")
                .empTypes(types3)
                .hourlyWage(22.2)
                .build();

        studentEmployeeRepository.save(studentEmployee);

        //ordered
        Set<Book> orderedBooks = libraryRepository.getBooksSortedByName(Long.valueOf(1));

        for (Book book : orderedBooks) {
            System.out.println(book.getName());
        }


        //Payment
        Payment payment1 = new Payment();
        payment1.setCardNumber(123456789);
        payment1.setCvv(123);
        payment1.setDateExpire(LocalDate.of(2023, 12, 31));
        payment1.setClient(client1);
        paymentRepository.save(payment1);

        //Student
        Student student = new Student();
        student.setName("Student1");
        student.setAge(20);
        student.setDateOfBirth(LocalDate.of(2003, 8, 10));
        student.setPhoneNumber("987654321");
        student.setYearOfStudy(2);
        student.setStudentId("123456789");
        clientRepository.save(student);

    }
}
