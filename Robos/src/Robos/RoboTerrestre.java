package Robos;



public class RoboTerrestre extends Robo implements Comunicavel {

    //Caracteristicas unicas do RoboTerrestre
    private int cap_Max;
    private String tipo_Carga;


    public RoboTerrestre(int cap_Max, String tipo_Carga, String id, String nome, double nivelBateria, StatusRobo status, int missoesRealizadas) {

        super(id, nome, nivelBateria, status, missoesRealizadas);

        this.cap_Max = cap_Max;
        this.tipo_Carga = tipo_Carga;


    }
    // Getters e Setters
    public int getCap_Max() {
        return cap_Max;
    }

    public String getTipo_Carga() {
        return tipo_Carga;
    }

    public void setCap_Max(int cap_Max) {
        this.cap_Max = cap_Max;
    }

    public void setTipo_Carga(String tipo_Carga) {
        this.tipo_Carga = tipo_Carga;
    }

    @Override
    public String exibirResumo() {
        return toString();
    }

    //Na classe Missao faltou adicionar um atributo do tipo TipoTerreno(o enum de terrenos) para o metodo executarMissao estar correto
    @Override
    public boolean executarMissao(Missao missao) {//Método executarMissão para o Robo Terrestre

        boolean retorno = false;
        if (missao.getStatus() != StatusMissao.PENDENTE) {
            System.out.println("Missão não esta pendente para executar");
            retorno = false;
        }

        if (missao.statusRobo() == StatusRobo.MANUTENCAO || missao.statusRobo() == StatusRobo.INDISPONIVEL) {

            System.out.println("Robo está indisponivel para missão!!");
            retorno =  false;
        }
        if ((missao.getArea().getTipoTerreno() != TipoTerreno.URBANO && missao.getArea().getTipoTerreno() != TipoTerreno.FLORESTA_FECHADA) || getCap_Max() > 1000.0) {

            System.out.println("Capacidade está além da maxima permitida para o robo terrestre OU terreno da missão não é permitido");
            return false;

        } else if (missao.getStatus() == StatusMissao.PENDENTE && missao.getNivelRisco() == NivelRisco.BAIXO && missao.statusRobo() == StatusRobo.DISPONIVEL && getNivelBateria() > 20) {

            System.out.println("Robo pronto para missão!");
            missao.setStatus(StatusMissao.EM_ANDAMENTO);
            setStatus(StatusRobo.EM_MISSAO);
            setMissoesRealizadas(getMissoesRealizadas() + 1);
            consumirBateria(20.0);

            missao.setStatus(StatusMissao.CONCLUIDA);
            setStatus(StatusRobo.DISPONIVEL);

            retorno =  true;
        } else if (missao.getStatus() == StatusMissao.PENDENTE && missao.getNivelRisco() == NivelRisco.MEDIO && missao.statusRobo() == StatusRobo.DISPONIVEL && getNivelBateria() > 35) {

            System.out.println("Robo pronto para missão!");
            missao.setStatus(StatusMissao.EM_ANDAMENTO);
            setStatus(StatusRobo.EM_MISSAO);
            setMissoesRealizadas(getMissoesRealizadas() + 1);
            consumirBateria(35.0);

            missao.setStatus(StatusMissao.CONCLUIDA);
            setStatus(StatusRobo.DISPONIVEL);

            retorno =  true;

        } else if (missao.getStatus() == StatusMissao.PENDENTE && missao.getNivelRisco() == NivelRisco.ALTO && missao.statusRobo() == StatusRobo.DISPONIVEL && getNivelBateria() > 50) {

            System.out.println("Robo pronto para missão!");
            missao.setStatus(StatusMissao.EM_ANDAMENTO);
            setStatus(StatusRobo.EM_MISSAO);
            setMissoesRealizadas(getMissoesRealizadas() + 1);
            consumirBateria(50.0);

            missao.setStatus(StatusMissao.CONCLUIDA);
            setStatus(StatusRobo.DISPONIVEL);
            retorno =  true;

        } else if (missao.getStatus() == StatusMissao.PENDENTE && missao.getNivelRisco() == NivelRisco.CRITICO && missao.statusRobo() == StatusRobo.DISPONIVEL && getNivelBateria() > 75) {

            System.out.println("Robo pronto para missão!");
            missao.setStatus(StatusMissao.EM_ANDAMENTO);
            setStatus(StatusRobo.EM_MISSAO);
            setMissoesRealizadas(getMissoesRealizadas() + 1);
            consumirBateria(75.0);

            missao.setStatus(StatusMissao.CONCLUIDA);
            setStatus(StatusRobo.DISPONIVEL);
            retorno =  true;
        }

        return retorno;

}
    //Método enviarMensagem para alguma mensagem que o Robo Terrestre relate sobre a missão
    @Override
    public void enviarMensagem(String mensagem) {
        System.out.println("[COMUNICAÇÃO ROBO TERRESTRE -> CENTRAL] " + getNome() + " (ID: " + getId() + ") relata: " + mensagem);


    }

    //Metodo receberMissao que a "central" manda para o Robo Terrestre como um novo comando para fazer
    @Override
    public String receberMensagem() {

        // Simulando que o drone foi até a "caixa de entrada" dele
        // e leu a última instrução enviada pela Central de Resgate:
        String ordemDaCentral = "Central: Fazer varredura dentro do local da missão.";
        System.out.println("[COMUNICAÇÃO] O Robo Terrestre " + getNome() + " recebeu a mensagem da central.");

        // Ele retorna essa String para quem chamou o método poder ler
        return ordemDaCentral;
    }

    public String toString() {

        return "Id do Robo Terrestre: " + getId() + "\nNome do Robo Terrestre: " + getNome() + "\nBateria do Robo Terrestre: " + getNivelBateria() + "\nStatus do Robo Terrestre: " + getStatus()
                + "\nMissões realizadas: " + getMissoesRealizadas();
    }



}
