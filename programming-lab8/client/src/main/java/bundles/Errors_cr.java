package bundles;

import java.util.ListResourceBundle;

public class Errors_cr extends ListResourceBundle {
    private final Object[][] contents = {
            {"UNKNOWN_USER", "Netočno korisničko ime ili lozinka"},
            {"INCORRECT_HOST", "host mora biti unesen u formatu: ip:host"},
            {"FAILED_TO_CONNECT", "nije uspjelo uspostaviti vezu s ovom adresom"},
            {"FIELD", "polje"},
            {"CAN'T_BE_NULL", "ne može biti prazno"},
            {"INCORRECT_COORDINATES_FORMAT", "nevažeći format koordinata"},
            {"INCORRECT_SALARY_FORMAT", "nevaljani format plaće"},
            {"INCORRECT_HEIGHT_FORMAT", "nevažeći format rasta"},
            {"INCORRECT_ID_FORMAT", "nevažeći format id"},
            {"PERMISSION_DENIED", "odobrenje odbijeno"},
            {"YOU_SHOULD_CHOOSE_ANY_STATUS", "trebali biste odabrati bilo koji status"},
            {"YOU_SHOULD_ENTER_WORKER_FIRST", "prvo trebate unijeti radnika"},
            {"PICK_WORKER_FIRST", "prvo morate odabrati radnika"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
