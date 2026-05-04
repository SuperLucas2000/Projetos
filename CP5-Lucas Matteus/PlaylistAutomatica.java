import java.util.ArrayList;

public class PlaylistAutomatica extends Playlist {

    private String criterio;

    public PlaylistAutomatica(String nome, String criterio) {
        super(nome);
        this.criterio = criterio;
    }

    @Override
    public void listarMusicas() {
        System.out.println("🤖 Playlist Automática: " + nome);
        System.out.println("Critério: " + criterio);
        super.listarMusicas();
    }

    public void atualizar(ArrayList<Musica> todas) {
        musicas.clear();

        for (Musica m : todas) {
            if (m.getGenero().equalsIgnoreCase(criterio)) {
                musicas.add(m);
            }
        }
    }
}