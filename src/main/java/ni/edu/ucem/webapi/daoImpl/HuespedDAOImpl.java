package ni.edu.ucem.webapi.daoImpl;

import ni.edu.ucem.webapi.dao.HuespedDAO;
import ni.edu.ucem.webapi.modelo.Huesped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class HuespedDAOImpl implements HuespedDAO{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HuespedDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Huesped obtenerPorId(int pId) {
        String sql = "select * from huesped where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{pId},
                new BeanPropertyRowMapper<Huesped>(Huesped.class));
    }

    @Override
    public List<Huesped> obtenerTodos(final int pOffset, final int pLimit) {
        String sql = "select * from huesped offset ? limit ?";
        return this.jdbcTemplate.query(sql, new Object[]{pOffset, pLimit},
                new BeanPropertyRowMapper<Huesped>(Huesped.class));
    }
}
