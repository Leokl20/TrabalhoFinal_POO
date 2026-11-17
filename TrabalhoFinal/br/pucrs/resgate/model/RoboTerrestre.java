package br.pucrs.resgate.model;

import br.pucrs.resgate.enums.NivelRisco;
import br.pucrs.resgate.enums.StatusRobo;
import br.pucrs.resgate.enums.TipoMissao;
import br.pucrs.resgate.enums.TipoTerreno;
import br.pucrs.resgate.exceptions.RoboException;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class RoboTerrestre extends Robo {

    // Compatibilidade: tipos de missão e terrenos que o robô terrestre atende
    private static final Set<TipoMissao> MISSOES =
            EnumSet.of(TipoMissao.TRANSPORTE_SUPRIMENTOS, TipoMissao.LOCALIZACAO_PESSOAS);
    private static final Set<TipoTerreno> TERRENOS =
            EnumSet.of(TipoTerreno.URBANO, TipoTerreno.FLORESTA_FECHADA, TipoTerreno.IRREGULAR);

    // Consumo de bateria (%) por nível de risco da área
    private static final Map<NivelRisco, Double> CONSUMO = new EnumMap<>(NivelRisco.class);
    static {
        CONSUMO.put(NivelRisco.BAIXO, 20.0);
        CONSUMO.put(NivelRisco.MEDIO, 35.0);
        CONSUMO.put(NivelRisco.ALTO, 50.0);
        CONSUMO.put(NivelRisco.CRITICO, 75.0);
    }

    private int capMax;
    private String tipoCarga;

    public RoboTerrestre(int capMax, String tipoCarga, String id, String nome,
                         double nivelBateria, StatusRobo status, int missoesRealizadas) {
        super(id, nome, nivelBateria, status, missoesRealizadas);
        setCapMax(capMax);
        setTipoCarga(tipoCarga);
    }

    @Override
    public boolean compativelCom(Missao missao) {
        return missao != null
                && MISSOES.contains(missao.getTipo())
                && TERRENOS.contains(missao.getArea().getTipoTerreno());
    }

    @Override
    public boolean executarMissao(Missao missao) {
        if (!compativelCom(missao)) {
            System.out.println("Robô terrestre " + getNome() + " não é compatível com esta missão (tipo ou terreno).");
            return false;
        }
        return processarMissao(missao, CONSUMO.get(missao.getArea().getNivelRisco()));
    }

    public int getCapMax() { return capMax; }

    public void setCapMax(int capMax) {
        if (capMax <= 0) throw new RoboException("Capacidade máxima deve ser positiva.");
        this.capMax = capMax;
    }

    public String getTipoCarga() { return tipoCarga; }

    public void setTipoCarga(String tipoCarga) {
        if (tipoCarga == null || tipoCarga.isBlank()) throw new RoboException("Tipo de carga não pode ser vazio.");
        this.tipoCarga = tipoCarga.trim();
    }

    @Override
    public String toString() {
        return "RoboTerrestre " + super.toString()
                + String.format(" [CapMax=%d, Carga=%s]", capMax, tipoCarga);
    }
}
