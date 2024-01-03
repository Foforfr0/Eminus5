/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.POJO;


public class Usuario {
    private int idUsuario;
    private String matricula;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correoPersonal;
    private String correoInstitucional;
    private int semestre;
    private String rolSistema;
    private int idProyecto;

    public Usuario() {
    }

    public Usuario(int idUsuario, String matricula, String nombre, String apellidoPaterno, String apellidoMaterno, String correoPersonal, String correoInstitucional, int semestre, String rolSistema, int idProyecto) {
        this.idUsuario = idUsuario;
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correoPersonal = correoPersonal;
        this.correoInstitucional = correoInstitucional;
        this.semestre = semestre;
        this.rolSistema = rolSistema;
        this.idProyecto = idProyecto;
    }
    
    public Usuario(int idUsuario, String rolSistema) {
        this.idUsuario = idUsuario;
        this.rolSistema = rolSistema;
    }
    
    public Usuario(int idUsuario, String rolSistema, int idProyecto) {
        this.idUsuario = idUsuario;
        this.rolSistema = rolSistema;
        this.idProyecto = idProyecto;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    public String getCorreoPersonal() {
        return correoPersonal;
    }

    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public String getRolSistema() {
        return rolSistema;
    }

    public void setRolSistema(String rolSistema) {
        this.rolSistema = rolSistema;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }
}
