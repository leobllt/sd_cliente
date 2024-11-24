package sd.cliente.backend;

// @Author Leonardo Bellato

public class Usuario {
    private final String RA;
    private final String nome;
    private final String token;
    private final boolean admin;

    public Usuario(String RA, String nome, String token, boolean admin) {
        this.RA = RA;
        this.nome = nome;
        this.token = token;
        this.admin = admin;
    }

    public String getRA() {
        return this.RA;
    }

    public String getNome() {
        return this.nome;
    }

    public String getToken() {
        return this.token;
    }

    public boolean isAdmin() {
        return this.admin;
    }
}
