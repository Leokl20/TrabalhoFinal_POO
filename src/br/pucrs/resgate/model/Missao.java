package br.pucrs.resgate.model;

import br.pucrs.resgate.enums.NivelRisco;
import br.pucrs.resgate.enums.StatusMissao;

public class Missao {
    private String id;
    private String descricao;
    private NivelRisco nivelRisco;
    private StatusMissao status;
    private AreaOperacao area;

    public Missao(String id, String descricao, NivelRisco nivelRisco, AreaOperacao area) {
        this.id = id;
        this.descricao = descricao;
        this.nivelRisco = nivelRisco;
        this.area = area;
        this.status = StatusMissao.PENDENTE;
    }

    public String getId() { return id; }
    public String getDescricao() { return descricao; }
    public NivelRisco getNivelRisco() { return nivelRisco; }
    public StatusMissao getStatus() { return status; }
    public void setStatus(StatusMissao status) { this.status = status; }
    public AreaOperacao getArea() { return area; }
}