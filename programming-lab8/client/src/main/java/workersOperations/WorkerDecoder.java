package workersOperations;

import data.Person;
import data.Worker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class WorkerDecoder {
    private final Locale locale;

    public WorkerDecoder(Locale loc) {
        locale = loc;
    }

    public String describeWorker(Worker w) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.MainLabels", locale);
        ResourceBundle formats = ResourceBundle.getBundle("bundles.Formats", locale);
        StringBuilder builder = new StringBuilder();
        builder.append("---------------------------------------------------").
                append("\n").
                append(resourceBundle.getString("worker")).
                append("\n").
                append(resourceBundle.getString("id")).
                append(": ").append(w.getId()).append("\n").
                append(resourceBundle.getString("name")).
                append(": ").
                append(w.getName()).append("\n").
                append(resourceBundle.getString("coordinates")).
                append(": ").append(w.getCoordinates().toString()).append("\n").
                append(resourceBundle.getString("salary")).
                append(": ").append(w.getSalary()).append("\n").
                append(resourceBundle.getString("start_date")).
                append(": ").append(w.getStartDate().format((DateTimeFormatter) formats.getObject("date_time"))).
                append("\n").append(resourceBundle.getString("end_date")).append(": ");
        LocalDate endDate = w.getEndDate();
        builder.append(endDate == null ? "null" : endDate.format((DateTimeFormatter) formats.getObject("date")));
        builder.append("\n");
        builder.append(resourceBundle.getString("status")).
                append(": ").append(w.getStatus()).append("\n").
                append(resourceBundle.getString("personal_stats")).append(":\n");
        Person p = w.getPerson();
        builder.append("\t").append(resourceBundle.getString("height")).
                append(": ").append(p.getHeight()).append("\n");
        builder.append("\t").append(resourceBundle.getString("eye_color")).
                append(": ").append(p.getEyeColor()).append("\n");
        builder.append("\t").append(resourceBundle.getString("hair_color")).
                append(": ").append(p.getHairColor()).append("\n");
        builder.append("\t").append(resourceBundle.getString("nationality")).
                append(": ").append(p.getNationality()).append("\n");
        builder.append(resourceBundle.getString("creator")).append(": ").
                append(w.getCreator());
        return builder.toString();
    }
}
