/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.Libreria.Servicio;


import com.libreria.Libreria.Entidades.Autor;
import com.libreria.Libreria.Entidades.Editorial;
import com.libreria.Libreria.Entidades.Libro;
import com.libreria.Libreria.Excepciones.ExcepcionLibreria;
import com.libreria.Libreria.Repository.AutorRepo;
import com.libreria.Libreria.Repository.EditorialRepo;
import com.libreria.Libreria.Repository.LibroRepo;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepo libR;
    @Autowired
    private AutorRepo autR;
    @Autowired
    private EditorialRepo editR;
    
    public void validacionLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes, String idAutor, String idEditorial) throws ExcepcionLibreria{
        
        if(isbn.toString().length()<13 || isbn.toString().length()>13 || isbn.toString().isEmpty() || isbn == null){
            throw new ExcepcionLibreria("Algo malio sal... ingresa bien el ISBN");
        }
        if(titulo.isEmpty() || titulo == null){
            throw new ExcepcionLibreria("Algo malio sal... ingresa bien el Titulo");
        }
        if(anio.toString().length()<4 || anio.toString().isEmpty() || anio.toString() == null){
            throw new ExcepcionLibreria("Algo malio sal... ingresa bien el Anio");
        }
        if(ejemplares < 1 || ejemplares.toString().isEmpty() || ejemplares.toString() == null){
            throw new ExcepcionLibreria("Algo malio sal... ingresa bien la cantidad de Ejemplares");
        }
        if(ejemplaresPrestados < 1 || ejemplaresPrestados.toString().isEmpty() || ejemplaresPrestados.toString() == null){
            throw new ExcepcionLibreria("Algo malio sal... ingresa bien la cantidad de Ejemplares Prestados");
        }
        if(ejemplaresRestantes < 1 || ejemplaresRestantes.toString().isEmpty() || ejemplaresRestantes.toString() == null){
            throw new ExcepcionLibreria("Algo malio sal... ingresa bien la cantidad de Ejemplares Restantes");
        }
        if(idAutor.isEmpty() || idAutor == null){
            throw new ExcepcionLibreria("Algo malio sal... ingresa bien el Id del Autor");
        }
        if(idEditorial.isEmpty() || idEditorial == null){
            throw new ExcepcionLibreria("Algo malio sal... ingresa bien el Id de la Editorial");
        }
        
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes, String idAutor, String idEditorial) throws ExcepcionLibreria{
        
        Autor autorcito = autR.findById(idAutor).get();
        Libro librito = new Libro();
        Editorial edi = editR.findById(idEditorial).get();
        
        
        validacionLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, idAutor, idEditorial);
        librito.setIsbn(isbn);
        librito.setTitulo(titulo);
        librito.setAnio(anio);
        librito.setEjemplares(ejemplares);
        librito.setEjemplaresPrestados(ejemplaresPrestados);
        librito.setEjemplaresRestantes(ejemplaresRestantes);
        librito.setAlta(true);
        librito.setAutor(autorcito);
        librito.setEditorial(edi);
        
        libR.save(librito);  
    }
    
    public void modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados,
            Integer ejemplaresRestantes, String idAutor, String idEditorial) throws ExcepcionLibreria{
        
        validacionLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, idAutor, idEditorial);
        Optional<Libro> respuesta = libR.findById(id);
        if(respuesta.isPresent()){
            Libro librito = respuesta.get();
            if(librito.getAutor().getId().equals(idAutor)){
                if(librito.getEditorial().getId().equals(idEditorial)){
                    
                    librito.setIsbn(isbn);
                    librito.setTitulo(titulo);
                    librito.setAnio(anio);
                    librito.setEjemplares(ejemplares);
                    librito.setEjemplaresPrestados(ejemplaresPrestados);
                    librito.setEjemplaresRestantes(ejemplaresRestantes);
                    libR.save(librito);
                }else{
                    throw new ExcepcionLibreria("No coincide la Editorial ingresada");
                }   
            }else{
                throw new ExcepcionLibreria("No coincide el Autor ingresado");
                } 
        }else{
            throw new ExcepcionLibreria("No Existe el libro segun el Id ingresado");
       }  

    }
    
    public void bajaLibro(String id,String idAutor, String idEditorial) throws ExcepcionLibreria{
        
        Optional<Libro> respuesta = libR.findById(id);
        if(respuesta.isPresent()){
            Libro librito = respuesta.get();
            if(librito.getAutor().getId().equals(idAutor)){
                if(librito.getEditorial().getId().equals(idEditorial)){
                    
                    librito.setAlta(false);
                   
                    libR.save(librito);
                }else{
                    throw new ExcepcionLibreria("No coincide la Editorial ingresada");
                }   
            }else{
                throw new ExcepcionLibreria("No coincide el Autor ingresado");
            } 
        }else{
            throw new ExcepcionLibreria("No Existe el libro segun el Id ingresado");
        }  
    }
    
    public void altaLibro(String id,String idAutor, String idEditorial) throws ExcepcionLibreria{
        
        Optional<Libro> respuesta = libR.findById(id);
        if(respuesta.isPresent()){
            Libro librito = respuesta.get();
            if(librito.getAutor().getId().equals(idAutor)){
                if(librito.getEditorial().getId().equals(idEditorial)){
                    
                    librito.setAlta(true);
                   
                    libR.save(librito);
                }else{
                    throw new ExcepcionLibreria("No coincide la Editorial ingresada");
                }   
            }else{
                throw new ExcepcionLibreria("No coincide el Autor ingresado");
            } 
        }else{
            throw new ExcepcionLibreria("No Existe el libro segun el Id ingresado");
        }  
    }
}
