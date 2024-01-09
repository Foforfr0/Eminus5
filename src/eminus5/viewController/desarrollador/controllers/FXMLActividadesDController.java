package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.DAO.ActividadDAO;
import eminus5.databaseManagment.model.POJO.Actividad;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessage;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import static eminus5.utils.loadView.loadScene;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Autor: Abraham Vazquez
 * Fecha de creacion: noviembre 2023
 * Ultima actualizacion: diciembre 25, 2023
 */
public class FXMLActividadesDController implements Initializable {
    
    @FXML
    private TableView<Actividad> tvActividades;
    @FXML
    private TableColumn<Actividad, String> tcNombreActividad;
    @FXML
    private TableColumn<Actividad, String> tcDescripcionActividad;
    
    
    public static int idUser = 0;
    private ObservableList<Actividad> actividades = FXCollections.observableArrayList();
    private Actividad actividadSeleccionada = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTable();
        cargarActividades();
    }    
    
    public void initializeTable() {
        this.tcNombreActividad.setCellValueFactory(new PropertyValueFactory("Nombre"));
        this.tcDescripcionActividad.setCellValueFactory(new PropertyValueFactory("Descripcion"));
        
        tvActividades.setRowFactory(tv -> {
            TableRow<Actividad> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    actividadSeleccionada = row.getItem();
                    try {
                        Stage stageActividad = new Stage();
                        FXMLDetallesActividadDController.currentActividad = actividadSeleccionada;
                        stageActividad.setScene(loadScene("viewController/desarrollador/views/FXMLDetallesActividadD.fxml"));
                        stageActividad.setTitle("Entregar actividad");
                        stageActividad.initModality(Modality.WINDOW_MODAL);
                        stageActividad.initOwner(
                            (Stage) this.tvActividades.getScene().getWindow()
                        );
                        stageActividad.initStyle(StageStyle.UTILITY);
                        stageActividad.showAndWait();
                    } catch (IOException ioex) {
                        System.err.println("Error de \"IOException\" en archivo \"FXMLActividadesDController\" en método \"btnEntregarActividad\"");
                        ioex.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
    public void cargarActividades() {
        try{
            ResultOperation resultGetActividades = ActividadDAO.getActividadesDesarrollador(idUser);
            
            if (resultGetActividades.getIsError() == true && resultGetActividades.getData()  == null
                || resultGetActividades.getNumberRowsAffected() <= 0) {
                showMessage(
                        "INFORMATION",
                        "Sin registros",
                        resultGetActividades.getMessage(),
                        "Intente mas tarde"
                );
            } else {
                this.actividades.clear();
                this.actividades = FXCollections.observableArrayList(
                    (ObservableList) ActividadDAO.getActividadesDesarrollador(idUser).getData()
                );
                this.tvActividades.getItems().clear();
                this.tvActividades.setItems(this.actividades);
            }
        } catch (SQLException sqlex) {
            showMessageFailureConnection();
            System.out.println("\"Error de \"SQLException\" en archivo \"FXMLActividadesDController\"");
            sqlex.printStackTrace();
        }
    }

    @FXML
    private void btnEntregarActividad(ActionEvent event) {
        if (verifySelectedActividad() != null) {
            try {
                Stage stageActividad = new Stage();
                FXMLDetallesActividadDController.currentActividad = verifySelectedActividad();
                stageActividad.setScene(loadScene("viewController/desarrollador/views/FXMLDetallesActividadD.fxml"));
                stageActividad.setTitle("Entregar actividad");
                stageActividad.initModality(Modality.WINDOW_MODAL);
                stageActividad.initOwner(
                        (Stage) this.tvActividades.getScene().getWindow()
                );
                stageActividad.initStyle(StageStyle.UTILITY);
                stageActividad.setOnCloseRequest(eventStage -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("¿Está seguro?");
                    alert.setHeaderText("¿Está seguro de cancelar?");
                    alert.setContentText("¿Ésta acción no se podrá revertir?");
                    
                    alert.showAndWait().ifPresent(response -> {
                        String responseMessage = response.getText();
                        if (responseMessage.equals("Aceptar")) {
                            stageActividad.close(); 
                        }
                    });
                });
                stageActividad.showAndWait();
                cargarActividades();
            } catch (IOException ioex) {
                System.err.println("Error de \"IOException\" en archivo \"FXMLActividadesDController\""
                        + "en metodo \"btnEntregarActividad\"");
                ioex.printStackTrace();
            }
        } else {
            showMessage(
                "WARNING",
                "Seleccion requerida",
                "Primero selecciona una actividad",
                "Elije una actividad para entregarla"
            );
        }
    }
    
    private Actividad verifySelectedActividad(){
        int selectedRow = this.tvActividades.getSelectionModel().getSelectedIndex();
        this.actividadSeleccionada = (selectedRow >= 0) ? this.actividades.get(selectedRow) : null;
        return actividadSeleccionada;
    }
}
