/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.utils;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ChangeStage {
    public Node obtenerNodoPorId(Stage stage, String id) {
        Scene scene = stage.getScene();
        if (scene != null) {
            return obtenerNodoPorId(scene.getRoot(), id);
        }
        return null;
    }

    private Node obtenerNodoPorId(Parent parent, String id) {
        if (parent == null) {
            return null;
        }

        if (id.equals(parent.getId())) {
            return parent;
        }

        for (Node child : parent.getChildrenUnmodifiable()) {
            if (id.equals(child.getId())) {
                return child;
            }
            if (child instanceof Parent) {
                Node resultado = obtenerNodoPorId((Parent) child, id);
                if (resultado != null) {
                    return resultado;
                }
            }
        }

        return null;
    }
    
    
}