package ies.comercio.laberintointerfaz;

import interfaces.ControladorGeneral;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import laberintoJuego.Habitacion;
import laberintoJuego.Juego;

/**
 * Clase abstracta que sirve como base para las habitaciones del juego. Contiene
 * métodos comunes y lógica compartida entre las habitaciones.
 *
 * @author Quiñones Majuelo, Sergio
 */
public abstract class HabitacionBase implements Initializable, ControladorGeneral {

    protected App main;
    protected Juego juego;

    protected String COMANDO_ERRONEO = "ESE COMANDO NO EXISTE DEBERIAS REVISARLOS USANDO H";

    private final String HABITACION_MEDICO = """
                                             BIENVENIDO A LA HABITACION DEL MEDICO
                                             PARECE SER QUE ES UNA HABITACION LLENA DE MEDICINAS, PUEDE SER QUE HAYA ALGO UTIL""";
    private final static String HABITACION_PASO_2 = "PARECE UN BOSQUE NORMALITO";
    private final static String HABITACION_PASO = """
                                                  PARECE UN BOSQUE NORMALITO, PERO A LO LEJOS SE VEN UNOS ARBOLES MUY FRONDOSOS LLENOS DE FRUTA,
                                                   QUIZAS DEBERIA IR HACIA ALLI""";
    private final String HABITACION_FRUTERO = "ES UN BOSQUE LLENO DE FRUTA!!!!";
    private final String HABITACION_VIAJERO = """
                                              CUENTA LA LEYENDA QUE EN ESTE BOSQUE UN VIAJERO PERDIO SUS PERTENENCIAS
                                              SERIA INTERESANTE INVESTIGAR""";
    private final String MENSAJE_ERROR = "DIRECCIÓN NO VÁLIDA";

    /**
     * Carga una serie de comandos desde un archivo y ejecuta cada uno de ellos,
     * almacenando los resultados en un archivo de historial.
     *
     * @param rutaArchivo La ruta del archivo que contiene los comandos.
     * @throws IOException Si hay un error de entrada/salida al leer o escribir
     * archivos.
     */
    public void cargarComandosDesdeFichero(String rutaArchivo) throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/" + rutaArchivo); BufferedReader br = new BufferedReader(new InputStreamReader(inputStream)); FileWriter fw = new FileWriter("historial.txt", false); PrintWriter pw = new PrintWriter(fw)) {

            if (inputStream == null) {
                throw new IOException("Archivo no encontrado: " + rutaArchivo);
            }

            String comando;
            while ((comando = br.readLine()) != null) {
                String resultado = ejecutarComando(comando);
                pw.println("Comando: " + comando);
                pw.println("Resultado: " + resultado);
                pw.println();
            }

        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Ejecuta un comando proporcionado y devuelve el resultado como una cadena
     * de texto. El comando se mapea a un evento de teclado y se avanza en la
     * habitación con él.
     *
     * @param comando El comando a ejecutar.
     * @return El resultado de ejecutar el comando como una cadena de texto.
     * @throws IOException Si hay un error de entrada/salida al avanzar en la
     * habitación.
     */
    private String ejecutarComando(String comando) throws IOException {
        KeyEvent keyEvent = mapComandoToKeyEvent(comando);
        if (keyEvent != null) {
            TextArea tempCuadroTexto = new TextArea();
            avanzar(keyEvent, tempCuadroTexto);
            return tempCuadroTexto.getText();
        }
        return "Comando no reconocido: " + comando;
    }

    /**
     * Mapea un comando de texto a un evento de teclado según el comando
     * proporcionado.
     *
     * @param comando El comando de texto a mapear.
     * @return El evento de teclado mapeado, o null si el comando no tiene un
     * evento asociado.
     */
    private KeyEvent mapComandoToKeyEvent(String comando) {
        return switch (comando.toUpperCase()) {
            case "O" ->
                new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.O, false, false, false, false);
            case "I" ->
                new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.I, false, false, false, false);
            case "H" ->
                new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.H, false, false, false, false);
            case "P" ->
                new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.P, false, false, false, false);
            case "F" ->
                new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.F, false, false, false, false);
            case "B" ->
                new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.B, false, false, false, false);
            case "UP" ->
                new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, false, false, false);
            case "DOWN" ->
                new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, false, false, false);
            case "LEFT" ->
                new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.LEFT, false, false, false, false);
            case "RIGHT" ->
                new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.RIGHT, false, false, false, false);
            default ->
                null;
        };
    }

    /**
     * Establece el controlador principal de la aplicación.
     *
     * @param main La instancia principal de la aplicación.
     */
    @Override
    public void setMain(App main) {
        this.main = main;
    }

    /**
     * Establece el juego asociado a la habitación.
     *
     * @param juego La instancia del juego.
     */
    @Override
    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    /**
     * Maneja el evento de avanzar en la habitación basado en la entrada del
     * teclado.
     *
     * @param event El evento de teclado.
     * @param cuadroTexto El área de texto donde mostrar los resultados.
     * @throws IOException Si hay un error de entrada/salida.
     */
    public void avanzar(KeyEvent event, TextArea cuadroTexto) throws IOException {
        if (null != event.getCode()) {
            switch (event.getCode()) {
                case O ->
                    cuadroTexto.setText(juego.olfatear());
                case I ->
                    cuadroTexto.setText(juego.verInventario());
                case H ->
                    cuadroTexto.setText(juego.ayuda());
                case P ->
                    cuadroTexto.setText(juego.rastrear());
                case F -> {
                    cuadroTexto.setText("Finalizando el juego...");
                    main.cerrarAplicacion();
                }
                case B -> {
                    cuadroTexto.setText(juego.bienvenido());
                }
                case UP -> {
                    cuadroTexto.setText(juego.irA("norte"));
                    if (!cuadroTexto.getText().equals(MENSAJE_ERROR)) {
                        cuadroTexto.setText(actualizarTexto());
                    }
                }
                case DOWN -> {
                    switch (juego.descripcionHabitacion()) {
                        case "VIAJERO" -> {
                            cuadroTexto.setText(HABITACION_VIAJERO);
                            main.cambiarEscena("preNoxus");
                        }
                        default -> {
                            cuadroTexto.setText(juego.irA("sur"));
                            if (!cuadroTexto.getText().equals(MENSAJE_ERROR)) {
                                cuadroTexto.setText(actualizarTexto());
                            }
                        }
                    }
                }
                case LEFT -> {
                    switch (juego.descripcionHabitacion()) {
                        case "FRUTERO" -> {
                            cuadroTexto.setText(HABITACION_FRUTERO);
                            main.cambiarEscena("prePollito");
                        }
                        case "MEDICO" -> {
                            cuadroTexto.setText(HABITACION_MEDICO);
                            main.cambiarEscena("hbComida");
                        }
                        default -> {
                            cuadroTexto.setText(juego.irA("oeste"));
                            if (!cuadroTexto.getText().equals(MENSAJE_ERROR)) {
                                cuadroTexto.setText(actualizarTexto());
                            }
                        }
                    }
                }
                case RIGHT -> {
                    cuadroTexto.setText(juego.irA("este"));
                    if (!cuadroTexto.getText().equals(MENSAJE_ERROR)) {
                        cuadroTexto.setText(actualizarTexto());
                    }
                }
                default ->
                    cuadroTexto.setText(COMANDO_ERRONEO);
            }
        }

    }

    /**
     * Actualiza el texto mostrado en el área de texto de acuerdo con la
     * habitación actual del juego.
     *
     * @return El texto actualizado para mostrar en el área de texto.
     */
    public String actualizarTexto() {
        Habitacion habitacionActual = juego.saberHabitacionActual();

        switch (habitacionActual.getDescripcion()) {
            case "MEDICO" -> {
                return HABITACION_MEDICO;
            }
            case "VIAJERO" -> {
                return HABITACION_VIAJERO;
            }
            case "HABITACION DE PASO" -> {
                return HABITACION_PASO;
            }
            case "HABITACION DE PASO 2" -> {
                return HABITACION_PASO_2;
            }
            case "FRUTERO" -> {
                return HABITACION_FRUTERO;
            }
            default -> {
                return "INICIAL";
            }
        }

    }
}
