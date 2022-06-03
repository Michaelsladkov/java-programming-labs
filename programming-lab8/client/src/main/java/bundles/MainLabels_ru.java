package bundles;

import java.util.ListResourceBundle;

public class MainLabels_ru extends ListResourceBundle {
    private final Object[][] contents = {
            {"you logined as", "вы вошли как: "},
            {"info", "информация"},
            {"add", "добавить"},
            {"delete", "удалить"},
            {"descendingSalaries", "зарплаты по убыванию"},
            {"update", "обновить"},
            {"checkConnection", "проверить связь"},
            {"visualization", "визуализация"},
            {"table", "таблица"},
            {"id", "id"},
            {"name", "имя"},
            {"coordinates", "координаты"},
            {"created", "создан"},
            {"salary", "зарплата"},
            {"start_date", "дата начала"},
            {"end_date", "дата конца"},
            {"status", "статус"},
            {"height", "рост"},
            {"eye_color", "цвет глаз"},
            {"hair_color", "цвет волос"},
            {"nationality", "национальность"},
            {"filters", "фильтры"},
            {"by_name", "по имени"},
            {"by_distance_to_O", "по дистанции до (0,0)"},
            {"by_creation_date", "по дате создания"},
            {"from", "с"},
            {"till", "по"},
            {"by_salary", "по зрплате"},
            {"to", "до"},
            {"by", "по"},
            {"by_start_date", "по дате начала"},
            {"by_end_date", "по дате конца"},
            {"by_status", "по статусу"},
            {"by_height", "по росту"},
            {"by_eye_color", "по цвету глаз"},
            {"by_hair_color", "по цвету волос"},
            {"by_nationality", "по национальности"},
            {"language", "язык"},
            {"executeScript", "выполнить сценарий"},
            {"personal_stats", "персональные показатели"},
            {"eyeColor", "цвет глаз"},
            {"hairColor", "цвет волос"},
            {"addIfMax", "добавить, если больше наибольшего"},
            {"addIfMin", "добавить, если меньше наименьшего"},
            {"creator", "создатель"},
            {"worker", "работник"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
