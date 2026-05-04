import java.util.ArrayList;

public class Usuario {

    protected String nome;
    protected String email;
    protected ArrayList<Playlist> playlists;
    protected ArrayList<Musica> historicoReproducao;

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
        playlists = new ArrayList<>();
        historicoReproducao = new ArrayList<>();
    }

    public String getNome() { return nome; }

    public void criarPlaylist(String nome) {
        playlists.add(new Playlist(nome));
        System.out.println("✅ Playlist criada");
    }

    public void reproduzirMusica(Musica musica) {
        System.out.println("🎵 Reproduzindo: " + musica.getTitulo());
        historicoReproducao.add(musica);
    }

    public void exibirHistorico() {
        for (Musica m : historicoReproducao) {
            m.exibir();
        }
    }

    public int getTotalReproducoes() {
        return historicoReproducao.size();
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }
}
