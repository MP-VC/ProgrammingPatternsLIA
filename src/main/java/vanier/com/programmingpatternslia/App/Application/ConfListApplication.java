package vanier.com.programmingpatternslia.App.Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfListApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ConfListApplication.class.getResource("/vanier/com/programmingpatternslia/UI/ConfList.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ConfList");
        stage.setMinWidth(155);
        stage.setScene(scene);
        stage.show();
    }
}
