
package com.libreriaweb.Libreria.controladores;

import com.libreriaweb.Libreria.entidades.Cliente;
import com.libreriaweb.Libreria.entidades.Libro;
import com.libreriaweb.Libreria.entidades.Prestamo;
import com.libreriaweb.Libreria.excepciones.ExcepcionesServicio;
import com.libreriaweb.Libreria.servicios.LibroServicio;
import com.libreriaweb.Libreria.servicios.PrestamoServicio;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/prestamo")
public class PrestamoControlador {
    
    @Autowired
    private PrestamoServicio prestamoServicio;
    
    @Autowired
    private LibroServicio libroServicio;
    
    @GetMapping ("/registrodeprestamo")
    
     public String registrarPrestamo(ModelMap modelo, HttpSession session) {
       List<Libro> libros = libroServicio.listarLibros();
       Cliente login = (Cliente) session.getAttribute("clientesession");
    
       modelo.put("libros", libros);
       modelo.addAttribute("perfil", login);
         
       return "prestamo.html";
    }
    
        @PostMapping ("/prestamo")
     
    public String registrar(ModelMap modelo,HttpSession session, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") java.util.Date fechaprestamo, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") java.util.Date fechadevolucion, @RequestParam String libro, @RequestParam String cliente) throws ExcepcionesServicio, Exception {
        List <Libro> libros=  libroServicio.listarLibros();
        Cliente login= (Cliente) session.getAttribute("clientesession");
        try {
            prestamoServicio.crearPrestamo(fechaprestamo, fechadevolucion, libro, cliente);
            modelo.put("exito", "Prestamos cargado correctamente");
            modelo.addAttribute("perfil", login);
            return "prestamo"; 
               
        } catch (Exception e) {
            
            modelo.put("error", e.getMessage());
            modelo.put("Fechaprestamo",fechaprestamo);
            modelo.put("Fechadevolucion",fechadevolucion);
            modelo.put("libro", libro);
            modelo.put("cliente", cliente);
           
            return "prestamo";
        }
    }
    
           
    @GetMapping("/lista")
    public String lista(ModelMap modelo,HttpSession session) {
        List<Prestamo> prestamoLista = prestamoServicio.ListarPrestamosPorCliente(session);
    
        modelo.addAttribute("prestamos", prestamoLista);

        return "lista-prestamo";
    }

  
    
    
    
    
    
    
    
     @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {
        try {
            prestamoServicio.alta(id);
            return "redirect:/prestamo/lista";
        } catch (Exception e) {
            return "redirect:/inicioAdmin";
        }
    }

    //>>>>>>>>>>SETEA EL ATRIBUTO ALTA EN FALSE Y LUEGO MUESTRA LA LISTA DE LIBROS<<<<<<<<<<
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {
        try {
            prestamoServicio.baja(id);
            return "redirect:/prestamo/lista";
        } catch (Exception e) {
            return "redirect:/inicioAdmin";
        }
}   
}

                
                
                
                
                
                
                
                
                
                
                
                
            
       
       
