/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.responsableProyecto.controllers;

import eminus5.databaseManagment.model.DAO.DesarrolladorDAO;
import eminus5.databaseManagment.model.POJO.Actividad;
import eminus5.databaseManagment.model.POJO.Desarrollador;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessage;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class FXMLDetallesActividadController implements Initializable {
    @FXML
    private AnchorPane stageCrearActividad;
    @FXML
    private Pane pnPrincipal;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbDescripcion;
    @FXML
    private Label lbTipo;
    @FXML
    private Label lbFechaInicio;
    @FXML
    private Label lbFechaFin;
    @FXML
    private Button btAceptar;
    @FXML
    private Label lbAsignado;
    
    
    public static Actividad currentActividad = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
        initializeStage();
    }    
    
    private void initializeData(){
        this.lbNombre.setText(currentActividad.getNombre());
        this.lbDescripcion.setText(currentActividad.getDescripcion());
        this.lbTipo.setText(currentActividad.getTipo());
        this.lbFechaInicio.setText(currentActividad.getFechaInicio());
        this.lbFechaFin.setText(currentActividad.getFechaFin());

        if (currentActividad.getIdDesarrollador() <= 0) {
            this.lbAsignado.setText("No ha asignado la actividad todavía.");
        } else {
            try {
                ResultOperation resultGetDesarrollador = DesarrolladorDAO.getDesarrollador(currentActividad.getIdDesarrollador());
                if (resultGetDesarrollador.getIsError() == true) {
                    showMessage(
                        "ERROR", 
                        "Error inesperado", 
                        "No se ha podido encontrar el desarrollador",
                        "Intente mas tarde"
                    );
                } else {
                    Desarrollador asigned = (Desarrollador) resultGetDesarrollador.getData();
                    this.lbAsignado.setText(asigned.getNombre()+"\n"+asigned.getApellidoPaterno()+"\n"+asigned.getApellidoMaterno()+"\n");
                }
            } catch (SQLException sqlex) {
                System.err.println("\"Error de \"SQLException\" en archivo \"FXMLDetallesActividadController\" en método \"DesarrolladorDAO.getDesarrollador\"\"");
                sqlex.printStackTrace();
            }
        }
    }
    
    private void initializeStage() {
        stageCrearActividad.addEventFilter(KeyEvent.KEY_PRESSED, event -> {     //addActionToStage();
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
