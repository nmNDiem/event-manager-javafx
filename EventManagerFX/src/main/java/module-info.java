module com.ktpm.eventmanagerfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires jbcrypt;

    opens com.ktpm.eventmanagerfx to javafx.fxml;
    exports com.ktpm.eventmanagerfx;
}
