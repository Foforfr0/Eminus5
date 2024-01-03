package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.DAO.ActividadDAO;
import eminus5.databaseManagment.model.POJO.Actividad;
import eminus5.databaseManagment.model.ResultOperation;
import eminus5.utils.ShowMessage;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author abrah
 */
public class FXMLDetallesActividadDController implements Initializable {

    @FXML
    private Label lbTituloActividad;
    @FXML
    private Label lbDescripcionActividad;

    public static Actividad currentActividad = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
    }    

    private void initializeData() {
        this.lbTituloActividad.setText(currentActividad.getNombre());
        this.lbDescripcionActividad.setText(currentActividad.getDescripcion());
    }
    
    @FXML
    private void btnEntregar(ActionEvent event) {
        ButtonType result = ShowMessage.showConfirmation(
                "Confirmar",
                "¿Está seguro de entregar la actividad?",
                "Esta acción no se podrá revertir."
        );
        if (result == ButtonType.OK) {
            try {
                Actividad newActividad = new Actividad();
                    newActividad.setIdActividad(currentActividad.getIdActividad());
                    newActividad.setEstado("Entregado");

                ResultOperation resultModify = ActividadDAO.modificarActividadDesarrollador(newActividad);
                if (resultModify.getIsError() == true) {
                    ShowMessage.showMessage(
                            "ERROR",
                            "Error inesperado",
                            resultModify.getMessage(),
                            "Intente mas tarde"
                    );
                } else {
                    ShowMessage.showMessage(
                            "INFORMATION",
                            "Se ha modificado correctamente",
                            "Se entrego la actividad",
                            ""
                    );
                    Stage currentStage = (Stage)  this.lbTituloActividad.getScene().getWindow();
                    currentStage.close();
                }
            } catch (SQLException sqlex) {
                System.err.println("\"Error de \"SQLException\" en archivo \"FXMLDetallesActividadDController\" en método \"modificarActividadDesarrollador\"");
                sqlex.printStackTrace();
            }
        }
    }

    private void closeWindow(Stage currentStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¿Está seguro?");
        alert.setHeaderText("¿Está seguro de cancelar?");
        alert.setContentText("¿Ésta acción no se podrá revertir?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                currentStage.close(); 
            }
        });
    }
    
    @FXML
    private void btnCancelarActividad(ActionEvent event) {
        closeWindow((Stage) this.lbTituloActividad.getScene().getWindow());
    }
    
}
