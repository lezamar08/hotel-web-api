/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.edu.ucem.webapi.web.inventario;

import javax.validation.Valid;
import ni.edu.ucem.webapi.core.ApiResponse;
import ni.edu.ucem.webapi.core.ListApiResponse;
import ni.edu.ucem.webapi.modelo.CategoriaCuarto;
import ni.edu.ucem.webapi.modelo.Filtro;
import ni.edu.ucem.webapi.modelo.Pagina;
import ni.edu.ucem.webapi.serviceImpl.InventarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/inventario/categorias")
public class CategoriaCuartoResource {
    
     private final InventarioServiceImpl inventarioService;

    @Autowired
    public CategoriaCuartoResource(InventarioServiceImpl inventarioService) {
        this.inventarioService = inventarioService;
    }
     
    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public ListApiResponse<CategoriaCuarto> obtenerCategorias(
            @RequestParam(value = "offset", required = false, defaultValue ="0") final Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue="0") final Integer limit)
    {
        final Filtro paginacion = new Filtro.Builder()
                .paginacion(offset, limit)
                .build();

        Pagina<CategoriaCuarto> pagina;
              pagina = this.inventarioService.obtenerTodosCategoriaCuartos(paginacion);
      return new ListApiResponse<CategoriaCuarto>(ApiResponse.Status.OK, pagina);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST,
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse guardarCategoria(@Valid @RequestBody final CategoriaCuarto categoria, BindingResult result) 
    {
        if(result.hasErrors())
        {
            throw new IllegalArgumentException(result.getFieldError().getDefaultMessage());
        }
        
        this.inventarioService.agregarCategoriaCuarto(categoria);
        return new ApiResponse(ApiResponse.Status.OK, categoria);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            produces="application/json")
    public ApiResponse guardarCategoria(@PathVariable("id") final int id, 
            @RequestBody final CategoriaCuarto categoriaActualizado) 
    {
        final CategoriaCuarto categoria = new CategoriaCuarto(id,
                categoriaActualizado.getNombre(), 
                categoriaActualizado.getDescripcion(),
                categoriaActualizado.getPrecio());
        this.inventarioService.guardarCategoriaCuarto(categoria);
        return new ApiResponse(ApiResponse.Status.OK, categoria);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces="application/json")
    public ApiResponse eliminarCategoria(@PathVariable("id") final int id) 
    {
        final CategoriaCuarto categoria = this.inventarioService.obtenerCategoriaCuarto(id);
        this.inventarioService.eliminarCuarto(categoria.getId());
        return new ApiResponse(ApiResponse.Status.OK,null);
    }
}
