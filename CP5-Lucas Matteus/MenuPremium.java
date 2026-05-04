import java.util.Scanner;

public class MenuPremium {
    private UsuarioPremium usuario;
    private Scanner scanner;

    public MenuPremium(UsuarioPremium usuario) {
        this.usuario = usuario;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        System.out.println("\n=== MENU PREMIUM ===");
        System.out.println("1 - Reproduzir música (Alta Qualidade)");
        System.out.println("2 - Ver histórico");
        System.out.println("3 - Criar playlist (ilimitado)");
        System.out.println("4 - Baixar música");
        System.out.println("5 - Ver músicas baixadas");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    public void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> {
                System.out.print("Digite o título da música: ");
                String titulo = scanner.nextLine();
                Musica musica = new Musica(titulo, "Artista Exemplo", 200, "Pop");
                usuario.reproduzirMusica(musica);
            }
            case 2 -> usuario.exibirHistorico();
            case 3 -> {
                System.out.print("Nome da playlist: ");
                String nome = scanner.nextLine();
                usuario.criarPlaylist(nome);
            }
            case 4 -> {
                System.out.print("Digite o título da música para baixar: ");
                String titulo = scanner.nextLine();
                Musica musica = new Musica(titulo, "Artista Exemplo", 180, "Jazz");
                usuario.baixarMusica(musica);
            }
            case 5 -> usuario.listarMusicasBaixadas();
            case 0 -> System.out.println("Saindo...");
            default -> System.out.println("❌ Opção inválida!");
        }
    }

    public int lerOpcao() {
        return Integer.parseInt(scanner.nextLine());
    }
}
