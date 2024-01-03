/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.POJO;

public class Desarrollador {
    private int idDesarrollador;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String matricula;
    private String correoInstitucional;
    private String semestre;
    private boolean isAsignado;

    public Desarrollador() {
    }
    
    public Desarrollador(int idDesarrollador, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.idDesarrollador = idDesarrollador;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }
    
    public Desarrollador(int idDesarrollador, String nombre, String apellidoPaterno, String apellidoMaterno, String matricula, String correoInstitucional, String semestre) {
        this.idDesarrollador = idDesarrollador;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.matricula = matricula;
        this.correoInstitucional = correoInstitucional;
        this.semestre = semestre;
    }
    
    public Desarrollador(int idDesarrollador, String nombre, String apellidoPaterno, String apellidoMaterno, String matricula, String correoInstitucional, String semestre, boolean isAsignado) {
        this.idDesarrollador = idDesarrollador;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.matricula = matricula;
        this.correoInstitucional = correoInstitucional;
        this.semestre = semestre;
    }
    
    public int getIdDesarrollador() {
        return idDesarrollador;
    }

    public void setIdDesarrollador(int idDesarrollador) {
        this.idDesarrollador = idDesarrollador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public boolean isIsAsignado() {
        return isAsignado;
    }

    public void setIsAsignado(boolean isAsignado) {
        this.isAsignado = isAsignado;
    }

    @Override
    public String toString() {
        return "Desarrollador{" + "idDesarrollador=" + idDesarrollador + ", nombre=" + nombre + '}';
    }
}
