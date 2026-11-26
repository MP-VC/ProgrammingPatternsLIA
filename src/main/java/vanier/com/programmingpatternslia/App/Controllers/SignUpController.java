package vanier.com.programmingpatternslia.App.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import vanier.com.programmingpatternslia.App.Controllers.Locale.GlobalLocale;
import vanier.com.programmingpatternslia.App.Controllers.Locale.LocaleModifier;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class SignUpController implements LocaleModifier {

    @FXML
    public Button Login;
    @FXML
    public PasswordField passwordfield;
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

    private final Map<String, Locale> languages = new LinkedHashMap<>();

    @FXML
    public void initialize() {
        languages.put("English", Locale.ENGLISH);
        languages.put("Francais", Locale.FRENCH);
        backImage.setImage(new Image(
                getClass().getResource(
                        "/vanier/com/programmingpatternslia/assets/Background.gif"
                ).toExternalForm(),
                650, 366, true, true
        ));
        languagecombo.setItems(
                FXCollections.observableArrayList(languages.keySet())
        );

        for (String language : languages.keySet()) {
            if (languages.get(language).getLanguage().equals(
                    GlobalLocale.getCurrentLocale().getLanguage())) {
                languagecombo.setValue(language);
                break;
            }
        }

        languagecombo.setOnAction(event -> {
            if (languagecombo.getValue() != null) {
                switchLanguage(languagecombo.getValue());
            }
        });

        updateLanguage();
    }

    @Override
    public void updateLanguage() {
        ResourceBundle bundle = ResourceBundle.getBundle(
                "vanier.com.programmingpatternslia.Internalization.file",
                GlobalLocale.getCurrentLocale()
        );

        userfield.setPromptText(bundle.getString("textbox.fullname"));
        mailfield.setPromptText(bundle.getString("textbox.email"));
        agefield.setPromptText(bundle.getString("textbox.age"));
        passwordfield.setPromptText(bundle.getString("textbox.password"));
        passcheckfield.setPromptText(bundle.getString("textbox.passwordcheck"));
        Login.setText(bundle.getString("button.login"));

    }

}