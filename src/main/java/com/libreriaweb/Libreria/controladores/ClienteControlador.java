
package com.libreriaweb.Libreria.controladores;

import com.libreriaweb.Libreria.entidades.Cliente;
import com.libreriaweb.Libreria.excepciones.ExcepcionesServicio;
import com.libreriaweb.Libreria.servicios.ClienteServicio;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    @GetMapping("/registrarse")
    public String registrarse(ModelMap modelo) {
       
        return "formulario-registro.html";
    }
    
    @PostMapping("/registro-cliente")
    public String registrar(ModelMap modelo, @RequestParam String documento, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, @RequestParam String clave) throws ExcepcionesServicio, Exception {
            try {
            clienteServicio.registrar(nombre, apellido, documento, telefono, clave);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("documento", documento);
            modelo.put("telefono", telefono);
            modelo.put("clave", clave);
            return "formulario-registro.html";
        }
        modelo.put("titulo", "Bienvenido a biblioteca web");
        modelo.put("descripcion", "Tu usuario fué registrado de manera satisfactoria");
        return "index.html";
        }
    
        @GetMapping("/prestamo")
    public String prestamo(ModelMap modelo) {
       
        return "prestamo.html";
    }
    
    @GetMapping("/devolucion")
    public String devolcion(ModelMap modelo) {
       
        return "devolucion.html";
    }

    }
    
    



