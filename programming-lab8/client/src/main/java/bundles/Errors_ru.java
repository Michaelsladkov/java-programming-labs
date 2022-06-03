package bundles;

import java.util.ListResourceBundle;

public class Errors_ru extends ListResourceBundle {
    private final Object[][] contents = {
            {"UNKNOWN_USER", "неверные имя пользователя или пароль"},
            {"INCORRECT_HOST", "хост надо ввести в формате: ip:host"},
            {"FAILED_TO_CONNECT", "не получилось подключиться по этому адресу"},
            {"FIELD", "поле"},
            {"CAN'T_BE_NULL", "не может быть пустым"},
            {"INCORRECT_COORDINATES_FORMAT", "неправильный формат координат"},
            {"INCORRECT_SALARY_FORMAT", "неправильный формат зарплаты"},
            {"INCORRECT_HEIGHT_FORMAT", "неправильный формат роста"},
            {"INCORRECT_ID_FORMAT", "неправильный формат id"},
            {"PERMISSION_DENIED", "в доступе отказано"},
            {"YOU_SHOULD_CHOOSE_ANY_STATUS", "вам следует выбрать статус"},
            {"PICK_WORKER_FIRST", "сначала нужно выбрать работника"},
            {"YOU_SHOULD_ENTER_WORKER_FIRST", "сначала вам нужно ввести работника"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
