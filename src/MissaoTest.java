package br.pucrs.resgate.model;

import br.pucrs.resgate.enums.*;
import br.pucrs.resgate.exceptions.MissaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MissaoTest {
    // Robô mínimo só para teste (ajuste se a interface Comunicavel mudar).
    static class RoboFake extends Robo {
        RoboFake() { super("R1", "Fake", 100); }
        @Override public boolean executarMissao(Missao m) { return true; }
        @Override public void enviarMensagem(String s) { }
        @Override public String receberMensagem() { return ""; }
    }

    private AreaOperacao area;
    private Missao missao;

    @BeforeEach
    void setup() {
        area = new AreaOperacao("A1", "Centro", "Porto Alegre",
                TipoTerreno.ALAGADO, NivelRisco.ALTO, "Enchente");
        missao = new Missao("M1", "Buscar pessoas", TipoMissao.LOCALIZACAO_PESSOAS,
                area, PrioridadeMissao.ALTA);
    }

    @Test
    void fluxoValido() {
        missao.atribuirRobo(new RoboFake());
        missao.iniciar();
        assertEquals(StatusMissao.EM_ANDAMENTO, missao.getStatus());
        missao.concluir("2 pessoas localizadas");
        assertEquals(StatusMissao.CONCLUIDA, missao.getStatus());
        assertEquals("2 pessoas localizadas", missao.getResultado());
        assertEquals(4, missao.getHistorico().size());
    }

    @Test
    void naoIniciaSemRobo() {
        assertThrows(MissaoException.class, () -> missao.iniciar());
    }

    @Test
    void naoReiniciaMissaoConcluidaOuCancelada() {
        missao.atribuirRobo(new RoboFake());
        missao.iniciar();
        missao.concluir("ok");
        assertThrows(MissaoException.class, () -> missao.iniciar());

        Missao outra = new Missao("M2", "x", TipoMissao.RECONHECIMENTO, area, PrioridadeMissao.BAIXA);
        outra.cancelar();
        assertThrows(MissaoException.class, () -> outra.iniciar());
        assertThrows(MissaoException.class, () -> outra.cancelar());
    }

    @Test
    void missaoExigeArea() {
        assertThrows(MissaoException.class, () ->
                new Missao("M3", "x", TipoMissao.RECONHECIMENTO, null, PrioridadeMissao.BAIXA));
    }

    @Test
    void historicoNaoPodeSerAlteradoPorFora() {
        assertThrows(UnsupportedOperationException.class, () -> missao.getHistorico().add("x"));
    }
}
