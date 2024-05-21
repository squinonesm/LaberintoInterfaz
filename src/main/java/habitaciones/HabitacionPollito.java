package habitaciones;

import interfaces.OlorFuerte;
import laberintoJuego.Habitacion;

/**
 * Clase que representa una habitación específica del juego con características especiales relacionadas con un pollito.
 * Implementa la interfaz OlorFuerte para bloquear el rastro del jugador.
 * Extiende la clase Habitacion del juego.
 * 
 * Esta habitación puede requerir una acción especial del jugador relacionada con el pollito.
 * También tiene la capacidad de bloquear el rastro del jugador debido a un fuerte olor.
 * 
 * @author Sergio
 */
public class HabitacionPollito extends Habitacion implements OlorFuerte {
    
     /**
     * Constructor de la clase HabitacionPollito.
     * 
     * @param descripcion La descripción de la habitación.
     */
    public HabitacionPollito(String descripcion) {
        super(descripcion);
    }
    
    /**
     * Realiza una acción específica relacionada con el pollito en la habitación.
     * 
     * @param decision La decisión del jugador (A para avanzar, R para retroceder).
     * @return true si se avanza, false si se retrocede.
     */
    public boolean accionPollito(String decision) {
        if (decision.equalsIgnoreCase("A")) {
            return true; 
        } else if (decision.equalsIgnoreCase("R")) {
            return false; 
        }
        return false;
    }
    
     /**
     * Implementación del método de la interfaz OlorFuerte para bloquear el rastro del jugador.
     * 
     * @return Un mensaje indicando que el olor es tan fuerte que no se puede rastrear.
     */
    @Override
    public String bloquearRastro() {
        return "Huele tan mal que no puedes rastrear las salidas";
    }

}
