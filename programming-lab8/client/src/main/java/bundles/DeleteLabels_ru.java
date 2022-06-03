package bundles;

import java.util.ListResourceBundle;

public class DeleteLabels_ru extends ListResourceBundle {
    private final Object[][] contents = {
            {"byId", "по идентификатору"},
            {"byStatus", "по статусу"},
            {"lessThen", "меньше чем"},
            {"all", "все"},
            {"delete", "удалить"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}