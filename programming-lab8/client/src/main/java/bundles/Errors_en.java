package bundles;

import java.util.ListResourceBundle;

public class Errors_en extends ListResourceBundle {
    private final Object[][] contents = {
            {"UNKNOWN_USER", "we don't know this combination of login and password"},
            {"INCORRECT_HOST", "host should be in format: ip:host"},
            {"FAILED_TO_CONNECT", "FAILED TO CONNECT TO THIS HOST"},
            {"FIELD", "field"},
            {"CAN'T_BE_NULL", "can't be empty"},
            {"INCORRECT_COORDINATES_FORMAT", "incorrect coordinates format"},
            {"INCORRECT_SALARY_FORMAT", "incorrect salary format"},
            {"INCORRECT_HEIGHT_FORMAT", "incorrect height format"},
            {"INCORRECT_ID_FORMAT", "incorrect id format"},
            {"PERMISSION_DENIED", "permission denied"},
            {"YOU_SHOULD_CHOOSE_ANY_STATUS", "you should choose any status"},
            {"PICK_WORKER_FIRST", "you should pick worker"},
            {"YOU_SHOULD_ENTER_WORKER_FIRST", "you should enter worker first"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
