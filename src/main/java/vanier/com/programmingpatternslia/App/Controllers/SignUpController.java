package vanier.com.programmingpatternslia.App.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import vanier.com.programmingpatternslia.App.Controllers.CurrentUser.CurrentUser;
import vanier.com.programmingpatternslia.App.Controllers.Locale.GlobalLocale;
import vanier.com.programmingpatternslia.App.Controllers.Locale.LocaleModifier;
import vanier.com.programmingpatternslia.DataBase.Credential.CredentialDAO;
import vanier.com.programmingpatternslia.DataBase.User.UserDAO;
import vanier.com.programmingpatternslia.Model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class SignUpController implements LocaleModifier {

    @FXML
    public Button Login;
    @FXML
    public PasswordField passfield,passcheckfield;
    @FXML
    public TextField agefield,mailfield,userfield;
    @FXML
    public ComboBox<String> languagecombo;
    @FXML
    public ImageView backImage;
    UserDAO user = UserDAO.getInstance();
    CredentialDAO credentials = CredentialDAO.getInstance();

    @FXML
    public void initialize() {
        backImage.setImage(new Image(
                Objects.requireNonNull(getClass().getResource(
                        "/vanier/com/programmingpatternslia/assets/Background.gif"
                )).toExternalForm(),
                650, 366, true, true
        ));

        languagecombo.setItems(FXCollections.observableArrayList(GlobalLocale.getLanguages().keySet()));

        for (String language : GlobalLocale.getLanguages().keySet()) {
            if (GlobalLocale.getLanguages().get(language).getLanguage().equals(
                    GlobalLocale.getCurrentLocale().getLanguage())) {
                languagecombo.setValue(language);
                break;
            }
        }

        languagecombo.setOnAction(event -> {
            if (languagecombo.getValue() != null) {switchLanguage(languagecombo.getValue());}
        });

        updateLanguage();
    }

    @Override
    public void updateLanguage() {
        ResourceBundle bundle = ResourceBundle.getBundle(
                "vanier.com.programmingpatternslia.Internalization.file",
                GlobalLocale.getCurrentLocale()
        );

        if (userfield != null)
            userfield.setPromptText(bundle.getString("textbox.fullname"));

        if (mailfield != null)
            mailfield.setPromptText(bundle.getString("textbox.email"));

        if (agefield != null)
            agefield.setPromptText(bundle.getString("textbox.age"));

        if (passfield != null)
            passfield.setPromptText(bundle.getString("textbox.password"));

        if (passcheckfield != null)
            passcheckfield.setPromptText(bundle.getString("textbox.passwordcheck"));

        if (Login != null)
            Login.setText(bundle.getString("button.signup"));
    }

    @FXML
    public void loginButtonOnClick(ActionEvent event) throws SQLException, IOException {
        String pass = passfield.getText();
        String passcheck = passcheckfield.getText();
        String age = agefield.getText();
        String mail = mailfield.getText();
        String fname = userfield.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if (fname.isBlank() || mail.isBlank() || age.isBlank() || pass.isBlank() || passcheck.isBlank()) {
            alert.setHeaderText(null);
            alert.setContentText("Please fill every field");
            alert.showAndWait();
            return;
        }
        if (pass.length() < 8) {
            alert.setContentText("Password too short, needs 8 characters");
            alert.showAndWait();
            return;
        }
        if (!pass.equals(passcheck)) {
            alert.setContentText("Passwords don't match");
            alert.showAndWait();
            return;
        }
        int userAge;
        try { userAge = Integer.parseInt(age); }
        catch (NumberFormatException e) {
            alert.setContentText("Enter a valid age");
            alert.showAndWait();
            return;
        }
        if (userAge < 0 || userAge > 100) {
            alert.setContentText("Age has to be between 0 and 100");
            alert.showAndWait();
            return;
        }
        int userId = user.createUser(fname, userAge, "PARTICIPANT");
        CurrentUser.setCurrentUser(new User(userId,fname,userAge,"PARTICIPANT"));
        CurrentUser.setEmail(mail);
        credentials.createPassword(userId, mail, pass);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vanier/com/programmingpatternslia/UI/UserHome.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(loader.load());
    }
}