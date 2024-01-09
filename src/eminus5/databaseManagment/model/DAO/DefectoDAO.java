/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.OpenConnectionDB;
import eminus5.databaseManagment.model.POJO.Defecto;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class DefectoDAO {
    public static ResultOperation getDefectosDesarrollador(int idProyecto) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {
            try {
                String sqlQuery = "SELECT DE.IdDefecto, DE.Nombre, DE.Descripcion, E.Nombre AS 'Estado', " +
                                  "DE.Esfuerzo, DATE_FORMAT(DE.FechaEncontrado, '%d-%m-%Y') AS FechaEncontrado, " +
                                  "DATE_FORMAT(DE.FechaSolucionado, '%d-%m-%Y') AS FechaSolucionado, TA.Nombre AS 'Tipo' \n" +
                                  "FROM Defecto DE \n" +
                                  "JOIN Estado E ON E.IdEstado = DE.IdEstado \n" +
                                  "JOIN TipoActividad TA ON TA.IdTipoActividad = DE.IdTipo \n" + 
                                  "JOIN Proyecto P ON DE.IdProyecto = P.IdProyecto \n" +
                                  "WHERE P.IdProyecto = ? AND DE.IdEstado = 1";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                prepareQuery.setInt(1, idProyecto);
                ResultSet resultQuery = prepareQuery.executeQuery();

                ObservableList<Defecto> listDefectos = FXCollections.observableArrayList();
                while(resultQuery.next()) {
                    Defecto newDefecto = new Defecto();
                        newDefecto.setIdDefecto(resultQuery.getInt("IdDefecto"));
                        newDefecto.setNombre(resultQuery.getString("Nombre"));
                        newDefecto.setDescripcion(resultQuery.getString("Descripcion"));
                        newDefecto.setEstado(resultQuery.getString("Estado"));
                        newDefecto.setEsfuerzoMin(resultQuery.getInt("Esfuerzo"));
                        newDefecto.setFechaEncontrado(resultQuery.getString("FechaEncontrado"));
                        newDefecto.setFechaSolucionado(resultQuery.getString("FechaSolucionado"));
                        newDefecto.setTipo(resultQuery.getString("Tipo"));
                        listDefectos.add(newDefecto);
                        resultOperation = new ResultOperation(
                                false,
                                "Se encontraron defectos",
                                listDefectos.size(),
                                listDefectos
                        );
                        System.out.println("DefectoDAO//DEFECTOS ENCONTRADOS :" + listDefectos.size());
                }
                if (listDefectos.size() <=  0) {
                    resultOperation = new ResultOperation(
                            false,
                            "Sin registros",
                            0,
                            null
                    );
                    System.out.println("DefectoDAO//NO SE ENCONTRARON DEFECTOS ");
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(
                        true, 
                        "Fallo la conexion con la base de datos",
                        -1,
                        null
                );
                System.err.println("Error de \"SQLException\" en archivo \"DefectoDAO\" en metodo \"getDefectos\"");
                sqlex.printStackTrace();
            } finally {
                connectionDB.close();
            }
        } else {
            resultOperation = new ResultOperation(
                    true,
                    "Fallo la conexion con la base de datos",
                    -1,
                    null
            );
            showMessageFailureConnection();
        }
        return resultOperation;
    }
    
    private static int getEstadoDefectoToInt(String idEstadoDefecto) {
        switch(idEstadoDefecto) {
            case "Iniciado":
                return 1;
            case "Entregado":
                return 2;
            default:
                return 0;
        }
    }
    
    private static int getTipoDefectoToInt (String idTipoDefecto) {
        switch (idTipoDefecto) {
            case "Backend":
                return 1;
            case "Base de datos":    
                return 2;
            case "Controlador":
                return 3;
            case "Frontend":
                return 4;
            case "JavaScript":
                return 5;
            default:
                return 0;
        }
    }
    
    public static ResultOperation registrarDefecto(int IdProyecto, Defecto newDefecto) throws SQLException {
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if(connectionDB != null) {
            try {
                String sqlQuery = "INSERT INTO Defecto (Nombre, Descripcion, FechaEncontrado, " +
                                  "IdProyecto, IdEstado, IdTipo) \n" +
                                  "VALUES (?,?,(STR_TO_DATE(?, '%d-%m-%Y')),?,?,?); ";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                prepareQuery.setString(1, newDefecto.getNombre());
                prepareQuery.setString(2, newDefecto.getDescripcion());
                prepareQuery.setString(3, newDefecto.getFechaEncontrado().replace("/", "-"));
                prepareQuery.setInt(4, IdProyecto);
                prepareQuery.setInt(5, getEstadoDefectoToInt(newDefecto.getEstado()));
                prepareQuery.setInt(6, getTipoDefectoToInt(newDefecto.getTipo()));
                int numberAffectedRows = prepareQuery.executeUpdate();
                
                if(numberAffectedRows > 0) {
                    resultOperation = new ResultOperation(
                            false,
                            "Se ha registrado el defecto",
                            numberAffectedRows,
                            newDefecto
                    );
                    System.out.println("DefectoDAO//SE HA REGISTRADO EL DEFECTO: " + newDefecto.getNombre());
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(
                        true,
                        "Fallo la conexion con la base de datos",
                        -1,
                        null
                );
            } finally {
                connectionDB.close();
            }
        } else {
            resultOperation = new ResultOperation(
                    true,
                    "Fallo la conexion con la base de datos",
                    -1,
                    null
            );
            showMessageFailureConnection();
        }
        return resultOperation;
    }
    
    public static ResultOperation modificarDefecto(Defecto newDefecto) throws SQLException {
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {
            try {
                String sqlQuery = "UPDATE Defecto DE " +
                                  "SET DE.Esfuerzo = ?, DE.IdEstado = ?, DE.FechaSolucionado = (STR_TO_DATE(?, '%d-%m-%Y')) \n" +
                                  "WHERE DE.IdDefecto = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                prepareQuery.setInt(1, newDefecto.getEsfuerzoMin());
                prepareQuery.setInt(2, getEstadoDefectoToInt(newDefecto.getEstado()));
                prepareQuery.setString(3, newDefecto.getFechaSolucionado().replace("/", "-"));
                prepareQuery.setInt(4, newDefecto.getIdDefecto());
                
                int numberAffectedRows = prepareQuery.executeUpdate();
                
                if (numberAffectedRows > 0) {
                    resultOperation = new ResultOperation(
                            false,
                            "Se ha modificado el defecto",
                            numberAffectedRows,
                            newDefecto
                    );
                } else {
                    resultOperation = new ResultOperation(
                            true,
                            "No se ha modificado el defecto",
                            numberAffectedRows,
                            newDefecto
                    );
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(
                        true,
                        "Fallo la conexion con la base de datos",
                        -1,
                        null
                );
                System.err.println("Error de \"SQLException\" en archivo \"DefectoDAO\" en método \"modificarDefecto\"");
                sqlex.printStackTrace();
            } finally {
                connectionDB.close();
            }
        } else {
            resultOperation = new ResultOperation(
                    true,
                    "Fallo la conexion con la base de datos",
                    -1,
                    null
            );
            showMessageFailureConnection();
        }
        return resultOperation;
    }
    
    public static ResultOperation getDefectosProyecto(int idProyecto) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {    
            try {            
                String sqlQuery = "SELECT D.IdDefecto, D.Nombre, D.Descripcion, E.Nombre AS Estado, D.Esfuerzo, " +
                                  "DATE_FORMAT(D.FechaEncontrado, '%d-%m-%Y') AS FechaEncontrado, " +
                                  "DATE_FORMAT(D.FechaSolucionado, '%d-%m-%Y') AS FechaSolucionado, " +
                                  "TA.Nombre AS Tipo " +
                                  "FROM Defecto D RIGHT JOIN Proyecto P ON D.IdProyecto = P.IdProyecto " +
                                  "LEFT JOIN Estado E ON D.IdEstado = E.IdEstado " +
                                  "LEFT JOIN TipoActividad TA ON TA.IdTipoActividad = D.IdTipo " +
                                  "WHERE P.IdProyecto = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                    prepareQuery.setInt(1, idProyecto);
                ResultSet resultQuery = prepareQuery.executeQuery();
                
                ObservableList <Defecto> listDefectos = FXCollections.observableArrayList();
                while(resultQuery.next()){
                    Defecto newDefecto = new Defecto();
                        newDefecto.setIdDefecto(resultQuery.getInt("IdDefecto"));
                        newDefecto.setNombre(resultQuery.getString("Nombre"));
                        newDefecto.setDescripcion(resultQuery.getString("Descripcion"));
                        newDefecto.setEstado(resultQuery.getString("Estado"));
                        newDefecto.setEsfuerzoMin(resultQuery.getInt("Esfuerzo"));
                        newDefecto.setFechaEncontrado(resultQuery.getString("FechaEncontrado"));
                        newDefecto.setFechaSolucionado(resultQuery.getString("FechaSolucionado"));
                        newDefecto.setTipo(resultQuery.getString("Tipo"));
                    listDefectos.add(newDefecto);
                    resultOperation = new ResultOperation(            //It´s exists
                        false, 
                        "Se encontraron defectos", 
                        listDefectos.size(),
                        listDefectos
                    );
                    System.out.println("DefectoDAO//DEFECTOS ENCONTRADOS: "+listDefectos.size()+" DEL PROYECTO ID "+idProyecto);
                }
                
                if (listDefectos.size() <= 0) {
                    resultOperation = new ResultOperation(            //It doesn't exist but it wasn't an error
                        false, 
                        "No se encontraron defectos", 
                        0, 
                        null
                    );
                    System.out.println("DefectoDAO//NO SE ENCONTRARON DEFECTOS DEL PROYECTO ID "+idProyecto);
                }

            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.err.println("Error de \"SQLException\" en archivo \"DefectoDAO\" en método \"getDefectosProyecto\"");
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
