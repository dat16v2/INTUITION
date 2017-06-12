package kea.intuition.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import kea.intuition.Intuition;
import kea.intuition.IntuitionLockEvent;
import kea.intuition.Tools;
import kea.intuition.data.CandidateContainer;
import kea.intuition.data.DatabaseBackgroundSyncAsync;
import kea.intuition.data.SearchFieldHandler;
import kea.intuition.model.Candidate;

import java.util.function.Predicate;

public class CandidateScreen extends IScene {
    private int currentCandidateSelection = 0;
    private TextField searchField;

    public CandidateScreen(Stage stage) {
        sceneId = 2;
        layout = new BorderPane();
        scene = new Scene(this.layout, Intuition.Config.getMINWIDTH(), Intuition.Config.getMINHEIGHT());
        scene.setFill(Paint.valueOf("#202936"));
        layout.setStyle("-fx-background-color: #202936");
        this.stage = stage;

        layout.setTop(Header.GetHeader(this));

        // Body

        Pane bodyPane = new StackPane();
        bodyPane.setId("body");

        CandidateContainer.setTableStructure(getCandidatesTable());

        CandidateSingularDisplay candidateSingularDisplay = new CandidateSingularDisplay((Candidate) CandidateContainer.getTableStructure().getItems().get(0), stage);
        Pane candidateDisplay = candidateSingularDisplay.getCandidateDisplay();
        VBox candidateTablePane = new VBox(0);
        candidateTablePane.setVgrow(CandidateContainer.getTableStructure(), Priority.ALWAYS); // Used to make the table scale vertically with window size.

        // Search field
        searchField = new TextField();
        Pane seachFieldPane = new StackPane();
        HBox searchFieldPaneItems = new HBox(0);

        searchFieldPaneItems.setAlignment(Pos.CENTER);
        seachFieldPane.setId("search-field");
        searchField.getStyleClass().add("input-field");

        // Add new Candidate button
        Label addSignLabel = new Label("+");
        addSignLabel.setId("add-sign-label");

        addSignLabel.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addSignLabel.setId("add-sign-label-hover");
            }
        });

        addSignLabel.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addSignLabel.setId("add-sign-label");
            }
        });

        addSignLabel.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addSignLabel.setId("add-sign-label-clicked");
            }
        });

        /*
        addSignLabel.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addSignLabel.setId("add-sign-label-hover");
                CandidateCreationSingularDisplay candidateCreationSingularDisplay = new CandidateCreationSingularDisplay(stage);
                layout.setCenter(CandidateCreationSingularDisplay.getLayout());
                // Initiates IntuitionLockEvent, locking certain UI elements.
                IntuitionLockEvent.fireEvent(stage, new IntuitionLockEvent(null, null, IntuitionLockEvent.LOCK_CHANGED_ROOT_EVENT, false));
            }
        });*/


        CandidateContainer.setFilteredList(new FilteredList<Candidate>(CandidateContainer.getData(), new Predicate<Candidate>() {
            @Override
            public boolean test(Candidate candidate) {
                return true;
            }
        }));

        // Add sorting to search field

        SearchFieldHandler searchFieldHandler = new SearchFieldHandler(searchField);
        searchField.addEventHandler(KeyEvent.KEY_RELEASED, searchFieldHandler);

        CandidateContainer.setSortedList(new SortedList<Candidate>(CandidateContainer.getFilteredList()));

        CandidateContainer.getSortedList().comparatorProperty().bind(CandidateContainer.getTableStructure().comparatorProperty());
        CandidateContainer.getTableStructure().setItems(CandidateContainer.getSortedList());

        searchFieldPaneItems.getChildren().addAll(searchField, addSignLabel);
        seachFieldPane.getChildren().add(searchFieldPaneItems);
        candidateTablePane.getChildren().addAll(seachFieldPane, CandidateContainer.getTableStructure());

        layout.setLeft(candidateTablePane);
        layout.setCenter(candidateDisplay);

        Tools.addDragToScene(layout, this);
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        scene.getStylesheets().add(contextClassLoader.getResource("css/index_screen.css").toExternalForm());

        // Start background db sync
        Thread asyncDbSync = new Thread(new DatabaseBackgroundSyncAsync(this.stage, searchField), "async-db-sync");
        asyncDbSync.start();

        // Handle UI lock event
        scene.addEventHandler(IntuitionLockEvent.LOCK_CHANGED_EVENT, new EventHandler<IntuitionLockEvent>() {
            @Override
            public void handle(IntuitionLockEvent event) {
                System.out.println("Event handled");

                if (event.newLockState() == true) {
                    searchField.setDisable(false);
                    CandidateContainer.getTableStructure().setDisable(false);
                } else if (event.newLockState() == false) {
                    searchField.setDisable(true);
                    CandidateContainer.getTableStructure().setDisable(true);
                }
            }
        });
    }

    private TableView getCandidatesTable() {
        TableView candidatesTable = new TableView();

        // Add table columns
        TableColumn<Candidate, Integer> candidateId = new TableColumn<>("ID");
        candidateId.setCellValueFactory(new PropertyValueFactory<Candidate, Integer>("id"));

        TableColumn<Candidate, String> candidateName = new TableColumn<>("Name");
        candidateName.setMinWidth(300);
        candidateName.setCellValueFactory(new PropertyValueFactory<Candidate, String>("name"));

        candidatesTable.getColumns().addAll(candidateId, candidateName);

        candidatesTable.setStyle("-fx-padding: 7 0 0 19; -fx-min-width: 400px;");

        // Set table data
        CandidateContainer.setData(CandidateContainer.getCandidatesFromDb());

        candidatesTable.setItems(CandidateContainer.getData());

        candidatesTable.getSelectionModel().selectFirst();

        candidatesTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Candidate>() {
            @Override
            public void changed(ObservableValue observable, Candidate oldValue, Candidate newValue) {
                if (newValue == null) {
                    currentCandidateSelection = (0);
                } else {
                    currentCandidateSelection = (newValue.getId() - 1);
                }


                if (newValue != null) {
                    if (!Tools.validateCandidateHash(newValue, CandidateContainer.getCurrentCandidateHash())) {
                        CandidateSingularDisplay candidateSingularDisplay = new CandidateSingularDisplay(newValue, stage);
                        Pane candidateDisplay = candidateSingularDisplay.getCandidateDisplay();
                        layout.setCenter(candidateDisplay);
                    }
                }
            }
        });


        return candidatesTable;
    }

    public static class Button {
        private javafx.scene.control.Button button;

        public Button(String buttonText) {
            button = new javafx.scene.control.Button(buttonText);
            button.getStyleClass().add("button");

            button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.getStyleClass().clear();
                    button.getStyleClass().add("button-entered");
                }
            });

            button.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.getStyleClass().clear();
                    button.getStyleClass().add("button-pressed");
                }
            });

            button.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.getStyleClass().clear();
                    button.getStyleClass().add("button-released");
                }
            });

            button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.getStyleClass().clear();
                    button.getStyleClass().add("button");
                }
            });
        }

        public javafx.scene.control.Button getButton() {
            return button;
        }

        public void addButtonHandler(IndexScreen.ButtonHandler type) {
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    type.handle();
                }
            });
        }

    }

    public interface ButtonHandler  {
        void handle();
    }

    public TextField getSearchField() {
        return searchField;
    }
}
