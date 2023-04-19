module com.example.buttontextfield {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.buttontextfield to javafx.fxml;
    exports com.example.buttontextfield;
}