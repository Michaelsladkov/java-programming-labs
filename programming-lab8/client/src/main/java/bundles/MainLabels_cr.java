package bundles;

import java.util.ListResourceBundle;

public class MainLabels_cr extends ListResourceBundle {
    private final Object[][] contents = {
            {"you logined as", "prijavljeni ste kao: "},
            {"info", "informacije"},
            {"add", "dodaj"},
            {"delete", "ukloni"},
            {"descendingSalaries", "smanjenje plaća"},
            {"update", "ažuriranje"},
            {"checkConnection", "provjerite vezu"},
            {"visualization", "vizualizacija"},
            {"table", "stol"},
            {"id", "id"},
            {"name", "ime"},
            {"coordinates", "koordinate"},
            {"created", "generirano"},
            {"salary", "plaća"},
            {"start_date", "datum početka"},
            {"end_date", "datum završet"},
            {"status", "status"},
            {"height", "visina"},
            {"eye_color", "boja očiju"},
            {"hair_color", "boja kose"},
            {"nationality", "nacionalnost"},
            {"filters", "filtri"},
            {"by_name", "po imenu"},
            {"by_distance_to_O", "udaljenost do (0,0)"},
            {"by_creation_date", "prema datumu stvaranja"},
            {"from", "od"},
            {"till", "bok"},
            {"by_salary", "na plaće"},
            {"to", "k"},
            {"by", "oko"},
            {"by_start_date", "po datumu početka"},
            {"by_end_date", "do datuma završetka"},
            {"by_status", "prema statusu"},
            {"by_height", "visina"},
            {"by_eye_color", "boja očiju"},
            {"by_hair_color", "boja kose"},
            {"by_nationality", "nacionalnost"},
            {"language", "jezik"},
            {"executeScript", "pokreni skriptu"},
            {"personal_stats", "osobne statistike"},
            {"eyeColor", "boja očiju"},
            {"hairColor", "boja kose"},
            {"addIfMax", "dodaj ako maks"},
            {"addIfMin", "dodajte ako je moj"},
            {"creator", "stvoritelj"},
            {"worker", "radnik"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}