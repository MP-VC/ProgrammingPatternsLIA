package vanier.com.programmingpatternslia.App.Controllers.CurrentUser;

import vanier.com.programmingpatternslia.Model.User;

public class CurrentUser {
    private static User currentUser = null;
    private static String email = null;
    public static void setCurrentUser(User user) { currentUser = user; }
    public static User getCurrentUser() { return currentUser; }
    public static String getEmail() { return email; }
    public static void setEmail(String email) { CurrentUser.email = email; }
}
