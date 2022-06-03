package bundles;

import java.util.ListResourceBundle;

public class AuthorizationLabels_cr extends ListResourceBundle {
    private final Object[][] contents = {
            {"login", "prijava"},
            {"password", "zaporka"},
            {"register", "upisati se"},
            {"loginAction", "prijaviti se"},
            {"language", "jezik"},
            {"host", "domaćin"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
