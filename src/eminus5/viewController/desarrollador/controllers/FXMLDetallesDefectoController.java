/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.POJO.Defecto;
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


public class FXMLDetallesDefectoController implements Initializable {
    @FXML
    private AnchorPane stageDetallesDefecto;
    @FXML
    private Pane pnPrincipal;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbTipo;
    @FXML
    private Button btAceptar;
    @FXML
    private Label lbDescripcion;
    @FXML
    private Label lbEsfuerzo;
    @FXML
    private Label lbFechaEncontrado;
    @FXML
    private Label lbFechaSolucionado;
    
    
    public static Defecto currentDefecto = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
        initializeStage();
    }    

    private void initializeData() {
        this.lbNombre.setText(currentDefecto.getNombre());
        this.lbDescripcion.setText(currentDefecto.getDescripcion());
        this.lbEsfuerzo.setText(String.valueOf(currentDefecto.getEsfuerzoMin())+" minutos");
        this.lbTipo.setText(currentDefecto.getTipo());
        this.lbFechaEncontrado.setText(currentDefecto.getFechaEncontrado());
        //this.lbFechaSolucionado.setText(currentDefecto.getFechaSolucionado().equals("") ? "Sin solucionar" : currentDefecto.getFechaSolucionado());
        if (currentDefecto.getFechaSolucionado() == null) {
            this.lbFechaSolucionado.setText("Sin solucionar");
        } else {
            this.lbFechaSolucionado.setText(String.valueOf(currentDefecto.getFechaSolucionado()));
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
