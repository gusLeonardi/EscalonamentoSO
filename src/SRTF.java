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