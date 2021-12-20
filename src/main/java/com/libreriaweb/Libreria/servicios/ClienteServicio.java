
package com.libreriaweb.Libreria.servicios;

import com.libreriaweb.Libreria.entidades.Cliente;
import com.libreriaweb.Libreria.enums.Rol;
import com.libreriaweb.Libreria.excepciones.ExcepcionesServicio;
import com.libreriaweb.Libreria.repositorios.ClienteRepositorio;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteServicio implements UserDetailsService {
 
    
     @Autowired
    private ClienteRepositorio clienteRepositorio;

     
    @Transactional
    public void registrar(String nombre, String apellido, String documento, String telefono, String clave) throws Exception {
        validar(documento, nombre, apellido, telefono, clave);
        Cliente respuesta = clienteRepositorio.buscarPorDocumento(documento);
        if (respuesta != null) {
            throw new ExcepcionesServicio("Ya esxiste un usuario con ese documento");
        } else {
            Cliente cliente = new Cliente();

            cliente.setDocumento(documento);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);

            String encriptada = new BCryptPasswordEncoder().encode(clave);
            cliente.setClave(encriptada);

            cliente.setAlta(true);
            cliente.setRol(Rol.USER);

            clienteRepositorio.save(cliente);
        }
    }

     
     @Transactional
    public void modificar( String id, String nombre, String apellido, String documento, String clave, String telefono) throws ExcepcionesServicio {

        
        validar(documento , nombre, apellido, telefono, clave);

        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Cliente cliente = respuesta.get();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setDocumento(documento);
            cliente.setTelefono(telefono);
            
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            cliente.setClave(encriptada);
            
            clienteRepositorio.save(cliente);

           
        } else {

            throw new ExcepcionesServicio("No se encontró el cliente solicitado");
        }

    }
     
     
     
     
     
     public void validar ( String documento, String nombre, String apellido, String telefono, String clave) throws ExcepcionesServicio{
         
     if (documento == null || documento.isEmpty()) {
            throw new ExcepcionesServicio("El documento del usuario no puede ser nulo");
        }
         
         
         if (nombre == null || nombre.isEmpty()) {
            throw new ExcepcionesServicio("El nombre del usuario no puede ser nulo");
        }
         
         if (apellido == null || apellido.isEmpty()) {
            throw new ExcepcionesServicio("El apellido del usuario no puede ser nulo");
        }
         
         
         if (telefono == null || telefono.isEmpty()) {
            throw new ExcepcionesServicio("El documento del usuario no puede ser nulo");
        }  
         
         
         
          if (clave == null || clave.isEmpty() || clave.length() <= 6) {
            throw new ExcepcionesServicio("La clave del usuario no puede ser nula y tiene que tener mas de seis digitos");
        }
         
          
          
               
     }
     
     @Override
    public UserDetails loadUserByUsername(String documento) throws UsernameNotFoundException {
        Cliente cliente = clienteRepositorio.buscarPorDocumento(documento);
        if (cliente != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
                        
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_"+ cliente.getRol());
            permisos.add(p1);
         
            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("clientesession", cliente);

            User user = new User(cliente.getDocumento(), cliente.getClave(), permisos);
            return user;

        } else {
            return null;
        }

    }
     
  @Transactional(readOnly=true)
    public Cliente buscarPorId(String id) throws ExcepcionesServicio {

        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Cliente cliente = respuesta.get();
            return cliente;
        } else {

            throw new ExcepcionesServicio("No se encontró el cliente solicitado");
        }

    }
     
     
    
     
    
    
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void altaCliente(String id) {
        Cliente cliente = clienteRepositorio.getOne(id);
        cliente.setAlta(true);
        clienteRepositorio.save(cliente);
    }
    
    
    
    
    
     @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void bajaCliente (String id) {
        Cliente cliente = clienteRepositorio.getOne(id);
        cliente.setAlta(false);
        clienteRepositorio.save(cliente);
    }
    
       
     public Cliente buscarUnoPorDocumento(String documento) {
         return clienteRepositorio.buscarPorDocumento(documento);
       
    }
    
    }

     
     
     
     
     
     
     
    
     
    
    

