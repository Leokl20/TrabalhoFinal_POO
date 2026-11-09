package br.pucrs.resgate.model;

import br.pucrs.resgate.enums.TipoTerreno;

public class AreaOperacao {
    private String id;
    private String nome;
    private TipoTerreno tipoTerreno;

    public AreaOperacao(String id, String nome, TipoTerreno tipoTerreno) {
        this.id = id;
        this.nome = nome;
        this.tipoTerreno = tipoTerreno;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public TipoTerreno getTipoTerreno() { return tipoTerreno; }
}