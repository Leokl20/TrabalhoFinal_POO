package br.pucrs.resgate.model;

import br.pucrs.resgate.enums.*;
import br.pucrs.resgate.exceptions.MissaoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AreaOperacaoTest {
    @Test
    void atualizaNivelRisco() {
        AreaOperacao a = new AreaOperacao("A1", "Centro", "POA", TipoTerreno.URBANO, NivelRisco.BAIXO, "");
        a.atualizarNivelRisco(NivelRisco.ALTO);
        assertEquals(NivelRisco.ALTO, a.getNivelRisco());
    }

    @Test
    void rejeitaDadosInvalidos() {
        assertThrows(MissaoException.class, () ->
                new AreaOperacao(" ", "Centro", "POA", TipoTerreno.URBANO, NivelRisco.BAIXO, ""));
        assertThrows(MissaoException.class, () ->
                new AreaOperacao("A1", "Centro", "POA", null, NivelRisco.BAIXO, ""));
        AreaOperacao a = new AreaOperacao("A1", "Centro", "POA", TipoTerreno.URBANO, NivelRisco.BAIXO, "");
        assertThrows(MissaoException.class, () -> a.atualizarNivelRisco(null));
    }
}
