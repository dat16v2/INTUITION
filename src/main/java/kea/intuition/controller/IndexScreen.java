package kea.intuition.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
import kea.intuition.data.Lock;
import kea.intuition.model.Company;

import java.util.function.Predicate;

// ID: 1
public class IndexScreen extends IScene {
    private int currentCompanySelection = 0;
    private ObservableList<Company> data;
    private TableView companiesTable;

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

        companiesTable = getCompaniesTable();
        CompanySingularDisplay companySingularDisplay = new CompanySingularDisplay((Company) companiesTable.getItems().get(0));
        Pane companyDisplay = companySingularDisplay.getCompanyDisplay();
        VBox companiesTablePane = new VBox(0);
        companiesTablePane.setVgrow(companiesTable, Priority.ALWAYS);

        TextField searchField = new TextField();
        Pane seachFieldPane = new StackPane();
        seachFieldPane.setId("search-field");
        searchField.getStyleClass().add("input-field");

        FilteredList<Company> filteredList = new FilteredList<Company>(data, new Predicate<Company>() {
            @Override
            public boolean test(Company company) {
                return true;
            }
        });

        searchField.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                    filteredList.setPredicate(new Predicate<Company>() {
                        @Override
                        public boolean test(Company company) {
                            if (searchField.getText().isEmpty() || searchField.getText() == null) {
                                return true;
                            }

                            String lowerCasedSearch = searchField.getText().toLowerCase();

                            if (lowerCasedSearch.contains("id:")) {
                                String id = "";
                                for (int i = 3; i < lowerCasedSearch.length(); i++) {
                                    id = id + lowerCasedSearch.charAt(i);
                                }

                                if (Integer.toString(company.getId()).equals(id)) {
                                    return true;
                                }
                            }

                            if (company.getName().toLowerCase().contains(lowerCasedSearch)) {
                                return true;
                            }

                            return false;
                        }
                    });

                    companiesTable.getSelectionModel().selectFirst();
            }
        });

        SortedList<Company> sortedData = new SortedList<Company>(filteredList);

        sortedData.comparatorProperty().bind(companiesTable.comparatorProperty());
        companiesTable.setItems(sortedData);

        seachFieldPane.getChildren().add(searchField);
        companiesTablePane.getChildren().addAll(seachFieldPane, companiesTable);

        layout.setLeft(companiesTablePane);
        layout.setCenter(companyDisplay);

        Tools.addDragToScene(layout, this);
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        scene.getStylesheets().add(contextClassLoader.getResource("css/index_screen.css").toExternalForm());
    }

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
        companyName.setCellValueFactory(new PropertyValueFactory<Company, String>("name"));

        TableColumn<Company, Double> companyScore = new TableColumn<>("Score");
        companyScore.setCellValueFactory(new PropertyValueFactory<Company, Double>("score"));

        companiesTable.getColumns().addAll(companyId, companyName, companyScore);

        companiesTable.setStyle("-fx-padding: 7 0 0 19; -fx-min-width: 400px;");
        //companiesTable.setMaxWidth(600);
        //companiesTable.setMinWidth(400);
        //companiesTable.getStyleClass().add("companyTable");

        // test data
        data = FXCollections.observableArrayList(
                new Company(1, "Phuong Quan Inc.", "phuong@phuongcorp.com",4.01, "00", "45", "20978633"),
                new Company(2, "Asam Ali Corporation", "asam@ali.io",99.9, "-1", "1", "543534"),
                new Company(3, "Konstantyner", "ssksi@konstaaaa.dk",99.99, "0011", "1", "2543123"),
                new Company(4, "Emil H. Clausen Freelance", "kekekeke@ggg.kk",99.9, "-1", "45", "01234567"),
                new Company(5, "kek", "example@example.example",4.01, "-1", "1", "0"),
                new Company(6, "demo 00", "example@example.example",99.9, "-1", "1", "0"),
                new Company(7, "demo 01", "example@example.example",99.99, "-1", "1", "0"),
                new Company(8, "demo 02", "example@example.example",99.9, "-1", "1", "0"),
                new Company(5, "kek", "example@example.example",4.01, "-1", "1", "0"),
                new Company(6, "demo 00", "example@example.example",99.9, "-1", "1", "0"),
                new Company(7, "demo 01", "example@example.example",99.99, "-1", "1", "0"),
                new Company(5, "kek", "example@example.example",4.01, "-1", "1", "0"),
                new Company(6, "demo 00", "example@example.example",99.9, "-1", "1", "0"),
                new Company(7, "demo 01", "example@example.example",99.99, "-1", "1", "0")
        );

        companiesTable.setItems(data);

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