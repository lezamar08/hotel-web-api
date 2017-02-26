package ni.edu.ucem.webapi.web.inventario;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ni.edu.ucem.webapi.core.ApiResponse;
import ni.edu.ucem.webapi.core.ApiResponse.Status;
import ni.edu.ucem.webapi.core.ListApiResponse;
import ni.edu.ucem.webapi.modelo.Cuarto;
import ni.edu.ucem.webapi.modelo.Pagina;
import ni.edu.ucem.webapi.modelo.Filtro;
import ni.edu.ucem.webapi.serviceImpl.InventarioServiceImpl;

@RestController
@RequestMapping("/v1/inventario/cuartos")
public class CuartoResource 
{
    private final InventarioServiceImpl inventarioService;
    
    @Autowired
    public CuartoResource(final InventarioServiceImpl inventarioService)
    {
        this.inventarioService = inventarioService;
    }

    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public ListApiResponse<Cuarto> obtenerCuartos(
            @RequestParam(value = "categoria", required = false) final Integer categoria,
            @RequestParam(value = "offset", required = false, defaultValue ="0") final Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue="0") final Integer limit)
    {
        final Filtro paginacion = new Filtro.Builder()
                .paginacion(offset, limit)
                .build();

        Pagina<Cuarto> pagina;
        if(categoria != null)
        {
            pagina = this.inventarioService.obtenerTodosCuartoEnCategoria(categoria, paginacion);
        }
        else
        {
            pagina = this.inventarioService.obtenerTodosCuarto(paginacion);
        }
        return new ListApiResponse<Cuarto>(Status.OK, pagina);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces="application/json")
    public ApiResponse obtener(@PathVariable("id") final int id)
    {
        final Cuarto cuarto = this.inventarioService.obtenerCuarto(id);
        return new ApiResponse(Status.OK, cuarto);
    }
    
    /**
     * Para que la validación funcione solo es necesario agregar la anotación @valid al método.
     * Spring se asegura que la petición entrante sea validado con las reglas definidas en el POJO.
     *  
     * @see <a href="http://docs.spring.io/spring-security/site/docs/current/reference/html/el-access.html#method-security-expressions">
     * method-security-expressions</a>
     *   
     * @param cuarto
     * @param result
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST,
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse guardarCuarto(@Valid @RequestBody final Cuarto cuarto, BindingResult result) 
    {
        if(result.hasErrors())
        {
            throw new IllegalArgumentException(result.getFieldError().getDefaultMessage());
        }
        
        this.inventarioService.agregarCuarto(cuarto);
        return new ApiResponse(Status.OK, cuarto);
    }
    
    /**
     * Negociación de contenido. Aceptamos form-parameters para la creación de un nuevo recurso.
     * @param numero
     * @param descripcion
     * @param categoria
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse guardarCuartoConFormData(final Short numero, final String descripcion, final Integer categoria) 
    {
        Cuarto cuarto = new Cuarto(numero, descripcion, categoria);
        this.inventarioService.agregarCuarto(cuarto);
        return new ApiResponse(Status.OK, cuarto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            produces="application/json")
    public ApiResponse guardarCuarto(@PathVariable("id") final int id, 
            @RequestBody final Cuarto cuartoActualizado) 
    {
        final Cuarto cuarto = new Cuarto(id,
                cuartoActualizado.getNumero(), 
                cuartoActualizado.getDescripcion(),
                cuartoActualizado.getCategoria());
        this.inventarioService.guardarCuarto(cuarto);
        return new ApiResponse(Status.OK, cuarto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces="application/json")
    public ApiResponse eliminarCuarto(@PathVariable("id") final int id) 
    {
        final Cuarto cuarto = this.inventarioService.obtenerCuarto(id);
        this.inventarioService.eliminarCuarto(cuarto.getId());
        return new ApiResponse(Status.OK,null);
    }
}
