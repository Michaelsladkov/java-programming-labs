package bundles;

import java.util.ListResourceBundle;

public class Errors_ne extends ListResourceBundle {
    private final Object[][] contents = {
            {"UNKNOWN_USER", "foute gebruikersnaam of wachtwoord"},
            {"INCORRECT_HOST", "host moet worden ingevoerd in het formaat: ip:host"},
            {"FAILED_TO_CONNECT", "kan geen verbinding maken met dit adres"},
            {"FIELD", "veld"},
            {"CANT'T_BE_NULL", "mag niet leeg zijn"},
            {"INCORRECT_COORDINATES_FORMAT", "ongeldige coördinaten notatie"},
            {"INCORRECT_SALARY_FORMAT", "ongeldige salaris indeling"},
            {"INCORRECT_HEIGHT_FORMAT", "ongeldige groei-indeling"},
            {"INCORRECT_ID_FORMAT", "ongeldige id indeling"},
            {"PERMISSION_DENIED", "toestemming geweigerd"},
            {"YOU_SHOULD_CHOOSE_ANY_STATUS", "u moet elke status kiezen"},
            {"YOU_SHOULD_ENTER_WORKER_FIRST", "u moet eerst een werknemer invoeren"},
            {"PICK_WORKER_FIRST", "je moet eerst een werknemer selecteren"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
