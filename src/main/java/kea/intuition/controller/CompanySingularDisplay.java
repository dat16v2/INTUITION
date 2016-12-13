package kea.intuition.controller;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.util.ArrayList;

import javafx.stage.Stage;
import kea.intuition.Intuition;
import kea.intuition.Tools;
import kea.intuition.data.CompanyContainer;
import kea.intuition.data.Lock;
import kea.intuition.data.ModifiedValues;
import kea.intuition.model.Company;
import kea.intuition.model.Note;

public class CompanySingularDisplay {
    private Pane layout;
    private Lock lock;
    private Company company;
    private ModifiedValues modifiedValues;
    private String integrityHash;
    private Stage stage;

    public String getIntegrityHash() {
        return integrityHash;
    }

    public void setIntegrityHash(String integrityHash) {
        this.integrityHash = integrityHash;
    }

    public ModifiedValues getModifiedValues() {
        return modifiedValues;
    }

    public void setModifiedValues(ModifiedValues modifiedValues) {
        this.modifiedValues = modifiedValues;
    }

    public Company getCompany() {
        return company;
    }

    public Stage getStage() {
        return stage;
    }

    public CompanySingularDisplay(Company company, Stage stage) {
        this.stage = stage;
        lock = new Lock(this); // Used alongside IntuitionLockEvent to handling locking of UI elements.
        this.company = company;
        this.layout = new VBox(0);
        if (company != null) {
            this.integrityHash = Tools.getCompanyHash(company);
            CompanyContainer.setCurrentCompanyHash(company.getHash());
            System.out.printf("Setting new company hash(%s): %s\n", company.getName(),CompanyContainer.getCurrentCompanyHash());
        }
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

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Make sure the horizontal scroll bar does not appear.

        // Body of display
        VBox content = new VBox(2);

        if (company != null) {
            // Company name
            Label companyNameLabel = new Label(company.getName());
            companyNameLabel.setId("label-title");

            // Phone number
            HBox companyPhoneNumberPane = new HBox(5);
            companyPhoneNumberPane.setId("phone-number-box");

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

            // Comment
            VBox getCompanyNotes = getCompanyNotes();

            VBox noteAdder = noteAdder(company.getId());

            content.getChildren().addAll(companyNameLabel, companyPhoneNumberPane, companyEmailPane, getCompanyNotes, noteAdder);
        }

        scrollPane.setContent(content);

        layout.getChildren().addAll(paddingTopContent, lockPane, scrollPane);
    }

    private VBox getCompanyNotes() {
        VBox vBox = new VBox(5);
        vBox.setId("comments-box");
        ArrayList<Note> notes = company.getNotes();

        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            VBox innerVBox = new VBox(5);

            Label infoLabel = new Label("Posted by " + Intuition.Config.getDb().getDbUsername( note.getUserId()) + " | On " + Tools.convertTime(note.getTimestamp()));
            infoLabel.getStyleClass().add("label-default");
            Label commentLabel = new Label("Comment: " + note.getComment());
            commentLabel.getStyleClass().add("label-default");

            innerVBox.getChildren().addAll(infoLabel, commentLabel);

            vBox.getChildren().add(innerVBox);
        }

        return vBox;
    }

    private VBox noteAdder(int companyID) {
        VBox vBox = new VBox(5);
        vBox.setId("comment-box-pane");
        TextArea textArea = new TextArea();
        textArea.setId("comment-box");
        Button addNoteButton = new Button("Submit Note");

        Note note = new Note();

        vBox.getChildren().addAll(textArea, addNoteButton);

        addNoteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if( !textArea.getText().equals("") ) {
                    note.setCompanyId(companyID);
                    note.setUserId(Intuition.Config.getUSER().getId());
                    note.setComment(textArea.getText());

                    CompanyContainer.saveExistingNotesToDb(note);

                    // Error checking does not occur here, which means the success alert will show up whether it is successful or not.
                    // Currently put this in the backlog priority.

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Success");
                    alert.setContentText("The note was inserted into the database!");

                    alert.showAndWait();
                }
            }
        });

        return vBox;
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
            // Company name
            HBox companyName = new HBox(0);
            companyName.getStyleClass().add("unlocked-value-pane");
            Label companyNameLabel = new Label("Company:");
            companyNameLabel.getStyleClass().add("unlocked-label");

            getModifiedValues().setCompanyNameField(new TextField());

            getModifiedValues().getCompanyNameField().setText(company.getName());

            companyName.getChildren().addAll(companyNameLabel, getModifiedValues().getCompanyNameField());

            // Phone number
            HBox companyPhoneNumber = new HBox(0);
            companyPhoneNumber .getStyleClass().add("unlocked-value-pane");
            Label companyPhoneNumberLabel = new Label("Phone number:");
            companyPhoneNumberLabel.getStyleClass().add("unlocked-label");

            getModifiedValues().setCompanyPhoneNumberField(new TextField());

            getModifiedValues().getCompanyPhoneNumberField().setText(company.getPhoneNumber());

            companyPhoneNumber.getChildren().addAll(companyPhoneNumberLabel, getModifiedValues().getCompanyPhoneNumberField());

            // Email
            HBox companyEmail = new HBox(0);
            companyEmail.getStyleClass().addAll("unlocked-value-pane");
            Label companyEmailLabel = new Label("Email:");
            companyEmailLabel.getStyleClass().add("unlocked-label");

            getModifiedValues().setCompanyEmailField(new TextField());

            getModifiedValues().getCompanyEmailField().setText(company.getEmail());

            companyEmail.getChildren().addAll(companyEmailLabel, getModifiedValues().getCompanyEmailField());

            content.getChildren().addAll(companyName, companyPhoneNumber, companyEmail);
        }

        layout.getChildren().addAll(paddingTopContent, lockPane, content);
    }

    public Pane getCompanyDisplay() {
        return layout;
    }
}
