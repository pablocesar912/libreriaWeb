/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.Libreria.Servicio;

import com.libreria.Libreria.Entidades.Autor;
import com.libreria.Libreria.Excepciones.ExcepcionLibreria;
import com.libreria.Libreria.Repository.AutorRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {
    @Autowired
    private AutorRepo autorcito;
    
    public void crearAutor(String nombre) throws ExcepcionLibreria{

            if(nombre.isEmpty()|| nombre == null){
                throw new ExcepcionLibreria("Tenes que ingresar los datos pedidos salamin/a");
            }
            Autor autor = new Autor();
            autor.setNombre(nombre);
            autor.setAlta(true);
            autorcito.save(autor);
    }
    
    public void modificarAutor(String nombre, String Id) throws ExcepcionLibreria{
      
        if(nombre.isEmpty()|| nombre == null){
                throw new ExcepcionLibreria("Tenes que ingresar los datos pedidos salamin/a");
            }
        
        Optional<Autor> respuesta = autorcito.findById(Id);
        
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorcito.save(autor);
        }else{
            throw new ExcepcionLibreria("No existe el autor ingresado");
        }
    }
    
    public void darBajaAutor(String Id) throws ExcepcionLibreria{
        
        Optional<Autor> respuesta = autorcito.findById(Id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setAlta(false);
            autorcito.save(autor);
        }else{
            throw new ExcepcionLibreria("No existe el autor ingresado");
        }
    }
    
   public void darAltaAutor(String Id) throws ExcepcionLibreria{
        
        Optional<Autor> respuesta = autorcito.findById(Id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setAlta(true);
            autorcito.save(autor);
        }else{
            throw new ExcepcionLibreria("No existe el autor ingresado");
        }
    } 
}
