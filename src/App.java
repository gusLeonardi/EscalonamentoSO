import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class App {

    public static void main(String[] args) {
        System.out.println("Iniciando o Simulador de Escalonamento...\n");
        
        String caminhoArquivo = "processos.txt";
        List<Processo> processos = lerArquivoProcessos(caminhoArquivo);
        
        if (processos.isEmpty()) {
            System.out.println("Nenhum processo carregado. Verifique o arquivo processos.txt");
            return;
        }

        System.out.println("=== INICIANDO COMPARAÇÃO DE ESCALONADORES ===");

        executarEExibir("FCFS", new FCFS(clonarLista(processos)));
        
        executarEExibir("SRTF", new SRTF(clonarLista(processos)));
        
        executarEExibir("Round-Robin Preditivo", new RRPreditivo(clonarLista(processos)));
        
        executarEExibir("Multilevel Queue (MLQ)", new MLQ(clonarLista(processos)));
    }

    /**
     * Cria uma cópia profunda da lista para que um simulador não estrague os dados do próximo.
     */
    static List<Processo> clonarLista(List<Processo> listaOriginal) {
        List<Processo> copia = new ArrayList<>();
        for (Processo p : listaOriginal) {
            copia.add(new Processo(p.getPid(), p.getTempoChegada(), p.getBurstTotal(), 
                                   p.getPrioridade(), p.getFilaIOCopia())); 
        }
        return copia;
    }

    /**
     * Método auxiliar para rodar a simulação e imprimir o relatório de métricas exigido.
     */
    static void executarEExibir(String nomeAlgoritmo, Simulador simulador) {
        simulador.iniciar();
        
        List<Processo> concluidos = simulador.getProcessosFinalizados();
        int tempoTotal = simulador.getTempoAtual();
        
        double esperaMedia = concluidos.stream().mapToDouble(Processo::getTempoEspera).average().orElse(0.0);
        double turnaroundMedio = concluidos.stream().mapToDouble(Processo::getTempoTurnaround).average().orElse(0.0);
        double vazao = (double) concluidos.size() / tempoTotal;

        System.out.println("\n--- RELATÓRIO: " + nomeAlgoritmo + " ---");
        System.out.printf("Tempo de Espera Médio: %.2f ms\n", esperaMedia);
        System.out.printf("Tempo de Retorno (Turnaround) Médio: %.2f ms\n", turnaroundMedio);
        System.out.printf("Vazão (Throughput): %.4f processos/unidade de tempo\n", vazao);
        System.out.println("--------------------------------------------");
    }

    /**
     * Método responsável por ler o arquivo .txt e converter para uma lista de objetos Processo.
     */
    static List<Processo> lerArquivoProcessos(String caminhoArquivo) {
        List<Processo> listaProcessos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] campos = linha.split(";");
                
                int pid = Integer.parseInt(campos[0].trim());
                int tempoChegada = Integer.parseInt(campos[1].trim());
                int burstTotal = Integer.parseInt(campos[2].trim());
                int prioridade = Integer.parseInt(campos[3].trim());

                Queue<Integer> instantesIO = new LinkedList<>();
                if (campos.length > 4 && !campos[4].trim().isEmpty()) {
                    String[] ios = campos[4].split(",");
                    for (String io : ios) {
                        instantesIO.add(Integer.parseInt(io.trim()));
                    }
                }

                Processo p = new Processo(pid, tempoChegada, burstTotal, prioridade, instantesIO);
                listaProcessos.add(p);
            }
            
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro de formatação nos números do arquivo: " + e.getMessage());
        }

        return listaProcessos;
    }
}