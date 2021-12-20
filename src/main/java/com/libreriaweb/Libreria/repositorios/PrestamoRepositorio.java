
package com.libreriaweb.Libreria.repositorios;

import com.libreriaweb.Libreria.entidades.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepositorio extends JpaRepository <Prestamo, String>{
    
     @Query(value = "SELECT p FROM Prestamo p ORDER BY p.cliente.documento")
    List<Prestamo> listar(String documento);
    
    
}
