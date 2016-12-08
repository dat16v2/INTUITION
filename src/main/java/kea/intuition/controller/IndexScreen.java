package kea.intuition.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import kea.intuition.Intuition;
import kea.intuition.Tools;
import kea.intuition.data.CompanyContainer;
import kea.intuition.data.DatabaseBackgroundSyncAsync;
import kea.intuition.data.SearchFieldHandler;
import kea.intuition.model.Company;

import java.util.function.Predicate;

// ID: 1
public class IndexScreen extends IScene {
    private int currentCompanySelection = 0;

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

        CompanySingularDisplay companySingularDisplay = new CompanySingularDisplay((Company) CompanyContainer.getTableStructure().getItems().get(0));
        Pane companyDisplay = companySingularDisplay.getCompanyDisplay();
        VBox companiesTablePane = new VBox(0);
        companiesTablePane.setVgrow(CompanyContainer.getTableStructure(), Priority.ALWAYS);

        TextField searchField = new TextField();
        Pane seachFieldPane = new StackPane();
        seachFieldPane.setId("search-field");
        searchField.getStyleClass().add("input-field");

        CompanyContainer.setFilteredList(new FilteredList<Company>(CompanyContainer.getData(), new Predicate<Company>() {
            @Override
            public boolean test(Company company) {
                return true;
            }
        }));

        SearchFieldHandler searchFieldHandler = new SearchFieldHandler(searchField);
        searchField.addEventHandler(KeyEvent.KEY_RELEASED, searchFieldHandler);

        CompanyContainer.setSortedList(new SortedList<Company>(CompanyContainer.getFilteredList()));

        CompanyContainer.getSortedList().comparatorProperty().bind(CompanyContainer.getTableStructure().comparatorProperty());
        CompanyContainer.getTableStructure().setItems(CompanyContainer.getSortedList());

        seachFieldPane.getChildren().add(searchField);
        companiesTablePane.getChildren().addAll(seachFieldPane, CompanyContainer.getTableStructure());

        layout.setLeft(companiesTablePane);
        layout.setCenter(companyDisplay);

        Tools.addDragToScene(layout, this);
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        scene.getStylesheets().add(contextClassLoader.getResource("css/index_screen.css").toExternalForm());

        // Start background db sync
        Thread asyncDbSync = new Thread(new DatabaseBackgroundSyncAsync(this.stage, searchField), "async-db-sync");
        asyncDbSync.start();
    }

    /*
    private Pane getCompanyDisplay(Company company) {
        VBox layout = new VBox(0);
        layout.getStyleClass().add("company-display");
        layout.setStyle("-fx-background-color: #2f3d50; -fx-padding: 0 0 0 0; -fx-background-insets: 0 0 0 0;");

        VBox paddingTopContent = new VBox(0);
        paddingTopContent.setId("padding-head");

        BorderPane lockPane = new BorderPane();
        //Lock lock = new Lock();
        //lock.getLabel().setId("lock");

        //lockPane.setRight(lock.getLabel());

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

        return layout;
    }

    */

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

                CompanySingularDisplay companySingularDisplay = new CompanySingularDisplay(newValue);
                Pane companyDisplay = companySingularDisplay.getCompanyDisplay();
                layout.setCenter(companyDisplay);
            }
        });


        return companiesTable;
    }
}