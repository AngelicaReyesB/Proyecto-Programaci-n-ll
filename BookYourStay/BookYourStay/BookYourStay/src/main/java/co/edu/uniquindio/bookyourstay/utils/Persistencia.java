package co.edu.uniquindio.bookyourstay.utils;

import co.edu.uniquindio.bookyourstay.modelo.Alojamiento;
import co.edu.uniquindio.bookyourstay.modelo.Cliente;
import co.edu.uniquindio.bookyourstay.modelo.Factura;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {

    private static final String RUTA_CLIENTES = "src/main/resources/persistencia/dataClientes.ser";
    private static final String RUTA_ALOJAMIENTOS = "src/main/resources/persistencia/dataFacturas.ser";
    private static final String RUTA_FACTURAS = "src/main/resources/persistencia/dataEventos.ser";

    private void serializarObjeto(String ruta, Object objeto) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta));
        oos.writeObject(objeto);
        oos.close();
    }

    private Object deserializarObjeto(String ruta) throws Exception{
        if(!new File(ruta).exists()){
            return null;
        }

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta));
        Object objeto = ois.readObject();
        ois.close();

        return objeto;
    }

    public void guardarClientes(List<Cliente> clientes) throws IOException {
        serializarObjeto(RUTA_CLIENTES, clientes);
    }

    public ArrayList<Cliente> cargarClientes() throws Exception {
        return (ArrayList<Cliente>) deserializarObjeto(RUTA_CLIENTES);
    }

    public void guardarAlojamientos(List<Alojamiento> evento) throws IOException {
        serializarObjeto(RUTA_ALOJAMIENTOS, evento);
    }

    public ArrayList<Alojamiento> cargarAlojamientos() throws Exception {
        return (ArrayList<Alojamiento>) deserializarObjeto(RUTA_ALOJAMIENTOS);
    }

    public void guardarFacturas(List<Factura> facturas) throws IOException {
        serializarObjeto(RUTA_FACTURAS, facturas);
    }

    public ArrayList<Factura> cargarFacturas() throws Exception {
        return (ArrayList<Factura>) deserializarObjeto(RUTA_FACTURAS);
    }





}
