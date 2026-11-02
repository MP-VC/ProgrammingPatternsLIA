module vanier.com.programmingpatternslia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens vanier.com.programmingpatternslia to javafx.fxml;
    exports vanier.com.programmingpatternslia;
}