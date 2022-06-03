package bundles;

import java.util.ListResourceBundle;

public class Errors_sp extends ListResourceBundle {
    private final Object[][] contents = {
            {"UNKNOWN_USER", "Nombre de usuario o contraseña incorrecta"},
            {"INCORRECT_HOST", "El host debe ingresarse en el formato: ip:host"},
            {"FAILED_TO_CONNECT", "no se pudo conectar a esta dirección"},
            {"FIELD", "el campo"},
            {"CAN'T_BE_NULL", "no puede estar vacío"},
            {"INCORRECT_COORDINATES_FORMAT", "formato de coordenadas no válido"},
            {"INCORRECT_SALARY_FORMAT", "formato de salario no válido"},
            {"INCORRECT_HEIGHT_FORMAT", "formato de crecimiento no válido"},
            {"INCORRECT_ID_FORMAT", "formato de identificación incorrecto"},
            {"PERMISSION_DENIED", "permiso denegado"},
            {"YOU_SHOULD_CHOOSE_ANY_STATUS", "debes elegir cualquier estado"},
            {"PICK_WORKER_FIRST", "primero debe seleccionar un trabajador"},
            {"YOU_SHOULD_ENTER_WORKER_FIRST", "primero debes ingresar un trabajador"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
