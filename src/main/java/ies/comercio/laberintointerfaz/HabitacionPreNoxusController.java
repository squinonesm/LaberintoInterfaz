package ies.comercio.laberintointerfaz;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
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

    private final String TEXTO_INICIAL = """
                                         \u00bfTE ENCUENTRAS CON NOXUS QUIERES ACEPTAR SU AYUDA?
                                         PULSA S PARA ACEPTAR O N PARA NO ACEPTAR""";
    private final String NOXUS = "Noxus te mira fijamente";

    private final String AEROPUERTO = """
                                      TE HAS CONSEGUIDO SALVAR POR LA MINIMA, 
                                      VES A NOXUS MIRARTE CON RECELO DESDE LEJOS LO MEJOR SERA IRSE PRONTO DE AQUI""";

    private final String USAR_VISA = "FUISTE LLEVADO AL AEROPUERTO LISTO PARA SER DEPORTADO, PERO MUESTRAS TU VISA"
            + "Y TE DEJAN QUEDARTE";

    private final String TRAMPA = "Aceptas la ayuda de Noxus. Este te ha engañado y te mete en un avión para deportarte.";

    private final String FIN_JUEGO = "GAME OVER";

    private final Image NOXUS_IMAGEN = new Image(getClass().getResourceAsStream("/imagenes/gatoLaberinto.jpeg"));
    private final Image VISA = new Image(getClass().getResourceAsStream("/imagenes/visaLaberinto.jpeg"));
    private final Image AVION = new Image(getClass().getResourceAsStream("/imagenes/avionLaberinto.jpeg"));
    private final Image AEROPUERTO_VISA = new Image(getClass().getResourceAsStream("/imagenes/aeropuertoLaberinto.jpeg"));
    private final Image MUERTE = new Image(getClass().getResourceAsStream("/imagenes/muerteLaberinto.jpg"));

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
                        main.cambiarEscena("hbA");
                        juego.irA("norte");
                        estadoInicial();
                    }
                }
                default -> {
                    if (evento) {
                        cuadroTexto.setText(TEXTO_INICIAL);
                    } else {
                        super.avanzar(event, cuadroTexto);
                    }
                }
            }
        }
    }

    public void estadoInicial() {
        cuadroTexto.setText(TEXTO_INICIAL);
        evento = true;
        imagenA.setImage(NOXUS_IMAGEN);
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
            cuadroTexto.setText(NOXUS);
        }
    }

    /**
     * Maneja la situación cuando el jugador tiene una visa.
     */
    private void manejarVisa() {
        cuadroTexto.setText(USAR_VISA);
        imagenA.setImage(VISA);
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                Platform.runLater(() -> {
                    cuadroTexto.setText(AEROPUERTO);
                    evento = false;
                    imagenA.setImage(AEROPUERTO_VISA);
                });
            } catch (InterruptedException e) {
            }
        }).start();
    }

    /**
     * Maneja la situación cuando el jugador no tiene una visa.
     */
    private void manejarNoVisa() {
        cuadroTexto.setText(TRAMPA);
        imagenA.setImage(AVION);
        PauseTransition pausaAvion = new PauseTransition(Duration.seconds(3));
        pausaAvion.setOnFinished(event -> {
            imagenA.setImage(MUERTE);
            cuadroTexto.setText(FIN_JUEGO);
            PauseTransition pausaMuerte = new PauseTransition(Duration.seconds(2));
            pausaMuerte.setOnFinished(e -> main.cerrarAplicacion());
            pausaMuerte.play();
        });
        pausaAvion.play();
    }
}
