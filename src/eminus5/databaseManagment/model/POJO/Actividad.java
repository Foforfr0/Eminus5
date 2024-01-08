/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.POJO;

public class Actividad {
    private int idActividad;
    private String nombre;
    private String descripcion;
    private String isAsignado = "";
    private String estado;
    private String tipo;
    private String fechaInicio;
    private String fechaFin;
    private int idProyecto;
    private int idDesarrollador;
    private String nombreDesarrollador;

    public Actividad() {
    }

    public Actividad(int idActividad, String nombre, String isAsignado, String estado, String tipo, String fechaInicio, String fechaFin) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.isAsignado = isAsignado;
        this.estado = estado;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Actividad(int idActividad, String nombre, String descripcion, String isAsignado, String estado, String tipo, String fechaInicio, String fechaFin, int idProyecto, int idDesarrollador, String nombreDesarrollador) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.isAsignado = isAsignado;
        this.estado = estado;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idProyecto = idProyecto;
        this.idDesarrollador = idDesarrollador;
        this.nombreDesarrollador = nombreDesarrollador;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
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

    public String getIsAsignado() {
        return isAsignado;
    }

    public void setIsAsignado(String isAsignado) {
        this.isAsignado = isAsignado;
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

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getIdDesarrollador() {
        return idDesarrollador;
    }

    public void setIdDesarrollador(int idDesarrollador) {
        this.idDesarrollador = idDesarrollador;
    }

    public String getNombreDesarrollador() {
        return nombreDesarrollador;
    }

    public void setNombreDesarrollador(String nombreDesarrollador) {
        this.nombreDesarrollador = nombreDesarrollador;
    }
}
