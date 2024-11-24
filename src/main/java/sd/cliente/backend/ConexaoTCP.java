package sd.cliente.backend;

// @Author Leonardo Bellato

import java.io.*;
import java.net.UnknownHostException;
import java.net.Socket;
import java.util.concurrent.TimeoutException;

public class ConexaoTCP {

    private String IPServidor;
    private int porta;
    private Socket soquete;
    private BufferedReader entradaSoquete;
    private PrintWriter saidaSoquete;
    //private BufferedReader entradaUsuario;

    public ConexaoTCP(String IP, int porta){
        this.IPServidor = IP;
        this.porta = porta;
    }

    public void inicializarSoquete() throws UnknownHostException, IOException{
        this.soquete = new Socket(this.IPServidor, this.porta);
        this.soquete.setSoTimeout(10000); // Timeout de 5 segundos
        this.entradaSoquete = new BufferedReader(new InputStreamReader(this.soquete.getInputStream()));
        this.saidaSoquete = new PrintWriter(this.soquete.getOutputStream(), true);
    }

    public String comunicar(String json) throws IOException, TimeoutException {
        // Enviando mensagem
        this.saidaSoquete.println(json);
        // Aguardando resposta
        return entradaSoquete.readLine();
    }

    public void encerrarSoquete() throws IOException{
        this.entradaSoquete.close();
        this.saidaSoquete.close();
        this.soquete.close();
    }
}

