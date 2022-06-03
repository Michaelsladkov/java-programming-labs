package bundles;

import java.util.ListResourceBundle;

public class AuthorizationLabels_sp extends ListResourceBundle {
    private final Object[][] contents = {
            {"login", "acceso"},
            {"password", "contraseña"},
            {"register", "registrarse"},
            {"loginAction", "iniciar"},
            {"language", "lenguaje"},
            {"host", "hueste"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
