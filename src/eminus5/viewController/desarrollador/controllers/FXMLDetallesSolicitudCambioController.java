/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.POJO.SolicitudCambio;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class FXMLDetallesSolicitudCambioController implements Initializable {

    @FXML
    private AnchorPane stageDetallesDefecto;
    @FXML
    private Pane pnPrincipal;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbDescripcion;
    @FXML
    private Label lbRazon;
    @FXML
    private Label lbImpacto;
    @FXML
    private Label lbAccionPropuesta;
    @FXML
    private Label lbFechaCreacion;
    @FXML
    private Label lbFechaAceptada;
    @FXML
    private Button btAceptar;

    
    public static SolicitudCambio currentSolicitud = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
        initializeStage();
    }    
    
    private void initializeData() {
        this.lbNombre.setText(currentSolicitud.getNombre());
        this.lbDescripcion.setText(currentSolicitud.getDescripcion());
        this.lbRazon.setText(currentSolicitud.getRazon());
        this.lbImpacto.setText(currentSolicitud.getImpacto());
        this.lbAccionPropuesta.setText(currentSolicitud.getAccionPropuesta());
        this.lbFechaCreacion.setText(currentSolicitud.getFechaCreacion());
        //this.lbFechaAceptada.setText(currentSolicitud.getFechaAceptada().equals("") ? "Sin aceptar" : currentSolicitud.getFechaAceptada());
        if (currentSolicitud.getFechaAceptada() == null) {
            this.lbFechaAceptada.setText("Sin respuesta");
        } else {
            this.lbFechaAceptada.setText(currentSolicitud.getFechaAceptada());
        }
    }
    
    private void initializeStage() {
        stageDetallesDefecto.addEventFilter(KeyEvent.KEY_PRESSED, event -> {     //addActionToStage();
            if (event.getCode() == KeyCode.ESCAPE){
                event.consume();
                Stage current = (Stage) this.pnPrincipal.getScene().getWindow();
                current.close();
            }
        });
    }
    
    @FXML
    private void clicAceptar(ActionEvent event) {
        Stage currentStage = (Stage) this.pnPrincipal.getScene().getWindow();
        currentStage.close();
    }
}
