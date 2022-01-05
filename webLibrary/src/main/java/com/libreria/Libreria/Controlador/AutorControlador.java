
package com.libreria.Libreria.Controlador;

import com.libreria.Libreria.Entidades.Autor;
import com.libreria.Libreria.Excepciones.ExcepcionLibreria;
import com.libreria.Libreria.Repository.AutorRepo;
import com.libreria.Libreria.Servicio.AutorServicio;
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
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio servA;
    
    @Autowired
    private AutorRepo auR;

    @GetMapping("/registro")
    public String registroLibro() {
        return "InsertarAutor.html";
    }

    @PostMapping("/registro")
    public String registroAutor(ModelMap modelo, @RequestParam String nombre) {

        try {
            servA.crearAutor(nombre);
        } catch (ExcepcionLibreria ex) {
            modelo.put("error",ex.getMessage());
            
            return "InsertarAutor.html";
        }

        modelo.put("mensaje","El autor se registró de manera satisfactoria.");
        return "exito.html";
    }
   @GetMapping("/mostrar")
   public String mostrarAutor(ModelMap mod){
       List<Autor> autores = auR.findAll();
       mod.put("autores",autores);
       return "autores.html";
   }
   
   @GetMapping("/modificar/{id}")
   public String modificarAutor(ModelMap model, @PathVariable String id){
       model.put("autor", auR.getOne(id));
       return "modificarAutor.html";   
   }
   
   @PostMapping("/modificar/{id}")
   public String modificarAutor(ModelMap mod,@PathVariable String id, @RequestParam String nombre){
       try {
           servA.modificarAutor(nombre, id);
       } catch (ExcepcionLibreria e) {
           mod.put("error", e.getMessage());
       }
       mod.put("mensaje", "Se modificó el autor exitosamente, te felicito, sos el/la masme, no hay nadie mejor");
       return "exito.html";
   }
   
   @GetMapping("/baja/{id}")
	public String baja(ModelMap mod,@PathVariable String id) {
				
		try {
			servA.darBajaAutor(id);
			return "redirect:/autor/mostrar";
		} catch (ExcepcionLibreria e) {
                        
			return "redirect:/autor/mostrar";
		}
		
	}
	
   @GetMapping("/alta/{id}")
	public String alta(ModelMap mod,@PathVariable String id) {
				
		try {
			servA.darAltaAutor(id);
			return "redirect:/autor/mostrar";
		} catch (ExcepcionLibreria e) {
                        
			return "redirect:/autor/mostrar";
		}
		
	}
}
