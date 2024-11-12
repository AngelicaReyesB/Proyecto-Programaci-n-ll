package co.edu.uniquindio.bookyourstay.modelo;

import co.edu.uniquindio.bookyourstay.modelo.enums.TipoHabitacion;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Reserva {

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private float costoTotal;
    private Alojamiento alojamiento;
    private Cliente cliente;
    private ArrayList<Habitacion> habitacionesReservadas;
    private TipoHabitacion tipoHabitacion;


}
