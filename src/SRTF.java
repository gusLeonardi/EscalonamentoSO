/**
 * SRTF.java
 *
 * Implementa o algoritmo Shortest Remaining Time First.
 *
 * O escalonador escolhe sempre o processo com menor tempo restante de CPU.
 * Se um processo mais curto chegar enquanto a CPU está ocupada, ele preempte o
 * processo atual e o coloca de volta na fila de prontos.
 *
 * Destaques:
 * - Preemptivo: troca de processo pode acontecer a qualquer instante
 * - Minimiza o tempo de espera médio em muitos cenários
 * - Pode causar starvation de processos longos se chegarem muitos curtos
 */
import java.util.List;

public class SRTF extends Simulador {

    public SRTF(List<Processo> processos) {
        super(processos);
    }

    @Override
    protected void escalonar() {
        if (filaProntos.isEmpty()) return;

        Processo maisCurto = filaProntos.get(0);
        for (Processo p : filaProntos) {
            if (p.getTempoRestante() < maisCurto.getTempoRestante()) {
                maisCurto = p;
            }
        }

        if (processoNaCpu == null) {
            processoNaCpu = maisCurto;
            filaProntos.remove(maisCurto);
            processoNaCpu.setEstado(EEstadoProcesso.EXECUTANDO);
        } else {
            if (maisCurto.getTempoRestante() < processoNaCpu.getTempoRestante()) {
                processoNaCpu.setEstado(EEstadoProcesso.PRONTO);
                filaProntos.add(processoNaCpu);

                processoNaCpu = maisCurto;
                filaProntos.remove(maisCurto);
                processoNaCpu.setEstado(EEstadoProcesso.EXECUTANDO);
            }
        }
    }
}