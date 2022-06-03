package bundles;

import java.time.format.DateTimeFormatter;
import java.util.ListResourceBundle;

public class Formats_sp extends ListResourceBundle {
    private final Object[][] contents = {
            {"date_time", DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss x")},
            {"date", DateTimeFormatter.ofPattern("yyyy/MM/dd")}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
