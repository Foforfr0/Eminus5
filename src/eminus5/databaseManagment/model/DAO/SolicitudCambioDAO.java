/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.POJO.SolicitudCambio;
import eminus5.databaseManagment.model.OpenConnectionDB;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class SolicitudCambioDAO {
    public static ResultOperation getSolicitudCambioDefecto (int idDefecto) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {    
            try {            
                String sqlQuery = "SELECT SC.IdSolicitud, SC.Nombre, SC.Descripcion, SC.Razon, " +
                                  "SC.Impacto, SC.AccionPropuesta, DATE_FORMAT(SC.FechaCreacion, '%d-%m-%Y') AS FechaCreacion, " +
                                  "DATE_FORMAT(FechaAceptada, '%d-%m-%Y') AS FechaAceptada " +
                                  "FROM SolicitudCambio SC RIGHT JOIN Defecto D ON SC.IdDefecto = D.IdDefecto " +
                                  "WHERE D.IdDefecto = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                    prepareQuery.setInt(1, idDefecto);
                ResultSet resultQuery = prepareQuery.executeQuery();
                int idSolicitud;
                
                if (resultQuery.next()) {
                    resultOperation = new ResultOperation(            //It´s exists
                        false, 
                        "Se encontró la solicitud", 
                        resultQuery.getInt("IdSolicitud"),
                        new SolicitudCambio(
                            resultQuery.getInt("IdSolicitud"),
                            resultQuery.getString("Nombre"),
                            resultQuery.getString("Descripcion"),
                            resultQuery.getString("Razon"),
                            resultQuery.getString("Impacto"),
                            resultQuery.getString("AccionPropuesta"),
                            resultQuery.getString("FechaCreacion"),
                            resultQuery.getString("FechaAceptada"),
                            idDefecto
                        )
                    );
                    System.out.println("SolicitudCambioDAO//SOLICITUD ENCONTRADA "+resultQuery.getInt("IdSolicitud")+" IDDEFECTO: "+idDefecto);
                }
                if (resultQuery.getInt("IdSolicitud") <= 0) {
                    resultOperation = new ResultOperation(            //It doesn't exist but it wasn't an error
                        false, 
                        "El defecto no tiene una solicitud de cambio relacionada", 
                        0, 
                        null
                    );
                    System.out.println("SolicitudCambioDAO//NO SE ENCONTRÓ SOLICITUD DE CAMBIO PARA DEFECTO ID: "+idDefecto);
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.err.println("Error de \"SQLException\" en archivo \"SolicitudCambioDAO\" en método \"getSolicitudCambioDefecto\"");
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
    
    public static ResultOperation getSolicitudesCambioProyecto (int idProyecto) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {    
            try {            
                String sqlQuery = "SELECT SC.IdSolicitud, SC.Nombre, SC.Descripcion, SC.Razon, SC.Impacto, SC.AccionPropuesta, " +
                                  "SC.FechaCreacion, SC.FechaAceptada, ES.Nombre AS EstadoSolicitud " +
                                  "FROM SolicitudCambio SC LEFT JOIN EstadoSolicitud ES ON SC.IdEstadoAceptacion = ES.IdSolicitud " +
                                  "LEFT JOIN Defecto D ON SC.IdDefecto = D.IdDefecto " +
                                  "LEFT JOIN Proyecto P ON SC.IdProyecto = P.IdProyecto " +
                                  "WHERE P.IdProyecto = ? OR D.IdProyecto = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                    prepareQuery.setInt(1, idProyecto);
                    prepareQuery.setInt(2, idProyecto);
                ResultSet resultQuery = prepareQuery.executeQuery();
                
                ObservableList<SolicitudCambio> listSolicitudes = FXCollections.observableArrayList();
                while (resultQuery.next()) {
                    listSolicitudes.add(
                        new SolicitudCambio(
                            resultQuery.getInt("IdSolicitud"), 
                            resultQuery.getString("Nombre"), 
                            resultQuery.getString("Descripcion"), 
                            resultQuery.getString("Razon"), 
                            resultQuery.getString("Impacto"), 
                            resultQuery.getString("AccionPropuesta"), 
                            resultQuery.getString("FechaCreacion"), 
                            //resultQuery.getString("FechaAceptada").equals("") ? "Sin aceptar" : resultQuery.getString("FechaAceptada"), 
                                resultQuery.getString("FechaAceptada"),
                            0
                        )
                    );
                    resultOperation = new ResultOperation(            //It´s exists
                        false, 
                        "Se encontró la solicitud", 
                        listSolicitudes.size(),
                        listSolicitudes
                    );
                    System.out.println("SolicitudCambioDAO//SOLICITUD ENCONTRADA "+resultQuery.getInt("IdSolicitud")+" PROYECTO: "+idProyecto);
                }
                if (listSolicitudes.size() <= 0) {
                    resultOperation = new ResultOperation(            //It doesn't exist but it wasn't an error
                        false, 
                        "El proyecto no contiene solicitudes relacionadas", 
                        0, 
                        null
                    );
                    System.out.println("SolicitudCambioDAO//NO SE ENCONTRÓ SOLICITUD DE CAMBIO");
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.err.println("Error de \"SQLException\" en archivo \"SolicitudCambioDAO\" en método \"getSolicitudCambioProyecto\"");
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
    
    public static ResultOperation createSolicitud(SolicitudCambio newSolicitud) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) { 
            try {            
                PreparedStatement prepareQuery;
                if (newSolicitud.getIdPadre()<= 0) {
                    String sqlQuery = "INSERT INTO SolicitudCambio (Nombre, Descripcion, Razon, Impacto, AccionPropuesta, " +
                                      "FechaCreacion, FechaAceptada, IdEstadoAceptacion, IdDefecto) " +
                                      "VALUES (?, ?, ?, ?, ?, (STR_TO_DATE(?, '%d-%m-%Y')), NULL, 1, NULL);";
                    prepareQuery = connectionDB.prepareStatement(sqlQuery);
                        prepareQuery.setString(1, newSolicitud.getNombre());
                        prepareQuery.setString(2, newSolicitud.getDescripcion());
                        prepareQuery.setString(3, newSolicitud.getRazon());
                        prepareQuery.setString(4, newSolicitud.getImpacto());
                        prepareQuery.setString(5, newSolicitud.getAccionPropuesta());
                        prepareQuery.setString(6, newSolicitud.getFechaCreacion().replace("/}", "-"));
                } else {
                    String sqlQuery = "INSERT INTO SolicitudCambio (Nombre, Descripcion, Razon, Impacto, AccionPropuesta, " +
                                      "FechaCreacion, FechaAceptada, IdEstadoAceptacion, IdDefecto) " +
                                      "VALUES (?, ?, ?, ?, ?, (STR_TO_DATE(?, '%d-%m-%Y')), NULL, 1, ?);";
                    prepareQuery = connectionDB.prepareStatement(sqlQuery);
                        prepareQuery.setString(1, newSolicitud.getNombre());
                        prepareQuery.setString(2, newSolicitud.getDescripcion());
                        prepareQuery.setString(3, newSolicitud.getRazon());
                        prepareQuery.setString(4, newSolicitud.getImpacto());
                        prepareQuery.setString(5, newSolicitud.getAccionPropuesta());
                        prepareQuery.setString(6, newSolicitud.getFechaCreacion().replace("/}", "-"));
                        prepareQuery.setInt(7, newSolicitud.getIdPadre());
                }
                int numberAffectedRows = prepareQuery.executeUpdate();
                
                if (numberAffectedRows > 0) {
                    resultOperation = new ResultOperation(
                        false, 
                        "Se ha registrado la solicitud de cambio", 
                        numberAffectedRows, 
                        newSolicitud
                    );
                    System.out.println("SolicitudCambioDAO//SE HA REGISTRADO LA SOLICITUD: "+newSolicitud.getNombre());
                } else {
                    resultOperation = new ResultOperation(
                        true, 
                        "No se ha registrado la solicitud", 
                        numberAffectedRows, 
                        newSolicitud
                    );
                    System.out.println("SolicitudDAO//NO SE REGISTRÓ LA SOLICITUD: "+newSolicitud.getNombre());
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.err.println("Error de \"SQLException\" en archivo \"SolicitudCambioDAO\" en método \"createSolicitud\"");
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
