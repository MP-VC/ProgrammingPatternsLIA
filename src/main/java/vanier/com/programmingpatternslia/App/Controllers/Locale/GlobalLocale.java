package vanier.com.programmingpatternslia.App.Controllers.Locale;
import java.util.Locale;
public class GlobalLocale {
    private static Locale currentLocale = Locale.CANADA;
    public static Locale getCurrentLocale() { return currentLocale; }
    public static void setCurrentLocale(Locale locale) { currentLocale = locale; }
}
