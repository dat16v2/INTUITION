package kea.intuition.controller;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import kea.intuition.Intuition;
import kea.intuition.Tools;
import kea.intuition.data.CandidateContainer;
import kea.intuition.data.CandidateLock;
import kea.intuition.data.Lock;
import kea.intuition.data.ModifiedValues;
import kea.intuition.model.Candidate;

import java.util.ArrayList;

public class CandidateSingularDisplay {
    private Pane layout;
    private CandidateLock lock;
    private Candidate candidate;
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

    public Candidate getCandiate() {
        return candidate;
    }

    public Stage getStage() {
        return stage;
    }

    public CandidateSingularDisplay(Candidate candidate, Stage stage) {
        this.stage = stage;
        lock = new CandidateLock(this); // Used alongside IntuitionLockEvent to handling locking of UI elements.
        this.candidate = candidate;
        this.layout = new VBox(0);
        if (candidate != null) {
            this.integrityHash = Tools.getCandidateHash(candidate);
            CandidateContainer.setCurrentCandidateHash(candidate.getHash());
            System.out.printf("Setting new candidate hash(%s): %s\n", candidate.getName(),CandidateContainer.getCurrentCandidateHash());
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

        if (candidate != null) {
            // Company name
            Label candidateNameLabel = new Label(candidate.getName());
            candidateNameLabel.setId("label-title");

            // Phone number
            Label candidatePhoneNumberLabel = new Label(candidate.getPhone());
            candidatePhoneNumberLabel.getStyleClass().add("text-label");

            // Email
            Label candidateEmailLabel = new Label(candidate.getEmail());
            candidateEmailLabel.getStyleClass().add("text-label");

            // description
            Label candidateDescriptionLabel = new Label(candidate.getDescription());
            candidateDescriptionLabel.getStyleClass().add("text-label");

            content.getChildren().addAll(candidateNameLabel, candidatePhoneNumberLabel, candidateEmailLabel, candidateDescriptionLabel);
        }

        scrollPane.setContent(content);

        layout.getChildren().addAll(paddingTopContent, lockPane, scrollPane);
    }

    public void setUnlockedLayout() {
    }

    public Pane getCandidateDisplay() {
        return layout;
    }
}
