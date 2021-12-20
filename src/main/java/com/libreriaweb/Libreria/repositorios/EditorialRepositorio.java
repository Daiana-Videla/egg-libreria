package com.libreriaweb.Libreria.repositorios;

import com.libreriaweb.Libreria.entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {

    @Query(value = "SELECT e FROM Editorial e WHERE e.nombre = :nombre")
    public Editorial buscarPorNombre(@Param("nombre") String nombre);
    //>>>>>>>>>>BUSCAR POR NOMBRE FILTRO<<<<<<<<<<
    @Query(value = "SELECT e FROM Editorial e WHERE e.nombre LIKE %:filtro% ORDER BY e.nombre")
    List<Editorial> buscar(@Param("filtro") String filtro);
    
    @Query(value = "SELECT e FROM Editorial e ORDER BY e.nombre")
    List<Editorial> listar();
    
     //>>>>>>>>>>BUSCAR POR NOMBRE Y ALTA TRUE FILTRO<<<<<<<<<<
    @Query(value = "SELECT e FROM Editorial e WHERE e.nombre LIKE %:filtro% AND e.alta = true ORDER BY e.nombre")
    List<Editorial> activos(@Param("filtro") String filtro);

    //>>>>>>>>>>BUSCAR POR NOMBRE Y ALTA FALSE FILTRO<<<<<<<<<<
    @Query(value = "SELECT e FROM Editorial e WHERE e.nombre LIKE %:filtro% AND e.alta = false ORDER BY e.nombre")
    List<Editorial> inactivos(@Param("filtro") String filtro);

}
