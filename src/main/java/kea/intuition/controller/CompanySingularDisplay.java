package kea.intuition.controller;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import kea.intuition.Tools;
import kea.intuition.data.Lock;
import kea.intuition.model.Company;

public class CompanySingularDisplay {
    private Pane layout;
    private Lock lock;
    private Company company;
    private ModifiedValues modifiedValues;
    private String integrityHash;

    public String getIntegrityHash() {
        return integrityHash;
    }

    public ModifiedValues getModifiedValues() {
        return modifiedValues;
    }

    public void setModifiedValues(ModifiedValues modifiedValues) {
        this.modifiedValues = modifiedValues;
    }

    public static class ModifiedValues {
        private TextField companyNameField;

        public TextField getCompanyNameField() {
            return companyNameField;
        }

        public void setCompanyNameField(TextField companyNameField) {
            this.companyNameField = companyNameField;
        }
    }

    public Company getCompany() {
        return company;
    }

    public CompanySingularDisplay(Company company) {
        lock = new Lock(this);
        this.company = company;
        this.layout = new VBox(0);
        this.integrityHash = Tools.getCompanyHash(this.company);
        setModifiedValues(new ModifiedValues());
        setLockedLayout();
    }

    public void setLockedLayout() {
        this.layout.getChildren().clear();

        layout.getStyleClass().add("company-display");
        layout.setStyle("-fx-background-color: #2f3d50; -fx-padding: 0 0 0 0; -fx-background-insets: 0 0 0 0;");

        VBox paddingTopContent = new VBox(0);
        paddingTopContent.setId("padding-head");

        BorderPane lockPane = new BorderPane();
        lock.getLabel().setId("lock");

        lockPane.setRight(lock.getLabel());

        // Body of display
        VBox content = new VBox(2);

        if (company != null) {
            // Company name
            Label companyNameLabel = new Label(company.getName());
            companyNameLabel.setId("label-title");

            // Phone number
            HBox companyPhoneNumberPane = new HBox(5);
            companyPhoneNumberPane.setId("phone-number-box");
            //companyPhoneNumberPane.setStyle("-fx-text-fill: #ffffff; -fx-font-family: 'Open Sans'; -fx-font-weight: 300; -fx-padding: 5 0 0 35; -fx-font-size: 15px;");

            Label companyPhoneNumberPrefixLabel = new Label(String.format("+%s%s", company.getPhoneNumberPrefix(), company.getPhoneNumberCountryCallingCode()));
            companyPhoneNumberPrefixLabel.getStyleClass().add("text-label");

            Label companyPhoneNumberLabel = new Label(company.getPhoneNumber());
            companyPhoneNumberLabel.getStyleClass().add("text-label");

            companyPhoneNumberPane.getChildren().addAll(companyPhoneNumberPrefixLabel,companyPhoneNumberLabel);

            // Email

            Label companyEmailLabel = new Label(company.getEmail());
            FlowPane companyEmailPane = new FlowPane();
            companyEmailPane.getStyleClass().add("label-box");
            companyEmailLabel.getStyleClass().add("text-label");

            companyEmailPane.getChildren().add(companyEmailLabel);


            content.getChildren().addAll(companyNameLabel, companyPhoneNumberPane, companyEmailPane);
        }

        layout.getChildren().addAll(paddingTopContent, lockPane, content);
    }

    public void setUnlockedLayout() {
        this.layout.getChildren().clear();
        layout.getStyleClass().add("company-display");
        layout.setStyle("-fx-background-color: #2f3d50; -fx-padding: 0 0 0 0; -fx-background-insets: 0 0 0 0;");

        VBox paddingTopContent = new VBox(0);
        paddingTopContent.setId("padding-head");

        BorderPane lockPane = new BorderPane();
        lock.getLabel().setId("lock");

        lockPane.setRight(lock.getLabel());

        // Body of display
        VBox content = new VBox(2);

        if (company != null) {
            HBox companyName = new HBox(0);
            companyName.getStyleClass().add("unlocked-value-pane");
            Label companyNameLabel = new Label("Company:");
            companyNameLabel.getStyleClass().add("unlocked-label");

            getModifiedValues().setCompanyNameField(new TextField());

            getModifiedValues().getCompanyNameField().setText(company.getName());

            companyName.getChildren().addAll(companyNameLabel, getModifiedValues().getCompanyNameField());

            content.getChildren().addAll(companyName);
        }

        layout.getChildren().addAll(paddingTopContent, lockPane, content);
    }

    public Pane getCompanyDisplay() {
        return layout;
    }
}
