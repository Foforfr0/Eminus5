/*
 * Descripción del programa
 * Últimos 3 cambios realizados
 */

package eminus5.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ConvertData {
    public static LocalDate convertStringToLocalDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(string, formatter);
            return localDate;
    }
}
