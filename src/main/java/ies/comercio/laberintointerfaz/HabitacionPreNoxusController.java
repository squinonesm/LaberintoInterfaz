package ies.comercio.laberintointerfaz;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import laberintoJuego.Juego;

/**
 * Controlador para la habitación previa a Noxus. En esta habitación, el jugador
 * se encuentra con Noxus y tiene la opción de aceptar su ayuda o no. Si decide
 * aceptar su ayuda, se producirán diferentes resultados según si el jugador
 * tiene o no una visa. Si decide no aceptar su ayuda, Noxus lo mirará
 * fijamente. Esta clase controla las interacciones y eventos en la habitación
 * previa a Noxus.
 *
 * @author Quiñones Majuelo, Sergio
 */
public class HabitacionPreNoxusController extends HabitacionBase {

    private boolean evento = true;

    @FXML
    private ImageView imagenA;

    @FXML
    private TextArea cuadroTexto;

    private final String TEXTO_INICIAL = "¿TE ENCUENTRAS CON NOXUS QUIERES ACEPTAR SU AYUDA?\n"
            + "PULSA S PARA ACEPTAR O N PARA NO ACEPTAR";
    private final String NOXUS = "Noxus te mira fijamente";

    private final Image NOXUS_IMAGEN = new Image(getClass().getResourceAsStream("/imagenes/cat.gif"));
    private final Image VISA = new Image(getClass().getResourceAsStream("/imagenes/visa.jpg"));
    private final Image AVION = new Image(getClass().getResourceAsStream("/imagenes/avion.jpg"));

    /**
     * Inicializa la habitación previa a Noxus.
     *
     * @param url La URL de inicialización.
     * @param rb El ResourceBundle para inicialización.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagenA.setImage(NOXUS_IMAGEN);
        cuadroTexto.setText(TEXTO_INICIAL);
        cuadroTexto.setEditable(false);
        Platform.runLater(() -> cuadroTexto.requestFocus());
    }

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
     * Maneja el evento de teclado para avanzar en la habitación.
     *
     * @param event El evento de teclado.
     * @throws IOException Si hay un error de entrada/salida.
     */
    @FXML
    void avanzar(KeyEvent event) throws IOException {
        if (event.getCode() != null) {
            switch (event.getCode()) {
                case S ->
                    manejarDecision("S");
                case N ->
                    manejarDecision("N");
                case UP -> {
                    if (!evento) {
                        cuadroTexto.setText(TEXTO_INICIAL);
                        evento = true;
                        main.cambiarEscena("hbA");
                        juego.irA("norte");
                    }
                }
                default -> {
                    if (evento) {
                        cuadroTexto.setText("DEBES PULSAR S PARA ACEPTAR SU AYUDA\n"
                                + "O N PARA NO ACE"
                                + "PTARLA");
                    } else {
                        super.avanzar(event, cuadroTexto);
                    }
                }
            }
        }
    }

    /**
     * Maneja la decisión del jugador.
     *
     * @param decision La decisión del jugador (S o N).
     */
    private void manejarDecision(String decision) {
        cuadroTexto.setText(TEXTO_INICIAL);
        juego.actualizarDecision(decision);
        juego.irA("sur");
        if (decision.equals("S")) {
            if (juego.tenerVisa()) {
                manejarVisa();
            } else {
                manejarNoVisa();
            }
        } else if (decision.equals("N")) {
            evento = false;
            cuadroTexto.setText("Noxus te mira fijamente");
        }
    }

    /**
     * Maneja la situación cuando el jugador tiene una visa.
     */
    private void manejarVisa() {
        cuadroTexto.setText("Te has salvado al tener una visa, la usas y te quedas donde estabas");
        imagenA.setImage(VISA);
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                Platform.runLater(() -> {
                    cuadroTexto.setText(NOXUS);
                    evento = false;
                    imagenA.setImage(NOXUS_IMAGEN);
                });
            } catch (InterruptedException e) {
            }
        }).start();
    }

    /**
     * Maneja la situación cuando el jugador no tiene una visa.
     */
    private void manejarNoVisa() {
        cuadroTexto.setText("Aceptas la ayuda de Noxus. Este te ha engañado y te mete en un avión para deportarte.");
        imagenA.setImage(AVION);
        main.cerrarAplicacion();
    }
}
