package ni.edu.ucem.webapi.service;

import ni.edu.ucem.webapi.modelo.Huesped;

import java.util.List;


public interface HuespedService {

    public Huesped obtenerPorId(final int pId);

    public List<Huesped> obtenerTodos(final int pOffset, final int pLimit);
}
