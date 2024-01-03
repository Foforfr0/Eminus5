/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.databaseManagment.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class OpenConnectionDB {
    private static final String URL_CONNECTION = 
        "jdbc:mysql://"+ConstantsConnectionDB.HOSTNAME+
        ":"+ConstantsConnectionDB.PORT+
        "/"+ConstantsConnectionDB.DATABASE+
        "?allowPublicKeyRetrieval=true&useSSL=false";
    
    public static Connection getConnection(){
        Connection connectionDB = null;
        
        try {
            Class.forName(ConstantsConnectionDB.DRIVER);
            connectionDB = DriverManager.getConnection(
                URL_CONNECTION, 
                ConstantsConnectionDB.USER, 
                ConstantsConnectionDB.PASSWORD
            );
        } catch (ClassNotFoundException clnfe) {
            System.err.println("Error de \"ClassNotFoundException\" en archivo \"OpenConnectionDB\" en método \"getConnection\"");
            clnfe.printStackTrace();
        } catch (SQLException sqle) {
            System.err.println("Error de \"SQLException\" en archivo \"OpenConnectionDB\" en método \"getConnection\"");
            sqle.printStackTrace();
        }
        
        return connectionDB;
    }
}
