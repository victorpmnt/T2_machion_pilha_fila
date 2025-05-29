import java.util.ArrayList;

public class FilaImpressao {
    private Fila indicesFila;
    private ArrayList<Documento> documentos;
    private int capacidade;
    private int prox = 0;

    public FilaImpressao(int capacidade) {
        this.capacidade = capacidade;
        this.indicesFila = new Fila(capacidade);
        this.documentos = new ArrayList<>(capacidade);
    }

    public boolean filaVazia() {
        return indicesFila.filaVazia();
    }

    public boolean filaCheia() {
        return indicesFila.filaCheia();
    }

    public void adicionarDocumento(Documento doc) {
        if (filaCheia())
            throw new RuntimeException("Fila cheia");
        documentos.add(doc);
        indicesFila.enfileira(prox++);
    }

    public Documento imprimirDocumento() {
        if (filaVazia())
            throw new RuntimeException("Fila vazia");
        int indice = indicesFila.desenfileira();
        return documentos.get(indice);
    }

    public int buscarDocumento(String nomeArquivo) {
        int posicao = 0;
        for (int i = indicesFila.primeiro, cont = 0;
             cont < indicesFila.ocupacao;
             cont++, i = indicesFila.proximaPosicao(i)) {

            Documento doc = documentos.get(indicesFila.dados[i]);
            if (doc.getnomeArquivo().equals(nomeArquivo)) return posicao;
            posicao++;
        }
        throw new DocumentoNaoEncontradoException(nomeArquivo);
    }

    public Documento consultarDocumento(String nomeArquivo) {
        for (int i = indicesFila.primeiro, cont = 0;
             cont < indicesFila.ocupacao;
             cont++, i = indicesFila.proximaPosicao(i)) {

            Documento doc = documentos.get(indicesFila.dados[i]);
            if (doc.getnomeArquivo().equals(nomeArquivo)) return doc;
        }
        return null;
    }

    public String gerarRelatorio() {
        if (filaVazia())
            return "Fila vazia";

        StringBuilder sb = new StringBuilder();
        sb.append("Relatório de Impressão:\n");
        sb.append("Ocupação: ").append(indicesFila.ocupacao).append("/").append(capacidade).append("\n");
        sb.append("Documentos na fila:\n");

        int posicao = 1;
        for (int i = indicesFila.primeiro, cont = 0;
             cont < indicesFila.ocupacao;
             cont++, i = indicesFila.proximaPosicao(i)) {

            Documento doc = documentos.get(indicesFila.dados[i]);
            sb.append(posicao++).append(". ").append(doc).append("\n");
        }

        return sb.toString();
    }

    public class DocumentoNaoEncontradoException extends RuntimeException {
        public DocumentoNaoEncontradoException(String nomeArquivo) {
            super("Documento não encontrado: " + nomeArquivo);
        }
    }
}
