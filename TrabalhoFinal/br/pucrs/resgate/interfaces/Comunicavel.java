package br.pucrs.resgate.interfaces;

public interface Comunicavel {
    void enviarMensagem(String mensagem);
    void receberMensagem(String mensagem);
}
