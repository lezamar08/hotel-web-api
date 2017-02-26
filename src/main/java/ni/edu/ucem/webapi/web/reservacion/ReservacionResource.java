package ni.edu.ucem.webapi.web.reservacion;

import java.util.List;
import javax.validation.Valid;
import ni.edu.ucem.webapi.core.ApiResponse;
import ni.edu.ucem.webapi.modelo.Cuarto;
import ni.edu.ucem.webapi.modelo.Reservacion;
import ni.edu.ucem.webapi.service.ReservacionService;
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

/**
 * Created by Susi on 25/02/2017.
 */
@RestController
@RequestMapping("/v1/reservaciones")
public class ReservacionResource {

    private final ReservacionService reservacionService;

    @Autowired
    public ReservacionResource(ReservacionService reservacionService) {
        this.reservacionService = reservacionService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces="application/json")
    public ApiResponse obtener(@PathVariable("id") final int id)
    {
        final Reservacion cuarto = this.reservacionService.obtenerPorId(id);
        return new ApiResponse(ApiResponse.Status.OK, cuarto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST,
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse guardarReservacion(@Valid @RequestBody final Reservacion reservacion, BindingResult result)
    {
        if(result.hasErrors())
        {
            throw new IllegalArgumentException(result.getFieldError().getDefaultMessage());
        }

        this.reservacionService.agregar(reservacion);
        return new ApiResponse(ApiResponse.Status.OK, reservacion);
    }

        @RequestMapping(method = RequestMethod.GET, produces="application/json")
    public List<Cuarto> obtenerCuartos(
            @RequestParam(value = "categoria", required = false) final Integer categoria,
            @RequestParam(value = "desde", required = false, defaultValue ="0") final String desde,
            @RequestParam(value = "hasta", required = false, defaultValue="0") final String hasta)
    {

        List<Cuarto> cuarto;
        cuarto= this.reservacionService.cuartosDisponibles(categoria, desde, hasta);
        return cuarto;
    }
}
