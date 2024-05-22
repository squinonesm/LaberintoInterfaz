package ies.comercio.laberintointerfaz;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import laberintoJuego.Habitacion;
import laberintoJuego.Juego;

/**
 * Controlador para la habitación A del juego. Esta habitación presenta una
 * imagen y un cuadro de texto donde se muestra la descripción de la habitación
 * actual. Además, permite al usuario avanzar a través de las habitaciones
 * utilizando eventos de teclado y muestra una imagen correspondiente a cada
 * habitación.
 *
 * @author Quiñones Majuelo, Sergio
 */
public class HabitacionAController extends HabitacionBase {

    protected boolean decision = true;

    private final String ENCONTRAR_IBUPROFENO = "HAS ENCONTRADO UN IBUPROFENO";
    private final String ENCONTRAR_VISA = "HAS ENCONTRADO UN VISA";
    private final String ENCONTRAR_FRUTA = "HAS ENCONTRADO UN FRUTA";

    private final Image IBUPROFENO = new Image(getClass().getResourceAsStream("/imagenes/ibuprofenoLaberinto.jpeg"));
    private final Image VISA = new Image(getClass().getResourceAsStream("/imagenes/visaItemLaberinto.jpeg"));
    private final Image FRUTA = new Image(getClass().getResourceAsStream("/imagenes/frutaLaberinto.jpeg"));
    private final Image IA = new Image(getClass().getResourceAsStream("/imagenes/ia.gif"));
    private final Image FOTO_INICIAL = new Image(getClass().getResourceAsStream("/imagenes/Chupivilla2.1 map.jpg"));

    @FXML
    private Button botonBot;

    @FXML
    private TextArea cuadroTexto;

    @FXML
    private ImageView imagenA;

    /**
     * Establece la referencia a la instancia principal de la aplicación.
     *
     * @param main La instancia principal de la aplicación.
     */
    @Override
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
        cuadroTexto.setText(juego.bienvenido());
    }

    /**
     * Inicializa la vista de la habitación. Carga una imagen predeterminada y
     * establece el cuadro de texto como no editable.
     *
     * @param url La ubicación del archivo FXML.
     * @param rb Recursos específicos de la localidad.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imagenA.setImage(FOTO_INICIAL);
        cuadroTexto.setEditable(false);
        cuadroTexto.requestFocus();

    }

    /**
     * Método manejador de eventos para el botón de prueba. Cambia la imagen y
     * muestra un mensaje en el cuadro de texto. Intenta cargar comandos desde
     * un archivo de texto para realizar un recorrido óptimo.
     *
     * @param event El evento del botón.
     */
    @FXML
    void fichero(ActionEvent event) {
        imagenA.setImage(IA);
        cuadroTexto.setText("DEJA A LA IA JUGAR TRANQUILA.");
        botonBot.setDisable(true);
        System.out.println("Botón presionado. Intentando cargar comandos desde fichero.");
        try {
            cargarComandosDesdeFichero("recorrido_optimo.txt");
        } catch (IOException e) {
        }
    }

    /**
     * Método manejador de eventos para avanzar en el juego. Llama al método
     * avanzar de la superclase y actualiza la vista de la habitación.
     *
     * @param event El evento de teclado.
     * @throws IOException Si hay un error al avanzar en el juego.
     */
    @FXML
    public void avanzar(KeyEvent event) throws IOException {
        if (event.getCode() != null) {
            switch (event.getCode()) {
                case O -> {
                    cuadroTexto.setText(juego.olfatear());
                    switch (cuadroTexto.getText()) {
                        case ENCONTRAR_IBUPROFENO ->
                            imagenA.setImage(IBUPROFENO);
                        case ENCONTRAR_FRUTA ->
                            imagenA.setImage(FRUTA);
                        case ENCONTRAR_VISA ->
                            imagenA.setImage(VISA);
                        default -> {
                        }
                    }
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(e -> {
                    actualizarVista();
                    cuadroTexto.setText(actualizarTexto());
                    });
                    pause.play();
                    return;
                }
                default -> {
                    super.avanzar(event, cuadroTexto);
                }
            }
        }
        actualizarVista();
        cuadroTexto.requestFocus();
    }

    /**
     * Actualiza la vista de la habitación. Muestra la imagen correspondiente a
     * la habitación actual y ajusta la visibilidad del botón según la
     * descripción de la habitación.
     */
    void actualizarVista() {

        Habitacion habitacionActual = juego.saberHabitacionActual();

        // Establecer la visibilidad del botón según la descripción de la habitación
        botonBot.setVisible(habitacionActual.getDescripcion().equals("INICIAL"));

        // Determinar qué imagen cargar según la habitación actual
        String imagenPath;
        imagenPath = switch (habitacionActual.getDescripcion()) {
            case "MEDICO" ->
                "/imagenes/medicoLaberinto.jpeg";
            case "VIAJERO" ->
                "/imagenes/npcHabitacion.jpeg";
            case "HABITACION DE PASO" ->
                "/imagenes/npc2Habitacion.jpeg";
            case "HABITACION DE PASO 2" ->
                "/imagenes/npcHabitacion.jpeg";
            case "FRUTERO" ->
                "/imagenes/npc2Habitacion.jpeg";
            case "NOXUS" ->
                "/imagenes/gatoLaberinto.jpeg";
            case "POLLITO" ->
                "/imagenes/murcielagoDecisionLaberinto.jpeg";
            default ->
                "/imagenes/Chupivilla2.1 map.jpg";
        };

        // Cargar la imagen
        Image image = new Image(getClass().getResourceAsStream(imagenPath));
        imagenA.setImage(image);
    }
}
