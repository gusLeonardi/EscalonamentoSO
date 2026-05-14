/**
 * MLQ.java
 *
 * Implementa o algoritmo Multi-Level Queue com duas prioridades.
 *
 * Estrutura:
 * - Fila 1: processos de alta prioridade (prioridade == 1) usando Round-Robin
 *   com quantum fixo de 4 unidades.
 * - Fila 2: processos de baixa prioridade executando em FCFS.
 *
 * Importância:
 * - Mostra como o escalonador pode priorizar processos críticos
 * - Permite estudar trade-offs entre justiça e responsividade
 * - Demonstra preempção seletiva: fila 2 só recebe CPU quando fila 1 está vazia
 */
import java.util.ArrayList;
import java.util.List;

public class MLQ extends Simulador {
    private int quantumRestante;
    private final int QUANTUM_FIXO = 4; 

    public MLQ(List<Processo> processos) {
        super(processos);
        this.quantumRestante = QUANTUM_FIXO;
    }

    @Override
    protected void escalonar() {
        List<Processo> fila1 = new ArrayList<>(); 
        List<Processo> fila2 = new ArrayList<>(); 

        for (Processo p : filaProntos) {
            if (p.getPrioridade() == 1) fila1.add(p);
            else fila2.add(p);
        }

        if (processoNaCpu != null) {
            quantumRestante--; 
            
            if (quantumRestante == 0 && processoNaCpu.getPrioridade() == 1) {
                processoNaCpu.setEstado(EEstadoProcesso.PRONTO);
                filaProntos.add(processoNaCpu);
                processoNaCpu = null;
            } 
            else if (processoNaCpu.getPrioridade() == 2 && !fila1.isEmpty()) {
                processoNaCpu.setEstado(EEstadoProcesso.PRONTO);
                filaProntos.add(processoNaCpu);
                processoNaCpu = null;
            }
        }

        if (processoNaCpu == null) {
            if (!fila1.isEmpty()) {
                processoNaCpu = fila1.get(0); 
                filaProntos.remove(processoNaCpu);
                processoNaCpu.setEstado(EEstadoProcesso.EXECUTANDO);
                quantumRestante = QUANTUM_FIXO;
            } else if (!fila2.isEmpty()) {
                processoNaCpu = fila2.get(0);
                filaProntos.remove(processoNaCpu);
                processoNaCpu.setEstado(EEstadoProcesso.EXECUTANDO);
            }
        }
    }
}