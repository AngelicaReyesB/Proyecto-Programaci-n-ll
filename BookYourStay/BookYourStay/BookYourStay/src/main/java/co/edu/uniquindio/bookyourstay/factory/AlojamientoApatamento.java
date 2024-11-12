package co.edu.uniquindio.bookyourstay.factory;

import co.edu.uniquindio.bookyourstay.modelo.Alojamiento;
import co.edu.uniquindio.bookyourstay.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.bookyourstay.modelo.enums.TipoCiudad;
import co.edu.uniquindio.bookyourstay.servicio.CreacionAlojamiento;

import java.time.LocalDate;
import java.util.List;

public class AlojamientoApatamento implements CreacionAlojamiento {
    @Override
    public Alojamiento crearOrdenAlojamiento(String nombre, String descripcion, String imagen, LocalDate fechaEstancia, float valorNoche, int numHuespedes, List<String> serviciosIncluidos, TipoAlojamiento tipoAlojamiento, TipoCiudad tipoCiudad) throws Exception {
        return null;
    }
}
