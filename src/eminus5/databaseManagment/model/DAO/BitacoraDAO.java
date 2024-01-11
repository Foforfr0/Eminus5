package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.OpenConnectionDB;
import eminus5.databaseManagment.model.ResultOperation;
import eminus5.databaseManagment.model.POJO.Bitacora;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class BitacoraDAO {
    public static ResultOperation getBitacoras(int idUser) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;

        if(connectionDB != null){
            try{
                String sqlQuery = "SELECT BA.Nombre, BA.Descripción, A.Nombre AS 'Nombre actividad', U.Nombre AS 'Autor' " +
                                  "FROM BitacoraActividad BA " +
                                  "JOIN Actividad A ON A.IdActividad = BA.IdActividad " +
                                  "JOIN Usuario U ON BA.IdDesarrollador = U.IDUsuario " +
                                  "WHERE U.IDUsuario = ? " +
                                  "UNION " +
                                  "SELECT BC.Nombre, BC.Descripción, C.Nombre AS 'Nombre cambio', U.Nombre AS 'Autor' " +
                                  "FROM BitacoraCambio BC " +
                                  "JOIN Cambio C ON C.IdCambio = BC.IdCambio " +
                                  "JOIN Usuario U ON BC.IdDesarrollador = U.IDUsuario " +
                                  "WHERE U.IDUsuario = ?; ";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                prepareQuery.setInt(1, idUser);
                prepareQuery.setInt(2, idUser);
                ResultSet resultQuery = prepareQuery.executeQuery();
                
                ObservableList<Bitacora> listBitacoras = FXCollections.observableArrayList();
                while(resultQuery.next()){
                    Bitacora newBitacora = new Bitacora();
                    newBitacora.setNombre(resultQuery.getString("Nombre"));
                    newBitacora.setDescripcion(resultQuery.getString("Descripción"));
                    newBitacora.setNombreActividad(resultQuery.getString("Nombre actividad"));
                    newBitacora.setAutor(resultQuery.getString("Autor"));
                    listBitacoras.add(newBitacora);
                    resultOperation = new ResultOperation(
                        false,
                        "Se encontraron bitacoras",
                        listBitacoras.size(),
                        listBitacoras
                    );
                    System.out.println("BitacoraDAO//BITACORAS ENCONTRADAS: " + listBitacoras.size() + " DEL DESARROLLADOR ID" + idUser);
                }
                
                if(listBitacoras.size() <= 0) {
                    resultOperation = new ResultOperation(
                        false,
                        "Sin registros",
                        0,
                        null
                    );
                    System.out.println("BitacoraDAO//NO SE ENCONTRARON BITACORAS DEL DESARROLLADOR ID" + idUser);
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(
                    true,
                    "Fallo la conexion con la base de datos",
                    -1,
                    null
                );
                System.out.println("Error de \"SQLException\" en archivo\"BitacoraDAO\" en metodo \"getBitacoras\"");
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
