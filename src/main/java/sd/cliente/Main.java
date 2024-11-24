package sd.cliente;

// @Author Leonardo Bellato

import sd.cliente.backend.Controlador;
import sd.cliente.frontend.*;


public class Main{

    public static void main(String[] args) {

        Controlador controlador = new Controlador();

        // Iniciando conexão
        JanelaConexao janelaConexao = new JanelaConexao(controlador);
        while(!janelaConexao.executar());
        if(janelaConexao.cancelar){
            controlador.desconectar();
            System.exit(0);
        }
        janelaConexao = null; // Desalocando

        // Loop do programa
        while(true) {
            // Autenticando
            JanelaAutenticacao janelaAutenticacao = new JanelaAutenticacao(controlador);
            if (!janelaAutenticacao.ok()) break;
            janelaAutenticacao = null; // Desalocando

            // Programa em si
            new JanelaPrincipal(controlador);
        }

        controlador.desconectar();
    }
}