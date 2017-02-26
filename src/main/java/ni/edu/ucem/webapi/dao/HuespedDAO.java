package ni.edu.ucem.webapi.dao;

import ni.edu.ucem.webapi.modelo.Huesped;

import java.util.List;


public interface HuespedDAO {

    public Huesped obtenerPorId(final int pId);

    public List<Huesped> obtenerTodos(final int pOffset, final int pLimit);    

}
