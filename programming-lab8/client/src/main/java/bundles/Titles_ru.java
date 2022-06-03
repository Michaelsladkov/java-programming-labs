package bundles;

import java.util.ListResourceBundle;

public class Titles_ru extends ListResourceBundle {
    private final Object[][] contents = {
            {"choose script", "выберите сценарий"},
            {"addition", "добавление работника"},
            {"update", "обновление работника"},
            {"delete", "удаление работника"},
            {"info", "информация"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
