package com.libreriaweb.Libreria.controladores;

import com.libreriaweb.Libreria.entidades.Editorial;
import com.libreriaweb.Libreria.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialControlador {
    @Autowired
    private EditorialServicio editorialServicio;
    @Autowired
    private LibroServicio libroServicio;
    @GetMapping("/guardar")
    public String guardar() {
        return "cargar-editorial";
    }
    @PostMapping("/guardarEditorial")
    public String guardar(ModelMap modelo, @RequestParam String nombre) {
        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "Registro exitoso");
            modelo.addAttribute("fecha", libroServicio.Fecha());
            modelo.put("cantLibros", libroServicio.cantidadLibros());
            return "cargar-editorial";
        } catch (Exception e) {
            modelo.put("error", "Error al cargar el autor");
            return "cargar-editorial";
        }
    }
    
     //>>>>>>>>>>MUESTRA LA LISTA DE EDITORIALES CON OPCIONES DE ALTA, BAJA Y MODIFICACION<<<<<<<<<<
    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Editorial> editorialLista = editorialServicio.listarTodos();
        modelo.addAttribute("editoriales", editorialLista);
        modelo.addAttribute("fecha", libroServicio.Fecha());
        modelo.put("cantLibros", libroServicio.cantidadLibros());

        return "lista-editorial";
    }
    
     //>>>>>>>>>>MUESTRA EL FORMULARIO DE MODIFICACION DE AUTOR<<<<<<<<<<
    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        modelo.put("editorial", editorialServicio.getOne(id));
        modelo.addAttribute("fecha", libroServicio.Fecha());
        modelo.put("cantLibros", libroServicio.cantidadLibros());
        return "modificar-editorial";
    }

    //>>>>>>>>>>RECIBE LOS DATOS DE LA MODIFICACION DEL AUTOR Y LUEGO MUESTRA LA LISTA DE AUTORES, SI DA ERROR MUESTRA DE NUEVO EL FORMULARIO<<<<<<<<<<
    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, String nombre) {
        try {
            editorialServicio.modificar(id, nombre);
            modelo.put("exito", "Modificacion exitosa");
            List<Editorial> editorialLista = editorialServicio.listarTodos();
            modelo.addAttribute("editoriales", editorialLista);
            modelo.addAttribute("fecha", libroServicio.Fecha());
            modelo.put("cantLibros", libroServicio.cantidadLibros());
            return "lista-editorial";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "modificar-editorial";
        }
    }

    //>>>>>>>>>>SETEA EL ATRIBUTO ALTA EN TRUE Y LUEGO MUESTRA LA LISTA DE AUTORES<<<<<<<<<<
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {
        try {
            editorialServicio.alta(id);
            return "redirect:/editorial/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    //>>>>>>>>>>SETEA EL ATRIBUTO ALTA EN FALSE Y LUEGO MUESTRA LA LISTA DE AUTORES<<<<<<<<<<
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {
        try {
            editorialServicio.baja(id);
            return "redirect:/editorial/lista";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
     //>>>>>>>>>>BUSCA POR NOMBRE FILTRADO<<<<<<<<<<
    @PostMapping("/buscar")
    public String buscar(ModelMap modelo, @RequestParam String nombre, String activo, String inactivo) {
        try {
            List<Editorial> bEditoriales = editorialServicio.buscarFiltrado(nombre, activo, inactivo);
            modelo.put("bEditoriales", bEditoriales);
            modelo.put("fecha", libroServicio.Fecha());
            modelo.put("cantLibros", libroServicio.cantidadLibros());
            return "detalle-editorial";
        } catch (Exception e) {
            modelo.put("error", "Error en la busqueda");
            return "lista-editorial";
        }
    }

}
