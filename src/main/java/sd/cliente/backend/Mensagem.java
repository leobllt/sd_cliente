package sd.cliente.backend;

// @Author Leonardo Bellato

public class Mensagem {
    private final int op;
    private final String user;
    private final String password;
    private final String name;
    private final String token;
    private final String response;
    private final String message;

    public Mensagem(int op, String user, String password, String name, String token, String response, String message) {
        this.op = op;
        this.user = user;
        this.password = password;
        this.name = name;
        this.token = token;
        this.response = response;
        this.message = message;
    }

    @Override
    public String toString(){
        return "Requisicao(" + this.op + ", " + this.user + ", " + this.password + ", " + this.name +
                             ", " + this.token + ", " + this.response + ", " + this.message + ")";
    }

    public int getOp() {
        return op;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public String getResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }
}
