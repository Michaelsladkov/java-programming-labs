package bundles;

import java.util.ListResourceBundle;

public class Titles_ne extends ListResourceBundle {
    private final Object[][] contents = {
            {"choose script", "script kiezen"},
            {"addition", "toevoeging van werknemers"},
            {"update", "werknemer bijwerken"},
            {"delete", "worker verwijderen"},
            {"info", "informatie"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
