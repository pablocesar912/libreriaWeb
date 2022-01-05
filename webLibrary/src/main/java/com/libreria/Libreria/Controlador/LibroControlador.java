/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.Libreria.Controlador;

import com.libreria.Libreria.Entidades.Autor;
import com.libreria.Libreria.Entidades.Editorial;
import com.libreria.Libreria.Entidades.Libro;
import com.libreria.Libreria.Excepciones.ExcepcionLibreria;
import com.libreria.Libreria.Repository.AutorRepo;
import com.libreria.Libreria.Repository.EditorialRepo;
import com.libreria.Libreria.Repository.LibroRepo;
import com.libreria.Libreria.Servicio.EditorialServicio;
import com.libreria.Libreria.Servicio.LibroServicio;
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
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio servL;


    @Autowired
    private AutorRepo ar;

    @Autowired
    private EditorialRepo er;

    @Autowired
    private LibroRepo lR;

    @GetMapping("/registro")
    public String registroLibro(ModelMap modelo) {
        List<Autor> autores = ar.findAll();
        List<Editorial> editoriales = er.findAll();
        modelo.put("editoriales", editoriales);
        modelo.put("autores", autores);
        return "registroLibro.html";
    }

    @PostMapping("/registro")
    public String registroLibro(ModelMap m, @RequestParam Long isbn,
            @RequestParam String titulo, @RequestParam Integer anio,
            @RequestParam Integer ejemplares,
            @RequestParam Integer ejemplaresPrestados,
            @RequestParam Integer ejemplaresRestantes,
            @RequestParam String autor, @RequestParam String editorial) {

        try {

            servL.crearLibro(isbn, titulo, anio, ejemplares,
                    ejemplaresPrestados, ejemplaresRestantes,
                    autor, editorial);

        } catch (ExcepcionLibreria ex) {
            List<Autor> autores = ar.findAll();
            List<Editorial> editoriales = er.findAll();
            m.put("editoriales", editoriales);
            m.put("autores", autores);
            m.put("error", ex.getMessage());
            m.put("isbn", isbn);
            m.put("titulo", titulo);
            m.put("anio", anio);
            m.put("ejemplares", ejemplares);
            m.put("ejemplaresPrestados", ejemplaresPrestados);
            m.put("ejemplaresRestantes", ejemplaresRestantes);
            m.put("autor", autor);
            m.put("editorial", editorial);
            return "registroLibro.html";
        }
        m.put("mensaje", "El libro se registró de manera satisfactoria.");
        return "exito.html";
    }

    @GetMapping("/mostrar")
    public String mostrarLibros(ModelMap mod) {
        List<Libro> libros = lR.findAll();
        mod.put("libros", libros);
        return "libros.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificarLibro(ModelMap model, @PathVariable String id) {
        List<Autor> autores = ar.findAll();
        List<Editorial> editoriales = er.findAll();
        model.put("editoriales", editoriales);
        model.put("autores", autores);
        model.put("libro", lR.getOne(id));
        return "modificarLibro.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificarLibros(ModelMap mod, @PathVariable String id, @RequestParam Long isbn,
            @RequestParam String titulo, @RequestParam Integer anio,
            @RequestParam Integer ejemplares,
            @RequestParam Integer ejemplaresPrestados,
            @RequestParam Integer ejemplaresRestantes,
            @RequestParam String autor, @RequestParam String editorial) {
        try {
            servL.modificarLibro(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresRestantes, autor, editorial);

        } catch (ExcepcionLibreria ex) {
            mod.put("error", ex.getMessage());
            mod.put("isbn", isbn);
            mod.put("titulo", titulo);
            mod.put("anio", anio);
            mod.put("ejemplares", ejemplares);
            mod.put("ejemplaresPrestados", ejemplaresPrestados);
            mod.put("ejemplaresRestantes", ejemplaresRestantes);
            mod.put("autor", autor);
            mod.put("editorial", editorial);
            return "modificarLibro.html";
        }
        mod.put("mensaje", "El libro se modificó de manera satisfactoria.");
        return "exito.html";
    }

  
     @GetMapping("/baja/{id}/{autor}/{editorial}")
	public String baja(ModelMap mod,@PathVariable String id,
                @PathVariable String autor,@PathVariable String editorial) {
				
		try {
			servL.bajaLibro(id,autor,editorial);
			return "redirect:/libro/mostrar";
		} catch (ExcepcionLibreria e) {
                        
			return "redirect:/libro/mostrar";
		}
		
	}
	
   @GetMapping("/alta/{id}/{autor}/{editorial}")
	public String alta(ModelMap mod,@PathVariable String id,
                @PathVariable String autor,@PathVariable String editorial) {
				
		try {
			servL.altaLibro(id,autor,editorial);
			return "redirect:/libro/mostrar";
		} catch (ExcepcionLibreria e) {
                        
			return "redirect:/libro/mostrar";
		}
		
	}

}
