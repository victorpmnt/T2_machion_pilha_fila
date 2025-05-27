// Gerenciar.java
import java.util.List;

public class Gerenciar {
    private static final int CAPACIDADE_FILA  = 10;
    private static final int CAPACIDADE_PILHA = 5;

    private FilaImpressao filaImpressao;
    private PilhaReimpressao pilhaReimpressao;
    private MenuView view;

    public Gerenciar() {
        this.filaImpressao = new FilaImpressao(CAPACIDADE_FILA);
        this.pilhaReimpressao = new PilhaReimpressao(CAPACIDADE_PILHA);
        this.view = new MenuView();
    }

    public void iniciar() {
        int opcao;
        do {
            view.mostrarMenu();
            opcao = view.lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);

        view.mostrarMensagem("Encerrando o sistema...");
        view.fechar();
    }

    private void processarOpcao(int opcao) {
        try {
            switch (opcao) {
                case 1 -> adicionarDocumentoFila();
                case 2 -> imprimirDocumentoFila();
                case 3 -> consultarFila();
                case 4 -> view.mostrarMensagem(filaImpressao.gerarRelatorio());
                case 5 -> reimprimirDocumentoPilha();
                case 6 -> consultarDocumentoPilha();
                case 7 -> view.mostrarMensagem(pilhaReimpressao.gerarRelatorio());
                case 8 -> carregarDocumentosDeArquivo();
                case 0 -> { /* desloga sistema() */ }
                default -> view.mostrarMensagem("Opção inválida! Tente novamente.");
            }
        } catch (RuntimeException e) {
            view.mostrarMensagem("Erro: " + e.getMessage());
        }
    }

    private void adicionarDocumentoFila() {
        String nomeArquivo = view.lerInput("Nome do arquivo: ");
        String usuario     = view.lerInput("Nome do usuário: ");
        filaImpressao.adicionarDocumento(new Documento(nomeArquivo, usuario));
        view.mostrarMensagem("Documento adicionado à fila: " + nomeArquivo);
    }

    // documento impresso vai direto pra pilha de Reimpressão
    private void imprimirDocumentoFila() {
        Documento doc = filaImpressao.imprimirDocumento();
        view.mostrarMensagem("Impressão concluída: " + doc.getnomeArquivo());
        view.mostrarMensagem("Tempo de espera: " + doc.calcularTempoEspera() + " segundos");

        try {
            pilhaReimpressao.adicionarDocumento(doc); // Aqui está a mágica
            view.mostrarMensagem("Documento adicionado automaticamente à pilha de reimpressão.");
        } catch (RuntimeException e) {
            view.mostrarMensagem("Erro ao adicionar à pilha: " + e.getMessage());
        }
    }

    private void consultarFila() {
        String nome = view.lerInput("Nome do arquivo a consultar: ");
        int pos = filaImpressao.buscarDocumento(nome);
        if (pos != -1) {
            Documento doc = filaImpressao.consultarDocumento(nome);
            view.mostrarMensagem("Documento na posição " + (pos+1) + ":");
            view.mostrarMensagem(doc.toString());
        } else {
            view.mostrarMensagem("Documento não encontrado na fila.");
        }
    }

    private void reimprimirDocumentoPilha() {
        Documento doc = pilhaReimpressao.reimprimirDocumento();
        view.mostrarMensagem("Reimpressão concluída: " + doc.getnomeArquivo());
        view.mostrarMensagem("Tempo de espera: " + doc.calcularTempoEspera() + " segundos");
    }

    private void consultarDocumentoPilha() {
        String nome = view.lerInput("Nome do arquivo a consultar: ");
        Documento doc = pilhaReimpressao.consultarDocumento(nome);
        if (doc != null) {
            view.mostrarMensagem("Documento encontrado na pilha:");
            view.mostrarMensagem(doc.toString());
        } else {
            view.mostrarMensagem("Documento não encontrado na pilha.");
        }
    }

    private void carregarDocumentosDeArquivo() {
        String caminho = view.lerInput("Caminho do arquivo de documentos: ");
        List<Documento> docs = LeitorArquivos.lerDocumentosDeArquivo(caminho);
        view.mostrarMensagem("Encontrados " + docs.size() + " documentos.");

        String destino = view.lerInput("Adicionar à fila (F) ou pilha (P)? ").toUpperCase();
        int adicionados = 0;
        for (Documento d : docs) {
            try {
                if ("F".equals(destino))   filaImpressao.adicionarDocumento(d);
                else if ("P".equals(destino)) pilhaReimpressao.adicionarDocumento(d);
                adicionados++;
            } catch (RuntimeException e) {
                view.mostrarMensagem("Limite atingido: " + e.getMessage());
                break;
            }
        }
        view.mostrarMensagem(adicionados + " documentos adicionados com sucesso.");
    }

    public static void main(String[] args) {
        new Gerenciar().iniciar();
    }
}
