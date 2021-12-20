/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.Libreria.repositorios;

import com.libreriaweb.Libreria.entidades.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository <Cliente, String> {
     @Query(value = "SELECT c FROM Cliente c WHERE c.documento LIKE %:filtro% ORDER BY c.documento")
    List<Cliente> buscar(@Param("filtro") String filtro);

    @Query(value = "SELECT c FROM Cliente c ORDER BY c.documento")
    List<Cliente> listar();
    
 @Query(value = "SELECT c FROM Cliente c WHERE c.documento = :documento")
    public Cliente buscarPorDocumento(@Param("documento") String documento);


    
   
}


