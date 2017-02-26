package ni.edu.ucem.webapi.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;


public class Reservacion {

    private Integer id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm")
    private Date desde = new Date();

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm")
    private Date hasta = new Date();

    private Integer cuarto;

    private Integer huesped;


    public Reservacion(){

    }

    public Reservacion(Date desde, Date hasta, Integer cuarto, Integer huesped) {
        this.desde = desde;
        this.hasta = hasta;
        this.cuarto = cuarto;
        this.huesped = huesped;
    }
    
    public Reservacion(Integer id,Date desde, Date hasta, Integer cuarto, Integer huesped) {
        this.id=id;
        this.desde = desde;
        this.hasta = hasta;
        this.cuarto = cuarto;
        this.huesped = huesped;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Integer getCuarto() {
        return cuarto;
    }

    public void setCuarto(Integer cuarto) {
        this.cuarto = cuarto;
    }

    public Integer getHuesped() {
        return huesped;
    }

    public void setHuesped(Integer huesped) {
        this.huesped = huesped;
    }
}
