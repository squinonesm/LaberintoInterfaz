package habitaciones;

import laberintoJuego.Habitacion;

/**
 * Representa una habitación que contiene un ítem.
 */
public class HabitacionConItem extends Habitacion {

    private final String item;

    /**
     * Crea una habitación con un ítem.
     * @param descripcion Descripción de la habitación.
     * @param item El ítem contenido en la habitación.
     */
    public HabitacionConItem(String descripcion, String item) {
        super(descripcion);
        this.item = item;
    }

    /**
     * Devuelve el ítem contenido en la habitación.
     * @return El ítem de la habitación.
     */
    public String getItem() {
        return item;
    }
}
