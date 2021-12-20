/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreriaweb.Libreria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Autowired
//    private UsuarioServicio usuarioServicio;
    
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LibreriaApplication.class);
	}
}
