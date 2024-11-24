package sd.cliente.backend;

// @Author Leonardo Bellato

import com.google.gson.Gson;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

public class Controlador {
    private ConexaoTCP conexao;
    private Usuario usuario;

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

    public boolean cadastrar(String RA, String senha, String nome){
        Mensagem requisicao = new Mensagem(1, RA, senha, nome, null, null, null);
        Mensagem resposta = this.requisitar(requisicao);
        if(resposta == null) return false;

        // EDITAR PARA ANALISAR DEMAIS RESPONSES
        return resposta.getResponse().equals("111");
    }

    // public boolean ler()            op3
    // public boolean atualizar()      op4
    // public boolean deletar()        op5

    public boolean logar(String RA, String senha){
        Mensagem requisicao = new Mensagem(5, RA, senha, null, null, null, null);
        Mensagem resposta = this.requisitar(requisicao);
        if(resposta == null) return false;

        String tipoUsuario = resposta.getResponse().substring(2);
        if(!(tipoUsuario.equals("0") || tipoUsuario.equals("1"))) return false;

        usuario = new Usuario(RA, "", resposta.getToken(), tipoUsuario.equals("1")); // Demais dados!?
        return true;
    }

    public boolean deslogar(){
        Mensagem requisicao = new Mensagem(6, null, null, null, usuario.getToken(), null, null);
        Mensagem resposta = this.requisitar(requisicao);
        if(resposta == null) return false;

        return resposta.getResponse().equals("001");
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
