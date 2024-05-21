package habitaciones;

import interfaces.OlorFuerte;
import laberintoJuego.Habitacion;

/**
 *
 * @author Sergio
 */
public class HabitacionMerienda extends Habitacion implements OlorFuerte {
    
    public HabitacionMerienda(String descripcion) {
        super(descripcion);
    }
    
    public boolean ahiDiceGratis(String decision){
         if (decision.equalsIgnoreCase("T")) {
            return true; 
        } else if (decision.equalsIgnoreCase("M")) {
            return true; 
        }
        return false;
    }
    
    @Override
    public String bloquearRastro() {
        return "HAY UN OLOR TAN FUERTEMENTE RICO QUE TE NUBLA LOS SENTIDOS Y NO PUEDES RASTREAR LAS SALIDAS";
    }
    
}
