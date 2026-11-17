package br.pucrs.resgate.model;

import br.pucrs.resgate.enums.PrioridadeMissao;
import br.pucrs.resgate.enums.StatusMissao;
import br.pucrs.resgate.enums.TipoMissao;
import br.pucrs.resgate.exceptions.MissaoException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Missao {
    private final String id;
    private final String descricao;
    private final TipoMissao tipo;
    private final AreaOperacao area;
    private final PrioridadeMissao prioridade;
    private StatusMissao status;
    private Robo roboResponsavel;
    private String resultado;
    private final List<String> historico = new ArrayList<>();

    public Missao(String id, String descricao, TipoMissao tipo,
                  AreaOperacao area, PrioridadeMissao prioridade) {
        if (id == null || id.isBlank()) throw new MissaoException("Id da missão não pode ser vazio.");
        if (descricao == null || descricao.isBlank()) throw new MissaoException("Descrição da missão não pode ser vazia.");
        if (tipo == null) throw new MissaoException("Tipo da missão é obrigatório.");
        if (area == null) throw new MissaoException("Missão deve estar associada a uma área (regra 5).");
        if (prioridade == null) throw new MissaoException("Prioridade da missão é obrigatória.");
        this.id = id.trim();
        this.descricao = descricao.trim();
        this.tipo = tipo;
        this.area = area;
        this.prioridade = prioridade;
        this.status = StatusMissao.PENDENTE;
        registrar("Missão criada na área " + area.getNome() + " (status PENDENTE).");
    }

    /** Associa um robô. Só é possível enquanto a missão está PENDENTE. */
    public void atribuirRobo(Robo robo) {
        if (robo == null) throw new MissaoException("Robô não pode ser nulo.");
        if (status != StatusMissao.PENDENTE) {
            throw new MissaoException("Só é possível atribuir robô a missão PENDENTE (status atual: " + status + ").");
        }
        this.roboResponsavel = robo;
        registrar("Robô " + robo.getNome() + " [" + robo.getId() + "] atribuído.");
    }

    /** Regras 6 e 9: exige robô atribuído e status PENDENTE. */
    public void iniciar() {
        if (roboResponsavel == null) {
            throw new MissaoException("Missão só pode ser iniciada após atribuição de um robô (regra 6).");
        }
        if (status != StatusMissao.PENDENTE) {
            throw new MissaoException("Missão " + status + " não pode ser iniciada (regra 9).");
        }
        status = StatusMissao.EM_ANDAMENTO;
        registrar("Missão iniciada.");
    }

    public void concluir(String resultado) {
        if (status != StatusMissao.EM_ANDAMENTO) {
            throw new MissaoException("Só é possível concluir missão EM_ANDAMENTO (status atual: " + status + ").");
        }
        if (resultado == null || resultado.isBlank()) {
            throw new MissaoException("Informe o resultado da missão.");
        }
        this.resultado = resultado.trim();
        status = StatusMissao.CONCLUIDA;
        registrar("Missão concluída: " + this.resultado);
    }

    public void cancelar() {
        if (status == StatusMissao.CONCLUIDA || status == StatusMissao.CANCELADA) {
            throw new MissaoException("Missão " + status + " não pode ser cancelada.");
        }
        status = StatusMissao.CANCELADA;
        registrar("Missão cancelada.");
    }

    public String exibirResumo() {
        return String.format("Missão %s [%s | %s | prioridade=%s | status=%s] área=%s robô=%s%s",
                id, tipo, descricao, prioridade, status, area.getNome(),
                roboResponsavel == null ? "nenhum" : roboResponsavel.getNome(),
                resultado == null ? "" : " resultado=\"" + resultado + "\"");
    }

    private void registrar(String evento) {
        historico.add("[" + LocalDateTime.now().withNano(0) + "] " + evento);
    }

    public String getId() { return id; }
    public String getDescricao() { return descricao; }
    public TipoMissao getTipo() { return tipo; }
    public AreaOperacao getArea() { return area; }
    public PrioridadeMissao getPrioridade() { return prioridade; }
    public StatusMissao getStatus() { return status; }
    public Robo getRoboResponsavel() { return roboResponsavel; }
    public String getResultado() { return resultado; }
    /** Histórico individual (somente leitura). */
    public List<String> getHistorico() { return Collections.unmodifiableList(historico); }

    @Override
    public String toString() { return exibirResumo(); }
}
