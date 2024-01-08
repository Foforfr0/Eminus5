    /*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.responsableProyecto.controllers;

import eminus5.databaseManagment.model.DAO.ActividadDAO;
import eminus5.databaseManagment.model.DAO.ProyectoDAO;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class FXMLActividadesProyectoController implements Initializable {
    @FXML
    private TableView<Actividad> tvActividades;
    @FXML
    private TableColumn<Actividad, String> tcNombre;
    @FXML
    private TableColumn<Actividad, String> tvAsignado;
    @FXML
    private TableColumn<Actividad, String> tcEstado;
    @FXML
    private TableColumn<Actividad, String> tcTipo;
    @FXML
    private TableColumn<Actividad, String> tcFechaInicio;
    @FXML
    private TableColumn<Actividad, String> tcFechaFin;
    
    
    public static int idResponsable = 0;
    private ObservableList<Actividad> actividades = FXCollections.observableArrayList();
    private Actividad selectedActividad = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTable();
        initializeData();
    }    
    
    public void initializeTable() {
        this.tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tvAsignado.setCellValueFactory(new PropertyValueFactory("isAsignado"));
        this.tcEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        this.tcTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        this.tcFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        this.tcFechaFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
        
        tvActividades.setRowFactory(tv -> {
            TableRow<Actividad> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    selectedActividad = row.getItem();
                    try {
                        Stage stageDetailsActividad = new Stage();
                        FXMLDetallesActividadController.currentActividad = selectedActividad;
                        stageDetailsActividad.setScene(loadScene("viewController/responsableProyecto/views/FXMLDetallesActividad.fxml"));
                        stageDetailsActividad.setTitle("Detalles de actividad");
                        stageDetailsActividad.initModality(Modality.WINDOW_MODAL);
                        stageDetailsActividad.initOwner(
                            (Stage) this.tvActividades.getScene().getWindow()
                        );
                        stageDetailsActividad.initStyle(StageStyle.UTILITY);
                        stageDetailsActividad.showAndWait();
                    } catch (IOException ioex) {
                        System.err.println("Error de \"IOException\" en archivo \"FXMLActividadesProyectoController\" en método \"clicModifyActividad\"");
                        ioex.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
    
    public void initializeData() {
        try{            
            ResultOperation resultGetProyecto = ProyectoDAO.getProyectoUsuario(idResponsable);
        
            if (resultGetProyecto.getIsError() == true && resultGetProyecto.getData() == null || resultGetProyecto.getNumberRowsAffected() <= 0) {
                showMessage(
                    "ERROR", 
                    "Error inesperado", 
                    resultGetProyecto.getMessage(), 
                    "Intente más tarde"
                );
            } else {
                this.actividades = FXCollections.observableArrayList(
                    (ObservableList) ActividadDAO.getActividadesProyecto(resultGetProyecto.getNumberRowsAffected()).getData()
                );
                if (!actividades.isEmpty()) {
                    if (actividades.get(0).getNombre() == null) {
                        actividades.clear();
                    }
                }
                this.tvActividades.setItems(this.actividades);
            }
        }catch(SQLException sqlex){
            showMessageFailureConnection();
            System.err.println("Error de \"SQLException\" en archivo \"FXMLActividadesProyectoController\" en método \"initializeData\"");
            sqlex.printStackTrace();
        }
        
        this.tvActividades.getSortOrder().add(this.tcNombre);
    }
    
    @FXML
    private void clicAddActividad(MouseEvent event) {
        try {
            Stage clicAddActividad = new Stage();
            FXMLCrearActividadController.idResponsable = idResponsable;
            FXMLCrearActividadController.idProyecto = ProyectoDAO.getProyectoUsuario(idResponsable).getNumberRowsAffected();
            
            clicAddActividad.setScene(loadScene("viewController/responsableProyecto/views/FXMLCrearActividad.fxml"));
            clicAddActividad.setTitle("Crear actividad");
            clicAddActividad.initModality(Modality.WINDOW_MODAL);
            clicAddActividad.initOwner(
                (Stage) this.tvActividades.getScene().getWindow()
            );
            clicAddActividad.initStyle(StageStyle.UTILITY);
            clicAddActividad.setOnCloseRequest(eventStage -> {
                eventStage.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("¿Está seguro?");
                alert.setHeaderText("¿Está seguro de cancelar?");
                alert.setContentText("No se guardarán los cambios realizados");


                alert.showAndWait().ifPresent(response -> {
                    String responseMessage = response.getText();
                    if (responseMessage.equals("Aceptar")) {
                        clicAddActividad.close(); 
                    }
                });
            });
            clicAddActividad.showAndWait();
            initializeData();
        } catch (SQLException sqlex) {
            System.err.println("Error de \"SQLException\" en archivo \"FXMLActividadesProyectoController\" en método \"clicAddActividad\"");
            sqlex.printStackTrace();
        } catch (IOException ioex) {
            System.err.println("Error de \"IOException\" en archivo \"FXMLActividadesProyectoController\" en método \"clicAddActividad\"");
            ioex.printStackTrace();
        } 
    }

    @FXML
    private void clicModifyActividad(MouseEvent event) {
        if (verifyActividadSelected() != null) {
            try {
                Stage stageModifyActividad = new Stage();
                FXMLModificarActividadController.currentActividad = verifyActividadSelected();
                FXMLModificarActividadController.idResponsable = idResponsable;
                
                stageModifyActividad.setScene(loadScene("viewController/responsableProyecto/views/FXMLModificarActividad.fxml"));
                stageModifyActividad.setTitle("Modificar actividad");
                stageModifyActividad.initModality(Modality.WINDOW_MODAL);
                stageModifyActividad.initOwner(
                    (Stage) this.tvActividades.getScene().getWindow()
                );
                stageModifyActividad.initStyle(StageStyle.UTILITY);
                stageModifyActividad.setOnCloseRequest(eventStage -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("¿Está seguro?");
                    alert.setHeaderText("¿Está seguro de cancelar?");
                    alert.setContentText("No se guardarán los cambios realizados");
                        
                    alert.showAndWait().ifPresent(response -> {
                        String responseMessage = response.getText();
                        if (responseMessage.equals("Aceptar")) {
                            stageModifyActividad.close(); 
                        }
                    });
                });
                stageModifyActividad.showAndWait();
                initializeData();
            } catch (IOException ioex) {
                System.err.println("Error de \"IOException\" en archivo \"FXMLActividadesProyectoController\" en método \"clicModifyActividad\"");
                ioex.printStackTrace();
            } 
        } else {
            showMessage(
                "WARNING", 
                "Selección requerida", 
                "Primero selecciona una actividad", 
                "Eliga una actividad para poder modificarla"
            );
        }
    }
    
    private Actividad verifyActividadSelected(){
        int selectedRow = this.tvActividades.getSelectionModel().getSelectedIndex();
        this.selectedActividad = (selectedRow >= 0) ? this.actividades.get(selectedRow) : null ;
        return selectedActividad;
    }
    
    private void deleteActividad() {
        try {
            ResultOperation resultDelete = ActividadDAO.deleteActividad(
                verifyActividadSelected().getIdActividad()
            );

            if (resultDelete.getIsError() == false && resultDelete.getNumberRowsAffected() > 0) {
                showMessage(
                    "INFORMATION", 
                    "Se ha eliminado correctamente", 
                    "Se ha eliminado con éxito la actividad", 
                    ""
                );
                initializeData();
            } else if (resultDelete.getIsError() == true) {
                showMessage(
                    "ERROR", 
                    "Falló de eliminación", 
                    "Falló la eliminación de la actividad", 
                    "Intente más tarde"
                );
            }
        } catch (SQLException sqlex) {
            System.err.println("Error de \"SQLException\" en archivo \"FXMLActividadesProyectoController\" en método \"ActividadDAO.deleteActividad\"");
            sqlex.printStackTrace();
        }
    }
    
    @FXML
    private void clicDeleteActividad(MouseEvent event) {
        if (verifyActividadSelected() != null) {
            Alert confirmationDelete = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationDelete.setTitle("¿Está seguro de eliminar?");
                confirmationDelete.setHeaderText("¿Está seguro de eliminar la actividad?");
                confirmationDelete.setContentText("No se podrá revertir la eliminación");
                confirmationDelete.showAndWait().ifPresent(response -> {
                    String responseMessage = response.getText();
                    if (responseMessage.equals("Aceptar")) {
                        deleteActividad();
                    }
                });
        } else {
            showMessage(
                "WARNING", 
                "Selección requerida", 
                "Primero selecciona una actividad", 
                "Eliga una actividad para poder eliminarla"
            );
        }
    }

    @FXML
    private void clicAsignarActividad(MouseEvent event) {
        if (verifyActividadSelected() != null) {
            try {
                Stage stageAsignActividad = new Stage();
                FXMLAsignarActividadController.idProyecto = verifyActividadSelected().getIdProyecto();
                FXMLAsignarActividadController.idActividad = verifyActividadSelected().getIdActividad();
                FXMLAsignarActividadController.idDesarrolladorAsignado = verifyActividadSelected().getIdDesarrollador();
                
                stageAsignActividad.setScene(loadScene("viewController/responsableProyecto/views/FXMLAsignarActividad.fxml"));
                stageAsignActividad.setTitle("Asignar actividad");
                stageAsignActividad.initModality(Modality.WINDOW_MODAL);
                stageAsignActividad.initOwner(
                    (Stage) this.tvActividades.getScene().getWindow()
                );
                stageAsignActividad.initStyle(StageStyle.UTILITY);
                stageAsignActividad.showAndWait();
                initializeData();
            } catch (IOException ioex) {
                System.err.println("Error de \"IOException\" en archivo \"FXMLActividadesProyectoController\" en método \"clicModifyActividad\"");
                ioex.printStackTrace();
            }
        } else {
            showMessage(
                "WARNING", 
                "Selección requerida", 
                "Primero selecciona una actividad", 
                "Eliga una actividad para poder asignarla"
            );
        }
    }
}
