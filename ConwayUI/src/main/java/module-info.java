module pl.aml.bk.conwayui {
    requires javafx.controls;
    requires javafx.fxml;

    requires static lombok;
    requires pl.aml.bk.core;

    opens pl.aml.bk.conwayui to javafx.fxml;
    exports pl.aml.bk.conwayui;

    opens pl.aml.bk.conwayui.components to javafx.fxml;
    exports pl.aml.bk.conwayui.components;
}
