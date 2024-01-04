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


public class FXMLAsignarActividadController implements Initializable {
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
    private ObservableList<Desarrollador> desarrolladores = FXCollections.observableArrayList();
    ToggleGroup tgAsignado = new ToggleGroup();
    private int numRadio = 0;
    private int radioSelected = 0;
    
    
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
            for (Desarrollador item : desarrolladores) {
                if (item.getIdDesarrollador() == idDesarrolladorAsignado) {
                    item.setIsAsignado(true);
                    break;
                }
            }
            this.desarrolladores = this.desarrolladores.sorted((desarrollador1, desarrollador2) -> 
                desarrollador1.getApellidoPaterno().compareTo(desarrollador2.getApellidoPaterno())
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
        this.tcAsignado.setCellValueFactory(new PropertyValueFactory("isAsignado"));
        this.tcAsignado.setCellFactory(col -> new TableCell<Desarrollador, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty == false) {
                    RadioButton radioButton = new RadioButton();
                        
                        radioButton.setToggleGroup(tgAsignado);
                        radioButton.setId(String.valueOf(numRadio));
                        radioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
                            if (newValue == true) {
                                radioSelected = Integer.parseInt(radioButton.getId());
                            }
                        });
                        if (item == true) {
                            radioButton.setSelected(true);
                        }
                    setGraphic(radioButton);
                    numRadio = numRadio+1;
                    System.out.println("        numRadio: "+numRadio);
                }
            }
        });
        
        pnPrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {     //addActionToStage();
            if (event.getCode() == KeyCode.ESCAPE){
                event.consume();
                closeWindow();
            }
        }); 
    }
    
    private int getNewDesarrolladorSelected() {
        
        return tvDesarrolladores.getItems().get(radioSelected-1).getIdDesarrollador();
    }
    
    private void saveChanges(int idActividad, int newIdDesarrollador) {
        try {
            ResultOperation resultAsignacion = ActividadDAO.setAsignacionActividad(idActividad, newIdDesarrollador);
            if (resultAsignacion.getIsError() == true) {
                showMessage(
                    "ERROR",
                    "Error inesperado",
                    "No se pudo asignar la actividad",
                    resultAsignacion.getMessage()
                );
            } else {
                showMessage(
                    "INFORMATION",
                    "Se han guardado los cambios correctamente",
                    "Se han guardado los cambios con éxito",
                    ""
                );
                closeWindow();
            }
        } catch (SQLException sqlex) {
            System.err.println("Error de \"SQLException\" en archivo \"AsignarActividadController\" en método \"saveChanges\"");
            sqlex.printStackTrace();
        }
    }
    
    @FXML
    private void clicSaveAsignacion(MouseEvent event) {
        //It wasn´t assigned and isn´t assigned
        if (idDesarrolladorAsignado <= 0 && tgAsignado.getSelectedToggle() == null) {
            Alert messageConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                messageConfirmation.setTitle("Sin asignar");
                messageConfirmation.setHeaderText("No seleccionó a un desarrollador");
                messageConfirmation.setContentText("No estaba asignada ¿Quiere guardar sin asignar?");
                messageConfirmation.showAndWait().ifPresent(response -> {
                    String responseMessage = response.getText();
                    if (responseMessage.equals("Aceptar")) {
                        closeWindow();
                    }
                });
        }
        //Assigned to the same developer
        if (idDesarrolladorAsignado == getNewDesarrolladorSelected() && tgAsignado.getSelectedToggle() != null) {
            Alert messageConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                messageConfirmation.setTitle("Sin cambios");
                messageConfirmation.setHeaderText("Ha seleccionado al mismo desarrollador");
                messageConfirmation.setContentText("¿No realizar cambios?");
                messageConfirmation.showAndWait().ifPresent(response -> {
                    String responseMessage = response.getText();
                    if (responseMessage.equals("Aceptar")) {
                        closeWindow();
                    }
                });
        }
        //Was assigned and isn´t assigned
        if (idDesarrolladorAsignado > 0 && tgAsignado.getSelectedToggle() == null) {
            Alert messageConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                messageConfirmation.setTitle("Des-asignar");
                messageConfirmation.setHeaderText("No seleccionó a un desarrollador");
                messageConfirmation.setContentText("YA ESTABA ASIGNADA LA ACTIVIDAD ¿Quiere guardar sin asignar?");
                messageConfirmation.showAndWait().ifPresent(response -> {
                    String responseMessage = response.getText();
                    if (responseMessage.equals("Aceptar")) {
                        saveChanges(idActividad, 0);
                    }
                });
        }
        //Was assigned and is assigned to another developer
        if (idDesarrolladorAsignado>0 && getNewDesarrolladorSelected()!= idDesarrolladorAsignado && tgAsignado.getSelectedToggle()!=null) {
            Alert messageConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                messageConfirmation.setTitle("Re-asignar a otro desarrollador");
                messageConfirmation.setHeaderText("Confirme RE-ASIGNACIÓN");
                messageConfirmation.setContentText("YA ESTABA ASIGNADA LA ACTIVIDAD ¿Quiere asignar la actividad a OTRO DESARROLLADOR?");
                messageConfirmation.showAndWait().ifPresent(response -> {
                    String responseMessage = response.getText();
                    if (responseMessage.equals("Aceptar")) {
                        saveChanges(idActividad, getNewDesarrolladorSelected());
                    }
                });
        }
        //Wasn´t assigned and is assigned
        if (idDesarrolladorAsignado<=0 && tgAsignado.getSelectedToggle()!=null) {
            saveChanges(idActividad, getNewDesarrolladorSelected());
        }
    }
    
    @FXML
    private void clicCleanAsignacion(MouseEvent event) {
        tgAsignado.selectToggle(null);
    }
    
    private void closeWindow() {
        Stage currenStage = (Stage) this.tvDesarrolladores.getScene().getWindow();
        currenStage.close();
    }
    
    private void closeWindowConfirmation() {
        Stage currenStage = (Stage) this.tvDesarrolladores.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¿Está seguro?");
        alert.setHeaderText("¿Está seguro de cancelar?");
        alert.setContentText("No se guardarán los cambios efectuados");


        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                currenStage.close();
            }
        });
    }
    
    @FXML
    private void clicCancelAsignacion(MouseEvent event) {
        closeWindowConfirmation();
    }
}
