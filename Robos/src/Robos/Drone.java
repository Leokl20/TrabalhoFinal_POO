package Robos;
import java.util.Scanner;

public class Drone extends Robo implements Comunicavel {

    //Caracteristicas unicas do Drone
    private double altitude_Max;
    private double alcance_Voo;

    public Drone(double altitude_Max, double alcance_Voo, String id, String nome, double nivelBateria, StatusRobo status, int missoesRealizadas) {

        super(id, nome, nivelBateria, status, missoesRealizadas);

        this.altitude_Max = altitude_Max;
        this.alcance_Voo = alcance_Voo;

    }

    // Getters e Setters
    public double getAltitude_Max() {
        return altitude_Max;
    }

    public void setAltitude_Max(double altitude_Max) {

        this.altitude_Max = altitude_Max;
    }


    public double getAlcance_Voo() {
        return alcance_Voo;

    }

    public void setAlcance_Voo(double alcance_Voo) {
        this.alcance_Voo = alcance_Voo;
    }

    @Override
    public String exibirResumo() {

        return toString();
    }

    //Na classe Missao faltou adicionar um atributo do tipo TipoTerreno(o enum de terrenos) para o metodo executarMissao estar correto
    @Override
    public boolean executarMissao(Missao missao) { //Método executarMissão para o drone
        boolean retorno = false;
        if (missao.getStatus() != StatusMissao.PENDENTE) {
            System.out.println("Missão não esta pendente para executar");
            retorno = false;
        }

        if (missao.statusRobo() == StatusRobo.MANUTENCAO || missao.statusRobo() == StatusRobo.INDISPONIVEL) {

            System.out.println("Robo está indisponivel para missão!!");
            retorno =  false;
        }
        if ((missao.getArea().getTipoTerreno() != TipoTerreno.MONTANHOSO && missao.getArea().getTipoTerreno() != TipoTerreno.ALAGADO) || getAltitude_Max() > 1000.0 || getAlcance_Voo() > 2000.0) {

            System.out.println("Altitude ou alcance fora do alcance do drone E Terreno da missão invalido para o drone");
            return false;

        } else if (missao.getStatus() == StatusMissao.PENDENTE && missao.getNivelRisco() == NivelRisco.BAIXO && missao.statusRobo() == StatusRobo.DISPONIVEL && getNivelBateria() > 10) {

            System.out.println("Robo pronto para missão!");
            missao.setStatus(StatusMissao.EM_ANDAMENTO);
            setStatus(StatusRobo.EM_MISSAO);
            setMissoesRealizadas(getMissoesRealizadas() + 1);
            consumirBateria(10.0);

            missao.setStatus(StatusMissao.CONCLUIDA);
            setStatus(StatusRobo.DISPONIVEL);

            retorno =  true;
        } else if (missao.getStatus() == StatusMissao.PENDENTE && missao.getNivelRisco() == NivelRisco.MEDIO && missao.statusRobo() == StatusRobo.DISPONIVEL && getNivelBateria() > 20) {

            System.out.println("Robo pronto para missão!");
            missao.setStatus(StatusMissao.EM_ANDAMENTO);
            setStatus(StatusRobo.EM_MISSAO);
            setMissoesRealizadas(getMissoesRealizadas() + 1);
            consumirBateria(20.0);

            missao.setStatus(StatusMissao.CONCLUIDA);
            setStatus(StatusRobo.DISPONIVEL);

            retorno =  true;

        } else if (missao.getStatus() == StatusMissao.PENDENTE && missao.getNivelRisco() == NivelRisco.ALTO && missao.statusRobo() == StatusRobo.DISPONIVEL && getNivelBateria() > 30) {

            System.out.println("Robo pronto para missão!");
            missao.setStatus(StatusMissao.EM_ANDAMENTO);
            setStatus(StatusRobo.EM_MISSAO);
            setMissoesRealizadas(getMissoesRealizadas() + 1);
            consumirBateria(30.0);

            missao.setStatus(StatusMissao.CONCLUIDA);
            setStatus(StatusRobo.DISPONIVEL);
            retorno =  true;

        } else if (missao.getStatus() == StatusMissao.PENDENTE && missao.getNivelRisco() == NivelRisco.CRITICO && missao.statusRobo() == StatusRobo.DISPONIVEL && getNivelBateria() > 50) {

            System.out.println("Robo pronto para missão!");
            missao.setStatus(StatusMissao.EM_ANDAMENTO);
            setStatus(StatusRobo.EM_MISSAO);
            setMissoesRealizadas(getMissoesRealizadas() + 1);
            consumirBateria(50.0);

            missao.setStatus(StatusMissao.CONCLUIDA);
            setStatus(StatusRobo.DISPONIVEL);
            retorno =  true;
        }

        return retorno;
    }

//Método enviarMensagem para alguma mensagem que o Drone relate sobre a missão
    @Override
    public void enviarMensagem(String mensagem) {

        System.out.println("[COMUNICAÇÃO DRONE -> CENTRAL] " + getNome() + " (ID: " + getId() + ") relata: " + mensagem);

    }

//Metodo receberMissao que a "central" manda para o Drone como um novo comando para fazer

    @Override
    public String receberMensagem() {
        // Simulando que o drone foi até a "caixa de entrada" dele
        // e leu a última instrução enviada pela Central de Resgate:
        String ordemDaCentral = "Central: Fazer varredura dentro do local da missão.";
        System.out.println("[COMUNICAÇÃO] O drone " + getNome() + " recebeu a mensagem da central.");

        // Ele retorna essa String para quem chamou o método poder ler
        return ordemDaCentral;
    }


    public String toString() {

        return "Id do Drone: " + getId() + "\nNome do drone: " + getNome() + "\nBateria do Drone: " + getNivelBateria() + "\nStatus do Drone: " + getStatus()
                + "\nMissões realizadas: " + getMissoesRealizadas();
    }

}



