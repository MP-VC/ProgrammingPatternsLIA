package vanier.com.programmingpatternslia.App.Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SignUpApplication.class.getResource("/vanier/com/programmingpatternslia/UI/SignUp.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("SignUp");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
