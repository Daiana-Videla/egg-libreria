package com.libreriaweb.Libreria.controladores;

import com.libreriaweb.Libreria.entidades.Autor;
import com.libreriaweb.Libreria.servicios.AutorServicio;
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
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private LibroServicio libroServicio;

    //>>>>>>>>>>MUESTRA EL FORMULARIO DE CARGA DE AUTOR<<<<<<<<<<
    @GetMapping("/cargarAutor")
    public String cargar(ModelMap modelo) {
        modelo.addAttribute("fecha", libroServicio.Fecha());
        modelo.put("cantLibros", libroServicio.cantidadLibros());
        return "cargar-autor";
    }

    //>>>>>>>>>>RECIBE LOS DATOS DE LA CARGA DEL AUTOR Y MUESTRA EL FORMULARIO NUEVAMENTE<<<<<<<<<<
    @PostMapping("/carga")
    public String guardar(ModelMap modelo, @RequestParam String nombre) {
        try {
            autorServicio.crearAutor(nombre);
            modelo.put("exito", "Registro exitoso");
            modelo.addAttribute("fecha", libroServicio.Fecha());
            modelo.put("cantLibros", libroServicio.cantidadLibros());
            return "cargar-autor";
        } catch (Exception e) {
            modelo.put("error", "Error al cargar el autor");
            return "cargar-autor";
        }
    }

    //>>>>>>>>>>MUESTRA EL FORMULARIO DE MODIFICACION DE AUTOR<<<<<<<<<<
    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        modelo.put("autor", autorServicio.getOne(id));
        modelo.addAttribute("fecha", libroServicio.Fecha());
        modelo.put("cantLibros", libroServicio.cantidadLibros());
        return "modificar-autor";
    }

    //>>>>>>>>>>RECIBE LOS DATOS DE LA MODIFICACION DEL AUTOR Y LUEGO MUESTRA LA LISTA DE AUTORES, SI DA ERROR MUESTRA DE NUEVO EL FORMULARIO<<<<<<<<<<
    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, String nombre) {
        try {
            autorServicio.modificar(id, nombre);
            modelo.put("exito", "Modificacion exitosa");
            List<Autor> autorLista = autorServicio.listarTodos();
            modelo.addAttribute("autores", autorLista);
            modelo.addAttribute("fecha", libroServicio.Fecha());
            modelo.put("cantLibros", libroServicio.cantidadLibros());
            return "lista-autor";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "modificar-autor";
        }
    }

    //>>>>>>>>>>SETEA EL ATRIBUTO ALTA EN TRUE Y LUEGO MUESTRA LA LISTA DE AUTORES<<<<<<<<<<
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {
        try {
            autorServicio.alta(id);
            return "redirect:/autor/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    //>>>>>>>>>>SETEA EL ATRIBUTO ALTA EN FALSE Y LUEGO MUESTRA LA LISTA DE AUTORES<<<<<<<<<<
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {
        try {
            autorServicio.baja(id);
            return "redirect:/autor/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    //>>>>>>>>>>MUESTRA LA LISTA DE AUTORES CON OPCIONES DE ALTA, BAJA Y MODIFICACION<<<<<<<<<<
    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Autor> autorLista = autorServicio.listarTodos();
        modelo.addAttribute("autores", autorLista);
        modelo.addAttribute("fecha", libroServicio.Fecha());
        modelo.put("cantLibros", libroServicio.cantidadLibros());

        return "lista-autor";
    }

    //>>>>>>>>>>BUSCA POR NOMBRE FILTRADO<<<<<<<<<<
    @PostMapping("/buscar")
    public String buscar(ModelMap modelo, @RequestParam String nombre, String activo, String inactivo) {
        try {
            List<Autor> bAutores = autorServicio.buscarFiltrado(nombre, activo, inactivo);
            modelo.put("bAutores", bAutores);
            modelo.put("fecha", libroServicio.Fecha());
            modelo.put("cantLibros", libroServicio.cantidadLibros());
            return "detalle-autor";
        } catch (Exception e) {
            modelo.put("error", "Error en la busqueda");
            return "lista-autor";
        }
    }

}
