package br.pucrs.resgate.model;

import br.pucrs.resgate.enums.NivelRisco;
import br.pucrs.resgate.enums.TipoTerreno;
import br.pucrs.resgate.exceptions.MissaoException;

public class AreaOperacao {
    private final String id;
    private String nome;
    private String localizacao;
    private TipoTerreno tipoTerreno;
    private NivelRisco nivelRisco;
    private String descricao;

    public AreaOperacao(String id, String nome, String localizacao,
                        TipoTerreno tipoTerreno, NivelRisco nivelRisco, String descricao) {
        this.id = exigirTexto(id, "id");
        this.nome = exigirTexto(nome, "nome");
        this.localizacao = exigirTexto(localizacao, "localizacao");
        if (tipoTerreno == null) throw new MissaoException("Tipo de terreno é obrigatório.");
        if (nivelRisco == null) throw new MissaoException("Nível de risco é obrigatório.");
        this.tipoTerreno = tipoTerreno;
        this.nivelRisco = nivelRisco;
        this.descricao = descricao == null ? "" : descricao.trim();
    }

    public void atualizarNivelRisco(NivelRisco novoNivel) {
        if (novoNivel == null) throw new MissaoException("Novo nível de risco é obrigatório.");
        this.nivelRisco = novoNivel;
    }

    public String exibirInformacoes() {
        return String.format("Área %s - %s [Local=%s, Terreno=%s, Risco=%s]%s",
                id, nome, localizacao, tipoTerreno, nivelRisco,
                descricao.isEmpty() ? "" : " - " + descricao);
    }

    private static String exigirTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new MissaoException("Campo '" + campo + "' não pode ser vazio.");
        }
        return valor.trim();
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getLocalizacao() { return localizacao; }
    public TipoTerreno getTipoTerreno() { return tipoTerreno; }
    public NivelRisco getNivelRisco() { return nivelRisco; }
    public String getDescricao() { return descricao; }

    @Override
    public String toString() { return exibirInformacoes(); }
}
