package ies.comercio.laberintointerfaz;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import laberintoJuego.Juego;

/**
 * Controlador para la habitación previa al encuentro con Pollito. En esta
 * habitación, el jugador se encuentra con Pollito y tiene la opción de atacarlo
 * o hacerle una reverencia. Si el jugador tiene una fruta, también puede
 * decidir dársela a Pollito. Después de tomar una decisión, se presentan
 * opciones para avanzar a otras habitaciones. Esta clase controla las
 * interacciones y eventos en la habitación previa al encuentro con Pollito.
 *
 * @author Quiñones Majuelo, Sergio
 */
public class HabitacionPrePollitoController extends HabitacionBase {

    private boolean evento = false;
    private boolean decisionTomada = false;
    private boolean primerEncuentro = false;

    private final Image IMAGE = new Image(getClass().getResourceAsStream("/imagenes/murcielagoDecisionLaberinto.jpeg"));
    private final Image IMAGE2 = new Image(getClass().getResourceAsStream("/imagenes/murcielagoLaberintoR.jpeg"));
    private final Image IMAGE3 = new Image(getClass().getResourceAsStream("/imagenes/murcielagoLaberintoBadEnding.jpeg"));
    private final Image MUERTE = new Image(getClass().getResourceAsStream("/imagenes/muerteLaberinto.jpg"));

    private final String TEXTO_INICIAL = """
                                                TE ENCUENTRAS CON POLLITO. \u00bfQUIERES ATACARLO O HACER UNA REVERENCIA?
                                                PULSA A PARA ATACAR O R PARA HACER UNA REVERENCIA""";
    private final String MENSAJE_ATACAR = """
                                                 ATACAS A POLLITO.
                                                 PERO NO ERES RIVAL PARA SUS GARRAS.
                                                 MUERES ENTRE TERRIBLES SUFRIMIENTOS.""";
    private final String MENSAJE_ERROR = """
                                                DEBES PULSAR A PARA ATACAR
                                                O R PARA HACER UNA REVERENCIA""";
    private final String MENSAJE_ERROR3 = "DEBES PULSAR S O N, DEBES TOMAR UNA DECISION\n";

    private final String MENSAJE_POST_R = """
                                                 POLLITO TE HA DEJADO ENTRAR Y TE MIRA MIENTRAS SE COME SU FRUTA TAN RICAMENTE
                                                 [PULSA ENTER PARA CONTINUAR]""";
    private final String MENSAJE_FRUTA = """
                                                TIENES UNA FRUTA. \u00bfQUIERES D\u00c1RSELA A POLLITO?
                                                PULSA S PARA D\u00c1RSELA O N PARA NO D\u00c1RSELA""";
    private final String MENSAJE_POLLITO = "POLLITO TE MIRA FIJAMENTE";
    private final String MENSAJE_DEVORADO = "Has decicido no darle la fruta a pollito.. y VAS A SER DEVORADO";

    private final String FIN_JUEGO = "GAME OVER";

    @FXML
    private TextArea cuadroTexto;

    @FXML
    private ImageView imagenA;

    /**
     * Inicializa la habitación previa al encuentro con Pollito.
     *
     * @param url La URL de inicialización.
     * @param rb El ResourceBundle para inicialización.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagenA.setImage(IMAGE);
        cuadroTexto.setText(TEXTO_INICIAL);
        cuadroTexto.setEditable(false);
        cuadroTexto.requestFocus();
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
            if (!primerEncuentro) {
                controladorEncuentroInicial(event);
            } else if (!evento) {
                controlarTenerFruta(event);
            } else if (!decisionTomada) {
                controlarDecision(event);
            } else {
                controlarPostDecision(event);
            }
        }
    }

    /**
     * Maneja el encuentro inicial con Pollito.
     *
     * @param event El evento de teclado.
     * @throws IOException Si hay un error de entrada/salida.
     */
    private void controladorEncuentroInicial(KeyEvent event) throws IOException {
        switch (event.getCode()) {
            case A -> {
                cuadroTexto.setText(MENSAJE_ATACAR);
                imagenA.setImage(IMAGE3);
                PauseTransition pausaAtaque = new PauseTransition(Duration.seconds(1));
                pausaAtaque.setOnFinished(e -> {
                    imagenA.setImage(MUERTE);
                    cuadroTexto.setText(FIN_JUEGO);
                    PauseTransition pausaGameOver = new PauseTransition(Duration.seconds(1));
                    pausaGameOver.setOnFinished(e2 -> main.cerrarAplicacion());
                    pausaGameOver.play();
                });
                pausaAtaque.play();
            }
            case R -> {
                if (juego.saberHabitacionActual().getDescripcion().equals("POLLITO")) {
                    cuadroTexto.setText(MENSAJE_POST_R);
                    imagenA.setImage(IMAGE2);
                    juego.actualizarDecision("R");
                    primerEncuentro = true;
                } else {
                    cuadroTexto.setText(MENSAJE_POST_R);
                    imagenA.setImage(IMAGE2);
                    juego.actualizarDecision("R");
                    primerEncuentro = true;
                    juego.irA("oeste");
                }
            }
            default ->
                cuadroTexto.setText(MENSAJE_ERROR);
        }
    }

    /**
     * Maneja el desencadenante del evento.
     *
     * @param event El evento de teclado.
     * @throws IOException Si hay un error de entrada/salida.
     */
    private void controlarTenerFruta(KeyEvent event) throws IOException {
        if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
            if (juego.tenerFruta()) {
                cuadroTexto.setText(MENSAJE_FRUTA);
                evento = true;
            } else {
                cuadroTexto.setText(MENSAJE_POLLITO);
                evento = true;
                decisionTomada = true;
            }
        } else {
            cuadroTexto.setText(MENSAJE_POST_R);
        }
    }

    /**
     * Maneja la decisión del jugador.
     *
     * @param event El evento de teclado.
     * @throws IOException Si hay un error de entrada/salida.
     */
    private void controlarDecision(KeyEvent event) throws IOException {
        switch (event.getCode()) {
            case S -> {
                cuadroTexto.setText(juego.darFruta("S"));
                evento = true;
                decisionTomada = true;
            }
            case N -> {
                imagenA.setImage(IMAGE3);
                cuadroTexto.setText(MENSAJE_DEVORADO);
                PauseTransition pausaAtaque = new PauseTransition(Duration.seconds(2));
                pausaAtaque.setOnFinished(e -> {
                    imagenA.setImage(MUERTE);
                    cuadroTexto.setText(FIN_JUEGO);
                    PauseTransition pausaGameOver = new PauseTransition(Duration.seconds(2));
                    pausaGameOver.setOnFinished(e2 -> main.cerrarAplicacion());
                    pausaGameOver.play();
                });
                pausaAtaque.play();
            }
            default ->
                cuadroTexto.setText(MENSAJE_ERROR3);
        }
    }

    /**
     * Maneja la situación después de que se toma una decisión.
     *
     * @param event El evento de teclado.
     * @throws IOException Si hay un error de entrada/salida.
     */
    private void controlarPostDecision(KeyEvent event) throws IOException {
        switch (event.getCode()) {
            case LEFT -> {
                main.cambiarEscena("hbFinal");
                juego.irA("oeste");
            }
            case RIGHT -> {
                estadoInicial();
                resetEventos();
                main.cambiarEscena("hbA");
                juego.irA("este");
            }
            case DOWN -> {
                estadoInicial();
                resetEventos();
                main.cambiarEscena("hbDormir");
                juego.irA("sur");
            }
            default ->
                super.avanzar(event, cuadroTexto);
        }
    }

    /**
     * Restablece los eventos a su estado inicial.
     */
    public void resetEventos() {
        primerEncuentro = false;
        evento = false;
        decisionTomada = false;
    }

    /**
     * Restaura el estado inicial de la habitación.
     */
    public void estadoInicial() {
        Image image = new Image(getClass().getResourceAsStream("/imagenes/murcielagoDecisionLaberinto.jpeg"));
        imagenA.setImage(image);
        cuadroTexto.setText(TEXTO_INICIAL);
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
}
