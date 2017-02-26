package ni.edu.ucem.webapi.serviceImpl;

import ni.edu.ucem.webapi.dao.HuespedDAO;
import ni.edu.ucem.webapi.modelo.Huesped;
import ni.edu.ucem.webapi.service.HuespedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Susi on 25/02/2017.
 */
@Service
public class HuespedServiceImpl implements HuespedService {

    private final HuespedDAO huespedDAO;

    public HuespedServiceImpl(final HuespedDAO huespedDAO) {
        this.huespedDAO=huespedDAO;
    }

    @Transactional
    @Override
    public Huesped obtenerPorId(int pId) {
        return huespedDAO.obtenerPorId(pId);
    }

    @Transactional
    @Override
    public List<Huesped> obtenerTodos(int pOffset, int pLimit) {
        return huespedDAO.obtenerTodos(pOffset,pLimit);
    }
}
