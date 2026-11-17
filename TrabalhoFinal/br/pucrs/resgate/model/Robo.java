package br.pucrs.resgate.model;

import br.pucrs.resgate.enums.StatusMissao;
import br.pucrs.resgate.enums.StatusRobo;
import br.pucrs.resgate.exceptions.RoboException;
import br.pucrs.resgate.interfaces.Comunicavel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Robo implements Comunicavel {

    private final String id;
    private String nome;
    private double nivelBateria; // 0.0 a 100.0
    private StatusRobo status;
    private int missoesRealizadas;
    private final List<String> mensagens = new ArrayList<>();

    public Robo(String id, String nome, double nivelBateria, StatusRobo status, int missoesRealizadas) {
        if (id == null || id.isBlank()) throw new RoboException("Id do robô não pode ser vazio.");
        if (nome == null || nome.isBlank()) throw new RoboException("Nome do robô não pode ser vazio.");
        validarBateria(nivelBateria);
        if (missoesRealizadas < 0) throw new RoboException("Missões realizadas não pode ser negativo.");
        this.id = id.trim();
        this.nome = nome.trim();
        this.nivelBateria = nivelBateria;
        this.status = status == null ? StatusRobo.DISPONIVEL : status;
        this.missoesRealizadas = missoesRealizadas;
    }

    // Método polimórfico abstrato implementado por Drone e RoboTerrestre
    public abstract boolean executarMissao(Missao missao);

    /** Compatibilidade (tipo de missão e terreno). Subclasses sobrescrevem. */
    public boolean compativelCom(Missao missao) {
        return missao != null;
    }

    /**
     * Fluxo comum de execução (regras 3, 4, 6 e 7): valida missão/robô/bateria,
     * executa, consome bateria, atualiza contador e status.
     */
    protected final boolean processarMissao(Missao missao, double consumo) {
        if (missao.getStatus() != StatusMissao.PENDENTE) {
            System.out.println("Missão não está pendente para executar.");
            return false;
        }
        if (status != StatusRobo.DISPONIVEL) {
            System.out.println("Robô " + nome + " não está disponível (status: " + status + ").");
            return false;
        }
        if (nivelBateria <= consumo) {
            System.out.println("Bateria insuficiente: " + nivelBateria + "% (necessário > " + consumo + "%).");
            return false;
        }
        if (missao.getRoboResponsavel() != null && missao.getRoboResponsavel() != this) {
            System.out.println("Missão já atribuída a outro robô.");
            return false;
        }
        try {
            if (missao.getRoboResponsavel() == null) {
                missao.atribuirRobo(this);
            }
            missao.iniciar();
            status = StatusRobo.EM_MISSAO;
            enviarMensagem("Iniciando missão " + missao.getId());
            consumirBateria(consumo);
            missao.concluir("Missão executada por " + nome);
            missoesRealizadas++;
            enviarMensagem("Missão " + missao.getId() + " concluída.");
        } finally {
            if (status == StatusRobo.EM_MISSAO) {
                status = StatusRobo.DISPONIVEL; // não sobrescreve SEM_BATERIA
            }
        }
        return true;
    }

    // Bateria
    public void recarregar() {
        this.nivelBateria = 100.0;
        if (status == StatusRobo.SEM_BATERIA) {
            this.status = StatusRobo.DISPONIVEL;
        }
    }

    public void consumirBateria(double quantidade) {
        if (quantidade < 0) throw new RoboException("Consumo de bateria não pode ser negativo.");
        this.nivelBateria = Math.max(0.0, this.nivelBateria - quantidade);
        if (this.nivelBateria == 0.0) {
            this.status = StatusRobo.SEM_BATERIA;
        }
    }

    public double consultarBateria() {
        return nivelBateria;
    }

    public boolean verificarDisponibilidade() {
        return status == StatusRobo.DISPONIVEL;
    }

    public String exibirResumo() {
        return toString();
    }

    // Comunicavel
    @Override
    public void enviarMensagem(String mensagem) {
        mensagens.add("ENVIADA: " + mensagem);
        System.out.println("[" + getClass().getSimpleName() + " -> CENTRAL] " + nome + " (ID: " + id + "): " + mensagem);
    }

    @Override
    public void receberMensagem(String mensagem) {
        mensagens.add("RECEBIDA: " + mensagem);
        System.out.println("[CENTRAL -> " + getClass().getSimpleName() + "] " + nome + " recebeu: " + mensagem);
    }

    public List<String> getMensagens() {
        return Collections.unmodifiableList(mensagens);
    }

    private static void validarBateria(double valor) {
        if (valor < 0.0 || valor > 100.0) {
            throw new RoboException("Bateria deve estar entre 0 e 100 (recebido: " + valor + ").");
        }
    }

    // Getters e Setters
    public String getId() { return id; }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) throw new RoboException("Nome do robô não pode ser vazio.");
        this.nome = nome.trim();
    }

    public double getNivelBateria() { return nivelBateria; }

    public void setNivelBateria(double nivelBateria) {
        validarBateria(nivelBateria);
        this.nivelBateria = nivelBateria;
    }

    public StatusRobo getStatus() { return status; }

    public void setStatus(StatusRobo status) {
        if (status == null) throw new RoboException("Status do robô é obrigatório.");
        this.status = status;
    }

    public int getMissoesRealizadas() { return missoesRealizadas; }

    @Override
    public String toString() {
        return String.format("%s [ID=%s, Bateria=%.1f%%, Status=%s, Missões=%d]",
                nome, id, nivelBateria, status, missoesRealizadas);
    }
}
