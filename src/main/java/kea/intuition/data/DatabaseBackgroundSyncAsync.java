package kea.intuition.data;

import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kea.intuition.Intuition;
import kea.intuition.model.Company;

import java.util.function.Predicate;

public class DatabaseBackgroundSyncAsync implements Runnable {
    private final long SLEEP_X_SECONDS = 1000 * 10;
    private Stage stage;
    private TextField searchField;

    public DatabaseBackgroundSyncAsync(Stage stage, TextField searchField) {
        this.stage = stage;
        this.searchField = searchField;
    }

    @Override
    public void run() {
        while (true) {
            System.out.printf("Sleeping %d secs.\n", (SLEEP_X_SECONDS / 1000));
            // Wait a minute before next repeat.
            try {
                Thread.sleep(SLEEP_X_SECONDS);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
                System.exit(1);
            }

            if (Intuition.Config.isDbLock()) {
                // Update local cache of db.
                CompanyContainer.setData(CompanyContainer.getCompaniesFromDb());
                Platform.runLater(new updateView());
            }

        }
    }

    private class updateView implements Runnable {

        @Override
        public void run() {
            // Update the table view, various lists(filtering, sorting, etc..) and attach a new SearchFieldHandler to searchField.
            int selected = CompanyContainer.getTableStructure().getSelectionModel().getSelectedIndex();
            CompanyContainer.setFilteredList(new FilteredList<Company>(CompanyContainer.getData(), new Predicate<Company>() {
                @Override
                public boolean test(Company company) {
                    return true;
                }
            }));

            CompanyContainer.setSortedList(new SortedList<Company>(CompanyContainer.getFilteredList()));

            CompanyContainer.getSortedList().comparatorProperty().bind(CompanyContainer.getTableStructure().comparatorProperty());
            CompanyContainer.getTableStructure().setItems(CompanyContainer.getSortedList());

            SearchFieldHandler.manualHandle(searchField);

            CompanyContainer.getTableStructure().refresh();
            CompanyContainer.getTableStructure().getSelectionModel().select(selected);
            CompanyContainer.getTableStructure().getFocusModel().focus(CompanyContainer.getTableStructure().getSelectionModel().getSelectedIndex());
        }
    }
}