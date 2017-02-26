package ni.edu.ucem.webapi.web.reservacion;

import ni.edu.ucem.webapi.service.ReservacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/disponibilidad/cupos")
public class DisponibilidadCuposResource {
    
    private final ReservacionService reservacionService;

    @Autowired
    public DisponibilidadCuposResource(final ReservacionService reservacionService) {
        this.reservacionService = reservacionService;
    }
    
}
