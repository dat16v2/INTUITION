package kea.intuition.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import kea.intuition.Intuition;
import kea.intuition.Tools;
import kea.intuition.model.Company;

// ID: 1
public class IndexScreen extends IScene {
    int currentCompanySelection = 0;

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
        bodyPane.setStyle("-fx-background-color: #252f3e");

        Label authorizedText = new Label("Authorized.");
        authorizedText.setStyle("-fx-text-fill: #ffffff");

        TableView companiesTable = getCompaniesTable();
        Pane companyDisplay = getCompanyDisplay((Company) companiesTable.getItems().get(0));

        bodyPane.getChildren().add(authorizedText);

        layout.setLeft(companiesTable);
        layout.setCenter(companyDisplay);

        Tools.addDragToScene(layout, this);
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        scene.getStylesheets().add(contextClassLoader.getResource("css/companies_table_compiled.css").toExternalForm());
    }

    private Pane getCompanyDisplay(Company company) {
        VBox layout = new VBox(0);
        VBox paddingTopContent = new VBox(0);
        paddingTopContent.setStyle("-fx-background-color: #252f3e; -fx-padding: 18 0 0 0; -fx-background-insets: 0 0 0 0; -fx-min-height: 10px;");

        VBox content = new VBox(10);
        layout.setStyle("-fx-background-color: #2f3d50; -fx-padding: 0 0 0 0; -fx-background-insets: 0 0 0 0;");

        Label label = new Label(company.getName());
        label.setStyle("-fx-text-fill: #ffffff");

        content.getChildren().addAll(label);
        layout.getChildren().addAll(paddingTopContent, content);

        return layout;
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

        companiesTable.setStyle("-fx-padding: 13 0 10 19; -fx-min-width: 400px;");
        //companiesTable.setMaxWidth(600);
        //companiesTable.setMinWidth(400);
        //companiesTable.getStyleClass().add("companyTable");

        // test data
        ObservableList<Company> data = FXCollections.observableArrayList(
                new Company(1, "Phuong Quan Inc.", 4.01),
                new Company(2, "Asam Ali Corporation", 99.9),
                new Company(3, "Konstantyner", 99.99),
                new Company(4, "Emil H. Clausen Freelance", 99.9)
        );

        companiesTable.setItems(data);

        companiesTable.getSelectionModel().selectFirst();

        companiesTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Company>() {
            @Override
            public void changed(ObservableValue observable, Company oldValue, Company newValue) {
                System.out.printf("Old: %s, new: %s\n", oldValue.getName(), newValue.getName());
                currentCompanySelection = (newValue.getId() - 1);
                Pane companyDisplay = getCompanyDisplay(newValue);
                layout.setCenter(companyDisplay);
            }
        });

        return companiesTable;
    }


}