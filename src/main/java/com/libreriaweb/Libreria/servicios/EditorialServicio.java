package com.libreriaweb.Libreria.servicios;

import com.libreriaweb.Libreria.entidades.Editorial;
import com.libreriaweb.Libreria.repositorios.EditorialRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public Editorial buscarPorNombre(String nombre) {
        return editorialRepositorio.buscarPorNombre(nombre);
    }
    
    //>>>>>>>>>>BUSCAR CON FILTROS<<<<<<<<<<
    public List<Editorial> buscarFiltrado(String nombre, String activo, String inactivo) {
        if ("activo".equals(activo) && "inactivo".equals(inactivo)) {
            return editorialRepositorio.buscar(nombre);
        } else {
            if ("activo".equals(activo)) {
                return editorialRepositorio.activos(nombre);
            } else {
                if ("inactivo".equals(inactivo)) {
                    return editorialRepositorio.inactivos(nombre);
                } else {
                    return editorialRepositorio.buscar(nombre);
                }
            }
        }
    }

    public Editorial crearEditorial(String nombre) {
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(true);

        return editorialRepositorio.save(editorial);
    }
    
    //>>>>>>>>>>LISTAR TODOS<<<<<<<<<<
    @Transactional(readOnly = true)
    public List<Editorial> listarTodos() {
        return editorialRepositorio.listar();
    }
    
     //>>>>>>>>>>ALTA DE EDITORIAL<<<<<<<<<<
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void alta(String id) {
        Editorial editorial = editorialRepositorio.getOne(id);
        editorial.setAlta(true);
        editorialRepositorio.save(editorial);
    }

    //>>>>>>>>>>BAJA DE EDITORIAL<<<<<<<<<<
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void baja(String id) {
        Editorial editorial = editorialRepositorio.getOne(id);
        editorial.setAlta(false);
        editorialRepositorio.save(editorial);
    }
    
    //>>>>>>>>>>OBTENER AUTOR POR ID<<<<<<<<<<
    @Transactional(readOnly = true)
    public Editorial getOne(String id) {
        return editorialRepositorio.getById(id);
    }
    
        //>>>>>>>>>>MODIFICAR<<<<<<<<<<
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void modificar(String id, String nombre) throws Exception {
        validar(nombre);
        Editorial editorial = editorialRepositorio.getOne(id);
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }
    
    //>>>>>>>>>>Validaciones<<<<<<<<<<
    public void validar(String nombre) throws Exception {
        if (nombre == null || nombre.isEmpty() || nombre.contains("  ")) {
            throw new Exception();
        }
    }
}
