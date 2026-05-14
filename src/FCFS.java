/**
 * FCFS.java
 *
 * Implementa o algoritmo First Come First Served.
 * Esta classe escolhe o processo mais antigo na fila de prontos
 * e não permite preempção.
 *
 * Importância:
 * - serve como baseline simples
 * - permite comparar comportamento com algoritmos preemptivos
 * - evidencia o efeito convoy em processos longos
 */

import java.util.List;

public class FCFS extends Simulador {

    public FCFS(List<Processo> processos) {
        super(processos); 
    }

    @Override
    protected void escalonar() {
        if (processoNaCpu == null && !filaProntos.isEmpty()) {
            processoNaCpu = filaProntos.remove(0); 
            processoNaCpu.setEstado(EEstadoProcesso.EXECUTANDO);
        }
    }
}