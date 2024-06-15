package com.example.forodiscusion.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.forodiscusion.model.Respuesta;
import com.example.forodiscusion.model.Tema;
import com.example.forodiscusion.repository.RespuestaRepository;
import com.example.forodiscusion.repository.TemaRepository;

@Controller
public class ForoController {

    private final TemaRepository temaRepository;
    private final RespuestaRepository respuestaRepository;

    public ForoController(TemaRepository temaRepository, RespuestaRepository respuestaRepository) {
        this.temaRepository = temaRepository;
        this.respuestaRepository = respuestaRepository;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/home")
    public String home2() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/foro")
    public String foro(Model model) {
        List<Tema> temas = temaRepository.findAll();
        model.addAttribute("temas", temas);
        return "foro";
    }

    @GetMapping("/nuevo-tema")
    public String nuevoTema(Model model) {
        model.addAttribute("tema", new Tema());
        return "nuevo-tema";
    }

    @PostMapping("/nuevo-tema")
    public String crearTema(@ModelAttribute("tema") Tema tema) {
        temaRepository.save(tema);
        return "redirect:/foro";
    }

    @GetMapping("/tema/{id}")
    public String verTema(@PathVariable("id") Long id, Model model) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tema no encontrado"));
        List<Respuesta> respuestas = respuestaRepository.findByTemaId(id);
        model.addAttribute("tema", tema);
        model.addAttribute("respuestas", respuestas);
        model.addAttribute("nuevaRespuesta", new Respuesta());
        return "tema";
    }

    @PostMapping("/tema/{id}/responder")
    public String responderTema(@PathVariable("id") Long id, @ModelAttribute("nuevaRespuesta") Respuesta respuesta) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tema no encontrado"));
        respuesta.setTema(tema);
        respuestaRepository.save(respuesta);
        return "redirect:/tema/{id}";
    }
}
