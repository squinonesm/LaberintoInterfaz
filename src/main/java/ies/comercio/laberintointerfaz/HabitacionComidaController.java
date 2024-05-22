package ies.comercio.laberintointerfaz;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.RIGHT;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * Controlador para la habitación de comida del juego. Esta habitación permite
 * al jugador elegir entre tarta y mate, con consecuencias diferentes para cada
 * elección. Si el jugador ya tiene ibuprofeno, se le curarán sus problemas de
 * salud. Si elige la tarta, podría enfermarse si es intolerante a la lactosa.
 * Si elige el mate, podría enfrentarse a problemas de estómago si está frío. En
 * ambos casos, el jugador eventualmente muere. Esta clase controla las
 * interacciones y eventos en la habitación de comida.
 *
 * @author Quiñones Majuelo, Sergio
 */
public class HabitacionComidaController extends HabitacionBase {

    private boolean evento = true;
    private final Image IMAGE_RUN = new Image(getClass().getResourceAsStream("/imagenes/rataCorriendo.jpeg"));
    private final Image TARTA = new Image(getClass().getResourceAsStream("/imagenes/tartaLaberinto.jpeg"));
    private final Image MATE = new Image(getClass().getResourceAsStream("/imagenes/mate.jpg"));
    private final Image POST_DECISION_MATE = new Image(getClass().getResourceAsStream("/imagenes/rataSaciosaLaberinto.jpg"));
    private final Image POST_DECISION_TARTA = new Image(getClass().getResourceAsStream("/imagenes/rataGolosaLaberinto.jpg"));

    private final String MENSAJE_IBUPROFENO = """
                                              LA COMIDA TE PRODUCE MALESTAR, PERO COMO TENIAS UN IBUPROFENO, 
                                              LO USAS Y CURAS TUS PROBLEMAS DE SALUD
                                              Puedes seguir avanzando/comiendo tranquil@""";
    private final String MENSAJE_TARTA = """
                                         Te comes la tarta, oh no! eres intolerante a la lactosa, te enfermas.
                                         Y decides ir al veterinario.""";

    private final String MENSAJE_MATE = "Te tomas el mate pero estaba frío, debes correr al baño";

    private final String MENSAJE_MUERTE = "Sin embargo, por el camino te resbalas y mueres.";

    private final String TEXTO_INICIAL = "Escoge entre tarta y mate, debes dar clic a uno de los dos";

    @FXML
    private TextArea cuadroTexto;

    @FXML
    private ImageView imagenA, imagenA1, muerte, postDecision;

    /**
     * Inicializa la habitación de comida.
     *
     * @param url La URL de inicialización.
     * @param rb El ResourceBundle para inicialización.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        postDecision.setVisible(false);
        imagenA.setImage(TARTA);
        imagenA1.setImage(MATE);
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
    public void avanzar(KeyEvent event) throws IOException {
        cuadroTexto.requestFocus();
        if (!evento) {
            switch (event.getCode()) {
                case RIGHT -> {
                    estadoInicial();
                    evento = true;
                    main.cambiarEscena("hbA");
                    juego.irA("este");
                }
                default ->
                    super.avanzar(event, cuadroTexto);
            }
        } else {
            cuadroTexto.setText(TEXTO_INICIAL);
        }
    }

    /**
     * Restaura el estado inicial de la habitación.
     */
    public void estadoInicial() {
        imagenA.setVisible(true);
        imagenA1.setVisible(true);
        postDecision.setVisible(false);
        imagenA.setImage(TARTA);
        imagenA1.setImage(MATE);
        cuadroTexto.setText(TEXTO_INICIAL);
    }

    /**
     * Maneja el evento de clic en la imagen del mate.
     *
     * @param event El evento de mouse.
     * @throws IOException Si hay un error de entrada/salida.
     */
    @FXML
    void mate(MouseEvent event) throws IOException {
        postDecision();
        postDecision.setImage(POST_DECISION_MATE);
        manejarDecision("M");
        evento = false;
    }

    /**
     * Maneja el evento de clic en la imagen de la tarta.
     *
     * @param event El evento de mouse.
     * @throws IOException Si hay un error de entrada/salida.
     */
    @FXML
    void tarta(MouseEvent event) throws IOException {
        postDecision();
        postDecision.setImage(POST_DECISION_TARTA);
        manejarDecision("T");
        evento = false;
    }

    public void postDecision() {
        imagenA.setVisible(false);
        imagenA1.setVisible(false);
        postDecision.setVisible(true);
    }

    /**
     * Maneja la decisión del jugador entre la tarta y el mate.
     *
     * @param decision La decisión del jugador.
     * @throws IOException Si hay un error de entrada/salida.
     */
    private void manejarDecision(String decision) throws IOException {
        cuadroTexto.setText(TEXTO_INICIAL);
        juego.actualizarDecision(decision);
        if (juego.descripcionHabitacion().equals("MEDICO")) {
            juego.irA("oeste");
        }
        evento = false;

        if (juego.tenerIbuprofeno()) {
            manejarIbuprofeno();
        } else if (decision.equals("T")) {
            manejarTarta();
        } else if (decision.equals("M")) {
            manejarMate();
        }
    }

    /**
     * Maneja el caso en que el jugador tenga ibuprofeno.
     */
    private void manejarIbuprofeno() {
        cuadroTexto.setText(MENSAJE_IBUPROFENO);
    }

    /**
     * Maneja el caso en que el jugador elija la tarta.
     */
    private void manejarTarta() {
        cuadroTexto.setText(MENSAJE_TARTA);
        muerteInevitable();
    }

    /**
     * Maneja el caso en que el jugador elija el mate.
     */
    private void manejarMate() {
        cuadroTexto.setText(MENSAJE_MATE);
        muerteInevitable();

    }

    /**
     * Maneja la muerte del jugador.
     */
    private void muerteInevitable() {
        imagenA.setVisible(false);
        imagenA1.setVisible(false);
        postDecision.setVisible(false);
        muerte.setImage(IMAGE_RUN);

        PauseTransition pause1 = new PauseTransition(Duration.seconds(5));
        pause1.setOnFinished(e -> {
            Image image3 = new Image(getClass().getResourceAsStream("/imagenes/deatj.jpg"));
            muerte.setImage(image3);
            cuadroTexto.setText(MENSAJE_MUERTE);

            PauseTransition pause2 = new PauseTransition(Duration.seconds(5));
            pause2.setOnFinished(ev -> main.cerrarAplicacion());
            pause2.play();
        });

        pause1.play();
    }

}
