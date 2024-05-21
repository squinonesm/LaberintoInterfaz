package ies.comercio.laberintointerfaz;

import interfaces.ControladorGeneral;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import laberintoJuego.Juego;

/**
 * Controlador para la pantalla de inicio del juego.
 * 
 * @author Quiñones Majuelo, Sergio
 */
public class InicioJuegoController implements ControladorGeneral {

    private App main;
    private Juego juego;
 
    /**
     * Establece la aplicación principal.
     *
     * @param main La aplicación principal.
     */
    public void setMain(App main) {
        this.main = main;
    }

    @Override
    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    @FXML
    private Button botonBot;

    /**
     * Cierra la aplicación.
     */
    @FXML
    void fin() {
        main.cerrarAplicacion();
    }

    /**
     * Cambia a la escena del juego.
     */
    @FXML
    void inicio() {
        main.cambiarEscena("hbA");
    }
}
