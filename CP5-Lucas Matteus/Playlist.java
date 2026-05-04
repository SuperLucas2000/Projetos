import java.util.ArrayList;

public class Playlist {
    protected String nome;
    protected ArrayList<Musica> musicas;

    public Playlist(String nome) {
        setNome(nome);
        this.musicas = new ArrayList<>();
    }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("❌ Nome inválido!");
        this.nome = nome.trim();
    }

    public void adicionarMusica(Musica musica) {
        musicas.add(musica);
        System.out.println("✅ Música adicionada: " + musica.getTitulo());
    }

    public void listarMusicas() {
        System.out.println("🎵 Playlist: " + nome);
        for (Musica m : musicas) {
            m.exibir();
        }
    }

    public int getQuantidadeMusicas() {
        return musicas.size();
    }
}