package kea.intuition.controller;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import kea.intuition.data.ModifiedValues;
import kea.intuition.model.Company;

public class CompanyCreationSingularDisplay {
    private Pane layout;
    private Company company;
    private ModifiedValues modifiedValues;

    public CompanyCreationSingularDisplay() {
        this.layout = new VBox(0);
        this.company = new Company();
        this.modifiedValues = new ModifiedValues();
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

        content.getChildren().addAll(companyName, companyPhoneNumber, companyEmail);

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