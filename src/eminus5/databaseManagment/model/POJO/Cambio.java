package eminus5.databaseManagment.model.POJO;

/**
 *
 * @author abrah
 */
public class Cambio {
    private int idCambio;
    private String nombre;
    private String descripcion;
    private int esfuerzo;
    private String fechaInicio;
    private String fechaFin;
    private int idDesarrollador;
    private int idSolicitud;
    private String estado;
    private String tipo;

    public Cambio() {
    }

    public Cambio(int idCambio, String nombre, String descripcion, int esfuerzo, String fechaInicio, String fechaFin, int idDesarrollador, int idSolicitud, String estado, String tipo) {
        this.idCambio = idCambio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.esfuerzo = esfuerzo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idDesarrollador = idDesarrollador;
        this.idSolicitud = idSolicitud;
        this.estado = estado;
        this.tipo = tipo;
    }

    public int getIdCambio() {
        return idCambio;
    }

    public void setIdCambio(int idCambio) {
        this.idCambio = idCambio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEsfuerzo() {
        return esfuerzo;
    }

    public void setEsfuerzo(int esfuerzo) {
        this.esfuerzo = esfuerzo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getIdDesarrollador() {
        return idDesarrollador;
    }

    public void setIdDesarrollador(int idDesarrollador) {
        this.idDesarrollador = idDesarrollador;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    
}
