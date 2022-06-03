package bundles;

import java.util.ListResourceBundle;

public class DeleteLabels_en extends ListResourceBundle {
    private final Object[][] contents = {
            {"byId", "by id"},
            {"byStatus", "by status"},
            {"lessThen", "less then"},
            {"all", "all"},
            {"delete", "delete"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
