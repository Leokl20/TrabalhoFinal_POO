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

public class Drone extends Robo {

    // Compatibilidade: tipos de missão e terrenos que o drone atende
    private static final Set<TipoMissao> MISSOES =
            EnumSet.of(TipoMissao.RECONHECIMENTO, TipoMissao.LOCALIZACAO_PESSOAS);
    private static final Set<TipoTerreno> TERRENOS =
            EnumSet.of(TipoTerreno.ALAGADO, TipoTerreno.MONTANHOSO, TipoTerreno.IRREGULAR);

    // Consumo de bateria (%) por nível de risco da área
    private static final Map<NivelRisco, Double> CONSUMO = new EnumMap<>(NivelRisco.class);
    static {
        CONSUMO.put(NivelRisco.BAIXO, 10.0);
        CONSUMO.put(NivelRisco.MEDIO, 20.0);
        CONSUMO.put(NivelRisco.ALTO, 30.0);
        CONSUMO.put(NivelRisco.CRITICO, 50.0);
    }

    private double altitudeMax;
    private double alcanceVoo;

    public Drone(double altitudeMax, double alcanceVoo, String id, String nome,
                 double nivelBateria, StatusRobo status, int missoesRealizadas) {
        super(id, nome, nivelBateria, status, missoesRealizadas);
        setAltitudeMax(altitudeMax);
        setAlcanceVoo(alcanceVoo);
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
            System.out.println("Drone " + getNome() + " não é compatível com esta missão (tipo ou terreno).");
            return false;
        }
        return processarMissao(missao, CONSUMO.get(missao.getArea().getNivelRisco()));
    }

    public double getAltitudeMax() { return altitudeMax; }

    public void setAltitudeMax(double altitudeMax) {
        if (altitudeMax <= 0) throw new RoboException("Altitude máxima deve ser positiva.");
        this.altitudeMax = altitudeMax;
    }

    public double getAlcanceVoo() { return alcanceVoo; }

    public void setAlcanceVoo(double alcanceVoo) {
        if (alcanceVoo <= 0) throw new RoboException("Alcance de voo deve ser positivo.");
        this.alcanceVoo = alcanceVoo;
    }

    @Override
    public String toString() {
        return "Drone " + super.toString()
                + String.format(" [AltMax=%.0fm, Alcance=%.0fm]", altitudeMax, alcanceVoo);
    }
}
