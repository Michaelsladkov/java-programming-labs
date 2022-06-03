package bundles;

import java.util.ListResourceBundle;

public class DeleteLabels_ne extends ListResourceBundle {
    private final Object[][] contents = {
            {"byId", "op id"},
            {"byStatus", "op status"},
            {"lessThen", "minder"},
            {"all", "al"},
            {"delete", "verwijderen"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}