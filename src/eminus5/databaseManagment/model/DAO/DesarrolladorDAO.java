/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.OpenConnectionDB;
import eminus5.databaseManagment.model.POJO.Desarrollador;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DesarrolladorDAO {
    public static ResultOperation getDesarrolladoresProyecto(int idProyecto) throws SQLException {
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {    
            try {            
                String sqlQuery = "SELECT U.IDUsuario, U.ApellidoPaterno, U.ApellidoMaterno, U.Nombre, U.Matricula, " +
                                  "U.CorreoInstitucional, U.Semestre FROM Usuario U RIGHT JOIN Proyecto P " +
                                  "ON U.IDProyecto = P.IDProyecto " +
                                  "WHERE U.RolSistema = 2 AND P.IDProyecto = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                    prepareQuery.setInt(1, idProyecto);
                ResultSet resultQuery = prepareQuery.executeQuery();
                
                ObservableList<Desarrollador> listDesarrolladores = FXCollections.observableArrayList();
                while(resultQuery.next()){
                    Desarrollador newDesarrollador = new Desarrollador();
                        newDesarrollador.setIdDesarrollador(resultQuery.getInt("IDUsuario"));
                        newDesarrollador.setNombre(resultQuery.getString("Nombre"));
                        newDesarrollador.setApellidoPaterno(resultQuery.getString("ApellidoPaterno"));
                        newDesarrollador.setApellidoMaterno(resultQuery.getString("ApellidoMaterno"));
                        newDesarrollador.setMatricula(resultQuery.getString("Matricula"));
                        newDesarrollador.setCorreoInstitucional(resultQuery.getString("CorreoInstitucional"));
                        newDesarrollador.setSemestre(String.valueOf(resultQuery.getInt("Semestre"))+"°");
                    listDesarrolladores.add(newDesarrollador);
                    resultOperation = new ResultOperation(            //It´s exists
                        false, 
                        "Se encontraron desarrolladores", 
                        listDesarrolladores.size(),
                        listDesarrolladores
                    );
                    System.out.println("DesarrolladorDAO//DESARROLLADORES ENCONTRADOS: "+listDesarrolladores.size()+" DEL PROYECTO ID"+idProyecto);
                }
                if (listDesarrolladores.size() <= 0) {
                    resultOperation = new ResultOperation(            //It doesn't exist but it wasn't an error
                        false, 
                        "No se encontraron desarrolladores", 
                        0, 
                        null
                    );
                    System.out.println("DesarrolladorDAO//NO SE ENCONTRARON DESARROLLADORES DEL PROYECTO ID"+idProyecto);
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.err.println("Error de \"SQLException\" en archivo \"DesarrolladorDAO\" en método \"getDesarrolladoresProyecto\"");
                sqlex.printStackTrace();
            } finally {
                connectionDB.close();
            }
        } else {
            resultOperation = new ResultOperation(                 //Could not connect to database
                true, 
                "Falló conexión con la base de datos", 
                -1, 
                null
            );
            showMessageFailureConnection();
        }  
        
        return resultOperation;
    }
    
    public static ResultOperation getDesarrollador(int idDesarrollador) throws SQLException {
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {    
            try {            
                String sqlQuery = "SELECT U.Nombre, U.ApellidoPaterno, U.ApellidoMaterno " +
                                  "FROM Usuario U WHERE U.IDUsuario = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                    prepareQuery.setInt(1, idDesarrollador);
                ResultSet resultQuery = prepareQuery.executeQuery();
                
                if(resultQuery.next()){
                    resultOperation = new ResultOperation(            //It´s exists
                        false, 
                        "Se encontró el desarrollador", 
                        1,
                            new Desarrollador(
                            idDesarrollador, 
                            resultQuery.getString("Nombre"),
                            resultQuery.getString("ApellidoPaterno"), 
                            resultQuery.getString("ApellidoMaterno")
                        )
                    );
                    System.out.println("DesarrolladorDAO//DESARROLLADOR ENCONTRADO: "+idDesarrollador);
                } else {
                    resultOperation = new ResultOperation(            //It doesn't exist but it wasn't an error
                        false, 
                        "No se encontró desarrollador", 
                        0, 
                        null
                    );
                    System.out.println("DesarrolladorDAO//NO SE ENCONTRÓ DESARROLLADOR ID"+idDesarrollador);
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.err.println("Error de \"SQLException\" en archivo \"DesarrolladorDAO\" en método \"getDesarrollador\"");
                sqlex.printStackTrace();
            } finally {
                connectionDB.close();
            }
        } else {
            resultOperation = new ResultOperation(                 //Could not connect to database
                true, 
                "Falló conexión con la base de datos", 
                -1, 
                null
            );
            showMessageFailureConnection();
        }  
        
        return resultOperation;
    }
}
