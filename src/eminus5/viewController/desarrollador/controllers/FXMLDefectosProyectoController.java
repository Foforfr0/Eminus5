/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.DAO.DefectoDAO;
import eminus5.databaseManagment.model.DAO.ProyectoDAO;
import eminus5.databaseManagment.model.DAO.SolicitudCambioDAO;
import eminus5.databaseManagment.model.POJO.Defecto;
import eminus5.databaseManagment.model.POJO.SolicitudCambio;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessage;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import static eminus5.utils.loadView.loadScene;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLDefectosProyectoController implements Initializable {
    @FXML
    private TableView<Defecto> tvDefectos;
    @FXML
    private TableColumn<Defecto, String> tcNombre;
    @FXML
    private TableColumn<Defecto, String> tcEstado;
    @FXML
    private TableColumn<Defecto, Integer> tcEsfuerzo;
    @FXML
    private TableColumn<Defecto, String> tcTipo;
    @FXML
    private TableColumn<Defecto, Button> tcSolCambio;
    
    
    public static int idUser = 0;
    private ObservableList<Defecto> defectos = FXCollections.observableArrayList();
    private Defecto selectedDefecto = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("IdUser controller: "+idUser);
        initializeData();
        initializeTable();
    }    
    
    private void initializeTable() {
        this.tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tcEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        this.tcEsfuerzo.setCellValueFactory(new PropertyValueFactory("esfurzoMin"));
        this.tcTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        this.tcSolCambio.setCellValueFactory(p -> {
            Button button = null;
            try {
                if (SolicitudCambioDAO.getSolicitudCambioDefecto(p.getValue().getIdDefecto()).getData() != null) {
                    button = new Button("Ver");
                    button.setOnMouseClicked(event -> {
                        try {
                            ResultOperation selectedSolicitud = SolicitudCambioDAO.getSolicitudCambioDefecto(p.getValue().getIdDefecto());
                            showDetallesSolicitud((SolicitudCambio) selectedSolicitud.getData());
                        } catch (SQLException sqlex) {
                            System.err.println("Error de \"SQLException\" en archivo \"FXMLDefectosProyectoController\" al agregar acción a botón");
                            sqlex.printStackTrace();
                        }
                        initializeData();
                    });
                } else {
                    button = new Button("Crear");
                    button.setOnMouseClicked(event -> {
                        createSolicitudCambio(p.getValue().getIdDefecto());
                        initializeData();
                    });
                }   
            } catch (SQLException sqlex) {
                System.err.println("Error de \"SQLException\" en archivo \"FXMLDefectosProyectoController\" al agregar acción a botón");
                sqlex.printStackTrace();
            }
            return new SimpleObjectProperty<>(button);
        });
        
        tvDefectos.setRowFactory(tv -> {
            TableRow<Defecto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    selectedDefecto = row.getItem();
                    try {
                        Stage stageDetailsDefecto = new Stage();
                        eminus5.viewController.desarrollador.controllers.FXMLDetallesDefectoController.currentDefecto = selectedDefecto;  
                        stageDetailsDefecto.setScene(loadScene("viewController/desarrollador/views/FXMLDetallesDefecto.fxml"));
                        stageDetailsDefecto.setTitle("Detalles de defecto");
                        stageDetailsDefecto.initModality(Modality.WINDOW_MODAL);
                        stageDetailsDefecto.initOwner(
                            (Stage) this.tvDefectos.getScene().getWindow()
                        );
                        stageDetailsDefecto.initStyle(StageStyle.UTILITY);
                        stageDetailsDefecto.showAndWait();
                    } catch (IOException ioex) {
                        System.err.println("Error de \"IOException\" en archivo \"FXMLDefectosProyectoController\" en método \"initializeTable\"");
                        ioex.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
    
    private void initializeData(){
        try{   
            this.defectos.clear();
            int idProyecto = ProyectoDAO.getProyectoUsuario(idUser).getNumberRowsAffected();
            ResultOperation resultGetDefectos = DefectoDAO.getDefectosProyecto(idProyecto);
        
            if (resultGetDefectos.getIsError() == true && resultGetDefectos.getData() == null || resultGetDefectos.getNumberRowsAffected() <= 0) {
                showMessage(
                    "ERROR", 
                    "Error inesperado", 
                    resultGetDefectos.getMessage(), 
                    "Intente más tarde"
                );
            } else {
                this.defectos.addAll((ObservableList<Defecto>) resultGetDefectos.getData());
                this.tvDefectos.setItems(this.defectos);
            }
        }catch(SQLException sqlex){
            showMessageFailureConnection();
            System.err.println("Error de \"SQLException\" en archivo \"FXMLDefectosProyectoController\" en método \"initializeData\"");
            sqlex.printStackTrace();
        }
    }
    
    private void showDetallesSolicitud (SolicitudCambio selectedSolicitud) {
        try {
            Stage stageDetailsSolicitudCambio = new Stage();
            eminus5.viewController.desarrollador.controllers.FXMLDetallesSolicitudCambioController.currentSolicitud = selectedSolicitud;
            stageDetailsSolicitudCambio.setScene(loadScene("viewController/desarrollador/views/FXMLDetallesSolicitudCambio.fxml"));
            stageDetailsSolicitudCambio.setTitle("Detalles de solicitud de cambio");
            stageDetailsSolicitudCambio.initModality(Modality.WINDOW_MODAL);
            stageDetailsSolicitudCambio.initOwner(
                (Stage) this.tvDefectos.getScene().getWindow()
            );
            stageDetailsSolicitudCambio.initStyle(StageStyle.UTILITY);
            stageDetailsSolicitudCambio.showAndWait();
        } catch (IOException ioex) {
            System.err.println("Error de \"IOException\" en archivo \"FXMLDefectosProyectoController\" en método \"showDetallesSolicitud\"");
            ioex.printStackTrace();
        }
    }
    
    private void createSolicitudCambio(int idDefecto) {
        try {
            Stage clicAddSolicitud = new Stage();
            FXMLCrearSolicitudCambioController.idDefecto = idDefecto;
            
            clicAddSolicitud.setScene(loadScene("viewController/desarrollador/views/FXMLCrearSolicitudCambio.fxml"));
            clicAddSolicitud.setTitle("Crear solicitud de cambio");
            clicAddSolicitud.initModality(Modality.WINDOW_MODAL);
            clicAddSolicitud.initOwner(
                (Stage) this.tvDefectos.getScene().getWindow()
            );
            clicAddSolicitud.initStyle(StageStyle.UTILITY);
            clicAddSolicitud.setOnCloseRequest(eventStage -> {
                eventStage.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("¿Está seguro?");
                alert.setHeaderText("¿Está seguro de cancelar?");
                alert.setContentText("¿Ésta acción no se podrá revertir?");


                alert.showAndWait().ifPresent(response -> {
                    String responseMessage = response.getText();
                    if (responseMessage.equals("Aceptar")) {
                        clicAddSolicitud.close(); 
                    }
                });
            });
            clicAddSolicitud.showAndWait();
            initializeData();
        } catch (IOException ioex) {
            System.err.println("Error de \"IOException\" en archivo \"FXMLActividadesProyectoController\" en método \"clicAddActividad\"");
            ioex.printStackTrace();
        } 
    }
    
    @FXML
    private void clicAddSolicitud(MouseEvent event) {
    }

    @FXML
    private void clicAddDefecto(MouseEvent event) {
    }

    @FXML
    private void clicModifyDefecto(MouseEvent event) {
    }
}
