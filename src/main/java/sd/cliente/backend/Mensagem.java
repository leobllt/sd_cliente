package sd.cliente.backend;

// @Author Leonardo Bellato

import java.util.List;

public class Mensagem {
    private String op;
    private String user;
    private String password;
    private String name;
    private String token;
    private String response;
    private String message;
    private Categoria[] categories;
    private String[] categoryIds;

    public Mensagem(String op, String user, String password, String name, String token) {
        this.op = op;
        this.user = user;
        this.password = password;
        this.name = name;
        this.token = token;
    }

    public Mensagem(String op, String token, Categoria[] categories, String[] categoryIds) {
        this.op = op;
        this.token = token;
        this.categories = categories;
        this.categoryIds = categoryIds;
    }

    public String getOp() {
        return this.op;
    }
    public void setOp(String op) { this.op = op; }

    public String getUser() {
        return this.user;
    }
    public void setUser(String user) { this.user = user; }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) { this.password = password; }

    public String getName() {
        return this.name;
    }
    public void setName(String name) { this.name = name; }

    public String getToken() {
        return this.token;
    }
    public void setToken(String token) { this.token = token; }

    public String getResponse() {
        return this.response;
    }
    public void setResponse(String response) { this.response = response; }

    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) { this.message = message; }

    public Categoria[] getCategories() { return this.categories; }
    public void setCategories(Categoria[] categories) { this.categories = categories; }

    public String[] getCategoryIds() { return this.categoryIds; }
    public void setCategoryIds(String[] categoryIds) { this.categoryIds = categoryIds; }
}
