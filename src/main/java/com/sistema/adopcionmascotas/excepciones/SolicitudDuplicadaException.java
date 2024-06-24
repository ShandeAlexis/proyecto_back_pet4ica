package com.sistema.adopcionmascotas.excepciones;

public class SolicitudDuplicadaException extends RuntimeException{
    public SolicitudDuplicadaException(String mensaje){
        super(mensaje);
    }
}
