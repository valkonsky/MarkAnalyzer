module com.valkonsky.markanalyzer1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens com.valkonsky.markanalyzer1 to javafx.fxml;
    exports com.valkonsky.markanalyzer1;
}