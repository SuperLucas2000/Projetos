import java.util.ArrayList;

public class UsuarioPremium extends Usuario {

    private String plano;
    private ArrayList<Musica> musicasBaixadas;

    public UsuarioPremium(String nome, String email, String plano) {
        super(nome, email);
        this.plano = plano;
        musicasBaixadas = new ArrayList<>();
    }

    public String getPlano() {
        return plano;
    }

    @Override
    public void reproduzirMusica(Musica musica) {
        System.out.println("Alta qualidade: " + musica.getTitulo());
        historicoReproducao.add(musica);
    }

    public void baixarMusica(Musica m) {
        musicasBaixadas.add(m);
        System.out.println("Baixada!");
    }

    public void listarMusicasBaixadas() {
        for (Musica m : musicasBaixadas) {
            m.exibir();
        }
    }
}