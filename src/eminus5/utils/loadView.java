/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.utils;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class loadView {
    public static void q(String pathSource) throws IOException{
        FXMLLoader loader = loadView(pathSource);
        Parent newParent = loadParent(pathSource);
        
        Scene newScene = loadScene(pathSource);
    }
    
    public static FXMLLoader loadView(String pathSource) throws IOException{
        URL url = eminus5.Eminus5.class.getResource(pathSource);
        return new FXMLLoader(url);
    }
    
    public static Parent loadParent(String pathSource) throws IOException{
        Parent newParent = loadView(pathSource).load();
        return newParent;
    }
    
    public static Scene loadScene(String pathSource) throws IOException {
        Scene newScene = new Scene(loadParent(pathSource));
        return newScene;
    }
}
