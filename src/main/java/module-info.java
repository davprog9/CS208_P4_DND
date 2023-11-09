module com.example.cs208_assignment4 {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.cs208_assignment4 to javafx.fxml;
    exports com.example.cs208_assignment4;
}