/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.POJO;


public class SolicitudCambio {
    private int idSolicitud;
    private String nombre;
    private String descripcion;
    private String razon;
    private String impacto;
    private String accionPropuesta;
    private String fechaCreacion;
    private String fechaAceptada;
    private int idDefecto;

    public SolicitudCambio() {
    }

    public SolicitudCambio(int idSolicitud, String nombre, String descripcion, String razon, String impacto, String accionPropuesta, String fechaCreacion, String fechaAceptada, int idDefecto) {
        this.idSolicitud = idSolicitud;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.razon = razon;
        this.impacto = impacto;
        this.accionPropuesta = accionPropuesta;
        this.fechaCreacion = fechaCreacion;
        this.fechaAceptada = fechaAceptada;
        this.idDefecto = idDefecto;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaAceptada() {
        return fechaAceptada;
    }

    public void setFechaAceptada(String fechaAceptada) {
        this.fechaAceptada = fechaAceptada;
    }
    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
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

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getImpacto() {
        return impacto;
    }

    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }

    public String getAccionPropuesta() {
        return accionPropuesta;
    }

    public void setAccionPropuesta(String accionPropuesta) {
        this.accionPropuesta = accionPropuesta;
    }

    public int getIdDefecto() {
        return idDefecto;
    }

    public void setIdDefecto(int idDefecto) {
        this.idDefecto = idDefecto;
    }
}
