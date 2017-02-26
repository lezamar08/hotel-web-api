package ni.edu.ucem.webapi.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ni.edu.ucem.webapi.dao.CategoriaCuartoDAO;
import ni.edu.ucem.webapi.modelo.CategoriaCuarto;

@Repository
public class CategoriaCuartoDAOImpl implements CategoriaCuartoDAO 
{
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public CategoriaCuartoDAOImpl(final JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CategoriaCuarto obtenerPorId(final int pId) 
    {
        final String sql = "select * from categoria_cuarto where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{pId}, 
                new BeanPropertyRowMapper<CategoriaCuarto>(CategoriaCuarto.class));
    }

    @Override
    public List<CategoriaCuarto> obtenerTodos(final int offset, final int limit) 
    {
        final String sql = "select * from categoria_cuarto offset ? limit ?";
        return this.jdbcTemplate.query(sql, new Object[]{offset, limit},
                new BeanPropertyRowMapper<CategoriaCuarto>(CategoriaCuarto.class));
    }

    @Override
    public void agregar(final CategoriaCuarto pCategoriaCuarto) 
    {
        final String sql = new StringBuilder()
                .append("INSERT INTO categoria_cuarto")
                .append(" ")
                .append("(nombre, descripcion, precio)")
                .append(" ")
                .append("VALUES(?,?,?)")
                .toString();
        final Object[] parametros = new Object[3];
        parametros[0] = pCategoriaCuarto.getNombre();
        parametros[1] = pCategoriaCuarto.getDescripcion();
        parametros[2] = pCategoriaCuarto.getPrecio();
        this.jdbcTemplate.update(sql,parametros);
    }

    @Override
    public void guardar(final CategoriaCuarto pCategoriaCuarto) 
    {
        final String sql = new StringBuilder()
                .append("UPDATE categoria_cuarto")
                .append(" ")
                .append("SET nombre = ?, descripcion = ?")
                .append(",precio = ?")
                .append(" ")
                .append("WHERE id = ?")
                .toString();
        final Object[] parametros = new Object[4];
        parametros[0] = pCategoriaCuarto.getNombre();
        parametros[1] = pCategoriaCuarto.getDescripcion();
        parametros[2] = pCategoriaCuarto.getPrecio();
        parametros[3] = pCategoriaCuarto.getId();
        this.jdbcTemplate.update(sql,parametros);
    }

    @Override
    public void eliminar(final int pId) 
    {
        final String sql = "delete from categoria_cuarto where id = ?";
        this.jdbcTemplate.update(sql, new Object[]{pId});
    }

}
