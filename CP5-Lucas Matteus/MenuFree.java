import java.util.Scanner;

public class MenuFree {
    private UsuarioFree usuario;
    private Scanner scanner;

    public MenuFree(UsuarioFree usuario) {
        this.usuario = usuario;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        System.out.println("\n=== MENU FREE ===");
        System.out.println("1 - Reproduzir música");
        System.out.println("2 - Ver histórico");
        System.out.println("3 - Criar playlist (máx. 3)");
        System.out.println("4 - Fazer upgrade para Premium");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    public void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> {
                System.out.print("Digite o título da música: ");
                String titulo = scanner.nextLine();
                Musica musica = new Musica(titulo, "Artista Exemplo", 200, "Rock");
                usuario.reproduzirMusica(musica);
            }
            case 2 -> usuario.exibirHistorico();
            case 3 -> {
                System.out.print("Nome da playlist: ");
                String nome = scanner.nextLine();
                usuario.criarPlaylist(nome);
            }
            case 4 -> System.out.println("Upgrade disponível! Reinicie como Premium.");
            case 0 -> System.out.println("Saindo...");
            default -> System.out.println("❌ Opção inválida!");
        }
    }

    public int lerOpcao() {
        return Integer.parseInt(scanner.nextLine());
    }
}
