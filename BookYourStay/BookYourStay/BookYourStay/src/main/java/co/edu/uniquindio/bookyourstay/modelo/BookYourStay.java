package co.edu.uniquindio.bookyourstay.modelo;

import co.edu.uniquindio.bookyourstay.factory.AlojamientoApatamento;
import co.edu.uniquindio.bookyourstay.factory.AlojamientoCasa;
import co.edu.uniquindio.bookyourstay.factory.AlojamientoHotel;
import co.edu.uniquindio.bookyourstay.modelo.enums.TipoAlojamiento;
import co.edu.uniquindio.bookyourstay.modelo.enums.TipoCiudad;
import co.edu.uniquindio.bookyourstay.servicio.CreacionAlojamiento;
import co.edu.uniquindio.bookyourstay.servicio.ServiciosEmpresa;
import co.edu.uniquindio.bookyourstay.utils.EnvioEmail;
import co.edu.uniquindio.bookyourstay.utils.Persistencia;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class BookYourStay extends Persistencia implements ServiciosEmpresa {
    private List<Cliente> clientes;
    private List<Alojamiento> alojamientos;
    private List<Factura> facturas;
    Persistencia persistencia = new Persistencia();

    public BookYourStay(){
        try {
            clientes = new ArrayList<>();
            alojamientos = new ArrayList<>();
            facturas = new ArrayList<>();
            cargarDatosEmpresa();
        }catch ( Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void cargarDatosEmpresa() throws Exception {
        try {
            ArrayList<Cliente> clientesCargados = persistencia.cargarClientes();
            ArrayList<Alojamiento> alojamientosCargados = persistencia.cargarAlojamientos();
            ArrayList<Factura> facturasCargados = persistencia.cargarFacturas();

            if (!clientesCargados.isEmpty()) {
                clientes.addAll(clientesCargados);
            }
            if (!alojamientosCargados.isEmpty()) {
                alojamientos.addAll(alojamientosCargados);
            }
            if (!facturasCargados.isEmpty()) {
                facturas.addAll(facturasCargados);
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void guardarDatosEmpresa() throws Exception {
        try {
            persistencia.guardarAlojamientos(alojamientos);
            persistencia.guardarClientes(clientes);
            persistencia.guardarFacturas(facturas);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Cliente registrarCliente(String cedula, String nombre, String telefono, String email, String password) throws Exception {
        if(cedula.isEmpty() || cedula.isBlank() ){
            throw new Exception("El número de identificación es obligatorio");
        }

        if(obtenerCliente(cedula) != null){
            if(!obtenerCliente(cedula).getPassword().equals(password)){
                throw new Exception("Los datos de usuario y contraseña no coinciden");
            }
            throw new Exception("Ya existe un usuario con el número de identificación: "+cedula);
        }

        if(nombre.isEmpty() || nombre.isBlank()){
            throw new Exception("El nombre es obligatorio");
        }

        if(email.isEmpty() || email.isBlank()){
            throw new Exception("El correo electrónico es obligatorio");
        }

        if(password.isEmpty() || password.isBlank()){
            throw new Exception("La contraseña es obligatoria");
        }

        Cliente cliente;
        String codigoActivacion = UUID.randomUUID().toString();
        try{
            cliente = Cliente.builder()
                    .nombre(nombre)
                    .cedula(cedula)
                    .telefono(telefono)
                    .email(email)
                    .password(password)
                    .estadoCuenta(false)
                    .codigoActivacion(codigoActivacion)
                    .build();
            clientes.add(cliente);
            guardarDatosEmpresa();

        } catch(Exception e){
            throw new Exception("No se puede agregar el nuevo cliente");
        }
        return cliente;
    }

    @Override
    public Cliente iniciarSesion(String email, String password) throws Exception {
        return null;
    }

    @Override
    public Cliente obtenerCliente(String cedula) throws Exception {
        try {
            for (Cliente cliente : clientes) {
                System.out.println("CLIENTE QUE RECORRO EN BUSCAR" + cliente);
                if (cliente.getCedula().equals(cedula)) {
                    return cliente;
                }
            }
            return null;
        }catch (Exception e){
            throw new Exception("No se puede buscar cliente");
        }
    }

    @Override
    public Cliente validarUsuario(String email, String password) throws Exception {
        Cliente cliente = obtenerCliente(email);
        if(cliente != null){
            if(cliente.getPassword().equals(password)){
                return cliente;
            }else {
                throw new Exception("Datos de ingres son incorrectos");
            }
        } else {
            throw new Exception("El usuario no existe");
        }
    }

    @Override
    public Cliente obtenerUsuario(String email) throws Exception {
        for (Cliente cliente: clientes){
            if(cliente.getEmail().equals(email)){
                return cliente;
            }
        }
        return null;
    }

    @Override
    public boolean activarUsuario(String codigoActivacion, Cliente cliente) throws Exception {
        Cliente usuarioEncontrado = validarUsuario(cliente.getCedula(), cliente.getPassword());
        if(usuarioEncontrado != null){
            if(usuarioEncontrado.getCodigoActivacion().equals(codigoActivacion)){
                usuarioEncontrado.setEstadoCuenta(true);
                guardarDatosEmpresa();
                return true;
            }else {
                throw new Exception("El código de activación no coincide");
            }
        }else {
            throw new Exception("El usuario no existe");
        }
    }

    @Override
    public void enviarCodigoActivacion(Cliente cliente) throws Exception {
        String email = cliente.getEmail();
        String mensajeActivacion = "Recuerde activar la cuenta para poder realizar sus resrevas en BookYourStay. Código de activación: " +
                cliente.getCodigoActivacion();
        enviarNotificacion(email, "BookYourStay: Código de activación", mensajeActivacion );
    }

    @Override
    public Cliente editarCuenta(String cedula, String nombre, String telefono, String email, String password) throws Exception {
        return null;
    }

    @Override
    public boolean validarIngresoAdministrador(String email, String password) throws Exception {
        String usuarioAdministrador = "admin@bookyourstay.com";
        String passwordAdministrador = "admin123";
        return email.equals(usuarioAdministrador) && password.equals(passwordAdministrador);
    }

    @Override
    public Alojamiento crearAlojamiento(String nombre, String descripcion, String imagen, LocalDate fechaEstancia, float valorNoche, int numHuespedes, List<String> serviciosIncluidos, TipoAlojamiento tipoAlojamiento, TipoCiudad tipoCiudad) throws Exception {
        try {
            if (nombre.isEmpty() || descripcion.isEmpty() || imagen == null || fechaEstancia == null || valorNoche <- 0 || numHuespedes<- 0||
                    serviciosIncluidos.isEmpty() || tipoAlojamiento == null || tipoCiudad == null) {
                throw new Exception("Todos los campos son obligatorios");
            }
            CreacionAlojamiento creacionAlojamiento = crearAlojamiento(tipoAlojamiento);
            Alojamiento alojamiento = creacionAlojamiento.crearOrdenAlojamiento(nombre, descripcion, imagen, fechaEstancia, valorNoche, numHuespedes, serviciosIncluidos, tipoAlojamiento, tipoCiudad);
            alojamientos.add(alojamiento);
            guardarDatosEmpresa();
            return alojamiento;
        } catch (Exception e) {
            throw new Exception("No se pudo crear el alojamiento" + e.getMessage());
        }
    }

    //dudas sobre las habitaciones y el costo por mantenimiento
    @Override
    public CreacionAlojamiento crearAlojamiento(TipoAlojamiento tipoAlojamiento){
        CreacionAlojamiento creacionAlojamiento;
        if(tipoAlojamiento == TipoAlojamiento.APARTAMENTO){
            creacionAlojamiento = new AlojamientoApatamento();
        }else if (tipoAlojamiento == TipoAlojamiento.CASA){
            creacionAlojamiento = new AlojamientoCasa();
        }else if (tipoAlojamiento == TipoAlojamiento.HOTEL){
            creacionAlojamiento = new AlojamientoHotel();
        }
        return null;
    }

    @Override
    public Alojamiento obtenerAlojamiento(String codigo) throws Exception {
        for (Alojamiento alojamiento: alojamientos){
            if(alojamiento.getCodigo().equals(codigo)){
                return alojamiento;
            }
        }
        return null;
    }

    @Override
    public Alojamiento actualizarAlojamiento(Alojamiento alojamiento) throws Exception {
        Alojamiento alojamientoEncontrado = obtenerAlojamiento(alojamiento.getCodigo());
        if (alojamientoEncontrado != null){
            alojamientoEncontrado.setNombre(alojamiento.getNombre());
            alojamientoEncontrado.setDescripcion(alojamiento.getDescripcion());
            alojamientoEncontrado.setImagen(alojamiento.getImagen());
            alojamientoEncontrado.setFechaEstancia(alojamiento.getFechaEstancia());
            alojamientoEncontrado.setValorNoche(alojamiento.getValorNoche());
            alojamientoEncontrado.setNumHuespedes(alojamiento.getNumHuespedes());
            alojamientoEncontrado.setServiciosIncluidos(alojamiento.getServiciosIncluidos());
            alojamientoEncontrado.setTipoAlojamiento(alojamiento.getTipoAlojamiento());
            alojamientoEncontrado.setTipoCiudad(alojamiento.getTipoCiudad());
            guardarDatosEmpresa();
        }
        return alojamientoEncontrado;
    }


    //pendiente
    @Override
    public void actualizarAlojamiento(Reserva reserva) throws Exception {

    }

    @Override
    public ArrayList<Alojamiento> listarAlojamientos() throws Exception {
        ArrayList<Alojamiento> alojamientosActivos = new ArrayList<>();
        for (Alojamiento alojamiento: alojamientos){
            if(alojamiento.getActivo()){
                alojamientosActivos.add(alojamiento);
            }
        }
        return (ArrayList<Alojamiento>) alojamientos;
    }

    @Override
    public ArrayList<Alojamiento> listarAlojamientos(TipoAlojamiento tipoAlojamiento) throws Exception {
        ArrayList<Alojamiento> alojamientosPorTipo = new ArrayList<>();
        for (Alojamiento alojamiento: alojamientos){
            if(alojamiento.getTipoAlojamiento() == tipoAlojamiento){
                alojamientosPorTipo.add(alojamiento);
            }
        }
        return alojamientosPorTipo;
    }

    @Override
    public ArrayList<Alojamiento> listarAlojamientos(TipoCiudad tipoCiudad) throws Exception {
        ArrayList<Alojamiento> alojamientosPorCiudad = new ArrayList<>();
        for (Alojamiento alojamiento: alojamientos){
            if(alojamiento.getTipoCiudad() == tipoCiudad){
                alojamientosPorCiudad.add(alojamiento);
            }
        }
        return alojamientosPorCiudad;
    }

    @Override
    public ArrayList<Alojamiento> listarAlojamientos(String nombreAlojamiento) throws Exception {
        ArrayList<Alojamiento> alojamientosPorNombre = new ArrayList<>();
        for (Alojamiento alojamiento: alojamientos){
            if(alojamiento.getNombre().contains(nombreAlojamiento)){
                alojamientosPorNombre.add(alojamiento);
                return alojamientosPorNombre;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Cliente> listarClientes() throws Exception {
        return new ArrayList<Cliente>(clientes);
    }

    //falta
    @Override
    public List<Reserva> listarReservasCliente(String cedulaCliente) throws Exception {
        return null;
    }

    //decisiones y try catch
    @Override
    public Reserva realizarReserva(LocalDate fechaInicio, LocalDate fechaFin, float costoTotal, Cliente cliente, Alojamiento alojamiento, ArrayList<Habitacion> habitacionesReservadas) throws Exception {
        return null;
    }


    @Override
    public boolean cancelarReserva(Reserva reserva) throws Exception {
        return false;
    }

    @Override
    public ArrayList<Reserva> listarReservas() throws Exception {
        return null;
    }

    @Override
    public float calcularCostoReserva(Reserva reserva) throws Exception {
        return 0;
    }

    @Override
    public void recargarBilleteraVirtual(Cliente cliente, float monto) throws Exception {

    }

    @Override
    public String agregarResena(Reserva reserva, String resena, int calificacion) throws Exception {
        return null;
    }

    @Override
    public Cliente cambiarPasswordC(String cedula, String nuevaPassword) throws Exception {
        return null;
    }

    @Override
    public void enviarNotificacion(String destinatario, String asunto, String mensaje) throws Exception {
        try {
            new EnvioEmail(destinatario, asunto, mensaje);
        } catch (Exception e) {
            throw new Exception("No se puede enviar el notificación al correo electrónico");
        }
    }

    @Override
    public boolean eliminarAlojamiento(Alojamiento alojamiento) throws Exception {
        return false;
    }

    @Override
    public float aplicarDescuentos(Alojamiento alojamiento, float porcentaje) throws Exception {
        return 0;
    }

    @Override
    public float crearTarifaDescuento(Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin, float descuento) throws Exception {
        return 0;
    }

    @Override
    public int verEstadisticas(String ciudad) throws Exception {
        return 0;
    }

    @Override
    public ArrayList<Alojamiento> listaPopularesPorCiudad(String ciudad) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Reserva> listaMasRentables(int limite) throws Exception {
        return null;
    }

    @Override
    public Administrador cambiarPassword(String email, String nuevaPassword) throws Exception {
        return null;
    }


    //private float subtotal;
    //    private float total;
    //    private String codigo;
    @Override
    public Factura generarFactura(Reserva reserva) throws Exception {
        try {
           float subtotal = valorReserva(reserva);

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void enviarCodigoQR(Factura factura, String emailCliente) throws Exception {
        try {
            // Generar el código QR con la librería QRGen
            QrGenerator generator = new QrGenerator()
                    .withSize(300, 300)
                    .withMargin(3)
                    .as(ImageFileType.PNG)
                    .withErrorCorrection(ErrorCorrectionLevel.Q);

            // Escribir el código QR en un archivo temporal, reemplazar "Hello, World!" por el texto que se desea codificar
            Path img = generator
                    .writeToTmpFile(factura.toString());

            // Mostrar el código QR en la interfaz gráfica
            return img.toUri().toString();
        }catch (Exception e){
            throw new Exception("No es posible generar código Qr");
        }

    }

    @Override
    public void crearOfertaEspecial(Alojamiento alojamiento, LocalDate fechaInicio, LocalDate fechaFin, float descuento) throws Exception {

    }

    @Override
    public List<Alojamiento> listarOfertasEspeciales() throws Exception {
        return null;
    }
}
