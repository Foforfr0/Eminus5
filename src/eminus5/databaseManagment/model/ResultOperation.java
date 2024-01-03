/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.databaseManagment.model;


public class ResultOperation {
    private boolean isError;
    private String message;
    private int numberAffectedRows;     //If it's -1 it's an error
                                        //If it's 0 it's empty return
                                        //If it's >0 it's a non-empty return
    private Object data;
    
    public ResultOperation() {
    }

    public ResultOperation(boolean error, String message, int numberAffectedRows, Object data) {
        this.isError = error;
        this.message = message;
        this.numberAffectedRows = numberAffectedRows;
        this.data = data;
    }

    public boolean getIsError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNumberRowsAffected() {
        return numberAffectedRows;
    }

    public void setNumberRowsAffected(int numberAffectedRows) {
        this.numberAffectedRows = numberAffectedRows;
    }  
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
}
