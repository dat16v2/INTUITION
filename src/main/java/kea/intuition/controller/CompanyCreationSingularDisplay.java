package kea.intuition.controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kea.intuition.IntuitionLockEvent;
import kea.intuition.data.CompanyContainer;
import kea.intuition.data.ModifiedValues;
import kea.intuition.model.Company;

public class CompanyCreationSingularDisplay {
    private Pane layout;
    private Company company;
    private ModifiedValues modifiedValues;
    private Stage stage;

    public CompanyCreationSingularDisplay(Stage stage) {
        this.layout = new VBox(0);
        this.company = new Company();
        this.modifiedValues = new ModifiedValues();
        this.stage = stage;
        setLayout();
    }

    private void setLayout() {
        layout.getStyleClass().add("company-display");
        layout.setStyle("-fx-background-color: #2f3d50; -fx-padding: 0 0 0 0; -fx-background-insets: 0 0 0 0;");

        VBox paddingTopContent = new VBox(0);
        paddingTopContent.setId("padding-head");

        // Body of display
        VBox content = new VBox(2);

        // Company name
        HBox companyName = new HBox(0);
        companyName.getStyleClass().add("unlocked-value-pane");
        Label companyNameLabel = new Label("Company:");
        companyNameLabel.getStyleClass().add("unlocked-label");

        getModifiedValues().setCompanyNameField(new TextField());

        companyName.getChildren().addAll(companyNameLabel, getModifiedValues().getCompanyNameField());

        // Phone number
        HBox companyPhoneNumber = new HBox(0);
        companyPhoneNumber.getStyleClass().add("unlocked-value-pane");
        Label companyPhoneNumberLabel = new Label("Phone number:");
        companyPhoneNumberLabel.getStyleClass().add("unlocked-label");

        getModifiedValues().setCompanyPhoneNumberField(new TextField());

        companyPhoneNumber.getChildren().addAll(companyPhoneNumberLabel, getModifiedValues().getCompanyPhoneNumberField());

        // Email
        HBox companyEmail = new HBox(0);
        companyEmail.getStyleClass().addAll("unlocked-value-pane");
        Label companyEmailLabel = new Label("Email:");
        companyEmailLabel.getStyleClass().add("unlocked-label");

        getModifiedValues().setCompanyEmailField(new TextField());

        companyEmail.getChildren().addAll(companyEmailLabel, getModifiedValues().getCompanyEmailField());

        // Buttons
        HBox buttonsPane = new HBox(5);
        buttonsPane.setId("button-pane");

        IndexScreen.Button cancelButton = new IndexScreen.Button("Cancel");
        cancelButton.addButtonHandler(new IndexScreen.ButtonHandler() {
            @Override
            public void handle() {
                IntuitionLockEvent.fireEvent(stage, new IntuitionLockEvent(null, null, IntuitionLockEvent.LOCK_CHANGED_ROOT_EVENT, true));
                Company company = (Company) CompanyContainer.getTableStructure().getSelectionModel().getSelectedItem();
                CompanyContainer.getTableStructure().getSelectionModel().clearSelection();
                CompanyContainer.getTableStructure().getSelectionModel().select(company);
                System.out.println("Cancel");
            }
        });

        IndexScreen.Button submitButton = new IndexScreen.Button("Submit");
        submitButton.addButtonHandler(new IndexScreen.ButtonHandler() {
            @Override
            public void handle() {
                System.out.println("Submit");
            }
        });

        buttonsPane.getChildren().addAll(cancelButton.getButton(), submitButton.getButton());

        content.getChildren().addAll(companyName, companyPhoneNumber, companyEmail, buttonsPane);

        layout.getChildren().addAll(paddingTopContent, content);
    }

    public Company getCompany() {
        return company;
    }

    public Pane getLayout() {
        return layout;
    }

    public ModifiedValues getModifiedValues() {
        return modifiedValues;
    }
}