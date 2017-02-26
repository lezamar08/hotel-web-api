package ni.edu.ucem.webapi.daoImpl;

import java.util.List;
import ni.edu.ucem.webapi.dao.ReservacionDAO;
import ni.edu.ucem.webapi.modelo.Cuarto;
import ni.edu.ucem.webapi.modelo.Reservacion;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ReservacionDAOImpl implements ReservacionDAO {

    private final JdbcTemplate jdbcTemplate;

    public ReservacionDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservacion obtenerPorId(int pId) {
        String sql = "select * from reservacion where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{pId},
                new BeanPropertyRowMapper<Reservacion>(Reservacion.class));
    }

    @Override
    public void agregar(Reservacion pReservacion) {
        final String sql = new StringBuilder()
                .append("INSERT INTO reservacion")
                .append(" ")
                .append("(desde, hasta, cuarto, huesped)")
                .append(" ")
                .append("VALUES (?, ?, ?, ?)")
                .toString();
        final Object[] parametros = new Object[4];
        parametros[0] = pReservacion.getDesde();
        parametros[1] = pReservacion.getHasta();
        parametros[2] = pReservacion.getCuarto();
        parametros[3] = pReservacion.getHuesped();
        this.jdbcTemplate.update(sql,parametros);
    }
    
    @Override
    public List<Cuarto> cuartosDisponibles(Integer categoria,String desde, String hasta){
        final String sql = new StringBuilder()
                .append("select * from cuarto")
                .append(" where id NOT IN ( ")
                .append(" select cuarto from reservacion ")
                .append(" where desde= ? and hasta= ? ) and categoria = ? ")                
                .toString();
        return this.jdbcTemplate.query(sql, new Object[]{categoria,desde, hasta},
                new BeanPropertyRowMapper<Cuarto>(Cuarto.class));
    }
}
