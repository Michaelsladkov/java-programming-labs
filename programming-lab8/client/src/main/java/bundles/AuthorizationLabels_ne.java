package bundles;

import java.util.ListResourceBundle;

public class AuthorizationLabels_ne extends ListResourceBundle {
    private final Object[][] contents = {
            {"login", "login"},
            {"password", "wachtwoord"},
            {"register", "registreren"},
            {"loginAction", "log in"},
            {"language", "taal"},
            {"host", "gastheer"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
