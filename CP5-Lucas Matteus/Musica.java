public class Musica {
    private String titulo;
    private String artista;
    private int duracaoSegundos;
    private String genero;

    public static final String[] GENEROS_VALIDOS = {
            "Rock", "Pop", "Jazz", "MPB", "Samba", "Rap"
    };

    public Musica(String titulo, String artista, int duracaoSegundos, String genero) {
        setTitulo(titulo);
        setArtista(artista);
        setDuracaoSegundos(duracaoSegundos);
        setGenero(genero);
    }

    public Musica() {}

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) throw new IllegalArgumentException("❌ Título inválido!");
        this.titulo = titulo.trim();
    }

    public String getArtista() { return artista; }
    public void setArtista(String artista) {
        if (artista == null || artista.trim().isEmpty()) throw new IllegalArgumentException("❌ Artista inválido!");
        this.artista = artista.trim();
    }

    public int getDuracaoSegundos() { return duracaoSegundos; }
    public void setDuracaoSegundos(int duracaoSegundos) {
        if (duracaoSegundos <= 0 || duracaoSegundos > 3600) throw new IllegalArgumentException("❌ Duração inválida!");
        this.duracaoSegundos = duracaoSegundos;
    }

    public String getGenero() { return genero; }
    public void setGenero(String genero) {
        boolean valido = false;
        for (String g : GENEROS_VALIDOS) {
            if (g.equalsIgnoreCase(genero)) { valido = true; break; }
        }
        if (!valido) throw new IllegalArgumentException("❌ Gênero inválido!");
        this.genero = genero;
    }

    public void exibir() {
        System.out.printf("Título: %s | Artista: %s | Duração: %s | Gênero: %s%n",
                titulo, artista, getDuracaoFormatada(duracaoSegundos), genero);
    }

    public static String getDuracaoFormatada(int segundos) {
        int minutos = segundos / 60;
        int segs = segundos % 60;
        return String.format("%d:%02d", minutos, segs);
    }
}
