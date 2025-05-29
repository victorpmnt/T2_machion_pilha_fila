import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Documento {
    private String nomeArquivo;
    private String nomeUsuario;
    private LocalDateTime momentoSolicitado;
    
    public Documento(String nomeArquivo, String nomeUsuario) {
        this.nomeArquivo = nomeArquivo;
        this.nomeUsuario = nomeUsuario;
        this.momentoSolicitado = LocalDateTime.now();
    }
    public String getnomeArquivo() {
        return nomeArquivo;
    }
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public LocalDateTime getmomentoSolicitado() {
        return momentoSolicitado;
    }
    public long tempoEspera() {
        return ChronoUnit.SECONDS.between(momentoSolicitado, LocalDateTime.now());
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Documento[" + nomeArquivo + ", usuário: " + nomeUsuario + 
                ", solicitado: " + momentoSolicitado.format(formatter) + "]";
    }
}