package sd.cliente.frontend;

// @Author Leonardo Bellato

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import javax.swing.*;
import sd.cliente.backend.Controlador;

// Classe que solicita os dados de IP e Porta do servidor ao usuário
// Também faz a validação inicial desses dados
public class JanelaConexao{
    private final Controlador controlador;
    public boolean cancelar; // Se o usuário intencionalmente fechar a janela

    // Componentes de entrada de dados
    JTextField inputIP;
    JTextField inputPorta;

    // Elementos gráficos
    JPanel painelPrincipal;
    ImageIcon icon;

    public JanelaConexao(Controlador controlador){
        this.controlador = controlador;
        this.cancelar = false;
        this.montarInterface();
    }

    private void montarInterface(){
        // Criando componentes visuais
        Font fonteComum = new Font("Arial", Font.PLAIN, 14);

        JLabel labelTitulo = new JLabel("Informe os dados do servidor:");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel labelIP = new JLabel("IP:");
        labelIP.setFont(fonteComum);

        this.inputIP = new JTextField(10);
        this.inputIP.setFont(fonteComum);
        this.inputIP.setText("127.0.0.1");

        JLabel labelPorta = new JLabel("Porta:");
        labelPorta.setFont(fonteComum);

        this.inputPorta = new JTextField(5);
        this.inputPorta.setFont(fonteComum);
        this.inputPorta.setText("20000");

        this.painelPrincipal = new JPanel();
        this.painelPrincipal.setLayout(new GridLayout(2, 1, 5, 10));

        JPanel inputPainel = new JPanel();
        inputPainel.add(labelIP);
        inputPainel.add(inputIP);
        inputPainel.add(Box.createHorizontalStrut(15)); // espaço horizontal
        inputPainel.add(labelPorta);
        inputPainel.add(inputPorta);

        this.painelPrincipal.add(labelTitulo);
        this.painelPrincipal.add(inputPainel);

        // Carregar o ícone personalizado
        try{
            this.icon = new ImageIcon(Objects.requireNonNull(JanelaConexao.class.getResource("/images/link.png")));
        } catch (Exception e) {
            System.out.println("ERRO: carregamento da imagem. " + e.getMessage());
        }
    }

    // Interação com usuário
    public boolean conectar(){
        int opcao = JOptionPane.showConfirmDialog(null, this.painelPrincipal, "Dados do servidor",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, (icon!=null) ? icon : null);

        if (opcao == JOptionPane.OK_OPTION) {
            String IPServidor = inputIP.getText();
            int porta;

            // Validar dados
            try{
                if(!validarIPv4(IPServidor))
                    throw new DataFormatException();

                porta = Integer.parseInt(inputPorta.getText());

                if(!validarPorta(porta))
                    throw new NumberFormatException();
            }
            catch (DataFormatException e){
                JOptionPane.showMessageDialog(null, "IP inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Porta inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Adicionando tela de carregamento
            JDialog telaCarregamento = new JDialog();
            telaCarregamento.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            telaCarregamento.setSize(200, 200);
            telaCarregamento.setUndecorated(true);
            telaCarregamento.setBackground(new Color(255, 255, 255, 255));
            telaCarregamento.setLocationRelativeTo(null);
            telaCarregamento.setModalityType(JDialog.ModalityType.MODELESS); // Non-modal

            try{
                ImageIcon icone = new ImageIcon(Objects.requireNonNull(JanelaConexao.class.getResource("/images/load.gif")));
                JLabel label = new JLabel(icone);
                telaCarregamento.add(label, BorderLayout.CENTER);
                telaCarregamento.setVisible(true);
            } catch (Exception e){
                System.out.println("ERRO: carregamento da imagem. " + e.getMessage());
            }

            // Estabelecendo conexão
            boolean sucesso = this.controlador.conectar(IPServidor, porta);

            if(telaCarregamento.isVisible()) telaCarregamento.dispose();

            if(!sucesso){
                JOptionPane.showMessageDialog(null, "Erro de conexão!", "Erro", JOptionPane.ERROR_MESSAGE);
                cancelar = true;
            }
            return true;
        }
        else{
            this.cancelar = true;
            return true;
        }
    }

    // Método para validar o formato de um endereço IPv4
    private boolean validarIPv4(String ip) {
        if(ip.isBlank()) return false;
        String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(ip).matches();
    }

    // Método para validar se a porta é um número válido entre 0 e 65535
    private boolean validarPorta(int porta){
        return porta >= 0 && porta <= 65535;
    }

}