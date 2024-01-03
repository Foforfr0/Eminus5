/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model.DAO;

import eminus5.databaseManagment.model.OpenConnectionDB;
import eminus5.databaseManagment.model.POJO.Usuario;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO {
    public static ResultOperation verifyUser(String user, String password) throws SQLException{
        Connection connectionDB = OpenConnectionDB.getConnection();
        ResultOperation resultOperation = null;
        
        if (connectionDB != null) {    
            try {            
                String sqlQuery = "SELECT u.IDUsuario, rs.Nombre AS RolSistema " +
                                  "FROM RolSistema rs RIGHT JOIN Usuario u ON u.IdRolSistema = rs.IdRolSistema " +
                                  "WHERE BINARY u.Usuario = ? AND BINARY u.Password = ?;";
                PreparedStatement prepareQuery = connectionDB.prepareStatement(sqlQuery);
                    prepareQuery.setString(1, user);
                    prepareQuery.setString(2, password);
                ResultSet resultQuery = prepareQuery.executeQuery();

                if(resultQuery.next()){
                    resultOperation = new ResultOperation(            //It´s exists
                        false, 
                        "Se encontró el usuario", 
                        resultQuery.getInt("IdUsuario"), 
                        new Usuario(
                            resultQuery.getInt("IdUsuario"),
                            resultQuery.getString("RolSistema")
                        )  
                    );
                    System.out.println("UserDAO//ID DE USUARIO INGRESADO ES: " + resultOperation.getNumberRowsAffected());
                }else{
                    resultOperation = new ResultOperation(            //It doesn't exist but it wasn't an error
                        false, 
                        "No se encontró un usuario con las credenciales", 
                        0, 
                        null
                    );
                    System.out.println("UserDAO//NO SE ENCONTRÓ USUARIO CON EL USUARIO Y PASSWORD");
                }
            } catch (SQLException sqlex) {
                resultOperation = new ResultOperation(               
                    true, 
                    "Falló conexión con la base de datos", 
                    -1, 
                    null
                );
                System.err.println("Error de \"SQLException\" en archivo \"UserDAO\" en método \"verifyUser\"");
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
