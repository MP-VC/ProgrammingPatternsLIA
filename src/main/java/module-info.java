module vanier.com.programmingpatternslia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    opens vanier.com.programmingpatternslia to javafx.fxml;
    exports vanier.com.programmingpatternslia;

    exports vanier.com.programmingpatternslia.App.Application;
    opens vanier.com.programmingpatternslia.App.Application to javafx.fxml;

    exports vanier.com.programmingpatternslia.App.Controllers;
    opens vanier.com.programmingpatternslia.App.Controllers to javafx.fxml;

    exports vanier.com.programmingpatternslia.Model;
    exports vanier.com.programmingpatternslia.DataBase;

    exports vanier.com.programmingpatternslia.DataBase.Registration;

    // Lines for testing(jUnit)
    opens vanier.com.programmingpatternslia.DataBase.Registration to org.junit.platform.commons;
    opens vanier.com.programmingpatternslia.Model to org.junit.platform.commons;
}
