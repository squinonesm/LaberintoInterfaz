package interfaces;

import ies.comercio.laberintointerfaz.App;
import laberintoJuego.Juego;

/**
 * Interfaz general para los controladores del juego.
 */
public interface ControladorGeneral {

    /**
     * Establece la referencia a la clase principal de la aplicación.
     * @param main La instancia de la clase principal de la aplicación.
     */
    void setMain(App main);

    /**
     * Establece la referencia al juego.
     * @param juego La instancia del juego.
     */
    void setJuego(Juego juego);
}
