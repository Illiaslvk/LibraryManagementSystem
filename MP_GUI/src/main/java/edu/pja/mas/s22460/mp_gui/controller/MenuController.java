package edu.pja.mas.s22460.mp_gui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MenuController {

    @GetMapping("/")
    public String menu() {
        return "menu";
    }

}

