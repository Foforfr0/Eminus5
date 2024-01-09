package eminus5.viewController.desarrollador.controllers;

import eminus5.databaseManagment.model.DAO.CambioDAO;
import eminus5.databaseManagment.model.POJO.Cambio;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLCambiosDController implements Initializable {
    @FXML
    private TableView<Cambio> tvCambios;
    @FXML
    private TableColumn<Cambio, String> tcNombreCambio;
    @FXML
    private TableColumn<Cambio, String> tcDescripcionCambio;
    @FXML
    private Button btModificarCambio;

    
    public static int idUser = 0;
    private ObservableList<Cambio> cambios = FXCollections.observableArrayList();
    private Cambio selectedCambio = null;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarTabla();
        cargarCambios();
    }    
    
    public void inicializarTabla() {
        this.tcNombreCambio.setCellValueFactory(new PropertyValueFactory("Nombre"));
        this.tcDescripcionCambio.setCellValueFactory(new PropertyValueFactory("Descripcion"));
        tvCambios.setRowFactory(tv -> {
            TableRow<Cambio> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && !row.isEmpty()) {
                    selectedCambio = row.getItem();
                    try {
                        Stage stageCambio = new Stage();
                        FXMLFormularioCambioController.currentCambio = selectedCambio;
                        stageCambio.setScene(loadScene("viewController/desarrollador/views/FXMLFormularioModCambio.fxml"));
                        stageCambio.setTitle("Cambio");
                        stageCambio.initModality(Modality.WINDOW_MODAL);
                        stageCambio.initOwner(
                            (Stage) this.tvCambios.getScene().getWindow()
                        );
                        
                        stageCambio.initStyle(StageStyle.UTILITY);
                        stageCambio.showAndWait();
                    } catch (IOException e) {
                        System.out.println("Error de \"IOException\" en archivo "
                                + "\"FXMLFormularioModCambioController\" en metodo \"btmodificarCambio\"");
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }
    
    public void cargarCambios() {
        try {
            ResultOperation resultGetCambios = CambioDAO.getCambios(idUser);
            
            if(resultGetCambios.getIsError() == true && resultGetCambios.getData() == null
                || resultGetCambios.getNumberRowsAffected() <= 0) {
                showMessage(
                    "ERROR", 
                    "Error inesperado",
                    resultGetCambios.getMessage(),
                    "Intente mas tarde"
                );
            } else {
                this.cambios = FXCollections.observableArrayList(
                    (ObservableList) CambioDAO.getCambios(idUser).getData()
                );
                this.tvCambios.setItems(this.cambios);
            }
        } catch (SQLException sqlex) {
            showMessageFailureConnection();
            System.out.println("\"Error de \"SQLException\" en archivo \"FXMLCambiosDController\"");
            sqlex.printStackTrace();
        }
    }
    
    @FXML
    private void btnRegistrarCambio(ActionEvent event) {
        try{
            Stage clicRegistrarCambio = new Stage();
            FXMLFormularioCambioController.idUser = idUser;
            clicRegistrarCambio.setScene(loadScene("viewController/desarrollador/views/FXMLFormularioCambio.fxml"));
            clicRegistrarCambio.setTitle("Formulario cambio");
            clicRegistrarCambio.initModality(Modality.WINDOW_MODAL);
            clicRegistrarCambio.initOwner(
                (Stage) this.tvCambios.getScene().getWindow()
            );
            clicRegistrarCambio.initStyle(StageStyle.UTILITY);
            clicRegistrarCambio.setOnCloseRequest(eventStage -> {
                eventStage.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("¿Está seguro?");
                alert.setHeaderText("¿Está seguro de cancelar?");
                alert.setContentText("¿Ésta acción no se podrá revertir?");
                
                alert.showAndWait().ifPresent(response ->{
                   String responseMessage = response.getText();
                   if (responseMessage.equals("Aceptar")) {
                       clicRegistrarCambio.close();
                   }
                });
            });
            clicRegistrarCambio.showAndWait();
            cargarCambios();
        } catch (IOException ioex) {
            System.err.println("Error de \"IOException\" en archivo \"FXMLActividadesProyectoController\""
                    + " en método \"clicRegistrarCambio\"");
            ioex.printStackTrace();
        }
    }

    @FXML
    private void btnModificarCambio(ActionEvent event) {
        if (verifySelectedCambio() != null) {
            this.btModificarCambio.setVisible(true);
            try {
                Stage clicModificarCambio = new Stage();
                FXMLFormularioModCambioController.currentCambio = verifySelectedCambio();
                clicModificarCambio.setScene(loadScene("viewController/desarrollador/views/FXMLFormularioModCambio.fxml"));
                clicModificarCambio.setTitle("Modificar cambio");
                clicModificarCambio.initModality(Modality.WINDOW_MODAL);
                clicModificarCambio.initOwner(
                    (Stage) this.tvCambios.getScene().getWindow()
                );
                clicModificarCambio.initStyle(StageStyle.UTILITY);
                clicModificarCambio.setOnCloseRequest(eventStage -> {
                    eventStage.consume();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("¿Está seguro?");
                    alert.setHeaderText("¿Está seguro de cancelar?");
                    alert.setContentText("¿Ésta acción no se podrá revertir?");

                    alert.showAndWait().ifPresent(response ->{
                       String responseMessage = response.getText();
                       if (responseMessage.equals("Aceptar")) {
                           clicModificarCambio.close();
                       }
                    });
                });
                clicModificarCambio.showAndWait();
                cargarCambios();
            } catch (IOException ioex) {
                System.err.println("Error de \"IOException\" en archivo \"FXMLFormularioModCambio\" en método \"btModificarCambio\"");
                ioex.printStackTrace();
            }
        } else {
            showMessage(
                "WARNING",
                "Seleccion requerida",
                "Primero selecciona un cambio",
                "Elije un cambio para modificarlo"
            );
        }
    }
    
    private Cambio verifySelectedCambio(){
        int selectedRow = this.tvCambios.getSelectionModel().getSelectedIndex();
        this.selectedCambio = (selectedRow >= 0) ? this.cambios.get(selectedRow) : null;
        return selectedCambio;
    }
}
