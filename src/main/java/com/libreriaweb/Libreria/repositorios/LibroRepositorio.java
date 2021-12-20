package com.libreriaweb.Libreria.repositorios;

import com.libreriaweb.Libreria.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String> {
    //>>>>>>>>>>BUSCAR POR NOMBRE FILTRO<<<<<<<<<<

    @Query(value = "SELECT l FROM Libro l WHERE l.titulo LIKE %:filtro% ORDER BY l.titulo")
    List<Libro> buscar(@Param("filtro") String filtro);

    @Query(value = "SELECT l FROM Libro l ORDER BY l.titulo")
    List<Libro> listar();

    //>>>>>>>>>>BUSCAR POR NOMBRE Y ALTA TRUE FILTRO<<<<<<<<<<
    @Query(value = "SELECT l FROM Libro l WHERE l.titulo LIKE %:filtro% AND l.alta = true ORDER BY l.titulo")
    List<Libro> activos(@Param("filtro") String filtro);

    //>>>>>>>>>>BUSCAR POR NOMBRE Y ALTA FALSE FILTRO<<<<<<<<<<
    @Query(value = "SELECT l FROM Libro l WHERE l.titulo LIKE %:filtro% AND l.alta = false ORDER BY l.titulo")
    List<Libro> inactivos(@Param("filtro") String filtro);


   @Query(value = "SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro buscarUnoPorTitulo(@Param("titulo") String titulo);
    
   
}

