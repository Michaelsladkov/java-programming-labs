package bundles;

import java.time.format.DateTimeFormatter;
import java.util.ListResourceBundle;

public class Formats_ru extends ListResourceBundle {
    private final Object[][] contents = {
            {"date_time", DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss x")},
            {"date", DateTimeFormatter.ofPattern("dd.MM.yyyy")}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
