package sd.cliente.frontend;

// @Author Leonardo Bellato

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import sd.cliente.backend.Controlador;

public class JanelaPrincipal extends JDialog{
    private final Controlador controlador;
    public boolean cancelar; // Se o usuário intencionalmente fechar a janela

    Font fontePrincipal;

    public JanelaPrincipal(Controlador controlador) {
        super();
        this.controlador = controlador;
        this.cancelar = false;
        this.montarInterface();
    }

    private void montarInterface() {
        // Configurações iniciais
        this.setModal(true);
        this.setSize(400, 250);
        this.setResizable(false);
        this.setLocationRelativeTo(null); // Posiciona em relação ao pai
        this.setLayout(new BorderLayout());

        // Definindo fontes
        fontePrincipal = new Font("Arial", Font.BOLD, 14);

        // Container principal da janela
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.add(container, BorderLayout.CENTER);

        // Criando um painel para o título
        JPanel painelTitulo = new JPanel();
        painelTitulo.setLayout(new BorderLayout());
        painelTitulo.setBackground(new Color(100, 149, 237)); // Cor de fundo (azul suave)
        painelTitulo.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Criando o rótulo para mostrar o nome do usuário e o tipo de conta
        String tipoConta = (controlador.getUsuario().isAdmin()) ? "Admin" : "Comum";
        JLabel infoUsuario = new JLabel("Usuário: " + controlador.getUsuario().getRA() + " | Acesso: " + tipoConta);
        infoUsuario.setForeground(Color.WHITE);
        infoUsuario.setFont(fontePrincipal);
        infoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        painelTitulo.add(infoUsuario, BorderLayout.CENTER);
        container.add(painelTitulo, BorderLayout.NORTH);

        // Criando o botão de logout e centralizando
        JButton botaoLogout = new JButton("Logout");
        botaoLogout.setFont(fontePrincipal);
        botaoLogout.setBackground(new Color(220, 53, 69)); // Cor de fundo do botão (vermelho suave)
        botaoLogout.setForeground(Color.WHITE);
        botaoLogout.setFocusPainted(false);
        botaoLogout.setPreferredSize(new Dimension(150, 40));
        botaoLogout.addActionListener(_ -> aoFecharJanela());

        // Criando um painel para centralizar o botão
        JPanel painelBotao = new JPanel(new GridBagLayout());
        painelBotao.add(botaoLogout);
        container.add(painelBotao, BorderLayout.CENTER);

        // Redefinindo o comportamento do fechamento da janela
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancelar = true;
                aoFecharJanela();
            }
        });

        this.setVisible(true);
    }


    void aoFecharJanela() {
        this.controlador.deslogar();
        this.dispose();
    }
}
