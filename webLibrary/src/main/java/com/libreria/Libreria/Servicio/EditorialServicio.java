
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.Libreria.Servicio;


import com.libreria.Libreria.Entidades.Editorial;
import com.libreria.Libreria.Excepciones.ExcepcionLibreria;

import com.libreria.Libreria.Repository.EditorialRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired(required = false)
    private EditorialRepo er;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    
    
    public void crearEditorial(String nombre) throws ExcepcionLibreria{

            if(nombre.isEmpty()|| nombre == null){
                throw new ExcepcionLibreria("Tenes que ingresar los datos pedidos salamin/a");
            }
            Editorial editorial = new Editorial();
            editorial.setNombre(nombre);
            editorial.setAlta(true);
            er.save(editorial);
    }
     public void modificarEditorial(String nombre, String Id) throws ExcepcionLibreria{
      
        if(nombre.isEmpty()|| nombre == null){
                throw new ExcepcionLibreria("Tenes que ingresar los datos pedidos salamin/a");
            }
        
        Optional<Editorial> respuesta = er.findById(Id);
        
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            er.save(editorial);
        }else{
            throw new ExcepcionLibreria("No existe la editorial ingresado");
        }
    }
   
     
     public void darBajaEditorial(String Id) throws ExcepcionLibreria{
        
        Optional<Editorial> respuesta = er.findById(Id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setAlta(false);
            er.save(editorial);
        }else{
            throw new ExcepcionLibreria("No existe la editorial ingresado");
        }
    }
    
   public void darAltaEditorial(String Id) throws ExcepcionLibreria{
        
        Optional<Editorial> respuesta = er.findById(Id);
        if(respuesta.isPresent()){
             Editorial editorial = respuesta.get();
            editorial.setAlta(true);
            er.save(editorial);
        }else{
            throw new ExcepcionLibreria("No existe la editorial ingresado");
        }
    } 
}
