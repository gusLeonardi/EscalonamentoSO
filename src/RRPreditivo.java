import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RRPreditivo extends Simulador {
    private int quantumAtual;
    private int tempoNoSurtoAtual; 
    
    private Map<Integer, Double> previsaoTau; 
    
    private final double ALPHA = 0.5;

    public RRPreditivo(List<Processo> processos) {
        super(processos);
        this.previsaoTau = new HashMap<>();
        
        for (Processo p : processos) {
            previsaoTau.put(p.getPid(), 10.0);
        }
        
        this.quantumAtual = -1; 
        this.tempoNoSurtoAtual = 0;
    }

    @Override
    protected void escalonar() {
        if (processoNaCpu != null) {
            tempoNoSurtoAtual++;
            quantumAtual--;

            if (quantumAtual <= 0) {
                double tauAntigo = previsaoTau.get(processoNaCpu.getPid());
                double novoTau = (ALPHA * tempoNoSurtoAtual) + ((1 - ALPHA) * tauAntigo);
                previsaoTau.put(processoNaCpu.getPid(), novoTau);

                processoNaCpu.setEstado(EEstadoProcesso.PRONTO);
                filaProntos.add(processoNaCpu);
                processoNaCpu = null;
            }
        }

        if (processoNaCpu == null && !filaProntos.isEmpty()) {
            
            double menorTauFila = Double.MAX_VALUE;
            for (Processo p : filaProntos) {
                double tauDoProcesso = previsaoTau.get(p.getPid());
                if (tauDoProcesso < menorTauFila) {
                    menorTauFila = tauDoProcesso;
                }
            }
            
            quantumAtual = Math.max(1, (int) Math.round(menorTauFila));
            tempoNoSurtoAtual = 0; 

            processoNaCpu = filaProntos.remove(0);
            processoNaCpu.setEstado(EEstadoProcesso.EXECUTANDO);
        }
    }
}