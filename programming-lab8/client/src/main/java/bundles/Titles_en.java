package bundles;

import java.util.ListResourceBundle;

public class Titles_en extends ListResourceBundle {
    private final Object[][] contents = {
            {"choose script", "choose script"},
            {"addition", "worker addition"},
            {"update", "updating worker"},
            {"delete", "deleting worker"},
            {"info", "info"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
