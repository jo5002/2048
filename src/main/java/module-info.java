module org.example.game2048 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.game2048 to javafx.fxml;
    exports org.example.game2048;
}