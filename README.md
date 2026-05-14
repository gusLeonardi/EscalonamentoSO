# Simulador de Escalonamento de Processos (SO)

Um simulador que compara 4 algoritmos de escalonamento de CPU medindo desempenho através de 3 métricas principais.

## 🚀 Compilação e Execução

### Compilação
```bash
javac -d bin src/*.java
```

### Execução
```bash
cd bin
java App
```

### Saída Esperada
```
Iniciando o Simulador de Escalonamento...

=== INICIANDO COMPARAÇÃO DE ESCALONADORES ===

--- RELATÓRIO: FCFS ---
Tempo de Espera Médio: XX.XX ms
Tempo de Retorno (Turnaround) Médio: XX.XX ms
Vazão (Throughput): X.XXXX processos/unidade de tempo

--- RELATÓRIO: SRTF ---
[...]

--- RELATÓRIO: Round-Robin Preditivo ---
[...]

--- RELATÓRIO: Multilevel Queue (MLQ) ---
[...]
```

## 📂 Estrutura do Projeto

```
EscalonamentoSO/
├── src/
│   ├── App.java              # Ponto de entrada, orquestra simulação
│   ├── Simulador.java        # Classe abstrata com lógica comum
│   ├── FCFS.java             # First Come First Served (não-preemptivo)
│   ├── SRTF.java             # Shortest Remaining Time First (preemptivo)
│   ├── RRPreditivo.java      # Round-Robin com quantum adaptivo
│   ├── MLQ.java              # Multi-Level Queue (2 filas com prioridades)
│   ├── Processo.java         # Modelo de dados
│   └── EEstadoProcesso.java  # Estados possíveis
├── bin/                       # Compilados (gerado automaticamente)
├── processos.txt             # Arquivo de entrada com dados dos processos
└── README.md                 # Este arquivo
```

## 📥 Arquivo de Entrada: processos.txt

**Formato**:
```
PID;Tempo_Chegada;Burst_Total;Prioridade;[Instantes_IO]
```

**Campos**:
- **PID**: Identificador do processo (ex: 101)
- **Tempo_Chegada**: Quando entra no sistema (0 = imediato)
- **Burst_Total**: Tempo total de CPU necessário (ms)
- **Prioridade**: 1=Alta, 2=Normal, 3=Baixa (used by MLQ)
- **Instantes_IO**: Momentos do tempo de CPU acumulado onde faz I/O (separados por vírgula)

**Exemplo**:
```
101; 0; 40; 2; 10,25
102; 2; 15; 1; 5
103; 4; 8; 2;
104; 10; 25; 1; 10,18
105; 12; 5; 2;
```
