package co.edu.uniquindio.bookyourstay.servicio;

import co.edu.uniquindio.bookyourstay.modelo.Alojamiento;
import co.edu.uniquindio.bookyourstay.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.bookyourstay.modelo.enums.TipoCiudad;

import java.time.LocalDate;
import java.util.List;

public interface CreacionAlojamiento {

    Alojamiento crearOrdenAlojamiento(String nombre, String descripcion, String imagen, LocalDate fechaEstancia, float valorNoche, int numHuespedes, List<String> serviciosIncluidos, TipoAlojamiento tipoAlojamiento, TipoCiudad tipoCiudad)throws Exception;


}
