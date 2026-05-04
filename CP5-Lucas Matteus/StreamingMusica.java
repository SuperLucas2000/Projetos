import java.util.ArrayList;
import java.util.Scanner;

public class StreamingMusica {

    static ArrayList<Usuario> usuarios = new ArrayList<>();
    static Usuario usuarioLogado = null;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int op;

        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Criar usuário");
            System.out.println("2 - Login");
            System.out.println("3 - Listar usuários");
            System.out.println("4 - Estatísticas");
            System.out.println("0 - Sair");

            op = Integer.parseInt(scanner.nextLine());

            switch (op) {
                case 1 -> criarUsuario(scanner);
                case 2 -> login(scanner);
                case 3 -> listarUsuarios();
                case 4 -> estatisticas();
            }

        } while (op != 0);

        scanner.close();
    }

    static void criarUsuario(Scanner scanner) {

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.println("1-Free  2-Premium");
        int tipo = Integer.parseInt(scanner.nextLine());

        if (tipo == 1) {
            usuarios.add(new UsuarioFree(nome, email));
        } else {
            System.out.print("Plano: ");
            String plano = scanner.nextLine();
            usuarios.add(new UsuarioPremium(nome, email, plano));
        }

        System.out.println("✅ Criado!");
    }

    static void login(Scanner scanner) {

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado!");
            return;
        }

        listarUsuarios();

        System.out.print("Escolha: ");
        int i = Integer.parseInt(scanner.nextLine());

        usuarioLogado = usuarios.get(i - 1);

        if (usuarioLogado instanceof UsuarioFree) {
            MenuFree menu = new MenuFree((UsuarioFree) usuarioLogado);
            executarMenu(menu);
        } else {
            MenuPremium menu = new MenuPremium((UsuarioPremium) usuarioLogado);
            executarMenu(menu);
        }
    }

    static void executarMenu(Object menu) {

        int opcao;

        do {

            if (menu instanceof MenuFree) {
                MenuFree m = (MenuFree) menu;
                m.exibirMenu();
                opcao = m.lerOpcao();
                m.processarOpcao(opcao);

            } else {
                MenuPremium m = (MenuPremium) menu;
                m.exibirMenu();
                opcao = m.lerOpcao();
                m.processarOpcao(opcao);
            }

        } while (opcao != 0);
    }

    static void listarUsuarios() {

        int i = 1;

        for (Usuario u : usuarios) {

            if (u instanceof UsuarioPremium) {
                UsuarioPremium p = (UsuarioPremium) u;
                System.out.println(i + ". " + u.getNome() + " (Premium - " + p.getPlano() + ")");
            } else {
                System.out.println(i + ". " + u.getNome() + " (Free)");
            }

            i++;
        }
    }

    static void estatisticas() {

        int free = 0;
        int premium = 0;
        int total = 0;

        for (Usuario u : usuarios) {

            total += u.getTotalReproducoes();

            if (u instanceof UsuarioPremium) premium++;
            else free++;
        }

        System.out.println("\n=== ESTATÍSTICAS ===");
        System.out.println("Usuários: " + usuarios.size());
        System.out.println("Free: " + free);
        System.out.println("Premium: " + premium);
        System.out.println("Reproduções: " + total);
    }
}