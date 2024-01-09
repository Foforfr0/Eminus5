package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.DAO.CambioDAO;
import eminus5.databaseManagment.model.POJO.Cambio;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author abrah
 */
public class FXMLFormularioModCambioController implements Initializable {
    @FXML
    private TextField tfTituloCambio;
    @FXML
    private TextArea tfDescCambio;
    @FXML
    private ComboBox<String> cbEstadoCambio;
    @FXML
    private ComboBox<String> cbTipoCambio;
    @FXML
    private DatePicker dpFechaInicioCambio;
    @FXML
    private DatePicker dpFechaFinCambio;
    @FXML
    private TextField tfEsfuerzo;

    
    public static Cambio currentCambio = null;
    public static int idUser = 0;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
    }    

    private void initializeData() {
        this.cbEstadoCambio.getItems().setAll(
            "Iniciado",
            "Entregado"
        );
        
        tfEsfuerzo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfEsfuerzo.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        this.tfTituloCambio.setText(currentCambio.getNombre());
        this.tfDescCambio.setText(currentCambio.getDescripcion());
        this.cbEstadoCambio.setValue(currentCambio.getEstado());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(currentCambio.getFechaInicio(), formatter);
            this.dpFechaInicioCambio.setValue(localDate);
        
        this.cbTipoCambio.setValue(currentCambio.getTipo());
        
        this.dpFechaFinCambio.setDayCellFactory(picker -> new DateCell(){
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });
        
        this.tfTituloCambio.setDisable(true);
        this.tfDescCambio.setDisable(true);
        this.cbTipoCambio.setDisable(true);
        this.dpFechaInicioCambio.setDisable(true);
    }
    
    private boolean validateFields() {
        if (dpFechaFinCambio.getValue() == null || tfEsfuerzo.getText().trim().isEmpty()) {
        return true;
        }

        try {
            Integer.parseInt(tfEsfuerzo.getText().trim());
        } catch (NumberFormatException e) {
            return true; 
        }

        return false;
    }
    
    @FXML
    private void btnGuardarCambio(ActionEvent event) {
        if (validateFields() == true) {
            ShowMessage.showMessage(
                "ERROR",
                "Campos incompletos",
                "Faltan campos por ingresar",
                "Ingrese los datos faltantes"
            );
        } else {
            try {
                Cambio newCambio = new Cambio();
                    newCambio.setIdCambio(currentCambio.getIdCambio());
                    newCambio.setEstado(this.cbEstadoCambio.getValue());
                    newCambio.setFechaFin(this.dpFechaFinCambio.getValue()
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    newCambio.setEsfuerzo(Integer.parseInt(this.tfEsfuerzo.getText()));
                    
                ResultOperation resultModify = CambioDAO.modificarCambio(newCambio);
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
                        "Se modifico con exito",
                        ""
                    );
                    Stage currentStage = (Stage) this.tfTituloCambio.getScene().getWindow();
                    currentStage.close();
                }
            } catch (SQLException sqlex) {
                System.err.println("\"Error de \"SQLException\" en archivo \"FXMLFormularioModCambio\" en método \"modificarCambio\"");
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
    private void btnCancelarCambio(ActionEvent event) {
        closeWindow((Stage) this.tfTituloCambio.getScene().getWindow());
    }
    
    private boolean revisarEstado(Cambio newCambio) {
        return !newCambio.getEstado().equals(currentCambio.getEstado());
    }
}
