import javafx.application.Application;
import javafx.stage.Stage;
import kea.intuition.Intuition;

public class main extends Application{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Intuition.Initialize(primaryStage);
    }
}