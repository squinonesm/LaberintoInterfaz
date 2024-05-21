package habitaciones;

import interfaces.OlorFuerte;
import laberintoJuego.Habitacion;

/**
 * Representa una habitación de Noxus con un olor fuerte.
 */
public class HabitacionNoxus extends Habitacion implements OlorFuerte {

    /**
     * Clase que representa una habitación específica del juego situada en
     * Noxus, caracterizada por tener un olor fuerte. Implementa la interfaz
     * OlorFuerte para proporcionar funcionalidades relacionadas con el olor.
     * Extiende la clase Habitacion del juego.
     *
     * Esta habitación puede proporcionar ayuda al jugador dependiendo de su
     * decisión. También tiene la capacidad de bloquear el rastro del jugador
     * debido a su fuerte olor.
     *
     * @author Quiñones Majuelo, Sergio
     */
    public HabitacionNoxus(String descripcion) {
        super(descripcion);
    }

    /**
     * Proporciona ayuda en Noxus basada en la decisión del jugador.
     *
     * @param decision La decisión del jugador (S para sí, cualquier otra cosa
     * para no).
     * @return true si la ayuda es proporcionada, false en caso contrario.
     */
    public boolean ayudaNoxus(String decision) {
        return decision.equalsIgnoreCase("S");
    }

    /**
     * Implementación del método de la interfaz OlorFuerte para bloquear el
     * rastro del jugador.
     *
     * @return Un mensaje indicando que el fuerte olor en la habitación bloquea
     * el rastro del jugador.
     */
    @Override
    public String bloquearRastro() {
        return "El fuerte olor en la habitación bloquea tu rastro.";
    }
}
