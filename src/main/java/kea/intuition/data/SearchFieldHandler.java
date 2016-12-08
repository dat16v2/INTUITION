package kea.intuition.data;


import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import kea.intuition.model.Company;

import java.util.function.Predicate;

public class SearchFieldHandler implements EventHandler<javafx.scene.input.KeyEvent> {
    private TextField searchField;

    public SearchFieldHandler(TextField searchField) {
        this.searchField = searchField;
    }

    @Override
    public void handle(javafx.scene.input.KeyEvent event) {
        manualHandle(searchField);
    }

    public static void manualHandle(TextField searchField) {
        CompanyContainer.getFilteredList().setPredicate(new Predicate<Company>() {
            @Override
            public boolean test(Company company) {
                if (searchField.getText().isEmpty() || searchField.getText() == null) {
                    return true;
                }

                String lowerCasedSearch = searchField.getText().toLowerCase();

                if (lowerCasedSearch.contains("id:")) {
                    String id = "";
                    for (int i = 3; i < lowerCasedSearch.length(); i++) {
                        id = id + lowerCasedSearch.charAt(i);
                    }

                    if (Integer.toString(company.getId()).equals(id)) {
                        return true;
                    }
                }

                if (company.getName().toLowerCase().contains(lowerCasedSearch)) {
                    return true;
                }

                return false;
            }
        });

        if (!searchField.getText().isEmpty()) {
            CompanyContainer.getTableStructure().getSelectionModel().selectFirst();
        }
    }
}