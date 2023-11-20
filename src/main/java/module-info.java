module unb.cs3035.individualproject {
    requires javafx.controls;
    requires javafx.fxml;

    opens unb.cs3035.individualproject to javafx.fxml;
    exports unb.cs3035.individualproject;
}