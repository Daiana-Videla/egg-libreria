package com.libreriaweb.Libreria.controladores;

import com.libreriaweb.Libreria.entidades.Cliente;
import com.libreriaweb.Libreria.servicios.LibroServicio;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    LibroServicio libroServicio;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        modelo.addAttribute("fecha", libroServicio.Fecha());
        return "index.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Nombre de usuario o clave incorrectos.");
        }
        if (logout != null) {
            modelo.put("logout", "Ha salido correctamente");
        }
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {
        Cliente login = (Cliente) session.getAttribute("clientesession");
        if ("ADMIN".equals(login.getRol().toString())) {
            modelo.addAttribute("fecha", libroServicio.Fecha());
            return "inicioAdmin.html";
        } else {
            modelo.addAttribute("fecha", libroServicio.Fecha());
            return "inicioUsuario.html";
        }

    }
}
