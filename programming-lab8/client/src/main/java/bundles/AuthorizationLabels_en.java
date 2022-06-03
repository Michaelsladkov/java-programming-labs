package bundles;

import java.util.ListResourceBundle;

public class AuthorizationLabels_en extends ListResourceBundle {
    private final Object[][] contents = {
            {"login", "login"},
            {"password", "password"},
            {"register", "register"},
            {"loginAction", "log in"},
            {"language", "language"},
            {"host", "host"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
