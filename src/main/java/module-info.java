module graphic {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires json.simple;
    requires com.google.gson;

    exports view;
    opens view to javafx.fxml;

    exports model;
    opens model to com.google.gson;
}