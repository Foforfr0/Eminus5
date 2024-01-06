/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.DAO.ProyectoDAO;
import eminus5.databaseManagment.model.DAO.SolicitudCambioDAO;
import eminus5.databaseManagment.model.POJO.SolicitudCambio;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.loadView.loadScene;
import static eminus5.utils.ShowMessage.showMessage;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLSolicitudesCambioProyectoController implements Initializable {
    @FXML
    private TableView<SolicitudCambio> tvSolicitudes;
    @FXML
    private TableColumn<SolicitudCambio, String> tcNombre;
    @FXML
    private TableColumn<SolicitudCambio, String> tcFechaCreacion;
    @FXML
    private TableColumn<SolicitudCambio, String> tcFechaAceptada;
    
    
    public static int idUser = 0;
    private ObservableList<SolicitudCambio> solicitudes = FXCollections.observableArrayList();
    private SolicitudCambio selectedSolicitud = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
        initializeTable();
    }    
    
    private void initializeData() {
        try{   
            this.solicitudes.clear();
            int idProyecto = ProyectoDAO.getProyectoUsuario(idUser).getNumberRowsAffected();
            ResultOperation resultGetSolicitudes = SolicitudCambioDAO.getSolicitudesCambioProyecto(idProyecto);
        
            if (resultGetSolicitudes.getIsError() == true && resultGetSolicitudes.getData() == null || resultGetSolicitudes.getNumberRowsAffected() <= 0) {
                showMessage(
                    "ERROR", 
                    "Error inesperado", 
                    resultGetSolicitudes.getMessage(), 
                    "Intente más tarde"
                );
            } else {
                this.solicitudes.addAll((ObservableList<SolicitudCambio>) resultGetSolicitudes.getData());
                this.tvSolicitudes.setItems(this.solicitudes);
            }
        }catch(SQLException sqlex){
            showMessageFailureConnection();
            System.err.println("Error de \"SQLException\" en archivo \"FXMLSolicitudesCambioProyectoController\" en método \"initializeData\"");
            sqlex.printStackTrace();
        }
    }
    
    private void initializeTable() {
        this.tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tcFechaCreacion.setCellValueFactory(new PropertyValueFactory("fechaCreacion"));
        this.tcFechaAceptada.setCellValueFactory(new PropertyValueFactory("fechaAceptada"));
        this.tvSolicitudes.setRowFactory(tv -> {
            TableRow<SolicitudCambio> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    selectedSolicitud = row.getItem();
                    try {
                        Stage stageDetailsSolicitud = new Stage();
                        eminus5.viewController.desarrollador.controllers.FXMLDetallesSolicitudCambioController.currentSolicitud = selectedSolicitud;
                        stageDetailsSolicitud.setScene(loadScene("viewController/desarrollador/views/FXMLDetallesSolicitudCambio.fxml"));
                        stageDetailsSolicitud.setTitle("Detalles de solicitud de cambio");
                        stageDetailsSolicitud.initModality(Modality.WINDOW_MODAL);
                        stageDetailsSolicitud.initOwner(
                            (Stage) this.tvSolicitudes.getScene().getWindow()
                        );
                        stageDetailsSolicitud.initStyle(StageStyle.UTILITY);
                        stageDetailsSolicitud.showAndWait();
                    } catch (IOException ioex) {
                        System.err.println("Error de \"IOException\" en archivo \"FXMLSolicitudesCambioProyectoController\" en método \"initializeTable\"");
                        ioex.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
}
