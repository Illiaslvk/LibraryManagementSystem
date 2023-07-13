package edu.pja.mas.s22460.mp_gui.controller;

import edu.pja.mas.s22460.mp_gui.model.Reservation;
import edu.pja.mas.s22460.mp_gui.repository.*;
import edu.pja.mas.s22460.mp_gui.utils.ReservationState;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class ReservationController {
    @Autowired
    LibraryRepository libraryRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @GetMapping("/selectLibrary")
    public String getLibraries(Model model) {
        model.addAttribute("libraries", libraryRepository.findAll());
        return "selectLibrary";
    }

    @GetMapping("/selectBook")
    public String booksInLibrary(@RequestParam("selectedLibrary") Long selectedId, Model model) {
        if (selectedId == null) {
            model.addAttribute("libraries", libraryRepository.findAll());
            model.addAttribute("libraryError", "Please select a library.");
            return "selectLibrary";
        }

        model.addAttribute("library", libraryRepository.findById(selectedId));
        model.addAttribute("books", libraryRepository.findById(selectedId).get().getBooks());
        System.out.println(libraryRepository.findById(selectedId).get().getBooks());
        return "selectBook";
    }

    @GetMapping("/final/{id}")
    public String finalForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookRepository.findById(id).get());
        return "final";
    }

    @GetMapping("/final/approval")
    public String completeReservation(Model model, @RequestParam("bookId") Long selectedBook,
                                      @RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateFromValue,
                                      @RequestParam("endDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date dateToValue){
        model.addAttribute("client",clientRepository.findById(Long.valueOf(1)));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateStart = dateFromValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateEnd = dateToValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Reservation reservation = Reservation.builder()
                .book(bookRepository.findById(selectedBook).get())
                .client(clientRepository.findById(Long.valueOf(1)).get())
                .startDate(dateStart)
                .endDate(dateEnd)
                .bookStatusBefore(1)
                .bookStatusAfter(1)
                .state(ReservationState.CONFIRMED)
                .build();
        reservationRepository.save(reservation);

        return "approval";
    }

}
