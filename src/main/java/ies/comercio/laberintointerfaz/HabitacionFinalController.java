package ies.comercio.laberintointerfaz;

import interfaces.ControladorGeneral;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import laberintoJuego.Juego;

/**
 * Controlador para la escena final del laberinto. Este controlador maneja la
 * lógica para mostrar la imagen final y cerrar la aplicación cuando se hace
 * clic en el botón correspondiente.
 *
 * Esta clase implementa la interfaz {@link Initializable} para la
 * inicialización del controlador y {@link ControladorGeneral} para permitir la
 * configuración del juego y la aplicación principal.
 *
 * @autor Quiñones Majuelo, Sergio
 */
public class HabitacionFinalController implements Initializable, ControladorGeneral {

    private App main;
    private Juego juego;

    private final Image FINAL_LABERINTO = new Image(getClass().getResourceAsStream("/imagenes/finLaberinto.jpg"));

    @FXML
    private ImageView imagenFinal;
    
    /**
     * Establece el juego asociado a esta habitación.
     * 
     * @param juego El juego asociado.
     */
    @Override
    public void setJuego(Juego juego) {
        this.juego = juego;
    }
    
      
    /**
     * Establece la aplicación principal.
     * 
     * @param main La aplicación principal.
     */
    @Override
    public void setMain(App main) {
        this.main = main;
    }
    
     /**
     * Inicializa la habitación final.
     * 
     * @param url La URL de inicialización.
     * @param rb El ResourceBundle para inicialización.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagenFinal.setImage(FINAL_LABERINTO);
    }
    
    
    /**
     * Maneja el evento de cierre de la aplicación.
     * 
     * @param event El evento de acción que desencadena el cierre de la aplicación.
     */
    @FXML
    void fin(ActionEvent event) {
        Platform.exit();
    }

}
