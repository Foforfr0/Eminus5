package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.DAO.DefectoDAO;
import eminus5.databaseManagment.model.POJO.Defecto;
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
public class FXMLFormularioModDefectoController implements Initializable {

    @FXML
    private TextField tfTituloDefecto;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private ComboBox<String> cbEstadoDefecto;
    @FXML
    private ComboBox<String> cbTipoDefecto;
    @FXML
    private DatePicker dpFechaEncontrado;
    @FXML
    private DatePicker dpFechaSolucionado;
    @FXML
    private TextField tfEsfuerzo;

    
    public static Defecto currentDefecto = null; 
    public static int idUser = 0;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
    }   
    
    private void initializeData() {
        this.cbEstadoDefecto.getItems().setAll(
            "Iniciado",
            "Entregado"
        );
        
        tfEsfuerzo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfEsfuerzo.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        this.tfTituloDefecto.setText(currentDefecto.getNombre());
        this.taDescripcion.setText(currentDefecto.getDescripcion());
        this.cbEstadoDefecto.setValue(currentDefecto.getEstado());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(currentDefecto.getFechaEncontrado(), formatter);
            this.dpFechaEncontrado.setValue(localDate);
        this.cbTipoDefecto.setValue(currentDefecto.getTipo());
        
        this.dpFechaSolucionado.setDayCellFactory(picker -> new DateCell(){
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });
        this.tfTituloDefecto.setDisable(true);
        this.taDescripcion.setDisable(true);
        this.cbTipoDefecto.setDisable(true);
        this.dpFechaEncontrado.setDisable(true);
    }
    
    private boolean validateFields() {
        if (dpFechaSolucionado.getValue() == null || tfEsfuerzo.getText().trim().isEmpty()) {
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
    private void btnGuardarDefecto(ActionEvent event) {
        if (validateFields() == true) {
            ShowMessage.showMessage(
                "ERROR",
                "Campos incompletos",
                "Faltan campos por ingresar",
                "Ingrese los datos faltantes"
            );
        } else {
            try {
                Defecto newDefecto = new Defecto();
                    newDefecto.setIdDefecto(currentDefecto.getIdDefecto());
                    newDefecto.setEsfuerzoMin(Integer.parseInt(this.tfEsfuerzo.getText()));
                    newDefecto.setEstado(this.cbEstadoDefecto.getValue());
                    newDefecto.setFechaSolucionado(this.dpFechaSolucionado.getValue().
                            format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    
                ResultOperation resultModify = DefectoDAO.modificarDefecto(newDefecto);
                if (resultModify.getIsError() == true) {
                    ShowMessage.showMessage(
                        "Error",
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
                    Stage currentStage = (Stage) this.tfTituloDefecto.getScene().getWindow();
                    currentStage.close();
                }
            } catch (SQLException sqlex) {
                System.err.println("\"Error de \"SQLException\" en archivo \"FXMLFormularioModDefecto\" en método \"modificarDefecto\"");
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
    private void btnCancelarDefecto(ActionEvent event) {
        closeWindow((Stage) this.tfTituloDefecto.getScene().getWindow());
    }
    
}
