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