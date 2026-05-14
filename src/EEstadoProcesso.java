/**
 * EEstadoProcesso.java
 *
 * Enumeração dos estados possíveis de um processo no simulador.
 * Representa o ciclo de vida do processo durante a simulação de escalonamento.
 */
public enum EEstadoProcesso {
    NOVO,
    PRONTO,
    EXECUTANDO,
    BLOQUEADO,
    FINALIZADO
}
