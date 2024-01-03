/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.responsableProyecto.controllers;

import static eminus5.utils.loadView.loadScene;
import static eminus5.utils.loadView.loadView;
import static eminus5.utils.loadView.loadParent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class FXMLPantallaInicioRController implements Initializable {
    @FXML
    private Label lbTittleWindow;
    @FXML
    private Button btInicio;
    @FXML
    private Button btGestionarActividades;
    @FXML
    private Pane pnInsideScene;
    
    
    public static int idResponsable = 0;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addActionToButtons();
    }    
    
    @FXML
    private void clearInsideScene() {
        this.lbTittleWindow.setText("Menú principal");
        this.btInicio.setDisable(true);
        this.btGestionarActividades.setDisable(false);
        
        this.pnInsideScene.getChildren().clear();
    }
    
    private void addActionToButtons() {
        btInicio.setDisable(true);
        btInicio.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearInsideScene();
            }
        });
        btInicio.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ESCAPE) {
                    clearInsideScene();
                }
            }
        });  
    }
    
    @FXML
    private void clicClearScene(KeyEvent event) {           //To main anchor pane
        if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ESCAPE) {
            clearInsideScene();
        }
    }
    
    private void showSceneLogin() throws IOException{
        Stage stageActual = (Stage) this.lbTittleWindow.getScene().getWindow();
        stageActual.setScene(loadScene("viewController/FXMLInicioSesion.fxml"));
        
        stageActual.setTitle("Inicio de sesión");
        stageActual.setResizable(false);
        stageActual.show();
    }
    
    @FXML
    private void showSceneLogin(MouseEvent event) {
        try {
            showSceneLogin();
        } catch (IOException ioex) {
            System.err.println("\"Error de \"IOException\" en archivo \"FXMLPantallaInicioRController\" en método \"showSceneLogin\"\"");
            ioex.printStackTrace();
        }
    }

    @FXML
    private void clicShowGestionarActividadesScene(MouseEvent event) {
        this.lbTittleWindow.setText("Gestión de actividades");
        this.btInicio.setDisable(false);
        this.btGestionarActividades.setDisable(true);
        String FXMLActividadesProyecto = "viewController/responsableProyecto/views/FXMLActividadesProyecto.fxml";
        
        try {
            FXMLLoader loader = loadView(FXMLActividadesProyecto);
            FXMLActividadesProyectoController controller = loader.getController();
            FXMLActividadesProyectoController.idResponsable = idResponsable;
        
            Pane vistaSecundaria = (Pane) loadParent(FXMLActividadesProyecto);
            this.pnInsideScene.getChildren().setAll(vistaSecundaria);
        } catch (IOException ioex) {
            System.err.println("Error de \"IOException\" en archivo \"FXMLPantallaInicioRController\" en método \"clicShowGestionarActividadesScene\"");
            ioex.printStackTrace();
        }
    }    
}
