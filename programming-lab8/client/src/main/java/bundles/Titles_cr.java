package bundles;

import java.util.ListResourceBundle;

public class Titles_cr extends ListResourceBundle {
    private final Object[][] contents = {
            {"choose script", "odaberite skriptu"},
            {"addition", "dodavanje radnika"},
            {"update", "ažuriranje zaposlenika"},
            {"delete", "uklanjanje zaposlenika"},
            {"info", "informacija"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
