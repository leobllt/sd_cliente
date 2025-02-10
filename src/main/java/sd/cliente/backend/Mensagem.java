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
        return op;
    }
    public void setOp(String op) { this.setOp(op); }

    public String getUser() {
        return user;
    }
    public void setUser(String user) { this.setUser(user); }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) { this.setPassword(password); }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.setName(name); }

    public String getToken() {
        return token;
    }
    public void setToken(String token) { this.setToken(token); }

    public String getResponse() {
        return response;
    }
    public void setResponse(String response) { this.setResponse(response); }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) { this.setMessage(message); }

    public Categoria[] getCategories() { return categories; }
    public void setCategories(List<Categoria> categories) { this.setCategories(categories); }

    public String[] getCategoryIds() { return categoryIds; }
    public void setCategoryIds(List<String> categoryIds) { this.setCategoryIds(categoryIds); }
}
