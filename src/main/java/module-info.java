module ies.comercio.laberintointerfaz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens ies.comercio.laberintointerfaz to javafx.fxml;
    exports ies.comercio.laberintointerfaz;
}
