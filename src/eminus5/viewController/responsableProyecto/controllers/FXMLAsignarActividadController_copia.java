/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController.responsableProyecto.controllers;

import eminus5.databaseManagment.model.DAO.ActividadDAO;
import eminus5.databaseManagment.model.DAO.DesarrolladorDAO;
import eminus5.databaseManagment.model.POJO.Desarrollador;
import eminus5.databaseManagment.model.ResultOperation;
import static eminus5.utils.ShowMessage.showMessage;
import static eminus5.utils.ShowMessage.showMessageFailureConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class FXMLAsignarActividadController_copia implements Initializable {
    @FXML
    private Pane pnPrincipal;
    @FXML
    private TableView<Desarrollador> tvDesarrolladores;
    @FXML
    private TableColumn<Desarrollador, Integer> tcNumDesarrollador;
    @FXML
    private TableColumn<Desarrollador, String> tcApellidoPaterno;
    @FXML
    private TableColumn<Desarrollador, String> tcApellidoMaterno;
    @FXML
    private TableColumn<Desarrollador, String> tcNombre;
    @FXML
    private TableColumn<Desarrollador, String> tcMatricula;
    @FXML
    private TableColumn<Desarrollador, String> tcCorreoInstitucional;
    @FXML
    private TableColumn<Desarrollador, String> tcSemestre;
    @FXML
    private TableColumn<Desarrollador, Boolean> tcAsignado;

    
    public static int idProyecto = 0;
    public static int idActividad = 0;
    public static int idDesarrolladorAsignado = 0;
    public static int numDesarrolladores = 0;
    private ObservableList<Desarrollador> desarrolladores = FXCollections.observableArrayList();
    ToggleGroup tgAsignado = new ToggleGroup();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeStage();
        initializeData();
    }        
    
    private void initializeData() {
        try{  
            this.desarrolladores = FXCollections.observableArrayList(
                (ObservableList) DesarrolladorDAO.getDesarrolladoresProyecto(idProyecto).getData()
            );
            this.tvDesarrolladores.setItems(this.desarrolladores);
        }catch(SQLException sqlex){
            showMessageFailureConnection();
            System.err.println("Error de \"SQLException\" en archivo \"FXMLAsignarActividadController\" en método \"initializeData\"");
            sqlex.printStackTrace();
        }
    }
    
    private void initializeStage() {
        this.tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        this.tcApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        this.tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tcMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        this.tcCorreoInstitucional.setCellValueFactory(new PropertyValueFactory("correoInstitucional"));
        this.tcSemestre.setCellValueFactory(new PropertyValueFactory("semestre"));
        
        this.tcNumDesarrollador.setCellFactory(col -> new TableCell<Desarrollador, Integer>() {
            @Override       
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty == false) {
                    setText(Integer.toString(getIndex() + 1));
                }
            }
        });

        this.tcAsignado.setCellFactory(col -> new TableCell<Desarrollador, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty == false) {
                    numDesarrolladores++;
                    RadioButton radioButton = new RadioButton();
                    radioButton.setToggleGroup(tgAsignado);
                    if (numDesarrolladores == idDesarrolladorAsignado) {
                        radioButton.setSelected(true);
                    }
                    setGraphic(radioButton);
                }
            }
        });
        
        pnPrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {     //addActionToStage();
            if (event.getCode() == KeyCode.ESCAPE){
                event.consume();
                closeWindow((Stage) this.tvDesarrolladores.getScene().getWindow());
            }
        }); 
    }
    
    private int getIdDesarrolladorSelected() {
        for (int i = 1; i < tvDesarrolladores.getItems().size(); i++) {
            if (this.tcAsignado.getCellData(i) == true) {
                System.out.println("        "+this.tcAsignado.getCellData(i));
                return tvDesarrolladores.getItems().get(i).getIdDesarrollador();
            }
        }
        return 0;
    }
    
    @FXML
    private void clicSaveAsignacion(MouseEvent event) {
        System.out.println("        ToggleSelect: "+tgAsignado.getSelectedToggle());
        System.out.println("    Desarrollador seleccionado: "+getIdDesarrolladorSelected());
        
        if (tgAsignado.getSelectedToggle()==null && idDesarrolladorAsignado<=0) {
            closeWindow((Stage) this.tvDesarrolladores.getScene().getWindow());
        }
        if (tgAsignado.getSelectedToggle()==null && idDesarrolladorAsignado>0) {
            Alert messageConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            messageConfirmation.setTitle("Actividad ya asignada");
            messageConfirmation.setHeaderText("La actividad ya estaba asignada");
            messageConfirmation.setContentText("¿Desasignar la actividad por completo?");
            messageConfirmation.showAndWait().ifPresent(response -> {
                if (response.getText().equals("Aceptar")) {
                    try {
                        int newDesarrollador = getIdDesarrolladorSelected();
                        if (newDesarrollador <= 0) {
                            showMessage(
                                "ERROR", 
                                "Error inesperado", 
                                "Desarrollador no encontrado", 
                                ""
                            );
                        } else {
                            ResultOperation resultAsignacion = ActividadDAO.setAsignacionActividad(idActividad, newDesarrollador);
                            if (resultAsignacion.getIsError() == true) {
                                showMessage(
                                    "ERROR", 
                                    "Error inesperado", 
                                    resultAsignacion.getMessage(), 
                                    "Intente más tarde"
                                );
                            } else {
                                showMessage(
                                    "INFORMATION", 
                                    "Se ha asignado correctamente", 
                                    "Se ha asignado la actividad correctamente", 
                                    ""
                                );
                                closeWindow((Stage) this.tvDesarrolladores.getScene().getWindow());
                            }
                        }
                    } catch (SQLException sqlex) {
                        System.err.println("Error de \"SQLException\" en archivo \"FXMLAsignarActividadController\" en método \"clicSaveAsignacion\"");
                        sqlex.printStackTrace();
                    }
                }
            });
        }
    }
    
    @FXML
    private void clicCleanAsignacion(MouseEvent event) {
        tgAsignado.selectToggle(null);
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
    private void clicCancelAsignacion(MouseEvent event) {
        closeWindow((Stage) this.tvDesarrolladores.getScene().getWindow());
    }
}
