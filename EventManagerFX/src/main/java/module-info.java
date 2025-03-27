module com.ktpm.eventmanagerfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.ktpm.eventmanagerfx to javafx.fxml;
    exports com.ktpm.eventmanagerfx;
}
