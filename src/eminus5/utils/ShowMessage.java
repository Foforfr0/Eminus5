/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ShowMessage {
    public static void showMessage(String alertType, String tittle, String headerText, String contentText) {
        Alert alert = new Alert(AlertType.NONE);
        
        switch (alertType) {
            case "INFORMATION":
                alert.setAlertType(AlertType.INFORMATION);
                break;
            case "ERROR":
                alert.setAlertType(AlertType.ERROR);
                break;
            case "WARNING":
                alert.setAlertType(AlertType.WARNING);
                break;
            case "NONE":
                alert.setAlertType(AlertType.NONE);
                break;
            case "CONFIRMATION":
                alert.setAlertType(AlertType.CONFIRMATION);
                break;
            default:
                System.out.println("Error de \"AlertType\" en archivo \"ShowMessage\" en método \"ShowMessage\"");
        }
        alert.setTitle(tittle);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.setResizable(false);
        
        alert.showAndWait();
    }
    
    public static void showMessageConfirmationToCancel(Stage currentStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¿Está seguro?");
        alert.setHeaderText("¿Está seguro de cancelar?");
        alert.setContentText("Ésta acción no se podrá revertir");
        
        alert.showAndWait().ifPresent((button) -> {
            if (button.getText().equals("Aceptar")) {
                currentStage.close(); 
            } else {
                
            }
        });
    }
    
    public static void showMessageFailureConnection() {
        Alert alert = new Alert(AlertType.ERROR);
        
        alert.setTitle("Error de conexión");
        alert.setHeaderText("No se pudo conectar a la base de datos");
        alert.setContentText("Favor de intentar más tarde");
        alert.setResizable(false);
        
        alert.showAndWait();
    }
    
    public static void showMessageDataIncomplete() {
        Alert alert = new Alert(AlertType.ERROR);
        
        alert.setTitle("Error");
        alert.setHeaderText("No ingresó todos los campos requeridos");
        alert.setContentText("Favor de ingresar todos los datos");
        alert.setResizable(false);
        
        alert.showAndWait();
    }
    
    public static ButtonType showConfirmation(String title, String headerText, String contentText) {
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle(title);
        confirmationAlert.setHeaderText(headerText);
        confirmationAlert.setContentText(contentText);
        confirmationAlert.initModality(Modality.WINDOW_MODAL);
        confirmationAlert.initStyle(StageStyle.UTILITY);

        return confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);
    }
}
