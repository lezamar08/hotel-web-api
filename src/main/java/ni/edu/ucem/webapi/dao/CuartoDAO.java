package ni.edu.ucem.webapi.dao;

import java.util.List;

import ni.edu.ucem.webapi.modelo.Cuarto;

public interface CuartoDAO 
{
    public Cuarto obtenerPorId(final int pId);

    public int contar();
    
    public int contarPorCategoria(final int pCategoriaId);
    
    public List<Cuarto> obtenerTodos(final int offset, final int limit);

    public List<Cuarto> obtenerTodosPorCategoriaId(final int pCategoriaId, final int offset, final int limit);

    public void agregar(final Cuarto pCuarto);

    public void guardar(final Cuarto pCuarto);

    public void eliminar(final int pId);
}
