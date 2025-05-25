package com.Joseleandro.Sistema_gerenciador_de_certificados.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String formularioEnvio() {
        return "formAdmin";
    }

}
