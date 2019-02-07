package zeiterfassungssystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Zeiterfassungssystem");
        Image icon = new Image("file:icon.png");
        primaryStage.getIcons().addAll(icon);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //Testdaten in db einfuegen
        /*Datenbank dat = new Datenbank();
        dat.db_open();
        dat.dbFuellen(1,6,"2019-02-01");*/
        launch(args);
    }
}
