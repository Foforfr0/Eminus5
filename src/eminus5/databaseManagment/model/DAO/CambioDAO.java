package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.OpenConnectionDB;
import eminus5.databaseManagment.model.POJO.Cambio;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CambioDAO {
    public static ResultOperation getCambios(int idUser) throws SQLException {
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {
            try {
                String sqlQuery = "SELECT C.IdCambio, C.Nombre, C.Descripcion, C.Esfuerzo, " +
                                  " DATE_FORMAT(C.FechaInicio, '%d-%m-%Y') AS FechaInicio, DATE_FORMAT(C.FechaFin, '%d-%m-%Y') AS FechaFin, " +
                                  "E.Nombre AS 'Estado', TA.Nombre AS 'Tipo'\n" +
                                  "FROM Cambio C \n" +
                                  "JOIN Estado E ON E.IdEstado = C.IdEstado \n" +
                                  "JOIN TipoActividad TA ON TA.IdTipoActividad = C.IdTipo \n" +
                                  "JOIN Usuario U ON C.IdDesarrollador = U.IDUsuario \n" +
                                  "WHERE U.IDUsuario = ? AND C.IdEstado = 1;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                prepareQuery.setInt(1, idUser);
                ResultSet resultQuery = prepareQuery.executeQuery();
                
                ObservableList<Cambio> listCambios = FXCollections.observableArrayList();
                while(resultQuery.next()) {
                    Cambio newCambio = new Cambio();
                    newCambio.setIdCambio(resultQuery.getInt("IdCambio"));
                    newCambio.setNombre(resultQuery.getString("Nombre"));
                    newCambio.setDescripcion(resultQuery.getString("Descripcion"));
                    newCambio.setEsfuerzo(resultQuery.getInt("Esfuerzo"));
                    newCambio.setFechaInicio(resultQuery.getString("FechaInicio"));
                    newCambio.setFechaFin(resultQuery.getString("FechaFin"));
                    newCambio.setEstado(resultQuery.getString("Estado"));
                    newCambio.setTipo(resultQuery.getString("Tipo"));
                    listCambios.add(newCambio);
                    resultOperation = new ResultOperation(
                            false,
                            "Se encontraron cambios",
                            listCambios.size(),
                            listCambios
                    );
                    System.out.println("CambioDAO//CAMBIOS ENCONTRADOS: " + listCambios.size() + " DEL DESARROLLADOR ID" + idUser);
                }
                
                if (listCambios.size() <= 0) {
                    resultOperation = new ResultOperation(
                            false,
                            "Sin registros",
                            0,
                            null
                    );
                    System.out.println("CambioDAO//NO SE ENCONTRARON CAMBIOS DEL DESARROLLADOR ID " + idUser);
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(
                        true,
                        "Fallo la conexion con la base de datos",
                        -1,
                        null
                );
                System.out.println("Error de \"SQLException\" en archivo\"CambioDAO\" en metodo \"getCambios\"");
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
    
    private static int getEstadoCambioToInt(String idEstadoCambio) {
        switch(idEstadoCambio) {
            case "Iniciado":
                return 1;
            case "Entregado":
                return 2;
            default:
                return 0;
        }
    }
    
    private static int getTipoCambioToInt (String idTipoCambio) {
        switch (idTipoCambio) {
            case "Frontend":
                return 1;
            case "Backend":
                return 2;
            case "Base de datos":
                return 3;
            case "Controlador":
                return 4;
            case "JavaScript":
                return 5;
            default:
                return 0;
        }
    }
    
    public static ResultOperation registrarCambio(int idUser, Cambio newCambio) throws SQLException {
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {
            try {
                String sqlQuery = "INSERT INTO Cambio (Nombre, Descripcion, FechaInicio, " +
                                  "IdDesarrollador, IdEstado, IdTipo) " +
                                  "VALUES (?,?,(STR_TO_DATE(?, '%d-%m-%Y')),?,?,?);";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                prepareQuery.setString(1, newCambio.getNombre());
                prepareQuery.setString(2, newCambio.getDescripcion());
                prepareQuery.setString(3, newCambio.getFechaInicio().replace("/", "-"));
                prepareQuery.setInt(4, idUser);
                prepareQuery.setInt(5, getEstadoCambioToInt(newCambio.getEstado()));
                prepareQuery.setInt(6, getTipoCambioToInt(newCambio.getTipo()));
                int numberAffectedRows = prepareQuery.executeUpdate();
                
                if(numberAffectedRows > 0) {
                    resultOperation = new ResultOperation(
                            false,
                            "Se ha registrado el cambio",
                            numberAffectedRows,
                            newCambio
                    );
                    System.out.println("CambioDAO//SE HA REGISTRADO EL CAMBIO: " + newCambio.getNombre());
                } else {
                    resultOperation = new ResultOperation(
                            true,
                            "No se ha registrado el cambio",
                            numberAffectedRows,
                            newCambio
                    );
                    System.out.println("CambioDAO//NO SE REGISTRO EL CAMBIO" + newCambio.getNombre());
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
    
    public static ResultOperation modificarCambio(Cambio newCambio) throws SQLException {
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {
            try {
                String sqlQuery = "UPDATE Cambio C " +
                                  "SET C.IdEstado = ?, C.FechaFin = (STR_TO_DATE(?, '%d-%m-%Y')), C.Esfuerzo = ? " +
                                  "WHERE C.IdCambio = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                prepareQuery.setInt(1, getEstadoCambioToInt(newCambio.getEstado()));
                prepareQuery.setString(2, newCambio.getFechaFin().replace("/", "-"));
                prepareQuery.setInt(3, newCambio.getEsfuerzo());
                prepareQuery.setInt(4, newCambio.getIdCambio());
                
                int numberAffectedRows = prepareQuery.executeUpdate();
                
                if (numberAffectedRows > 0) {
                    resultOperation = new ResultOperation(
                            false,
                            "Se ha modificado el cambio",
                            numberAffectedRows,
                            newCambio
                    );
                } else {
                    resultOperation = new ResultOperation(
                            true,
                            "No se ha modificado el cambio",
                            numberAffectedRows,
                            newCambio
                    );
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(
                        true,
                        "Fallo la conexion con la base de datos",
                        -1,
                        null
                );
                System.err.println("Error de \"SQLException\" en archivo \"CambioDAO\" en método \"modificarCambio\"");
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
    
}
