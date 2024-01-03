/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.POJO;

public class Bitacora {
    int idBitacora;
    int numBitacora;
    String nombre;
    String descripcion;
    int idEstado;
    int idDesarrollador;
    int idActividad;
    int idCambio;

    public Bitacora() {
    }

    public Bitacora(int idBitacora, int numBitacora, String nombre, String descripcion, int idEstado, int idDesarrollador, int idActividad, int idCambio) {
        this.idBitacora = idBitacora;
        this.numBitacora = numBitacora;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idEstado = idEstado;
        this.idDesarrollador = idDesarrollador;
        this.idActividad = idActividad;
        this.idCambio = idCambio;
    }

    public int getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(int idBitacora) {
        this.idBitacora = idBitacora;
    }

    public int getNumBitacora() {
        return numBitacora;
    }

    public void setNumBitacora(int numBitacora) {
        this.numBitacora = numBitacora;
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

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdDesarrollador() {
        return idDesarrollador;
    }

    public void setIdDesarrollador(int idDesarrollador) {
        this.idDesarrollador = idDesarrollador;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public int getIdCambio() {
        return idCambio;
    }

    public void setIdCambio(int idCambio) {
        this.idCambio = idCambio;
    }
    
}
