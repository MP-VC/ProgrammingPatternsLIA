package vanier.com.programmingpatternslia.App.Controllers.Locale;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class GlobalLocale {
    private static Locale currentLocale = Locale.CANADA;
    private static final Map<String, Locale> languages = new LinkedHashMap<>();
    static {languages.put("English", Locale.ENGLISH);languages.put("Francais", Locale.FRENCH);}
    public static Locale getCurrentLocale() { return currentLocale; }
    public static void setCurrentLocale(Locale locale) { currentLocale = locale; }
    public static Map<String, Locale> getLanguages() { return languages; }
}
