package bundles;

import java.util.ListResourceBundle;

public class AuthorizationLabels_ru extends ListResourceBundle {
    private final Object[][] contents = {
            {"login", "логин"},
            {"password", "пароль"},
            {"register", "зарегистрироваться"},
            {"loginAction", "войти"},
            {"language", "язык"},
            {"host", "хост"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
