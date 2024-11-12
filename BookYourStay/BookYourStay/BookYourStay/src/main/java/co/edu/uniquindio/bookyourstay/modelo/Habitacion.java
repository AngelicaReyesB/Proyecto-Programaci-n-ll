package co.edu.uniquindio.bookyourstay.modelo;

import co.edu.uniquindio.bookyourstay.modelo.enums.TipoAlojamiento;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Habitacion {

    private  int numero;
    private float precio;
    private int capacidad;
    private String imagen;
    //private type field;
    private String descripcion;
    private TipoAlojamiento tipoAlejamiento;

}
