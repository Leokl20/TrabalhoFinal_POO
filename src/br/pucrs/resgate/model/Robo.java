package br.pucrs.resgate.model;

import br.pucrs.resgate.enums.StatusRobo;
import br.pucrs.resgate.interfaces.Comunicavel;

public abstract class Robo implements Comunicavel {
    private String id;
    private String nome;
    private double nivelBateria; // 0.0 a 100.0
    private StatusRobo status;

    public Robo(String id, String nome, double nivelBateria) {
        this.id = id;
        this.nome = nome;
        this.nivelBateria = nivelBateria;
        this.status = StatusRobo.DISPONIVEL;
    }

    // Método polimórfico abstrato a ser implementado por Drone e RoboTerrestre
    public abstract boolean executarMissao(Missao missao);

    // Métodos de controle de bateria
    public void recarregarBateria() {
        this.nivelBateria = 100.0;
        this.status = StatusRobo.DISPONIVEL;
    }

    /*  este método pode ser futuramente um metodo privado, no qual 
    pode gastar difernete niveis de bateria dependendo do nivel de risco
    da missão. (Só uma ideia que achei interessante)
    */
    public void consumirBateria(double quantidade) {
        this.nivelBateria = Math.max(0.0, this.nivelBateria - quantidade);
        if (this.nivelBateria == 0.0) {
            this.status = StatusRobo.INDISPONIVEL;
        }
    }

    // Getters e Setters
    public String getId() { return id; }
    public String getNome() { return nome; }
    public double getNivelBateria() { return nivelBateria; }
    public StatusRobo getStatus() { return status; }
    public void setStatus(StatusRobo status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("%s [ID=%s, Bateria=%.1f%%, Status=%s]", 
                nome, id, nivelBateria, status);
    }
}