import java.util.Queue;

public class Processo {
    private int pid;
    private int tempoChegada;
    private int burstTotal;
    private int prioridade;
    private Queue<Integer> instantesIO;

    private int tempoRestante;
    private int tempoExecutado;
    private int tempoEspera;
    private int tempoTurnaround;
    private int tempoBloqueadoRestante;
    private EEstadoProcesso estado;

    public Processo(int pid, int tempoChegada, int burstTotal, int prioridade, Queue<Integer> instantesIO) {
        this.pid = pid;
        this.tempoChegada = tempoChegada;
        this.burstTotal = burstTotal;
        this.prioridade = prioridade;
        this.instantesIO = instantesIO;

        this.tempoRestante = burstTotal;
        this.tempoExecutado = 0;
        this.tempoEspera = 0;
        this.tempoBloqueadoRestante = 0;
        this.estado = EEstadoProcesso.NOVO;
    }

    public int getPid() {
        return pid;
    }

    public int getTempoChegada() {
        return tempoChegada;
    }

    public EEstadoProcesso getEstado() {
        return estado;
    }

    public void setEstado(EEstadoProcesso estado) {
        this.estado = estado;
    }

    public boolean precisaFazerIO() {
        return !instantesIO.isEmpty() && instantesIO.peek() == tempoExecutado;
    }

    public int getTempoRestante() {
        return tempoRestante;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void registrarIO() {
        if (!instantesIO.isEmpty()) {
            instantesIO.poll();
            this.tempoBloqueadoRestante = 5;
        }
    }

    public void executar1Unidade() {
        tempoExecutado++;
        tempoRestante--;
    }

    public boolean terminou() {
        return tempoRestante <= 0;
    }

    public void incrementarEspera() {
        this.tempoEspera++;
    }

    public int getTempoEspera() {
        return tempoEspera;
    }

    public void decrementarTempoBloqueado() {
        if (this.tempoBloqueadoRestante > 0) {
            this.tempoBloqueadoRestante--;
        }
    }

    public int getTempoBloqueadoRestante() {
        return tempoBloqueadoRestante;
    }

    public int getTempoTurnaround() {
        return tempoTurnaround;
    }

    public void setTempoTurnaround(int tempoTurnaround) {
        this.tempoTurnaround = tempoTurnaround;
    }

    public int getBurstTotal(){
        return burstTotal;
    }

    public void setTempoBloqueadoRestante(int tempo) {
        this.tempoBloqueadoRestante = tempo;
    }

    public int getTempoExecutado() {
        return tempoExecutado;
    }

    public void setTempoExecutado(int tempo) {
        this.tempoExecutado = tempo;
    }

    public void setTempoRestante(int tempo) {
        this.tempoRestante = tempo;
    }


}