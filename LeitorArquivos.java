import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeitorArquivos {
    public static List<Documento> lerDocumentosDeArquivo(String caminhoArquivo) {
        List<Documento> documentos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length >= 2) {
                    String nomeArquivo = partes[0].trim();
                    String nomeUsuario = partes[1].trim();
                    documentos.add(new Documento(nomeArquivo, nomeUsuario));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return documentos;
    }
}