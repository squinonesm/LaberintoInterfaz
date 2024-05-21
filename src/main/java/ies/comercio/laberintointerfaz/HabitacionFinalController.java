package ies.comercio.laberintointerfaz;

import interfaces.ControladorGeneral;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import laberintoJuego.Juego;

/**
 * FXML Controller class
 *
 * @author Quiñones Majuelo, Sergio
 */
public class HabitacionFinalController implements Initializable, ControladorGeneral {
    
    
    private App main;
    private Juego juego;

    @Override
    public void setJuego(Juego juego) {
        this.juego = juego;
    }
    
    @Override
     public void setMain(App main) {
        this.main = main;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
