package co.edu.uniquindio.bookyourstay.modelo;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Factura {
    private float subtotal;
    private float total;
    private String codigo;
    private Reserva reserva;
}
