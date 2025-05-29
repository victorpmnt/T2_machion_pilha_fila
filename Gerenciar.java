import java.util.List;

public class Gerenciar {

    public static void main(String[] args) {
        new Gerenciar().iniciar();
    }

    private static final int CAPACIDADE_FILA = 10;
    private static final int CAPACIDADE_PILHA = 5;

    private FilaImpressao filaImpressao;
    private PilhaReimpressao pilhaReimpressao;
    private Menu view;

    public Gerenciar() {
        this.filaImpressao = new FilaImpressao(CAPACIDADE_FILA);
        this.pilhaReimpressao = new PilhaReimpressao(CAPACIDADE_PILHA);
        this.view = new Menu();
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
        switch (opcao) {
            case 1 -> adicionarDocumentoFila();
            case 2 -> {
                try {
                    imprimirDocumentoFila();
                } catch (RuntimeException e) {
                    view.mostrarMensagem("Erro ao imprimir: " + e.getMessage());
                }
            }
            case 3 -> consultarFila();
            case 4 -> view.mostrarMensagem(filaImpressao.gerarRelatorio());
            case 5 -> {
                try {
                    reimprimirDocumentoPilha();
                } catch (RuntimeException e) {
                    view.mostrarMensagem("Erro ao reimprimir: " + e.getMessage());
                }
            }
            case 6 -> consultarDocumentoPilha();
            case 7 -> view.mostrarMensagem(pilhaReimpressao.gerarRelatorio());
            case 8 -> carregarDocumentosDeArquivo();
            case 0 -> {} // encerra
            default -> view.mostrarMensagem("Opção inválida! Tente novamente.");
        }
    }

    private void adicionarDocumentoFila() {
        String nomeArquivo = view.lerInput("Nome do arquivo: ");
        String usuario = view.lerInput("Nome do usuário: ");
        filaImpressao.adicionarDocumento(new Documento(nomeArquivo, usuario));
        view.mostrarMensagem("Documento adicionado à fila: " + nomeArquivo);
    }

    private void imprimirDocumentoFila() {
        Documento doc = filaImpressao.imprimirDocumento();
        view.mostrarMensagem("Impressão concluída: " + doc.getnomeArquivo());
        view.mostrarMensagem("Tempo de espera: " + doc.tempoEspera() + " segundos");

        try {
            pilhaReimpressao.adicionarDocumento(doc);
            view.mostrarMensagem("Documento adicionado automaticamente à pilha de reimpressão.");
        } catch (RuntimeException e) {
            view.mostrarMensagem("Erro ao adicionar à pilha: " + e.getMessage());
        }
    }

    private void consultarFila() {
        String nome = view.lerInput("Nome do arquivo a consultar: ");
        try {
            int pos = filaImpressao.buscarDocumento(nome);
            Documento doc = filaImpressao.consultarDocumento(nome);
            view.mostrarMensagem("Documento na posição " + (pos + 1) + ":\n" + doc);
        } catch (RuntimeException e) {
            view.mostrarMensagem("Documento não encontrado na fila.");
        }
    }

    private void reimprimirDocumentoPilha() {
        Documento doc = pilhaReimpressao.reimprimirDocumento();
        view.mostrarMensagem("Reimpressão concluída: " + doc.getnomeArquivo());
        view.mostrarMensagem("Tempo de espera: " + doc.tempoEspera() + " segundos");
    }

    private void consultarDocumentoPilha() {
        String nome = view.lerInput("Nome do arquivo a consultar: ");
        try {
            Documento doc = pilhaReimpressao.consultarDocumento(nome);
            exibirDocumento(doc);
        } catch (RuntimeException e) {
            view.mostrarMensagem("Documento não encontrado na pilha.");
        }
    }

    private void carregarDocumentosDeArquivo() {
        String caminho = view.lerInput("Caminho do arquivo de documentos: ");
        List<Documento> docs = LeitorArquivos.lerDocumentosDeArquivo(caminho);

        if (docs.isEmpty()) {
            view.mostrarMensagem("Nenhum documento válido foi encontrado.");
            return;
        }

        view.mostrarMensagem("Encontrados " + docs.size() + " documentos.");
        int adicionados = 0;

        for (Documento d : docs) {
            try {
                filaImpressao.adicionarDocumento(d);
                adicionados++;
            } catch (RuntimeException e) {
                view.mostrarMensagem("Limite da fila atingido: " + e.getMessage());
                break;
            }
        }

        view.mostrarMensagem(adicionados + " documentos adicionados à fila com sucesso.");
    }

    private void exibirDocumento(Documento doc) {
        if (doc != null) {
            view.mostrarMensagem(doc.toString());
        } else {
            view.mostrarMensagem("Documento não encontrado.");
        }
    }
}
