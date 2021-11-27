package proyectopoo2;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    Socket s;
    OutputStream sal;
    DataOutputStream salDatos;
    Socket cliente;
    InputStream e;
    DataInputStream entrada;

    public static void main(String[] args) {
        Cliente miCliente = new Cliente();
        System.out.println("Cliente\n\n");
        Scanner teclado = new Scanner(System.in);

        int opcion = 0;
        miCliente.conectar();

        do {
            System.out.println("l.Altas");
            System.out.println("2 Consulta");
            System.out.println("retiro");
            opcion = teclado.nextInt();

            switch (opcion) {
                case 1: //alta
                {
                    teclado.nextLine();
                    System.out.println("nombre");
                    String nombre = teclado.nextLine();

                    System.out.println("apellido");
                    String apellido = teclado.nextLine();

                    System.out.println("correo");
                    String correo = teclado.nextLine();

                    System.out.println("ocupacion");
                    String ocupacion = teclado.nextLine();

                    System.out.println("telefono");
                    String telefono = teclado.nextLine();

                    System.out.println("Monto Inicial");
                    String montoI = teclado.nextLine();

                    System.out.println("Minimo a Retiro");
                    String minimoR = teclado.nextLine();

                    miCliente.altas(nombre, apellido, correo, ocupacion, telefono, montoI, minimoR);
                }
                break;
                case 2: //consulta
                {
                    teclado.nextLine();
                    System.out.println("Ingresa Correo");
                    String correo = teclado.nextLine();
                    String respuesta = miCliente.consulta(correo);
                    System.out.println(respuesta);
                }
                    break;
                case 3://retiro
                {
                    teclado.nextLine();
                    System.out.println("Ingresa Correo");
                    String correo = teclado.nextLine();

                    System.out.println("Ingresa monto a retirar");
                    String montoR = teclado.nextLine();

                    String respuesta = miCliente.retiro(correo, montoR);
                    System.out.println(respuesta);

                }

                break;
            }

        } while (opcion != 0);
        miCliente.desconectar();
    }

    public void conectar() {
        try {
            s = new Socket("localhost", 5001);

            sal = s.getOutputStream();
            salDatos = new DataOutputStream(sal);
            //recivir
            cliente = s;
            e = cliente.getInputStream();
            entrada = new DataInputStream(e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void desconectar() {
        try {
            cliente.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void altas(String nombre, String apellido, String correo, String ocupacion, String telefono, String montoI, String minimoR) {
        //validaciones
        String peticion = "1/" + nombre + "/" + apellido + "/" + correo + "/" + ocupacion + "/" + telefono + "/" + montoI + "/" + minimoR;
        try {
            salDatos.writeUTF(peticion);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String consulta(String correo) {
        String respuesta = "";
        String peticion = "2/" + correo;

        try {
            salDatos.writeUTF(peticion);
            //recivir

            respuesta = entrada.readUTF();
            // System.out.println("recibiendo:" + respuesta);
        } catch (Exception e) {
            System.out.println(e);
        }
        return respuesta;
    }

    public String retiro(String correo, String monto) {
        String respuesta = "";
        String peticion = "3/" + correo + "/" + monto;

        try {
            salDatos.writeUTF(peticion);
            //recivir

            respuesta = entrada.readUTF();

        } catch (Exception e) {
            System.out.println(e);
        }
        return respuesta;
    }
}
