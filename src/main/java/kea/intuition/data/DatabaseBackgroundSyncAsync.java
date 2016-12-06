package kea.intuition.data;

import javafx.application.Platform;
import javafx.stage.Stage;
import kea.intuition.model.Company;

public class DatabaseBackgroundSyncAsync implements Runnable {
    private final long SLEEP_60_SECONDS = 1000 * 10;
    private Stage stage;

    public DatabaseBackgroundSyncAsync(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void run() {
        while (true) {
            System.out.printf("Sleeping %d secs.\n", (SLEEP_60_SECONDS / 1000));
            // Wait a minute before next repeat.
            try {
                Thread.sleep(SLEEP_60_SECONDS);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
                System.exit(1);
            }

            CompanyContainer.setData(CompanyContainer.getCompaniesFromDb());
            Platform.runLater(new updateView());

            // Update local cache of db.
        }
    }

    private class updateView implements Runnable {

        @Override
        public void run() {
            int selected = CompanyContainer.getTableStructure().getSelectionModel().getSelectedIndex();
            CompanyContainer.getTableStructure().setItems(CompanyContainer.getData());
            CompanyContainer.getTableStructure().getSelectionModel().select(selected);
            CompanyContainer.getTableStructure().getFocusModel().focus(CompanyContainer.getTableStructure().getSelectionModel().getSelectedIndex());
        }
    }
}