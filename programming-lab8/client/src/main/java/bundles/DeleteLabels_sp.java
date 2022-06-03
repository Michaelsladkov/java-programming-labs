package bundles;

import java.util.ListResourceBundle;

public class DeleteLabels_sp extends ListResourceBundle {
    private final Object[][] contents = {
            {"byId", "por id"},
            {"byStatus", "por situación"},
            {"lessThen", "menor que"},
            {"all", "todo"},
            {"delete", "eliminar"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}