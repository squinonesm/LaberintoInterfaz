package ies.comercio.laberintointerfaz;

import java.io.IOException;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.N;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.UP;
import javafx.scene.input.KeyEvent;
import laberintoJuego.Juego;

/**
 * Controlador para la habitación del sueño del juego. En esta habitación, el
 * jugador puede elegir entre dormir (S) o no dormir (N). Si decide dormir, se
 * simulará el acto de dormir durante un tiempo y se producirán diferentes
 * resultados. Si decide no dormir, simplemente podrá continuar con la aventura.
 * Esta clase controla las interacciones y eventos en la habitación del sueño.
 *
 * @author Quiñones Majuelo, Sergio
 */
public class HabitacionDormirController extends HabitacionBase {

    private boolean evento = true;

    @FXML
    private ImageView imagenA;

    @FXML
    private TextArea cuadroTexto;

    private final String TEXTO_INICIAL = """
                                        BIENVENIDO A LA HABITACION DEL SUE\u00d1O
                                        \u00bfQUIERES ECHARTE UNA SIESTA?
                                        PULSA S O N""";
    private final String DESPERTAR = "TREMANDA SIESTA, TOCA PONERSE MANOS A LA OBRA";
    private final String TEXTO_DORMIR = "Durmiendo....";
    private final String TEXTO_DORMIR_CONT
            = """
              Pollito te ve durmiendo tan tranquilo despu\u00e9s de haberte indicado la salida.
               Se cabrea tanto que decide cogerte con sus patitas y tirarte al mar, 
              donde mueres ahogado.""";
    private final String TEXTO_NO_DORMIR = "A seguir la aventura";
    private final String ERROR_MESSAGE = """
                                            DEBES PULSAR S PARA ECHARTE UNA SIESTA
                                            O N PARA NO ECHARTE LA SIESTA
                                        """;

    /**
     * Inicializa la habitación del sueño.
     *
     * @param url La URL de inicialización.
     * @param rb El ResourceBundle para inicialización.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image(getClass().getResourceAsStream("/imagenes/dormir.jpg"));
        imagenA.setImage(image);
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
                case S -> {
                    if (evento) {
                        evento = false;
                        cuadroTexto.setText(TEXTO_DORMIR);
                        if (juego.devolverContador()) {
                            simularDormir(() -> {
                                cuadroTexto.setText(TEXTO_DORMIR_CONT);
                                main.cerrarAplicacion();
                            });
                        } else {
                            simularDormir(() -> {
                                cuadroTexto.setText(DESPERTAR);
                                juego.irA("sur");
                            });
                        }
                    }
                }
                case N -> {
                    if (evento) {
                        evento = false;
                        cuadroTexto.setText(TEXTO_NO_DORMIR);
                        juego.irA("sur");
                    }
                }
                case UP -> {
                    if (!evento) {
                        if (cuadroTexto.getText().equals(DESPERTAR) || cuadroTexto.getText().equals(TEXTO_NO_DORMIR)) {
                            cuadroTexto.setText(TEXTO_INICIAL);
                            evento = true;
                            main.cambiarEscena("prePollito");
                            juego.irA("norte");
                        } else {
                            cuadroTexto.setText(TEXTO_DORMIR);
                        }
                    } else {
                        cuadroTexto.setText(TEXTO_INICIAL);
                    }
                }
                default -> {
                    if (evento) {
                        cuadroTexto.setText(ERROR_MESSAGE);
                    } else if (!evento && cuadroTexto.getText().equals(TEXTO_DORMIR)) {
                        cuadroTexto.setText(TEXTO_DORMIR);
                    } else {
                        super.avanzar(event, cuadroTexto);
                    }
                }
            }
        }
    }

    /**
     * Simula el acto de dormir durante un período de tiempo determinado.
     *
     * @param action La acción a realizar después de dormir.
     */
    private void simularDormir(Runnable action) {
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> Platform.runLater(action));
        pause.play();
    }

}
