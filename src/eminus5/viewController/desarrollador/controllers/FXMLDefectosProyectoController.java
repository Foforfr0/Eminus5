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
import javafx.beans.property.ReadOnlyStringWrapper;
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
    private TableColumn<Defecto, String> tcEsfuerzo;
    @FXML
    private TableColumn<Defecto, String> tcTipo;
    @FXML
    private TableColumn<Defecto, Button> tcSolCambio;
    @FXML
    private Button btModificarDefecto;
    
    
    public static int idUser = 0;
    private ObservableList<Defecto> defectos = FXCollections.observableArrayList();
    private Defecto selectedDefecto = null;
    private Defecto defectoSeleccionado = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTable();
        initializeData();
    }    
    
    private void initializeTable() {
    this.tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tcEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        this.tcEsfuerzo.setCellValueFactory(dataCell -> {
            return new ReadOnlyStringWrapper(String.valueOf(dataCell.getValue().getEsfuerzoMin()) + " min");
        });
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
        createSolicitudCambio(0);
    }

    @FXML
    private void clicAddDefecto(MouseEvent event) {
        try {
            Stage clicRegistrarDefecto = new Stage();
            FXMLFormularioDefectoController.idUser = idUser;
            clicRegistrarDefecto.setScene(loadScene("viewController/desarrollador/views/FXMLFormularioDefecto.fxml"));
            clicRegistrarDefecto.setTitle("Formulario defecto");
            clicRegistrarDefecto.initModality(Modality.WINDOW_MODAL);
            clicRegistrarDefecto.initOwner(
                    (Stage) this.tvDefectos.getScene().getWindow()
            );
            clicRegistrarDefecto.initStyle(StageStyle.UTILITY);
            clicRegistrarDefecto.setOnCloseRequest(eventStage -> {
                eventStage.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("¿Está seguro?");
                alert.setHeaderText("¿Está seguro de cancelar?");
                alert.setContentText("¿Ésta acción no se podrá revertir?");

                alert.showAndWait().ifPresent(response -> {
                    String responseMessage = response.getText();
                    if (responseMessage.equals("Aceptar")) {
                        clicRegistrarDefecto.close();
                    }
                });
            });
            clicRegistrarDefecto.showAndWait();
        
        } catch (IOException ioex) {
            System.err.println("Error de \"IOException\" en archivo \"FXMLDefectosDController\""
                    + " en método \"clicRegistrarDefecto\"");
            ioex.printStackTrace();
        }
    }

    @FXML
    private void clicModifyDefecto(MouseEvent event) {
        if (verifySelectedDefecto() != null) {
            this.btModificarDefecto.setVisible(true);
            try {
                Stage modificarDefecto = new Stage();
                FXMLFormularioModDefectoController.currentDefecto = verifySelectedDefecto();
                modificarDefecto.setScene(loadScene("viewController/desarrollador/views/FXMLFormularioModDefecto.fxml"));
                modificarDefecto.setTitle("Modificar defecto");
                modificarDefecto.initModality(Modality.WINDOW_MODAL);
                modificarDefecto.initOwner(
                        (Stage) this.tvDefectos.getScene().getWindow()
                );
                modificarDefecto.initStyle(StageStyle.UTILITY);
                modificarDefecto.setOnCloseRequest(eventStage -> {
                    eventStage.consume();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("¿Está seguro?");
                    alert.setHeaderText("¿Está seguro de cancelar?");
                    alert.setContentText("¿Ésta acción no se podrá revertir?");
                    
                    alert.showAndWait().ifPresent(response -> {
                        String responseMessage = response.getText();
                        if (responseMessage.equals("Aceptar")) {
                            modificarDefecto.close();
                        }
                    });
                });
                modificarDefecto.showAndWait();
                cargarDefectos();
            } catch (IOException ioex) {
                System.err.println("Error de \"IOException\" en archivo \"FXMLFormularioModDefecto\" en método \"btModificarDefecto\"");
                ioex.printStackTrace();
            }
        } else {
            showMessage(
                "WARNING",
                "Seleccion requerida",
                "Primero selecciona un defecto",
                "Elije un defecto para modificarlo"
            );
        }
    }
        
    public void cargarDefectos() {
        try{
            ResultOperation resultGetProyecto = ProyectoDAO.getProyectoUsuario(idUser);

            if(resultGetProyecto.getIsError() == true && resultGetProyecto.getData() == null ||
               resultGetProyecto.getNumberRowsAffected() <= 0){
                showMessage(
                        "ERROR",
                        "Error inesperado",
                        resultGetProyecto.getMessage(),
                        "Intente mas tarde"
                );
            } else {
                System.out.println("Rows affected: " + resultGetProyecto.getNumberRowsAffected());

                Object data = DefectoDAO.getDefectosDesarrollador(resultGetProyecto.getNumberRowsAffected()).getData();
                System.out.println("Data from DAO: " + data);

                if (data instanceof ObservableList) {
                    this.defectos = (ObservableList<Defecto>) data;
                    this.tvDefectos.setItems(this.defectos);
                }
                /*this.defectos = FXCollections.observableArrayList(
                        (ObservableList) DefectoDAO.getDefectosDesarrollador(resultGetProyecto.getNumberRowsAffected()).getData()
                );
                this.tvDefectos.setItems(this.defectos);
                */
            }
        } catch (SQLException sqlex) {
            showMessageFailureConnection();
           System.out.println("\"Error de \"SQLException\" en archivo \"FXMLDefectosDController\"");
           sqlex.printStackTrace();
        }
    }
        
    private Defecto verifySelectedDefecto(){
        int selectedRow = this.tvDefectos.getSelectionModel().getSelectedIndex();
        this.defectoSeleccionado = (selectedRow >= 0) ? this.defectos.get(selectedRow) : null;
        return defectoSeleccionado;
    }

    @FXML
    private void clicGetSolicitudes(MouseEvent event) {
        try {
            Stage stageConsultSolicitudes = new Stage();
            FXMLSolicitudesCambioProyectoController.idUser = idUser;
            stageConsultSolicitudes.setScene(loadScene("viewController/desarrollador/views/FXMLSolicitudesCambioProyecto.fxml"));
            stageConsultSolicitudes.setTitle("Solicitudes de cambio");
            stageConsultSolicitudes.initModality(Modality.WINDOW_MODAL);
            stageConsultSolicitudes.initOwner(
                (Stage) this.tvDefectos.getScene().getWindow()
            );
            stageConsultSolicitudes.initStyle(StageStyle.UTILITY);
            stageConsultSolicitudes.showAndWait();
            cargarDefectos();
        } catch (IOException ioex) {
            System.err.println("Error de \"IOException\" en archivo \"FXMLFormularioModDefecto\" en método \"btModificarDefecto\"");
            ioex.printStackTrace();
        }
    }
}
