module io.github {
    requires javafx.controls;
    requires javafx.fxml;

    opens io.github to javafx.fxml;
    exports io.github;
}
