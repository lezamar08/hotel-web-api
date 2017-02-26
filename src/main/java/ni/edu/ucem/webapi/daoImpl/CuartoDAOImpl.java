package ni.edu.ucem.webapi.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ni.edu.ucem.webapi.dao.CuartoDAO;
import ni.edu.ucem.webapi.modelo.Cuarto;

@Repository
public class CuartoDAOImpl implements CuartoDAO 
{
    private final JdbcTemplate jdbcTemplate;
   
    @Autowired
    public CuartoDAOImpl(final JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Cuarto obtenerPorId(final int pId) 
    {
        String sql = "select * from cuarto where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{pId}, 
                new BeanPropertyRowMapper<Cuarto>(Cuarto.class));
    }
    
    @Override
    public int contar()
    {
        final String sql = "select count(*) from cuarto";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }
    
    @Override
    public int contarPorCategoria(final int categoriaId)
    {
        final String sql = "select count(*) from cuarto where ";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<Cuarto> obtenerTodos(final int pOffset, final int pLimit) 
    {
        String sql = "select * from cuarto offset ? limit ?";
        return this.jdbcTemplate.query(sql, new Object[]{pOffset, pLimit},
                new BeanPropertyRowMapper<Cuarto>(Cuarto.class));
    }

    @Override
    public List<Cuarto> obtenerTodosPorCategoriaId(int pCategoriaId, int pOffset, int pLimit) 
    {
        final String sql = "select * from cuarto where categoria = ? offset ? limit ?";
        return this.jdbcTemplate.query(sql, new Object[]{pCategoriaId, pOffset, pLimit},
                new BeanPropertyRowMapper<Cuarto>(Cuarto.class));
    }

    @Override
    public void agregar(final Cuarto pCuarto) 
    {
        final String sql = new StringBuilder()
                .append("INSERT INTO cuarto")
                .append(" ")
                .append("(numero, descripcion, categoria)")
                .append(" ")
                .append("VALUES (?, ?, ?)")
                .toString();
        final Object[] parametros = new Object[3];
        parametros[0] = pCuarto.getNumero();
        parametros[1] = pCuarto.getDescripcion();
        parametros[2] = pCuarto.getCategoria();
        this.jdbcTemplate.update(sql,parametros);
        
    }

    @Override
    public void guardar(final Cuarto pCuarto) 
    {        
       final String sql = new StringBuilder()
                .append("UPDATE cuarto")
                .append(" ")
                .append("set numero = ?")
                .append(",descripcion = ?")
                .append(",categoria = ?")
                .append(" ")
                .append("where id = ?")
                .toString();
        final Object[] parametros = new Object[4];
        parametros[0] = pCuarto.getNumero();
        parametros[1] = pCuarto.getDescripcion();
        parametros[2] = pCuarto.getCategoria();
        parametros[3] = pCuarto.getId();
        this.jdbcTemplate.update(sql,parametros);
    }

    @Override
    public void eliminar(final int pId) 
    {
        final String sql = "delete from cuarto where id = ?";
        this.jdbcTemplate.update(sql, new Object[]{pId});
    }
}
