import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println("\nDigite o numero da operação desejada");
        System.out.println("1. Adicionar documento à fila de impressão");
        System.out.println("2. Imprimir documento da fila");
        System.out.println("3. Consultar documento na fila");
        System.out.println("4. Exibir documentos da fila de impressão");
        System.out.println("5. Reimprimir documento da pilha");
        System.out.println("6. Consultar documento na pilha de reimpressão");
        System.out.println("7. Exibir documentos da pilha de reimpressão");
        System.out.println("8. Inserir documento.txt");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public int lerOpcao() {
        String linha = scanner.nextLine();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String lerInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void mostrarMensagem(String msg) {
        System.out.println(msg);
    }

    public void fechar() {
        scanner.close();
    }
}
