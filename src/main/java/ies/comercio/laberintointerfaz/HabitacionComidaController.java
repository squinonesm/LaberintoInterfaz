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
    private final Image imageRun = new Image(getClass().getResourceAsStream("/imagenes/run.gif"));

    @FXML
    private TextArea cuadroTexto;

    @FXML
    private ImageView imagenA, imagenA1, muerte;

    private final String TEXTO_INICIAL = "Escoge entre tarta y mate, debes dar clic a uno de los dos";

    /**
     * Inicializa la habitación de comida.
     *
     * @param url La URL de inicialización.
     * @param rb El ResourceBundle para inicialización.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image(getClass().getResourceAsStream("/imagenes/tarta.jpg"));
        imagenA.setImage(image);
        Image image2 = new Image(getClass().getResourceAsStream("/imagenes/mate.jpg"));
        imagenA1.setImage(image2);
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
            cuadroTexto.setText("Debes escoger entre la tarta y el mate");
        }
    }

    /**
     * Restaura el estado inicial de la habitación.
     */
    public void estadoInicial() {
        Image image = new Image(getClass().getResourceAsStream("/imagenes/tarta.jpg"));
        imagenA.setImage(image);
        Image image2 = new Image(getClass().getResourceAsStream("/imagenes/mate.jpg"));
        imagenA1.setImage(image2);
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
        manejarDecision("T");
        evento = false;
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
        } else {
            juego.irA("norte");
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
        cuadroTexto.setText("TENIAS UN IBUPROFENO, LO USAS Y CURAS TUS PROBLEMAS DE SALUD\nPuedes seguir avanzando");
    }

    /**
     * Maneja el caso en que el jugador elija la tarta.
     */
    private void manejarTarta() {
        cuadroTexto.setText("Te comes la tarta, oh no! eres intolerante a la lactosa, te enfermas y decides ir al veterinario para ello debes regresar al inicio.");
        muerteInevitable();
    }

    /**
     * Maneja el caso en que el jugador elija el mate.
     */
    private void manejarMate() {
        cuadroTexto.setText("Te tomas el mate pero estaba frío, debes correr al baño para ello debes regresar al inicio.");
        muerteInevitable();

    }

    /**
     * Maneja la muerte del jugador.
     */
    private void muerteInevitable() {
        imagenA.setVisible(false);
        imagenA1.setVisible(false);
        muerte.setImage(imageRun);

        PauseTransition pause1 = new PauseTransition(Duration.seconds(5));
        pause1.setOnFinished(e -> {
            Image image3 = new Image(getClass().getResourceAsStream("/imagenes/deatj.jpg"));
            muerte.setImage(image3);
            cuadroTexto.setText("Sin embargo, por el camino te resbalas y mueres");

            PauseTransition pause2 = new PauseTransition(Duration.seconds(5));
            pause2.setOnFinished(ev -> main.cerrarAplicacion());
            pause2.play();
        });

        pause1.play();
    }

}
