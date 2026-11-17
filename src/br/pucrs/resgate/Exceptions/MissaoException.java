package br.pucrs.resgate.exceptions;

/** Violação de regra de funcionamento ou dado inválido em Missao/AreaOperacao. */
public class MissaoException extends RuntimeException {
    public MissaoException(String mensagem) {
        super(mensagem);
    }
}
