module vanier.com.programmingpatternslia {
    requires javafx.controls;
    requires javafx.fxml;


    opens vanier.com.programmingpatternslia to javafx.fxml;
    exports vanier.com.programmingpatternslia;
}