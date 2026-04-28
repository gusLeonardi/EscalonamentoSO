import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Simulador {
    private List<Processo> processosFuturos;
    private List<Processo> filaProntos;
    private List<Processo> filaBloqueados;
    private List<Processo> processosFinalizados;
    
    private Processo processoNaCpu;
    private int tempoAtual;

    public Simulador(List<Processo> processos) {
        this.processosFuturos = new ArrayList<>(processos);
        this.filaProntos = new ArrayList<>();
        this.filaBloqueados = new ArrayList<>();
        this.processosFinalizados = new ArrayList<>();
        this.processoNaCpu = null;
        this.tempoAtual = 0;
    }

    public void iniciar() {
        System.out.println("\n--- INICIANDO SIMULAÇÃO ---");

        while (!processosFuturos.isEmpty() || !filaProntos.isEmpty() || 
               !filaBloqueados.isEmpty() || processoNaCpu != null) {
            
            verificarChegadas();

            atualizarBloqueados();

            executarCpu();

            tempoAtual++;
        }

        System.out.println("--- SIMULAÇÃO CONCLUÍDA NO TEMPO " + tempoAtual + " ---");
    }

    private void verificarChegadas() {
        Iterator<Processo> iterator = processosFuturos.iterator();
        while (iterator.hasNext()) {
            Processo p = iterator.next();
            if (p.getTempoChegada() == tempoAtual) {
                p.setEstado(EEstadoProcesso.PRONTO);
                filaProntos.add(p);
                iterator.remove();
                System.out.println("[Tempo " + tempoAtual + "] Processo " + p.getPid() + " chegou na fila de prontos.");
            }
        }
    }

    private void atualizarBloqueados() {
        Iterator<Processo> iterator = filaBloqueados.iterator();
        while (iterator.hasNext()) {
            Processo p = iterator.next();
            p.setTempoBloqueadoRestante(p.getTempoBloqueadoRestante() - 1);

            if (p.getTempoBloqueadoRestante() <= 0) {
                p.setEstado(EEstadoProcesso.PRONTO);
                filaProntos.add(p);
                iterator.remove();
                System.out.println("[Tempo " + tempoAtual + "] Processo " + p.getPid() + " terminou I/O e voltou para prontos.");
            }
        }
    }

    private void executarCpu() {
        if (processoNaCpu != null) {
            processoNaCpu.setTempoExecutado(processoNaCpu.getTempoExecutado() + 1);
            processoNaCpu.setTempoRestante(processoNaCpu.getTempoRestante() - 1);

            if (processoNaCpu.getTempoRestante() == 0) {
                processoNaCpu.setEstado(EEstadoProcesso.FINALIZADO);
                processoNaCpu.setTempoTurnaround(tempoAtual + 1 - processoNaCpu.getTempoChegada());
                processosFinalizados.add(processoNaCpu);
                System.out.println("[Tempo " + tempoAtual + "] Processo " + processoNaCpu.getPid() + " TERMINOU.");
                processoNaCpu = null;
            } 
            else if (processoNaCpu.precisaFazerIO()) {
                processoNaCpu.registrarIO();
                processoNaCpu.setEstado(EEstadoProcesso.BLOQUEADO);
                filaBloqueados.add(processoNaCpu);
                System.out.println("[Tempo " + tempoAtual + "] Processo " + processoNaCpu.getPid() + " foi para I/O (Bloqueado).");
                processoNaCpu = null;
            }
        }
    }
}