package vanier.com.programmingpatternslia.App.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import vanier.com.programmingpatternslia.App.Controllers.Locale.GlobalLocale;
import vanier.com.programmingpatternslia.App.Controllers.Locale.LocaleModifier;

import java.util.*;

public class SignUpController implements LocaleModifier {

    @FXML
    public Button Login;
    @FXML
    public PasswordField passfield;
    @FXML
    public PasswordField passcheckfield;
    @FXML
    public TextField agefield;
    @FXML
    public TextField mailfield;
    @FXML
    public TextField userfield;
    @FXML
    public ComboBox<String> languagecombo;
    @FXML
    public ImageView backImage;

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
    public void loginButtonOnClick() {
        if (passcheckfield == null || agefield == null) return;

        String passcheck = passcheckfield.getText();
        String age = agefield.getText();
    }
}