package workersOperations;

import com.sun.javafx.collections.ObservableListWrapper;
import data.Color;
import data.Country;
import data.Status;
import data.Worker;
import gui.MainController;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Filter {
    private final MainController controller;

    public Filter(MainController mainController) {
        controller = mainController;
    }

    public ObservableList<Worker> filter(ObservableList<Worker> list) {
        List<Worker> preReturn = list.stream().filter(this::checkName)
                .filter(this::checkCreationDate)
                .filter(this::checkSalary)
                .filter(this::checkCoordinates)
                .filter(this::checkStartDate)
                .filter(this::checkEndDate)
                .filter(this::checkHeight)
                .filter(this::checkStatus)
                .filter(this::checkEyeColor)
                .filter(this::checkHairColor)
                .filter(this::checkNationality)
                .collect(Collectors.toList());
        return new ObservableListWrapper<>(preReturn);
    }

    private boolean checkName(Worker w) {
        if (controller.nameFilter.getText() == null || controller.nameFilter.getText().isEmpty()) {
            return true;
        }
        return w.getName().contains(controller.nameFilter.getText());
    }

    private boolean checkCreationDate(Worker w) {
        LocalDate lower = controller.creationDateFilterFrom.getValue();
        LocalDate higher = controller.creationDateFilterTill.getValue();
        LocalDate now = w.getCreationDate() instanceof java.sql.Date ? ((java.sql.Date) w.getCreationDate()).toLocalDate() :
                LocalDate.ofEpochDay(w.getCreationDate().getTime() / (24 * 3600 * 1000));
        return datesCheck(now, lower, higher);
    }

    private boolean checkSalary(Worker w) {
        String from = controller.salaryFilterFrom.getText();
        String to = controller.salaryFilterTo.getText();
        boolean higherThenFrom;
        boolean lowerThenTo;
        if (from == null || from.isEmpty()) {
            higherThenFrom = true;
        } else {
            long fromNumber;
            try {
                fromNumber = Long.parseLong(from);
                higherThenFrom = w.getSalary() > fromNumber;
            } catch (NumberFormatException e) {
                higherThenFrom = true;
            }
        }
        if (to == null || to.isEmpty()) {
            lowerThenTo = true;
        } else {
            long toNumber;
            try {
                toNumber = Long.parseLong(to);
                lowerThenTo = w.getSalary() < toNumber;
            } catch (NumberFormatException e) {
                lowerThenTo = true;
            }
        }
        return higherThenFrom && lowerThenTo;
    }

    private boolean checkCoordinates(Worker w) {
        String dist = controller.coordinatesFilter.getText();
        if (dist == null || dist.isEmpty()) {
            return true;
        } else {
            try {
                double distance = Double.parseDouble(dist);
                return distance > w.getCoordinates().getDistanceFromZero();
            } catch (NumberFormatException e) {
                return true;
            }
        }
    }

    private boolean checkEndDate(Worker w) {
        LocalDate now = w.getEndDate();
        LocalDate lower = controller.endDateFilterFrom.getValue();
        LocalDate higher = controller.endDateFilterTill.getValue();
        return datesCheck(now, lower, higher);
    }

    private boolean checkStartDate(Worker w) {
        LocalDate now = w.getStartDate().toLocalDate();
        boolean higherThenLower;
        boolean lowerThenHigher;
        LocalDate lower = controller.startDateFilterFrom.getValue();
        LocalDate higher = controller.startDateFilterTill.getValue();
        return datesCheck(now, lower, higher);
    }

    private boolean checkHeight(Worker w) {
        String from = controller.heightFilterFrom.getText();
        String to = controller.heightFilterTo.getText();
        boolean higherThenFrom;
        boolean lowerThenTo;
        if (from == null || from.isEmpty()) {
            higherThenFrom = true;
        } else {
            double fromNumber;
            try {
                fromNumber = Double.parseDouble(from);
                higherThenFrom = Double.compare(w.getPerson().getHeight(), fromNumber) > 0;
            } catch (NumberFormatException e) {
                higherThenFrom = true;
            }
        }
        if (to == null || to.isEmpty()) {
            lowerThenTo = true;
        } else {
            double toNumber;
            try {
                toNumber = Double.parseDouble(to);
                lowerThenTo = Double.compare(w.getPerson().getHeight(), toNumber) < 0;
            } catch (NumberFormatException e) {
                lowerThenTo = true;
            }
        }
        return higherThenFrom && lowerThenTo;
    }

    private boolean checkStatus(Worker w) {
        Status value = controller.statusFilter.getValue();
        return w.getStatus() == value || value == null;
    }

    private boolean checkNationality(Worker w) {
        Country value = controller.nationalityFilter.getValue();
        return w.getPerson().getNationality() == value || value == null;
    }

    private boolean checkEyeColor(Worker w) {
        Color value = controller.eyeColorFilter.getValue();
        return w.getPerson().getEyeColor() == value || value == null;
    }

    private boolean checkHairColor(Worker w) {
        Color value = controller.hairColorFilter.getValue();
        return w.getPerson().getHairColor() == value || value == null;
    }

    private boolean datesCheck(LocalDate now, LocalDate lower, LocalDate higher) {
        boolean higherThenLower;
        boolean lowerThenHigher;
        if (lower == null) {
            higherThenLower = true;
        } else {
            if (now == null) {
                return false;
            }
            higherThenLower = now.compareTo(lower) > 0;
        }
        if (higher == null) {
            lowerThenHigher = true;
        } else {
            if (now == null) {
                return false;
            }
            lowerThenHigher = now.compareTo(higher) < 0;
        }
        return lowerThenHigher && higherThenLower;
    }
}
