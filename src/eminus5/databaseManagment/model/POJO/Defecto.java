/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.POJO;

public class Defecto {
    private int idDefecto;
    private String nombre;
    private String descripcion;
    private int idEstado;
    private String estado;
    private int esfuerzoMin;
    private String fechaEncontrado;
    private String fechaSolucionado;
    private int idTipo;
    private String tipo;

    public Defecto() {
    }

    public Defecto(int idDefecto, String nombre, String descripcion, String estado, int esfurzoMin, String fechaEncontrado, String fechaSolucionado, String tipo) {
        this.idDefecto = idDefecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.esfuerzoMin = esfurzoMin;
        this.fechaEncontrado = fechaEncontrado;
        this.fechaSolucionado = fechaSolucionado;
        this.tipo = tipo;
    }

    public Defecto(int idDefecto, String nombre, String descripcion, int idEstado, String estado, int esfurzoMin, String fechaEncontrado, String fechaSolucionado, int idTipo, String tipo) {
        this.idDefecto = idDefecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idEstado = idEstado;
        this.estado = estado;
        this.esfuerzoMin = esfurzoMin;
        this.fechaEncontrado = fechaEncontrado;
        this.fechaSolucionado = fechaSolucionado;
        this.idTipo = idTipo;
        this.tipo = tipo;
    }

    public int getIdDefecto() {
        return idDefecto;
    }

    public void setIdDefecto(int idDefecto) {
        this.idDefecto = idDefecto;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getEsfuerzoMin() {
        return esfuerzoMin;
    }

    public void setEsfuerzoMin(int esfuerzoMin) {
        this.esfuerzoMin = esfuerzoMin;
    }

    public String getFechaEncontrado() {
        return fechaEncontrado;
    }

    public void setFechaEncontrado(String fechaEncontrado) {
        this.fechaEncontrado = fechaEncontrado;
    }

    public String getFechaSolucionado() {
        return fechaSolucionado;
    }

    public void setFechaSolucionado(String fechaSolucionado) {
        this.fechaSolucionado = fechaSolucionado;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
