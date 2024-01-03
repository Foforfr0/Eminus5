/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */
package eminus5.viewController;

import eminus5.databaseManagment.model.DAO.UserDAO;
import eminus5.databaseManagment.model.POJO.Usuario;
import eminus5.databaseManagment.model.ResultOperation;
import eminus5.utils.ShowMessage;
import eminus5.viewController.desarrollador.controllers.FXMLPantallaInicioDController;
import eminus5.viewController.responsableProyecto.controllers.FXMLPantallaInicioRController;
import static eminus5.utils.loadView.loadScene;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class FXMLInicioSesionController implements Initializable {
    @FXML
    private TextField tfUserLogin;
    @FXML
    private PasswordField tfPasswordLogin;
    @FXML
    private Label lbErrorUser;
    @FXML
    private Label lbErrorPassword;
    @FXML
    private Button btLogin;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfUserLogin.setText("S21013909");
        tfPasswordLogin.setText("1234");
        addActionToButtonLogin();
    }
    
    private void validateFullFields() {
        if (tfUserLogin.getText().length() == 0) {
            lbErrorUser.setText("Favor de ingresar el usuario");
        } else if (tfUserLogin.getText().length() > 0) {
            lbErrorUser.setText("");
        } 
        if (tfPasswordLogin.getText().length() == 0) {
            lbErrorPassword.setText("Favor de ingresar la contraseña");
        } else if (tfPasswordLogin.getText().length() > 0) {
            lbErrorPassword.setText("");
        }
    }
    
    private void showScenePaginaInicial(int idUser, String systemRole) throws IOException{  
        String pathResource = "";
        if (systemRole.equals("Responsable")) {
            pathResource = "viewController/responsableProyecto/views/FXMLPantallaInicioR.fxml";
        } else if (systemRole.equals("Desarrollador")) {
            pathResource = "viewController/desarrollador/views/FXMLPantallaInicioD.fxml";
        }
        System.out.println("PathResource: "+pathResource);
        Stage currentStage = (Stage) this.tfUserLogin.getScene().getWindow();
        
        if (systemRole.equals("Responsable")) {
            FXMLPantallaInicioRController.idResponsable = idUser;
        } else if (systemRole.equals("Desarrollador")) {
            FXMLPantallaInicioDController.idUser = idUser;
        }
        
        currentStage.setScene(loadScene(pathResource));        
        currentStage.setTitle("Menú de inicio");
        currentStage.setResizable(false);
        currentStage.show();        
    }
    
    private void addActionToButtonLogin() {
        btLogin.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getLogin();
            }
        });
        btLogin.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE) {
                    getLogin();
                }
            }
        });       
    }
    
    @FXML
    private void clicGetLogin(KeyEvent event) {         //To main AnchorPane
        if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE) {
            getLogin();
        }
    }
    
    private void getLogin() {
        validateFullFields();
        
        if (lbErrorUser.getText().isEmpty() && lbErrorPassword.getText().isEmpty()) {
            try {
                ResultOperation resultOperationLogin = 
                    UserDAO.verifyUser(
                        tfUserLogin.getText(), 
                        tfPasswordLogin.getText()
                    );
                
                if (resultOperationLogin.getIsError() == false && resultOperationLogin.getNumberRowsAffected() == 0) {
                    ShowMessage.showMessage(
                        "ERROR", 
                        "Credenciales incorrectas", 
                        resultOperationLogin.getMessage(), 
                        "Favor de internar de nuevo"
                    );
                } else if (resultOperationLogin.getIsError() == false && resultOperationLogin.getNumberRowsAffected() > 0) {
                    tfUserLogin.setText("");
                    tfPasswordLogin.setText("");
                    
                    Usuario session = (Usuario) resultOperationLogin.getData();
                    showScenePaginaInicial(
                        session.getIdUsuario(), 
                        session.getRolSistema()
                    );
                }
            } catch (SQLException sqlex) {
                System.err.println("\"Error de \"SQLException\" en archivo \"FXMLInicioSesionController\" en método \"getLogin\"");
                sqlex.printStackTrace();
            } catch (IOException ioex) {
                System.err.println("\"Error de \"IOException\" en archivo \"FXMLInicioSesionController\" en método \"showScenePaginaInicial\"\"");
                ioex.printStackTrace();
            }
        }
    }
}
