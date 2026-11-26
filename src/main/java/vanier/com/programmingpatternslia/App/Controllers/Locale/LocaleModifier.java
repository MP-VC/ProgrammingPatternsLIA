package vanier.com.programmingpatternslia.App.Controllers.Locale;
import javafx.fxml.FXML;
import java.util.Locale;
public interface LocaleModifier {
    void updateLanguage();
    @FXML
    default void switchLanguageButton_onClick() {
        GlobalLocale.setCurrentLocale(GlobalLocale.getCurrentLocale() == Locale.CANADA ? Locale.CANADA_FRENCH : Locale.CANADA);
        updateLanguage();
    }
}
