
package com.libreriaweb.Libreria.servicios;

import com.libreriaweb.Libreria.entidades.Cliente;
import com.libreriaweb.Libreria.entidades.Libro;
import com.libreriaweb.Libreria.entidades.Prestamo;
import com.libreriaweb.Libreria.repositorios.PrestamoRepositorio;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoServicio {
   
    @Autowired
    private PrestamoRepositorio prestamoRepositorio;
    @Autowired
    private LibroServicio libroservicio;
    @Autowired
    private ClienteServicio clienteservicio; 
    
    
    
    public void crearPrestamo (Date fechaprestamo,Date fechadevolucion, String libro, String cliente ){
        
        
        Prestamo prestamo = new Prestamo ();
        Libro l = libroservicio.buscarUnoPorTitulo(libro);
        l.setEjemplaresPrestados(l.getEjemplaresPrestados()+1);
        l.setEjemplaresRestantes(l.getEjemplaresRestantes()-1);
        prestamo.setFechaprestamo(fechaprestamo);
        prestamo.setFechadevolucion(fechadevolucion);
        prestamo.setAlta(true);
        prestamo.setLibro(libroservicio.buscarUnoPorTitulo(libro));
        prestamo.setCliente(clienteservicio.buscarUnoPorDocumento(cliente));
        
        prestamoRepositorio.save(prestamo);
    }
    
    
    
     public List<Prestamo> ListarPrestamosPorCliente(HttpSession session) {
        Cliente login = (Cliente) session.getAttribute("clientesession");
        return prestamoRepositorio.listar(login.getDocumento());
        
    }
    

        
    
    
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class}) 
    public void alta(String id) {
        Prestamo prestamo = prestamoRepositorio.getOne(id);
        prestamo.setAlta(true);
        prestamoRepositorio.save(prestamo);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void baja(String id) {
        Prestamo prestamo = prestamoRepositorio.getOne(id);
        prestamo.setAlta(false);
        prestamoRepositorio.save(prestamo);
    }
    
    

    
    
}
