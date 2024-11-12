package co.edu.uniquindio.bookyourstay.modelo;

import co.edu.uniquindio.bookyourstay.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.bookyourstay.modelo.enums.TipoCiudad;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;


@Builder
@Getter
@Setter
@ToString
public class Alojamiento {

    private String codigo;
    private String nombre;
    private String descripcion;
    private String imagen;
    private LocalDate fechaEstancia;
    private float valorNoche;
    private  int numHuespedes;
    private List<String> serviciosIncluidos;
    private TipoAlojamiento tipoAlojamiento;
    private TipoCiudad tipoCiudad;
    private Boolean activo;



}
