public class UsuarioFree extends Usuario {

    private int contador = 0;

    public UsuarioFree(String nome, String email) {
        super(nome, email);
    }

    @Override
    public void reproduzirMusica(Musica musica) {
        contador++;

        if (contador % 3 == 0) {
            System.out.println("Anúncio...");
        }

        super.reproduzirMusica(musica);
    }

    @Override
    public void criarPlaylist(String nome) {
        if (playlists.size() >= 3) {
            System.out.println("❌ Limite de playlists atingido!");
            return;
        }

        super.criarPlaylist(nome);
    }
}