package com.libreriaweb.Libreria.servicios;

import com.libreriaweb.Libreria.entidades.Autor;
import com.libreriaweb.Libreria.repositorios.AutorRepositorio;
import java.text.Normalizer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    //>>>>>>>>>>CREAR AUTOR<<<<<<<<<<
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void crearAutor(String nombre) throws Exception {
        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(true);
        autorRepositorio.save(autor);
    }

    //>>>>>>>>>>MODIFICAR<<<<<<<<<<
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void modificar(String id, String nombre) throws Exception {
        validar(nombre);
        Autor autor = autorRepositorio.getOne(id);
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }

    //>>>>>>>>>>OBTENER AUTOR POR ID<<<<<<<<<<
    @Transactional(readOnly = true)
    public Autor getOne(String id) {
        return autorRepositorio.getById(id);
    }

    //>>>>>>>>>>LISTAR TODOS<<<<<<<<<<
    @Transactional(readOnly = true)
    public List<Autor> listarTodos() {
        return autorRepositorio.listar();
    }

    //>>>>>>>>>>BUSCAR POR NOMBRE<<<<<<<<<<
    public List<Autor> buscarPorNombre(String nombre) {
        return autorRepositorio.buscar(nombre);
    }

    //>>>>>>>>>>BUSCAR CON FILTROS<<<<<<<<<<
    public List<Autor> buscarFiltrado(String nombre, String activo, String inactivo) {
        if ("activo".equals(activo) && "inactivo".equals(inactivo)) {
            return autorRepositorio.buscar(nombre);
        } else {
            if ("activo".equals(activo)) {
                return autorRepositorio.activos(nombre);
            } else {
                if ("inactivo".equals(inactivo)) {
                    return autorRepositorio.inactivos(nombre);
                } else {
                    return autorRepositorio.buscar(nombre);
                }
            }
        }
    }

    //>>>>>>>>>>BUSCAR POR NOMBRE<<<<<<<<<<
    public Autor buscarUnoPorNombre(String nombre) {
        return autorRepositorio.buscarPorNombre(nombre);
    }

    //>>>>>>>>>>ALTA DE AUTOR<<<<<<<<<<
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void alta(String id) {
        Autor autor = autorRepositorio.getOne(id);
        autor.setAlta(true);
        autorRepositorio.save(autor);
    }

    //>>>>>>>>>>BAJA DE AUTOR<<<<<<<<<<
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void baja(String id) {
        Autor autor = autorRepositorio.getOne(id);
        autor.setAlta(false);
        autorRepositorio.save(autor);
    }

    //>>>>>>>>>>Validaciones<<<<<<<<<<
    public void validar(String nombre) throws Exception {
        if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
            throw new Exception();
            
          
                
            }
        }
    }
    
    
    

