package ni.edu.ucem.webapi.serviceImpl;

import ni.edu.ucem.webapi.dao.ReservacionDAO;
import ni.edu.ucem.webapi.modelo.Reservacion;
import ni.edu.ucem.webapi.service.ReservacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import ni.edu.ucem.webapi.modelo.Cuarto;

/**
 * Created by Susi on 25/02/2017.
 */
@Service
public class ReservacionServiceImpl implements ReservacionService {

    private final ReservacionDAO reservacionDAO;

    @Autowired
    public ReservacionServiceImpl(ReservacionDAO reservacionDAO) {
        this.reservacionDAO = reservacionDAO;
    }

    @Transactional
    @Override
    public Reservacion obtenerPorId(int pId) {
        return reservacionDAO.obtenerPorId(pId);
    }

    @Transactional
    @Override
    public void agregar(Reservacion pReservacion) {
        Date ahora= new Date();
        if (pReservacion.getDesde().before(ahora)){
            throw new IllegalArgumentException("La Fecha de ingreso no puede ser menor a la actual");
        }else if (pReservacion.getHasta().before(pReservacion.getDesde())){
            throw new IllegalArgumentException("La Fecha de salida no puede ser menor a la fecha de entrada");
        }else {
            reservacionDAO.agregar(pReservacion);
        }
    }

    @Transactional
    @Override
    public List<Cuarto> cuartosDisponibles(Integer categoria,String desde, String hasta) {
       return reservacionDAO.cuartosDisponibles(categoria,desde, hasta);
    }
    
    
}
