package laberintoJuego;

import habitaciones.HabitacionConItem;
import habitaciones.HabitacionMerienda;
import habitaciones.HabitacionNoxus;
import habitaciones.HabitacionPollito;
import interfaces.OlorFuerte;

/**
 * Clase principal que gestiona la lógica del juego.
 * 
 * @author Quiñones Majuelo, Sergio
 */
public final class Juego {

    private Habitacion habitacionActual;
    private Habitacion habitacionInicial;
    private Habitacion habitacionFinal;
    private int visa = 0, fruta = 0, ibuprofeno = 0, visasUsadas = 0, ibuprofenosUsados = 0;
    private String decision = "";
    private int contador = 0; // Para verificar si le has dado fruta a Pollito

    
    /**
     * Constructor de la clase Juego. Crea las habitaciones y establece la habitación actual como la inicial.
     */
    public Juego() {
        crearHabitaciones();
        habitacionActual = habitacionInicial;
    }

    /**
     * Crea las habitaciones del laberinto y sus conexiones.
     */
    public void crearHabitaciones() {

        habitacionInicial = new Habitacion("INICIAL");
        Habitacion habitacionA = new HabitacionConItem("MEDICO", "Ibuprofeno");
        Habitacion habitacionB = new HabitacionConItem("VIAJERO", "VISA");
        Habitacion habitacionC = new HabitacionNoxus("NOXUS");
        Habitacion habitacionD = new Habitacion("HABITACION DE PASO");
        Habitacion habitacionE = new Habitacion("HABITACION DE PASO 2");
        Habitacion habitacionF = new HabitacionMerienda("HABITACION DE LA GULA");
        Habitacion habitacionG = new HabitacionConItem("FRUTERO", "FRUTA");
        Habitacion habitacionH = new HabitacionPollito("POLLITO");
        Habitacion habitacionJ = new Habitacion("HABITACION DEL SUEÑO!!");
        habitacionFinal = new Habitacion("SALIDA");

        // Salidas Habitacion Inicial
        habitacionInicial.setSalida("oeste", habitacionA);
        habitacionInicial.setSalida("sur", habitacionB);

        // Salidas habitacion olfateables
        // HabitacionB
        habitacionB.setSalida("sur", habitacionC);
        habitacionB.setSalida("norte", habitacionInicial);

        // HabitacionA
        habitacionA.setSalida("sur", habitacionD);
        habitacionA.setSalida("este", habitacionInicial);
        habitacionA.setSalida("oeste", habitacionF);

        // HabitacionG
        habitacionG.setSalida("norte", habitacionD);
        habitacionG.setSalida("oeste", habitacionH);

        // Salidas habitacion normal
        // HabitacionD
        habitacionD.setSalida("oeste", habitacionE);
        habitacionD.setSalida("sur", habitacionG);
        habitacionD.setSalida("norte", habitacionA);

        // HabitacionE
        habitacionE.setSalida("este", habitacionD);

        // Salidas Noxus
        habitacionC.setSalida("norte", habitacionB);

        // Salidas Habitacion Merienda
        habitacionF.setSalida("este", habitacionA);

        // Salidas habitacion Pollito
        habitacionH.setSalida("sur", habitacionJ);
        habitacionH.setSalida("oeste", habitacionFinal);
        habitacionH.setSalida("este", habitacionG);

        // Salidas habitacion del Sueño
        habitacionJ.setSalida("norte", habitacionH);
    }

    /**
     * Devuelve el mensaje de bienvenida del juego.
     *
     * @return El mensaje de bienvenida.
     */
    public String bienvenido() {
        return """
               ¡Bienvenido al Laberinto!
               Te encuentras en un laberinto misterioso y tu objetivo es encontrar la salida.
               ¡Explora el laberinto y disfruta de la aventura! Puedes pulsar H en cualquier momento, en caso de que necesites ayuda.
               """;
    }

    /**
     * Devuelve el mensaje de ayuda del juego.
     *
     * @return El mensaje de ayuda.
     */
    public String ayuda() {
        return """
               Puedes utilizar los siguientes comandos:
               - ↑: Para moverte hacia arriba.
               - ↓: Para moverte hacia abajo.
               - ←: Para moverte hacia la izquierda.
               - →: Para moverte hacia la derecha.
               - A: Para ver los comandos disponibles.
               - O: Para ver si encuentras objetos escondidos.
               - R: Para ver las salidas de la habitación.
               - I: Para ver los objetos que has encontrado durante tu aventura.
               - P: Para ver en donde te encuentras.
               - B: Para generar el mensaje de bienvenida.
               - F: Para salir del juego.
               ¡Explora el laberinto y disfruta de la aventura!
               """;
    }

    /**
     * Mueve al jugador a una dirección específica.
     *
     * @param direccion La dirección a la que moverse.
     * @return La descripción de la nueva habitación, o un mensaje de error si
     * la dirección no es válida.
     */
    public String irA(String direccion) {
        Habitacion nuevaHabitacion = habitacionActual.getSalida(direccion);

        if (nuevaHabitacion != null) {
            habitacionActual = nuevaHabitacion;
            System.out.println("HAS CAMBIADO DE SALA");
            System.out.println(habitacionActual.getDescripcion());
            
            // Llama al método específico de la habitación actual
            accionHabitacionActual();

            if (habitacionActual.getDescripcion().equals("SALIDA")) {
                System.out.println("Has llegado a la salida");
                System.exit(0);
            }

            return habitacionActual.getDescripcion();

        } else {
            return "Dirección no válida";
        }
    }

    /**
     * Devuelve la descripción de la habitación actual.
     *
     * @return La descripción de la habitación actual.
     */
    public String descripcionHabitacion() {
        return habitacionActual.getDescripcion();
    }

    /**
     * Devuelve la habitación actual.
     *
     * @return La habitación actual.
     */
    public Habitacion saberHabitacionActual() {
        return habitacionActual;
    }

    /**
     * Olfatea la habitación actual para encontrar objetos.
     *
     * @return Un mensaje indicando si se encontró un objeto.
     */
    public String olfatear() {
        if (habitacionActual instanceof HabitacionConItem) {
            String itemEncontrado = ((HabitacionConItem) habitacionActual).getItem();
            switch (itemEncontrado.toLowerCase()) {
                case "visa" ->
                    visa++;
                case "fruta" ->
                    fruta++;
                case "ibuprofeno" ->
                    ibuprofeno++;
            }
            return "HAS ENCONTRADO UN " + itemEncontrado.toUpperCase();
        } else {
            return "En esta habitación no hay nada interesante.";
        }
    }

    /**
     * Devuelve el inventario del jugador.
     *
     * @return Una cadena con el inventario del jugador.
     */
    public String verInventario() {
        return "REVISAS TU MOCHILITA Y TIENES: \n"
                + visa + " visas\n" + ibuprofeno + " ibuprofenos \n"
                + fruta + " frutas";
    }

    /**
     * Rastrea la habitación actual.
     *
     * @return Un mensaje con la descripción de la habitación y sus salidas.
     */
    public String rastrear() {
        if (habitacionActual instanceof OlorFuerte olorFuerte) {
            return olorFuerte.bloquearRastro();
        } else {
            return "Te encuentras en la habitación: " + habitacionActual.getDescripcion() + " y sus salidas son: " + habitacionActual.getStringSalidas();
        }
    }

    
     /**
     * Actualiza la decisión del jugador.
     *
     * @param nuevaDecision La nueva decisión del jugador.
     */
    public void actualizarDecision(String nuevaDecision) {
        this.decision = nuevaDecision;
    }

     /**
     * Verifica si el jugador tiene visas.
     *
     * @return true si el jugador tiene visas, false en caso contrario.
     */
    public boolean tenerVisa() {
        return visasUsadas > 0;
    }

     // Métodos privados para acciones específicas en habitaciones...

    /**
     * Realiza la acción específica para la habitación Noxus.
     */
    private void habitacionNoxus() {
        visasUsadas = 0;
        if (((HabitacionNoxus) habitacionActual).ayudaNoxus(this.decision)) {
            if (visa >= 1) {
                visasUsadas++;
                visa--;
            }
        }
    }
    
     /**
     * Realiza la acción específica para la habitación Merienda.
     */
    private void habitacionMerienda() {
        ibuprofenosUsados = 0;
        if (((HabitacionMerienda) habitacionActual).ahiDiceGratis(this.decision)) {
            if (ibuprofeno >= 1) {
                ibuprofeno--;
                ibuprofenosUsados++;
            }
        }
    }

    /**
     * Verifica si el jugador tiene fruta en su inventario.
     *
     * @return true si el jugador tiene fruta, false en caso contrario.
     */
    public boolean tenerFruta() {
        return fruta > 0;
    }

    
    /**
     * Verifica si el jugador tiene ibuprofeno en su inventario.
     *
     * @return true si el jugador tiene ibuprofeno, false en caso contrario.
     */
    public boolean tenerIbuprofeno() {
        return ibuprofenosUsados > 0;
    }

    /**
     * Da una fruta al personaje Pollito en la habitación Pollito.
     *
     * @param decision La decisión del jugador (Sí o No).
     * @return Un mensaje indicando el resultado de dar la fruta.
     */
    public String darFruta(String decision) {
        if (fruta >= 1) {
            if (decision.equalsIgnoreCase("S")) {
                contador++;
                fruta--;
                return """
                       Pollito se come tan feliz su fruta 
                       Y con una de sus alas te indica donde se encuentra la salida del LABERINTO
                       AL OESTE!!""";
            }
        }
        return "No tienes fruta, ni lo intentes";
    }

    /**
     * Realiza la acción específica para la habitación Pollito.
     */
    private void habitacionPollito() {
        if (((HabitacionPollito) habitacionActual).accionPollito(this.decision)) {
            System.exit(0);
        }

    }
    
    /**
     * Verifica si el jugador ha dado fruta al personaje Pollito.
     *
     * @return true si el jugador ha dado fruta a Pollito, false en caso contrario.
     */
    public boolean devolverContador() {
        return contador > 0;
    }

    /**
     * Realiza la acción correspondiente a la habitación actual.
     */
    private void accionHabitacionActual() {
        if (habitacionActual instanceof HabitacionNoxus) {
            habitacionNoxus();
        } else if (habitacionActual instanceof HabitacionMerienda) {
            habitacionMerienda();
        } else if (habitacionActual instanceof HabitacionPollito) {
            habitacionPollito();
        }
    }
}
