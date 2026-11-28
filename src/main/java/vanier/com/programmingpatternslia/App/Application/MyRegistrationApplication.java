package vanier.com.programmingpatternslia.App.Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MyRegistrationApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MyRegistrationApplication.class.getResource("/vanier/com/programmingpatternslia/UI/MyRegistrations.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MyRegistrations");
        stage.setScene(scene);
        stage.show();
    }
}
