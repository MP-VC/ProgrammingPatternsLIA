package vanier.com.programmingpatternslia.App.Controllers.Locale;
import java.util.Locale;
public interface LocaleModifier {
    void updateLanguage();
    default void switchLanguage(String language) {
        if (language == null) return;
        if (language.equals("English")) GlobalLocale.setCurrentLocale(Locale.CANADA);
        else if (language.equals("Francais") || language.equals("French")) GlobalLocale.setCurrentLocale(Locale.CANADA_FRENCH);
        updateLanguage();
    }
}