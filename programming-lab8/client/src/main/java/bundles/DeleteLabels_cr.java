package bundles;

import java.util.ListResourceBundle;

public class DeleteLabels_cr extends ListResourceBundle {
    private final Object[][] contents = {
            {"byId", "prema identifikator"},
            {"byStatus", "prema statusu"},
            {"lessThen", "manje od"},
            {"all", "sve"},
            {"delete", "ukloni"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
