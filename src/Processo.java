import java.util.LinkedList;
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
    private String estado;

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
        this.estado = "NOVO";
    }

    public int getPid() {
        return pid;
    }

    public int getTempoChegada() {
        return tempoChegada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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

}