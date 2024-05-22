package ies.comercio.laberintointerfaz;

import interfaces.ControladorGeneral;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import laberintoJuego.Juego;

/**
 * Controlador para la pantalla de inicio del juego.
 *
 * @author Quiñones Majuelo, Sergio
 */
public class InicioJuegoController implements Initializable, ControladorGeneral {

    private App main;
    private Juego juego;

    private final Image ENTRADA_LABERINTO = new Image(getClass().getResourceAsStream("/imagenes/entradaLaberinto.jpg"));

    /**
     * Establece la aplicación principal.
     *
     * @param main La aplicación principal.
     */
    public void setMain(App main) {
        this.main = main;
    }

    /**
     * Establece la referencia al juego en curso.
     *
     * @param juego El juego en curso.
     */
    @Override
    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    @FXML
    private ImageView imagenInicio;

    @FXML
    private Button botonBot;

    /**
     * Cierra la aplicación.
     */
    @FXML
    void fin() {
        Platform.exit();
    }

    /**
     * Cambia a la escena del juego.
     */
    @FXML
    void inicio() {
        main.cambiarEscena("hbA");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagenInicio.setImage(ENTRADA_LABERINTO);
    }
}
