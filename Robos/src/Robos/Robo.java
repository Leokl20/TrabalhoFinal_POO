package Robos;

//Classe robo com atualizações feitas com a implementação dos metodos "exibirResumo" e "consultarBateria" e o "private missoesRealizadas"
public abstract class Robo implements Comunicavel {

    private String id;
    private String nome;
    private double nivelBateria; // 0.0 a 100.0
    private StatusRobo status;
    private int missoesRealizadas;

    public Robo(String id, String nome, double nivelBateria, StatusRobo status, int  missoesRealizadas) {
        this.id = id;
        this.nome = nome;
        this.nivelBateria = nivelBateria;
        this.status = StatusRobo.DISPONIVEL;
        this.missoesRealizadas = missoesRealizadas;
    }

    // Método polimórfico abstrato a ser implementado por Drone e RoboTerrestre
    public abstract boolean executarMissao(Missao missao);

    // Métodos de controle de bateria
    public void recarregarBateria() {
        this.nivelBateria = 100.0;
        this.status = StatusRobo.DISPONIVEL;
    }

    public void consumirBateria(double quantidade) {
        this.nivelBateria = Math.max(0.0, this.nivelBateria - quantidade);
        if (this.nivelBateria == 0.0) {
            this.status = StatusRobo.INDISPONIVEL;
        }
    }

    //Métodos minimos que devem estar presentes na classe robo
    public StatusRobo verificarDisponibilidade(){
        return status;
    }

    public String exibirResumo(){
        return toString();
    }

    public double consultarNivelBateria(){
        return this.nivelBateria;
    }


    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public double getNivelBateria() {
        return nivelBateria;
    }

    public void setNivelBateria(double nivelBateria) {
        this.nivelBateria = nivelBateria;
    }


    public StatusRobo getStatus() {
        return status;
    }

    public void setStatus(StatusRobo status) {
        this.status = status;
    }

    public int getMissoesRealizadas(){
        return missoesRealizadas;
    }

    public void setMissoesRealizadas(int missoesRealizadas){

        this.missoesRealizadas = missoesRealizadas;
    }



    @Override
    public String toString() {
        return String.format("%s [ID=%s, Bateria=%.1f%%, Status=%s]",
                nome, id, nivelBateria, status);
    }
}