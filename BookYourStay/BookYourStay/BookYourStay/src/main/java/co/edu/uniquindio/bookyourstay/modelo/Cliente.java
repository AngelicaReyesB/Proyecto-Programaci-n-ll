package co.edu.uniquindio.bookyourstay.modelo;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Cliente {

    private String cedula;
    private String nombre;
    private String telefono;
    private String email;
    private String password;
    private boolean estadoCuenta;
    private String codigoActivacion;


}
