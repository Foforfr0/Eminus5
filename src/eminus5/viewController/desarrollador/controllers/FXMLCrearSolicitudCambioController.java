/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.DAO.SolicitudCambioDAO;
import eminus5.databaseManagment.model.POJO.SolicitudCambio;
import eminus5.databaseManagment.model.ResultOperation;
import eminus5.utils.ShowMessage;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class FXMLCrearSolicitudCambioController implements Initializable {
    @FXML
    private AnchorPane stageCrearSolicitud;
    @FXML
    private Pane pnPrincipal;
    @FXML
    private TextArea tfDescripcion;
    @FXML
    private Button btAddSolicitud;
    @FXML
    private TextArea tfRazon;
    @FXML
    private TextArea tfImpacto;
    @FXML
    private TextArea tfAccionPropuesta;
    @FXML
    private TextField tfNombre;
    @FXML
    private Button btCancelAddActividad;
    
    
    public static int idPadre = 0;
    public static boolean fromDefecto;
    private String fechaActual = "";
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
        initializeStage();
    }    

    private void initializeData() {
        
    }
    
    private void initializeStage() {
        stageCrearSolicitud.addEventFilter(KeyEvent.KEY_PRESSED, event -> {     //addActionToStage();
            if (event.getCode() == KeyCode.ESCAPE){
                event.consume();
                closeWindow((Stage) this.tfNombre.getScene().getWindow());
            }
        });     
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

    private boolean validateFullFields() {      //faltanDatos ? return true : return false;
        if (tfNombre.getText().length() <= 0) {
            return true;
        }
        if (tfDescripcion.getText().length() <= 0) {
            return true;
        }
        if (tfRazon.getText().length() <= 0) {
            return true;
        }
        if (tfImpacto.getText().length() <= 0) {
            return true;
        }
        if (tfAccionPropuesta.getText().length() <= 0) {
            return true;
        }
        return false; 
    }
    
    @FXML
    private void clicAddSolicitud(ActionEvent event) {
        if (validateFullFields() == true) {
            ShowMessage.showMessage(
                "ERROR", 
                "Campos incompletos", 
                "Faltan campos por ingresar", 
                "Favor de ingresar datos faltantes e intente de nuevo"
            );
        } else {
            try {
                SolicitudCambio newSolicitudCambio = new SolicitudCambio(
                    0, 
                    this.tfNombre.getText(), 
                    this.tfDescripcion.getText(), 
                    this.tfRazon.getText(), 
                    this.tfImpacto.getText(), 
                    this.tfAccionPropuesta.getText(), 
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), 
                    null, 
                    idPadre
                );
                ResultOperation resultCreateSolicitud;
                if (fromDefecto == true) {
                    resultCreateSolicitud = SolicitudCambioDAO.createSolicitudCambioDefecto(newSolicitudCambio);
                } else {
                    resultCreateSolicitud = SolicitudCambioDAO.createSolicitudCambioProyecto(newSolicitudCambio);
                }
                
                if (resultCreateSolicitud.getIsError() == true) {
                    ShowMessage.showMessage(
                        "ERROR", 
                        "Error inesperado", 
                        resultCreateSolicitud.getMessage(), 
                        "Intente más tarde"
                    );
                } else {
                    ShowMessage.showMessage(
                            "INFORMATION", 
                            "Se ha creado correctamente", 
                            "Se creó con éxito la solicitud de cambio", 
                            ""
                    );
                    Stage currentStage = (Stage) this.tfNombre.getScene().getWindow();
                    currentStage.close();
                }
            } catch (SQLException sqlex) {
                System.err.println("\"Error de \"SQLException\" en archivo \"FXMLCrearSolicitudCambioController\" en método \"clicAddSolicitud\"");
                sqlex.printStackTrace();
            }
        }
    }

    @FXML
    private void clicCancelAdd(ActionEvent event) {
        closeWindow((Stage) this.tfNombre.getScene().getWindow());
    }
}
