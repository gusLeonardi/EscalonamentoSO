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
        
        System.out.println("Processos carregados na memória:");
        for (Processo p : processos) {
            System.out.println("PID: " + p.getPid() + " | Chegada: " + p.getTempoChegada() + 
                               " | Burst: " + p.getTempoRestante() + " | Prioridade: " + p.getPrioridade());
        }
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