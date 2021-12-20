package com.libreriaweb.Libreria.servicios;

import com.libreriaweb.Libreria.entidades.Autor;
import com.libreriaweb.Libreria.entidades.Editorial;
import com.libreriaweb.Libreria.entidades.Libro;
import com.libreriaweb.Libreria.excepciones.ExcepcionesServicio;
import com.libreriaweb.Libreria.repositorios.AutorRepositorio;
import com.libreriaweb.Libreria.repositorios.EditorialRepositorio;
import com.libreriaweb.Libreria.repositorios.LibroRepositorio;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service

public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorrepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    public void ingresarLibro(Integer isbn, String titulo, Integer anio, Integer ejemplares,
            boolean alta, String autor, String editorial) throws ExcepcionesServicio, Exception {
        validar(isbn, titulo, anio, ejemplares, alta, autor, editorial);
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setAlta(true);
        validarAutor(autor, libro);
        validarEditorial(editorial, libro);
        libroRepositorio.save(libro);
    }

    public void modificarLibro(String id, Integer isbn, String titulo, Integer anio, Integer ejemplares,
            String autor, String editorial) throws ExcepcionesServicio {
        validar(isbn, titulo, anio, ejemplares,
                true, autor, editorial);
        Libro libro = libroRepositorio.getOne(id);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEditorial(editorialServicio.crearEditorial(editorial));
        libroRepositorio.save(libro);
    }

    public Libro getOne(String id) {
        return libroRepositorio.getOne(id);
    }

    public List<Libro> listarTodos() {
        return libroRepositorio.findAll();
    }

    public void deshabilitarLibro(String idlibro) throws ExcepcionesServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(idlibro);
        if (respuesta.isPresent()) {
            Libro libro = libroRepositorio.findById(idlibro).get();
            libro.setAlta(false);
            libroRepositorio.save(libro);
        } else {
            throw new ExcepcionesServicio("No se encontro el libro buscado");
        }
    }

    private void validar(Integer isbn, String titulo, Integer anio, Integer ejemplares,
            boolean alta, String autor, String editorial) throws ExcepcionesServicio {

        if (isbn == null) {
            throw new ExcepcionesServicio("El isbn no puede estar vacio");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new ExcepcionesServicio("Debe ingresar un titulo");
        }

        if (anio == null) {
            throw new ExcepcionesServicio("El año no puede estar vacio");
        }
        if (ejemplares == null) {
            throw new ExcepcionesServicio("Los ejemplares no puede estar vacio");
        }

        if (alta == false) {
            throw new ExcepcionesServicio("El libro debe estar dado de alta");
        }

        if (autor == null) {
            throw new ExcepcionesServicio("El libro debe tener un autor");
        }
        if (editorial == null) {
            throw new ExcepcionesServicio("El libro debe tener una editorial");
        }

    }

    //>>>>>>>>>>BUSCAR CON FILTROS<<<<<<<<<<
    public List<Libro> buscarFiltrado(String titulo, String activo, String inactivo) {
        if ("activo".equals(activo) && "inactivo".equals(inactivo)) {
            return libroRepositorio.buscar(titulo);
        } else {
            if ("activo".equals(activo)) {
                return libroRepositorio.activos(titulo);
            } else {
                if ("inactivo".equals(inactivo)) {
                    return libroRepositorio.inactivos(titulo);
                } else {
                    return libroRepositorio.buscar(titulo);
                }
            }
        }
    }

    public void validarAutor(String autor, Libro libro) throws Exception {
        Autor a = new Autor();
        a = autorServicio.buscarUnoPorNombre(autor);
        if (a == null) {
            autorServicio.crearAutor(autor);
            libro.setAutor(autorServicio.buscarUnoPorNombre(autor));
        } else {
            libro.setAutor(a);
        }
    }

    //Metodos para fecha y cantidad de libros
    public long cantidadLibros() {
        return libroRepositorio.count();
    }

    public LocalDate Fecha() {
        LocalDate date = LocalDate.now();
        return date;
    }

    //>>>>>>>>>>BUSCAR POR TITULO<<<<<<<<<<
    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepositorio.buscar(titulo);
    }

    //>>>>>>>>>>LISTAR TODOS<<<<<<<<<<
    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {
        return libroRepositorio.listar();
    }
    
      //>>>>>>>>>>ALTA DE LIBRO<<<<<<<<<<
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void alta(String id) {
        Libro libro = libroRepositorio.getOne(id);
        libro.setAlta(true);
        libroRepositorio.save(libro);
    }

    //>>>>>>>>>>BAJA DE LIBRO<<<<<<<<<<
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void baja(String id) {
        Libro libro = libroRepositorio.getOne(id);
        libro.setAlta(false);
        libroRepositorio.save(libro);
    }

    public void validarEditorial(String editorial, Libro libro) {
        Editorial e = new Editorial();
        e = editorialServicio.buscarPorNombre(editorial);
        if (e == null) {
            libro.setEditorial(editorialServicio.crearEditorial(editorial));
        } else {
            libro.setEditorial(e);
        }
    }
    
         public Libro buscarUnoPorTitulo(String titulo) {
        return libroRepositorio.buscarUnoPorTitulo(titulo);
               
    }
    
}
