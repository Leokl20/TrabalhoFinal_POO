package br.pucrs.resgate.exceptions;

/** Dado inválido ou violação de regra em Robo e subclasses. */
public class RoboException extends RuntimeException {
    public RoboException(String mensagem) {
        super(mensagem);
    }
}
