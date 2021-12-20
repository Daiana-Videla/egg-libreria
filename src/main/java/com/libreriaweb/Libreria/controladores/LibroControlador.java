package com.libreriaweb.Libreria.controladores;

import com.libreriaweb.Libreria.entidades.Libro;
import com.libreriaweb.Libreria.excepciones.ExcepcionesServicio;
import com.libreriaweb.Libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroservicio;

    @GetMapping("/guardar")
    public String cargar(ModelMap modelo) {
        modelo.addAttribute("fecha", libroservicio.Fecha());
        modelo.put("cantLibros", libroservicio.cantidadLibros());
        return "cargar-libro.html";
    }

    @PostMapping("/guardarLibro")
    public String registrar(ModelMap modelo, @RequestParam Integer isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares,
            @RequestParam String autor, @RequestParam String editorial)  {
        try {
            libroservicio.ingresarLibro(isbn, titulo, anio, ejemplares, true, autor, editorial);
            modelo.put("exito", "Libro correctamente guardado");
             modelo.addAttribute("fecha", libroservicio.Fecha());
            modelo.put("cantLibros", libroservicio.cantidadLibros());
            return "cargar-libro.html";
        } catch (Exception e) {
            modelo.put("error", "Error en la carga de datos");
            return "cargar-libro.html";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("libro", libroservicio.getOne(id));
        libroservicio.getOne(id);
        return "modificar-libro";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam Integer isbn, @RequestParam String titulo, @RequestParam Integer anio,
            @RequestParam Integer ejemplares, @RequestParam String autor, @RequestParam String editorial) {
        try {
            libroservicio.modificarLibro(id, isbn, titulo, anio, ejemplares, autor, editorial);
            modelo.put("exito", "Modificacion exitosa");
            List<Libro> librosLista = libroservicio.listarTodos();
            modelo.addAttribute("libros", librosLista);
            return "lista-libros";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "modificar-libro";
        }
    }

    //>>>>>>>>>>MUESTRA LA LISTA DE AUTORES CON OPCIONES DE ALTA, BAJA Y MODIFICACION<<<<<<<<<<
    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Libro> libroLista = libroservicio.listarTodos();
        modelo.addAttribute("libros", libroLista);
        modelo.addAttribute("fecha", libroservicio.Fecha());
        modelo.put("cantLibros", libroservicio.cantidadLibros());
        return "lista-libros";
    }

    //>>>>>>>>>>BUSCA POR NOMBRE FILTRADO<<<<<<<<<<
    @PostMapping("/buscar")
    public String buscar(ModelMap modelo, String titulo, String activo, String inactivo) {
        try {
            List<Libro> bLibros = libroservicio.buscarFiltrado(titulo, activo, inactivo);
            modelo.put("bLibros", bLibros);
            modelo.put("fecha", libroservicio.Fecha());
            modelo.put("cantLibros", libroservicio.cantidadLibros());
            return "detalle-libro";
        } catch (Exception e) {
            modelo.put("error", "Error en la busqueda");
            return "lista-libros";
        }
    }
    
     //>>>>>>>>>>SETEA EL ATRIBUTO ALTA EN TRUE Y LUEGO MUESTRA LA LISTA DE LIBROS<<<<<<<<<<
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {
        try {
            libroservicio.alta(id);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            return "redirect:/inicioAdmin";
        }
    }

    //>>>>>>>>>>SETEA EL ATRIBUTO ALTA EN FALSE Y LUEGO MUESTRA LA LISTA DE LIBROS<<<<<<<<<<
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {
        try {
            libroservicio.baja(id);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            return "redirect:/inicioAdmin";
        }
    }
}
