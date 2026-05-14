/**
 * GeradorProcessos.java
 *
 * Utilitário para gerar automaticamente um arquivo processos.txt com muitos dados
 * para testes de escalonamento com volumes maiores de processos.
 *
 * Uso: java GeradorProcessos <quantidade> [semente]
 * Exemplo:
 *   java GeradorProcessos 50        -> gera 50 processos
 *   java GeradorProcessos 100 123   -> gera 100 processos com semente 123
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GeradorProcessos {
    
    public static void main(String[] args) {
        int quantidade = 10;  // padrão
        long semente = System.currentTimeMillis();
        
        if (args.length > 0) {
            quantidade = Integer.parseInt(args[0]);
        }
        if (args.length > 1) {
            semente = Long.parseLong(args[1]);
        }
        
        Random rand = new Random(semente);
        
        System.out.println("Gerando " + quantidade + " processos...");
        System.out.println("Semente: " + semente);
        
        gerarArquivo("processos.txt", quantidade, rand);
        
        System.out.println("✓ Arquivo 'processos.txt' gerado com sucesso!");
    }
    
    private static void gerarArquivo(String nomeArquivo, int quantidade, Random rand) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            
            for (int i = 1; i <= quantidade; i++) {
                int pid = 100 + i;
                int tempoChegada = rand.nextInt(50);           // 0-49 unidades
                int burst = 5 + rand.nextInt(45);              // 5-49 unidades
                int prioridade = rand.nextInt(3) + 1;          // 1-3
                
                StringBuilder linha = new StringBuilder();
                linha.append(pid).append("; ");
                linha.append(tempoChegada).append("; ");
                linha.append(burst).append("; ");
                linha.append(prioridade);
                
                // 30% de chance de ter operações de I/O
                if (rand.nextDouble() < 0.3) {
                    int numIO = rand.nextInt(3) + 1;           // 1-3 operações de I/O
                    for (int j = 0; j < numIO; j++) {
                        int instanteIO = rand.nextInt(burst);
                        if (j == 0) {
                            linha.append("; ").append(instanteIO);
                        } else {
                            linha.append(",").append(instanteIO);
                        }
                    }
                }
                
                writer.write(linha.toString());
                writer.write("\n");
            }
            
            System.out.println("Total: " + quantidade + " processos");
            System.out.println("Tempos de chegada: 0-49");
            System.out.println("Bursts: 5-49");
            System.out.println("Prioridades: 1-3");
            System.out.println("30% com operações de I/O");
            
        } catch (IOException e) {
            System.err.println("Erro ao gerar arquivo: " + e.getMessage());
        }
    }
}
