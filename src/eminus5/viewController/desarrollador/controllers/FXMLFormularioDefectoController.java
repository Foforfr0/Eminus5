package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.DAO.DefectoDAO;
import eminus5.databaseManagment.model.DAO.ProyectoDAO;
import eminus5.databaseManagment.model.POJO.Defecto;
import eminus5.databaseManagment.model.ResultOperation;
import eminus5.utils.ShowMessage;
import static eminus5.utils.ShowMessage.showMessage;
import static eminus5.utils.ConvertData.convertStringToLocalDate;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class FXMLFormularioDefectoController implements Initializable {

    @FXML
    private TextField tfTituloDefecto;
    @FXML
    private TextArea tfDescDefecto;
    @FXML
    private ComboBox<String> cbEstadoDefecto;
    @FXML
    private ComboBox<String> cbTipoDefecto;
    @FXML
    private TextField tfEsfuerzo;
    @FXML
    private DatePicker dpFechaEncontrado;

    
    public static int idUser = 0;
    public static Defecto currentDefecto = null;
    private String fechaInicio = "";
    private String fechaFin = "";
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeStage();
    }    

    private void initializeStage() {
        this.cbEstadoDefecto.getItems().setAll(
            "Iniciado",
            "Entregado"
        );
        
        this.cbTipoDefecto.getItems().setAll(
            "Backend",
            "Base de datos",
            "Controlador",
            "Frontend",
            "JavaScript"
        );
        
        this.tfEsfuerzo.setDisable(true);
        dpFechaEncontrado.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);

                setDisable(
                    date.isAfter(convertStringToLocalDate(fechaFin)) ||
                    date.isBefore(dpFechaEncontrado.getValue() == null ? LocalDate.now() : dpFechaEncontrado.getValue())
                );
            }
        });
    }
    
    private boolean validateFields() {
        if (tfTituloDefecto.getText().length() <= 0 || tfDescDefecto.getText().length() <= 0) {
            return true;
        }
        if (cbTipoDefecto.getValue() == null) {
            return true;
        }
        if (dpFechaEncontrado.getValue() == null) {
           return true; 
        }
        return false;
    }
    
    @FXML
    private void btnCancelarDefecto(ActionEvent event) {
        closeWindow((Stage) this.tfTituloDefecto.getScene().getWindow());
    }

    @FXML
    private void btnGuardarDefecto(ActionEvent event) {
        if (validateFields() == true) {
            ShowMessage.showMessage(
                "ERROR",
                "Campos incompletos",
                "Faltan datos por ingresar",
                "Por favor ingrese los datos faltantes"
            );
        } else {
            try {
                Defecto newDefecto = new Defecto();
                newDefecto.setNombre(this.tfTituloDefecto.getText());
                newDefecto.setDescripcion(this.tfDescDefecto.getText());
                newDefecto.setEstado(this.cbEstadoDefecto.getValue());
                newDefecto.setTipo(this.cbTipoDefecto.getValue());
                newDefecto.setFechaEncontrado(this.dpFechaEncontrado.getValue().format
                (DateTimeFormatter.ofPattern("dd-MM-yyy")));
                
                ResultOperation resultGetProyecto = ProyectoDAO.getProyectoUsuario(idUser);
                if (resultGetProyecto.getIsError() == true && resultGetProyecto.getData() == null
                    || resultGetProyecto.getNumberRowsAffected() <= 0) {
                    showMessage(
                        "ERROR", 
                        "Error inesperado", 
                        resultGetProyecto.getMessage(), 
                        "Intente más tarde"
                    );
                } else {
                    ResultOperation resultCreate = DefectoDAO.registrarDefecto(resultGetProyecto.getNumberRowsAffected(), newDefecto);
                    if (resultCreate.getIsError() == true) {
                        ShowMessage.showMessage(
                            "ERROR",
                            "Error inesperado",
                            resultCreate.getMessage(),
                            "Intente mas tarde"
                        );
                    } else {
                        ShowMessage.showMessage(
                            "INFORMATION", 
                            "Se ha creado correctamente", 
                            "Se registró con exito el defecto", 
                            ""
                        );
                        Stage currentStage = (Stage) this.tfTituloDefecto.getScene().getWindow();
                        currentStage.close();
                    }
                }
            } catch (SQLException sqlex) {
                System.err.println("\"Error de \"SQLException\" en archivo " + 
                                   "\"FXMLFormularioDefectoController\" en método \"registrarDefecto\"");
                sqlex.printStackTrace();
            }
        }
    }
    
    
    private void closeWindow(Stage currentStage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("¿Está seguro?");
        alert.setHeaderText("¿Está seguro de cancelar?");
        alert.setContentText("Ésta acción no se podrá revertir");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                currentStage.close();
            }
        });
    }
}
