package practica1_dacd_afonso_medina.control.exception;

import java.sql.SQLException;

public class SqliteException extends SQLException {
    public SqliteException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
