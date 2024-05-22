package ies.comercio.laberintointerfaz;

import interfaces.ControladorGeneral;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import laberintoJuego.Juego;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Clase principal de la aplicación. Extiende la clase `Application` de JavaFX para gestionar la interfaz gráfica.
 * Esta clase carga y muestra las diferentes escenas del juego, así como gestiona la transición entre ellas.
 * 
 * @author Quiñones Majuelo, Sergio
 * @version 1.0
 * @since  22-05-2024
 */
public class App extends Application {

    private Stage stage;
    private Juego juego;
    private Map<String, Scene> escenas;
    
    /**
     * Método de inicio de la aplicación.
     * Configura el escenario principal, carga las escenas y muestra la primera escena al iniciar la aplicación.
     * 
     * @param primaryStage El escenario principal de la aplicación.
     * @throws Exception Si hay un error durante el inicio de la aplicación.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.escenas = new HashMap<>();
        this.juego = new Juego();

        // Cargar las escenas desde los archivos FXML
        cargarEscena("inicioJuego.fxml", "inicioJuego");
        cargarEscena("habitacionA.fxml", "hbA");
        cargarEscena("habitacionComida.fxml", "hbComida");
        cargarEscena("habitacionFinal.fxml", "hbFinal");
        cargarEscena("habitacionPrePollito.fxml", "prePollito");
        cargarEscena("habitacionPreNoxus.fxml", "preNoxus");
        cargarEscena("habitacionDormir.fxml", "hbDormir");

        // Mostrar la primera escena al iniciar la aplicación
        stage.setScene(escenas.get("inicioJuego"));
        stage.show();

    }
    
     /**
     * Método para cargar una escena desde un archivo FXML y asociarla a una clave en el mapa de escenas.
     * 
     * @param fxml La ruta del archivo FXML que contiene la definición de la escena.
     * @param key La clave que se utilizará para identificar la escena en el mapa de escenas.
     * @throws IOException Si hay un error al cargar la escena desde el archivo FXML.
     */
    private void cargarEscena(String fxml, String key) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        escenas.put(key, scene);

        // Obtener el controlador de la escena y establecer las referencias necesarias
        ControladorGeneral controller = loader.getController();
        controller.setMain(this);
        controller.setJuego(juego);
    }
    
    
    /**
     * Método para cambiar la escena actual por otra escena especificada por su clave.
     * 
     * @param key La clave de la escena que se desea mostrar.
     */
    public void cambiarEscena(String key) {
        stage.setScene(escenas.get(key));
    }
      
    /**
     * Método para obtener el objeto del juego.
     * 
     * @return El objeto del juego.
     */
     public Juego getJuego() {
        return juego;
    }
     
     /**
     * Método para cerrar la aplicación.
     * Espera 5 segundos antes de cerrar la aplicación.
     */
    public void cerrarAplicacion() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                Platform.runLater(Platform::exit);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    /**
     * Método principal de la aplicación. Inicia la aplicación llamando al método `launch` de la clase `Application`.
     * 
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
