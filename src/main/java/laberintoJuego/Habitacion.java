package laberintoJuego;

import java.util.ArrayList;

/**
 * Representa una habitación en el laberinto.
 * 
 * @author Quiñones Majuelo, Sergio
 */
public class Habitacion {

    private final String descripcion;
    private Habitacion sNorte;
    private Habitacion sSur;    
    private Habitacion sEste;
    private Habitacion sOeste;

    public Habitacion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Establece una salida de la habitación.
     * @param direccion La dirección de la salida (norte, sur, este, oeste).
     * @param h La habitación a la que lleva la salida.
     */
    public void setSalida(String direccion, Habitacion h) {
        switch (direccion.toLowerCase()) {
            case "norte" -> this.sNorte = h;
            case "sur" -> this.sSur = h;
            case "este" -> this.sEste = h;
            case "oeste" -> this.sOeste = h;
            default -> System.out.println("Dirección no válida");
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Devuelve una cadena con las direcciones de las salidas disponibles.
     * @return Una cadena con las direcciones de las salidas.
     */
    public String getStringSalidas() {
        ArrayList<String> salidas = new ArrayList<>();
        
        if (sNorte != null) salidas.add("Norte");
        if (sSur != null) salidas.add("Sur");
        if (sEste != null) salidas.add("Este");
        if (sOeste != null) salidas.add("Oeste");
        
        return String.join(", ", salidas);
    }

    /**
     * Imprime la descripción larga de la habitación.
     */
    public void getDescripcionLarga() {
        System.out.println("Estas en " + getDescripcion() + " y las salidas posibles son " + getStringSalidas());
    }

    /**
     * Devuelve la habitación a la que se llega por una dirección específica.
     * @param direccion La dirección de la salida.
     * @return La habitación a la que se llega, o null si la dirección no es válida.
     */
    public Habitacion getSalida(String direccion) {
        return switch (direccion.toLowerCase()) {
            case "norte" -> sNorte;
            case "sur" -> sSur;
            case "este" -> sEste;
            case "oeste" -> sOeste;
            default -> {
                System.out.println("Dirección no válida");
                yield null;
            }
        };
    }
}
