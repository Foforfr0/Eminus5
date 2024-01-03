/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.POJO;


public class Proyecto {
    private int idProyecto;
    private String nombre;
    private String descripcion;
    private int numIntegrantes;
    private String fechaInicio;
    private String fechaFin;

    public Proyecto() {
    }

    public Proyecto(int idProyecto, String nombre) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
    }

    public Proyecto(int idProyecto, String nombre, String fechaInicio, String fechaFin) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    public Proyecto(int idProyecto, String nombre, String descripcion, int numIntegrantes, String fechaInicio, String fechaFin) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.numIntegrantes = numIntegrantes;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
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

    public int getNumIntegrantes() {
        return numIntegrantes;
    }

    public void setNumIntegrantes(int numIntegrantes) {
        this.numIntegrantes = numIntegrantes;
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
}
