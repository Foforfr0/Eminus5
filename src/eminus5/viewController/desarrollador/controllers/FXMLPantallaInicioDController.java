package eminus5.viewController.desarrollador.controllers;

import static eminus5.utils.loadView.loadParent;
import static eminus5.utils.loadView.loadScene;
import static eminus5.utils.loadView.loadView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class FXMLPantallaInicioDController implements Initializable {
    
    @FXML
    private Label lbTituloVentana;
    @FXML
    private Pane pnInsideSceneD;
    
    public static int idUser = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    private void clearInsideScene() {
        this.lbTituloVentana.setText("Detalles proyecto");
        this.pnInsideSceneD.getChildren().clear();
    }


    @FXML
    private void btnBitacoras(ActionEvent event) {
       String FXMLBitacorasD = "viewController/desarrollador/views/FXMLBitacorasD.fxml";
        
        try {
            FXMLLoader loader = loadView(FXMLBitacorasD);
            FXMLBitacorasDController controller = loader.getController();
            FXMLBitacorasDController.idUser = idUser;
        
            Pane vistaSecundaria = (Pane) loadParent(FXMLBitacorasD);
            this.pnInsideSceneD.getChildren().setAll(vistaSecundaria);
        } catch (IOException ioex) {
            System.out.println("Error de \"IOException\" en archivo \"FXMLPantallaInicioDController\" en método \"btnBitacoras\"");
            ioex.printStackTrace();
        }
        
    }

    @FXML
    private void btnActividades(ActionEvent event) {
        String FXMLActividadesD = "viewController/desarrollador/views/FXMLActividadesD.fxml";
        
        try {
            FXMLLoader loader = loadView(FXMLActividadesD);
            FXMLActividadesDController controller = loader.getController();
            FXMLActividadesDController.idUser = idUser;
            
            Pane vistaSecundaria = (Pane) loadParent(FXMLActividadesD);
            this.pnInsideSceneD.getChildren().setAll(vistaSecundaria);
        } catch (IOException ioex) {
            System.out.println("Error de \"IOException\" en archivo \"FXMLPantallaInicioDController\" en método \"btnActividades\"");
            ioex.printStackTrace();
        }
    }

    @FXML
    private void btnCambios(ActionEvent event) {
        String FXMLCambiosD = "viewController/desarrollador/views/FXMLCambiosD.fxml";
        
        try {
            FXMLLoader loader = loadView(FXMLCambiosD);
            FXMLCambiosDController controller = loader.getController();
            FXMLCambiosDController.idUser = idUser;
        
            Pane vistaSecundaria = (Pane) loadParent(FXMLCambiosD);
            this.pnInsideSceneD.getChildren().setAll(vistaSecundaria);
        } catch (IOException ioex) {
            System.out.println("Error de \"IOException\" en archivo \"FXMLPantallaInicioDController\" en método \"btnCambios\"");
            ioex.printStackTrace();
        }
        
    }

    @FXML
    private void btnDefectos(ActionEvent event) {
        String FXMLDefectosD = "viewController/desarrollador/views/FXMLDefectosProyecto.fxml";
        
        try {
            FXMLLoader loader = loadView(FXMLDefectosD);
            FXMLDefectosDController controller = loader.getController();
            FXMLDefectosProyectoController.idUser = idUser;
        
            Pane vistaSecundaria = (Pane) loadParent(FXMLDefectosD);
            this.pnInsideSceneD.getChildren().setAll(vistaSecundaria);
        } catch (IOException ioex) {
            System.out.println("Error de \"IOException\" en archivo \"FXMLPantallaInicioDController\" en método \"btnDefectos\"");
            ioex.printStackTrace();
        }
        
    }

    @FXML
     private void showSceneLogin() throws IOException{
        Stage stageActual = (Stage) this.lbTituloVentana.getScene().getWindow();
        stageActual.setScene(loadScene("viewController/FXMLInicioSesion.fxml"));
        
        stageActual.setTitle("Inicio de sesión");
        stageActual.setResizable(false);
        stageActual.show();
    }
    
    private void showSceneLogin(MouseEvent event) {
        try {
            showSceneLogin();
        } catch (IOException ioex) {
            System.out.println("\"Error de \"IOException\" en archivo \"FXMLPantallaInicioDController\" en método \"showSceneLogin\"\"");
            ioex.printStackTrace();
        }
    }
    
}
