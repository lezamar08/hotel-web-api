package ni.edu.ucem.webapi.service;

import java.util.List;
import ni.edu.ucem.webapi.modelo.Cuarto;
import ni.edu.ucem.webapi.modelo.Reservacion;


public interface ReservacionService {

    public Reservacion obtenerPorId(final int pId);

    public void agregar(final Reservacion pReservacion);
    
    public List<Cuarto> cuartosDisponibles(Integer categoria,String desde, String hasta);
}
