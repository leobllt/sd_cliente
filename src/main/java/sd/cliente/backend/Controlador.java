package sd.cliente.backend;

// @Author Leonardo Bellato

import com.google.gson.Gson;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Controlador {
    private ConexaoTCP conexao;
    private Usuario usuario;
    private Categoria[] categorias;

    public boolean conectar(String IP, int porta){
        if(this.conexao != null) return false;

        System.out.println("Tentando conexão com " + IP + ":" + porta);
        this.conexao = new ConexaoTCP(IP, porta);
        try{
            this.conexao.inicializarSoquete();
            System.out.println("Sucesso!");
            return true;
        }
        catch (UnknownHostException e){
            System.out.println("ERRO: Host desconhecido.");
        }
        catch (IOException e){
            System.out.println("ERRO: Não foi possível obter I/O para a conexão.");
        }

        this.conexao = null;
        return false;
    }

    public void desconectar(){
        if(this.conexao == null) return;
        try {
            this.conexao.encerrarSoquete();
            System.out.println("Conexão encerrada com sucesso!");
        } catch (IOException e) {
            System.out.println("ERRO: não foi possível encerrar o soquete. " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    public Mensagem requisitar(Mensagem requisicao){
        Gson gson = new Gson();
        String jsonEnviado = gson.toJson(requisicao);
        System.out.println("CLIENTE: " + jsonEnviado);

        String jsonRecebido;
        try {
            jsonRecebido = conexao.comunicar(jsonEnviado);
        } catch (IOException e) {
            System.out.println("ERRO: " + e.getMessage());
            return null;
        } catch (TimeoutException e) {
            System.out.println("ERRO: tempo de espera de resposta esgotou.");
            return null;
        }

        System.out.println("SERVIDOR: " + jsonRecebido);
        return gson.fromJson(jsonRecebido, Mensagem.class);
    }

    public void cadastrarUsuario(String RA, String senha, String nome) throws Exception{
        Mensagem requisicao = new Mensagem("1", RA, senha, nome, null);
        Mensagem resposta = this.requisitar(requisicao);
        if(resposta == null) throw new Exception("Recebeu uma resposta inválida.");

        if(!resposta.getResponse().equals("100")) throw new Exception(resposta.getMessage());
    }

    public void lerUsuario() throws Exception{
        Mensagem requisicao = new Mensagem("2", this.usuario.getRA(), null, null, this.usuario.getToken());
        Mensagem resposta = this.requisitar(requisicao);
        if(resposta == null) throw new Exception("Recebeu uma resposta inválida.");

        if(!resposta.getResponse().equals("110") && !resposta.getResponse().equals("111")) throw new Exception(resposta.getMessage());
        this.usuario.setNome(resposta.getName());
        this.usuario.setSenha(resposta.getPassword());
    }

    public Usuario lerUsuario(String RA) throws Exception{
        Mensagem requisicao = new Mensagem("2", RA, null, null, this.usuario.getToken());
        Mensagem resposta = this.requisitar(requisicao);
        if(resposta == null) throw new Exception("Recebeu uma resposta inválida.");

        if(!resposta.getResponse().equals("110") && !resposta.getResponse().equals("111")) throw new Exception(resposta.getMessage());
        return new Usuario(resposta.getUser(), resposta.getName(), resposta.getPassword(), null, resposta.getResponse().equals("111"));
    }

    public void editarUsuario(String RA, String nome, String senha) throws Exception{
        Mensagem requisicao = new Mensagem("3", RA, senha, nome, this.usuario.getToken());
        Mensagem resposta = this.requisitar(requisicao);
        if(resposta == null) throw new Exception("Recebeu uma resposta inválida.");

        if(!resposta.getResponse().equals("120")) throw new Exception(resposta.getMessage());
    }

    public void deletarUsuario(String RA) throws Exception{
        Mensagem requisicao = new Mensagem("4", RA, null, null, this.usuario.getToken());
        Mensagem resposta = this.requisitar(requisicao);
        if(resposta == null) throw new Exception("Recebeu uma resposta inválida.");

        if(!resposta.getResponse().equals("130")) throw new Exception(resposta.getMessage());
    }

    public void logar(String RA, String senha) throws Exception{
        Mensagem requisicao = new Mensagem("5", RA, senha, null, null);
        Mensagem resposta = this.requisitar(requisicao);
        if(resposta == null) throw new Exception("Recebeu uma resposta inválida.");

        boolean eAdmin = true;
        switch(resposta.getResponse()){
            case "000":
                eAdmin = false;
            case "001":
                this.usuario = new Usuario(RA, "", "", resposta.getToken(), eAdmin);
                lerUsuario();
                break;
            default:
                throw new Exception(resposta.getMessage());
        }
    }

    public void deslogar(){
        this.requisitar(new Mensagem("6", null, null, null, usuario.getToken()));
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void cadastrarCategorias(Categoria[] cats) throws Exception{
        Mensagem requisicao = new Mensagem("7", this.usuario.getToken(), cats, null);
        Mensagem resposta = this.requisitar(requisicao);
        if(resposta == null) throw new Exception("Recebeu uma resposta inválida.");

        if(!resposta.getResponse().equals("200")) throw new Exception(resposta.getMessage());
    }

    public Categoria[] lerCategorias() throws Exception{
        Mensagem requisicao = new Mensagem("8", this.usuario.getToken(), null, null);
        Mensagem resposta = this.requisitar(requisicao);
        if(resposta == null) throw new Exception("Recebeu uma resposta inválida.");

        if(!resposta.getResponse().equals("210")) throw new Exception(resposta.getMessage());
        this.categorias = resposta.getCategories();
        return this.categorias;
    }

    public void editarCategorias(Categoria[] cats) throws Exception{
        Mensagem requisicao = new Mensagem("9", this.usuario.getToken(), cats, null);
        Mensagem resposta = this.requisitar(requisicao);
        if(resposta == null) throw new Exception("Recebeu uma resposta inválida.");

        if(!resposta.getResponse().equals("220")) throw new Exception(resposta.getMessage());
    }

    public void deletarCategorias(String[] ids) throws Exception{
        Mensagem requisicao = new Mensagem("10", this.usuario.getToken(), null, ids);
        Mensagem resposta = this.requisitar(requisicao);
        if(resposta == null) throw new Exception("Recebeu uma resposta inválida.");

        if(!resposta.getResponse().equals("230")) throw new Exception(resposta.getMessage());
    }
}
