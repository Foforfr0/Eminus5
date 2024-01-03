package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.DAO.DesarrolladorDAO;
import eminus5.databaseManagment.model.POJO.Bitacora;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author abrah
 */
public class FXMLVerBitacoraController implements Initializable {
    
    @FXML
    private Pane pnPrincipalD;
    @FXML
    private AnchorPane stageCrearBitacora;
    @FXML
    private Label lbNombreBitacora;
    @FXML
    private Label lbDetallesBitacora;

    public static Bitacora currentBitacora = null;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
    }    
    
    private void initializeData(){
        this.lbNombreBitacora.setText(currentBitacora.getNombre());
        this.lbDetallesBitacora.setText(currentBitacora.getDescripcion());
        
        try {
            ResultOperation resultGetDesarrollador = DesarrolladorDAO.getDesarrollador(currentBitacora.getIdDesarrollador());
            if (resultGetDesarrollador.getIsError() == true) {
                showMessage(
                        "Error",
                        "Error inesperado",
                        "No se ha podido encontrar el desarrollador",
                        "Intente mas tarde");
            }
        } catch (Exception e) {
                
            }
    }
    
    private void initializeStage() {
        stageCrearBitacora.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                event.consume();
                Stage current = (Stage) this.pnPrincipalD.getScene().getWindow();
                current.close();
            }
        });
    }

    @FXML
    private void btnSalir(ActionEvent event) {
        Stage currentStage = (Stage) this.pnPrincipalD.getScene().getWindow();
        currentStage.close();
    }
}
