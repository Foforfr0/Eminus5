package eminus5;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import static eminus5.utils.loadView.loadScene;


public class Eminus5 extends Application {
    /*
        //Cada vez que se abre una vista
            1.-Pasar el id del usuario.
        //Para abrir una vista con su controlador se hace:
            this.lbTittleWindow.setText("Gestión de pacientes");
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
                System.out.println("Error de \"IOException\" en archivo \"FXMLPantallaInicioRController\" en método \"clicShowGestionarActividadesScene\"");
                ioex.printStackTrace();
            }
        //Todas las excepciones se manejan en el controlador, a no ser que sean 
            más específicas como los SQLException de los DAO
    */
    
    @Override
    public void start(Stage stage) {
        try {
            showSceneLogin(stage);
        } catch(IOException ioex) {
            System.err.println("\"Error de \"IOException\" en archivo \"Eminus5\" en método \"showSceneLogin\"\"");
            ioex.printStackTrace();
        }
    }
    
    private void showSceneLogin(Stage currentStage) throws IOException{
        currentStage.setScene(loadScene("viewController/FXMLInicioSesion.fxml"));
        
        currentStage.setTitle("Inicio de sesión");
        currentStage.setResizable(false);
        currentStage.show();    
    }
    
    public static void main(String[] args) {
        launch(args);
    }  
}
