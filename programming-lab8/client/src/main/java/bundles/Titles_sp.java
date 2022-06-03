package bundles;

import java.util.ListResourceBundle;

public class Titles_sp extends ListResourceBundle {
    private final Object[][] contents = {
            {"choose script", "elegir script"},
            {"addition", "adición de trabajadores"},
            {"update", "actualización del trabajador"},
            {"delete", "eliminar worker"},
            {"info", "información"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
