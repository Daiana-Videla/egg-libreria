package com.libreriaweb.Libreria.repositorios;

import com.libreriaweb.Libreria.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {

    //>>>>>>>>>>BUSCAR POR NOMBRE FILTRO<<<<<<<<<<
    @Query(value = "SELECT a FROM Autor a WHERE a.nombre LIKE %:filtro% ORDER BY a.nombre")
    List<Autor> buscar(@Param("filtro") String filtro);

    @Query(value = "SELECT a FROM Autor a ORDER BY a.nombre")
    List<Autor> listar();

    @Query(value = "SELECT a FROM Autor a WHERE a.nombre = :nombre")
    public Autor buscarPorNombre(@Param("nombre") String nombre);

    //>>>>>>>>>>BUSCAR POR NOMBRE Y ALTA TRUE FILTRO<<<<<<<<<<
    @Query(value = "SELECT a FROM Autor a WHERE a.nombre LIKE %:filtro% AND a.alta = true ORDER BY a.nombre")
    List<Autor> activos(@Param("filtro") String filtro);

    //>>>>>>>>>>BUSCAR POR NOMBRE Y ALTA FALSE FILTRO<<<<<<<<<<
    @Query(value = "SELECT a FROM Autor a WHERE a.nombre LIKE %:filtro% AND a.alta = false ORDER BY a.nombre")
    List<Autor> inactivos(@Param("filtro") String filtro);

}
