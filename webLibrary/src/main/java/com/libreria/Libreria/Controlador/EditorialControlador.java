/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.Libreria.Controlador;

import com.libreria.Libreria.Entidades.Editorial;
import com.libreria.Libreria.Excepciones.ExcepcionLibreria;
import com.libreria.Libreria.Repository.EditorialRepo;
import com.libreria.Libreria.Servicio.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping ("/editorial")
public class EditorialControlador {

    @Autowired(required = false)
    private EditorialServicio servE;

    @Autowired
    private EditorialRepo er;
    
    @GetMapping("/registro")
    public String registroLibro() {
        return "insertarEditorial.html";
    }
    
    @PostMapping("/registro")
    public String registrarEditorial(ModelMap modelo, @RequestParam String nombre){
        
        try {
            servE.crearEditorial(nombre);
            
        } catch (ExcepcionLibreria ex) {
            modelo.put("error", ex.getMessage());

            return "insertarEditorial.html";
        }
        modelo.put("mensaje","La editorial se registró de manera satisfactoria.");
        return "exito.html";
       
    }

    
@GetMapping("/mostrar")
   public String mostrarEditorial(ModelMap mod){
       List<Editorial> editoriales = er.findAll();
       mod.put("editoriales",editoriales);
       return "editoriales.html";
   }
   
   @GetMapping("/modificar/{id}")
   public String modificarEditorial(ModelMap model,@PathVariable String id){
       model.put("editorial", er.getOne(id));
       return "modificarEditorial.html";   
   }
   
   @PostMapping("/modificar/{id}")
   public String modificarEditorial(ModelMap mod,@PathVariable String id, @RequestParam String nombre){
       try {
           servE.modificarEditorial(nombre, id);
       } catch (ExcepcionLibreria e) {
           mod.put("error", e.getMessage());
       }
       mod.put("mensaje", "Se modificó la editorial exitosamente, te felicito, sos el/la masme, no hay nadie mejor");
       return "exito.html";
   }
   
   @GetMapping("/baja/{id}")
	public String baja(ModelMap mod,@PathVariable String id) {
				
		try {
			servE.darBajaEditorial(id);
			return "redirect:/editorial/mostrar";
		} catch (ExcepcionLibreria e) {
                        
			return "redirect:/editorial/mostrar";
		}
		
	}
	
   @GetMapping("/alta/{id}")
	public String alta(ModelMap mod,@PathVariable String id) {
				
		try {
			servE.darAltaEditorial(id);
			return "redirect:/editorial/mostrar";
		} catch (ExcepcionLibreria e) {
                        
			return "redirect:/editorial/mostrar";
		}
		
	}
    
    
    
}
