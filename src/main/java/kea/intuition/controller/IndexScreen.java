package kea.intuition.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import kea.intuition.Intuition;
import kea.intuition.IntuitionLockEvent;
import kea.intuition.Tools;
import kea.intuition.data.CompanyContainer;
import kea.intuition.data.DatabaseBackgroundSyncAsync;
import kea.intuition.data.SearchFieldHandler;
import kea.intuition.model.Company;

import java.util.function.Predicate;

// ID: 1
public class IndexScreen extends IScene {
    private int currentCompanySelection = 0;
    private TextField searchField;

    public IndexScreen(Stage stage) {
        sceneId = 1;
        layout = new BorderPane();
        layout.setBackground(new Background(new BackgroundFill(Paint.valueOf("#252f3e"), CornerRadii.EMPTY, Insets.EMPTY)));
        scene = new Scene(this.layout, Intuition.Config.getMINWIDTH(), Intuition.Config.getMINHEIGHT(), Paint.valueOf("#252f3e"));
        scene.setFill(Paint.valueOf("#202936"));
        layout.setStyle("-fx-background-color: #202936");
        this.stage = stage;

        layout.setTop(Header.GetHeader(this));

        // Body

        Pane bodyPane = new StackPane();
        bodyPane.setId("body");

        CompanyContainer.setTableStructure(getCompaniesTable());

        CompanySingularDisplay companySingularDisplay = new CompanySingularDisplay((Company) CompanyContainer.getTableStructure().getItems().get(0), stage);
        Pane companyDisplay = companySingularDisplay.getCompanyDisplay();
        VBox companiesTablePane = new VBox(0);
        companiesTablePane.setVgrow(CompanyContainer.getTableStructure(), Priority.ALWAYS);

        searchField = new TextField();
        Pane seachFieldPane = new StackPane();
        HBox searchFieldPaneItems = new HBox(0);

        // Search field

        searchFieldPaneItems.setAlignment(Pos.CENTER);
        seachFieldPane.setId("search-field");
        searchField.getStyleClass().add("input-field");

        // Add new company button
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

        addSignLabel.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addSignLabel.setId("add-sign-label-hover");
                CompanyCreationSingularDisplay companyCreationSingularDisplay = new CompanyCreationSingularDisplay(stage);
                layout.setCenter(companyCreationSingularDisplay.getLayout());
                IntuitionLockEvent.fireEvent(stage, new IntuitionLockEvent(null, null, IntuitionLockEvent.LOCK_CHANGED_ROOT_EVENT, false));
            }
        });


        CompanyContainer.setFilteredList(new FilteredList<Company>(CompanyContainer.getData(), new Predicate<Company>() {
            @Override
            public boolean test(Company company) {
                return true;
            }
        }));

        // Add sorting to search field

        SearchFieldHandler searchFieldHandler = new SearchFieldHandler(searchField);
        searchField.addEventHandler(KeyEvent.KEY_RELEASED, searchFieldHandler);

        CompanyContainer.setSortedList(new SortedList<Company>(CompanyContainer.getFilteredList()));

        CompanyContainer.getSortedList().comparatorProperty().bind(CompanyContainer.getTableStructure().comparatorProperty());
        CompanyContainer.getTableStructure().setItems(CompanyContainer.getSortedList());

        searchFieldPaneItems.getChildren().addAll(searchField, addSignLabel);
        seachFieldPane.getChildren().add(searchFieldPaneItems);
        companiesTablePane.getChildren().addAll(seachFieldPane, CompanyContainer.getTableStructure());

        layout.setLeft(companiesTablePane);
        layout.setCenter(companyDisplay);

        Tools.addDragToScene(layout, this);
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        scene.getStylesheets().add(contextClassLoader.getResource("css/index_screen.css").toExternalForm());

        // Start background db sync
        Thread asyncDbSync = new Thread(new DatabaseBackgroundSyncAsync(this.stage, searchField), "async-db-sync");
        asyncDbSync.start();

        scene.addEventHandler(IntuitionLockEvent.LOCK_CHANGED_EVENT, new EventHandler<IntuitionLockEvent>() {
            @Override
            public void handle(IntuitionLockEvent event) {
                System.out.println("Event handled");

                if (event.newLockState() == true) {
                    searchField.setDisable(false);
                    CompanyContainer.getTableStructure().setDisable(false);
                } else if (event.newLockState() == false) {
                    searchField.setDisable(true);
                    CompanyContainer.getTableStructure().setDisable(true);
                }
            }
        });
    }

    private void setTextFieldWidthProperly(TextField value) {
        value.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                /*
                TextLayout layout = Toolkit.getToolkit().getTextLayoutFactory().createLayout();
                layout.setContent(value.getText() != null ? value.getText() : "", value.getFont().impl_getNativeFont());
                layout.setWrapWidth((float)0.0D);

                value.setPrefWidth(layout.getBounds().getWidth());
                */
                value.setPrefColumnCount(value.getText().length() + 1);
            }
        });
    }

    private TableView getCompaniesTable() {
        TableView companiesTable = new TableView();

        TableColumn<Company, Integer> companyId = new TableColumn<>("ID");
        companyId.setCellValueFactory(new PropertyValueFactory<Company, Integer>("id"));

        TableColumn<Company, String> companyName = new TableColumn<>("Name");
        companyName.setMinWidth(300);
        companyName.setCellValueFactory(new PropertyValueFactory<Company, String>("name"));

        companiesTable.getColumns().addAll(companyId, companyName);

        companiesTable.setStyle("-fx-padding: 7 0 0 19; -fx-min-width: 400px;");
        //companiesTable.setMaxWidth(600);
        //companiesTable.setMinWidth(400);
        //companiesTable.getStyleClass().add("companyTable");

        // test data
        CompanyContainer.setData(CompanyContainer.getCompaniesFromDb());

        companiesTable.setItems(CompanyContainer.getData());

        companiesTable.getSelectionModel().selectFirst();

        companiesTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Company>() {
            @Override
            public void changed(ObservableValue observable, Company oldValue, Company newValue) {
                if (newValue == null) {
                    currentCompanySelection = (0);
                } else {
                    currentCompanySelection = (newValue.getId() - 1);
                }


                if (newValue != null) {
                    if (!Tools.validateCompanyHash(newValue, CompanyContainer.getCurrentCompanyHash())) {
                        CompanySingularDisplay companySingularDisplay = new CompanySingularDisplay(newValue, stage);
                        Pane companyDisplay = companySingularDisplay.getCompanyDisplay();
                        layout.setCenter(companyDisplay);
                    }
                }
            }
        });


        return companiesTable;
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

            button.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    /*
                    for (int i = 0; i < button.getStyleClass().size(); i++) {
                        System.out.println(button.getStyleClass().get(i));
                    }
                    */

                }
            });
        }

        public javafx.scene.control.Button getButton() {
            return button;
        }

        public void addButtonHandler(ButtonHandler type) {
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