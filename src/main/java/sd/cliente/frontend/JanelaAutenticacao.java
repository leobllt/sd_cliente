package sd.cliente.frontend;

// @Author Leonardo Bellato

import javax.swing.*;
import java.awt.*;
import sd.cliente.backend.Controlador;

// Classe que permite logar e cadastrar
public class JanelaAutenticacao extends JDialog {
    private final Controlador controlador;

    Font fontePrincipal;
    Font fonteSecundaria;

    public JanelaAutenticacao(Controlador controlador) {
        super();
        this.controlador = controlador;
        this.montarInterface();
    }

    public void montarInterface() {
        // Configurações iniciais
        this.setModal(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null); // Posiciona em relação ao pai

        // Definindo fontes
        fontePrincipal = new Font("Arial", Font.BOLD, 14);
        fonteSecundaria = new Font("Arial", Font.PLAIN, 14);

        // Criando layout
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 16));
        tabbedPane.setForeground(new Color(0, 100, 255));
        tabbedPane.addTab("Entrar", criarPainelLogin());
        tabbedPane.addTab("Cadastrar", criarPainelCadastro());
        this.setLayout(new BorderLayout());
        this.add(tabbedPane, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    private JPanel criarPainelLogin() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        // Labels e campos de texto
        JLabel labelRA = new JLabel("R.A.:");
        labelRA.setFont(fonteSecundaria);
        labelRA.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField inputLoginRA = new JTextField(15);
        inputLoginRA.setFont(fonteSecundaria);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(fonteSecundaria);
        labelSenha.setHorizontalAlignment(SwingConstants.LEFT);
        JPasswordField inputLoginSenha = new JPasswordField(15);
        inputLoginSenha.setFont(fonteSecundaria);

        // Botão de login
        JButton btnLogin = new JButton("Entrar");
        btnLogin.setFont(fontePrincipal);
        btnLogin.setBackground(new Color(0, 100, 255));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(50, 30));
        btnLogin.addActionListener(z -> {
            // Validar dados
            String RA = inputLoginRA.getText();
            String senha = new String(inputLoginSenha.getPassword());

            try{
                controlador.logar(RA, senha);
                dispose();  // Fecha o diálogo
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Colocando componentes no painel com GridBagLayout
        panel.add(labelRA, criarGBC(0, 0, 1, true));
        panel.add(inputLoginRA, criarGBC(1, 0, 1, true));
        panel.add(labelSenha, criarGBC(0, 1, 1, true));
        panel.add(inputLoginSenha, criarGBC(1, 1, 1, true));
        panel.add(btnLogin, criarGBC(0, 2, 2, false));

        return panel;
    }

    private JPanel criarPainelCadastro() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        // Labels e campos de texto
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setFont(fonteSecundaria);
        labelNome.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField inputCadastroNome = new JTextField(15);
        inputCadastroNome.setFont(fonteSecundaria);

        JLabel labelRA = new JLabel("R.A.:");
        labelRA.setFont(fonteSecundaria);
        labelRA.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField inputCadastroRA = new JTextField(15);
        inputCadastroRA.setFont(fonteSecundaria);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(fonteSecundaria);
        labelSenha.setHorizontalAlignment(SwingConstants.LEFT);
        JPasswordField inputCadastroSenha = new JPasswordField(15);
        inputCadastroSenha.setFont(fonteSecundaria);

        // Botão de cadastro
        JButton btnCadastro = new JButton("Cadastro");
        btnCadastro.setFont(fontePrincipal);
        btnCadastro.setBackground(new Color(0, 100, 255));
        btnCadastro.setForeground(Color.WHITE);
        btnCadastro.setFocusPainted(false);
        btnCadastro.setPreferredSize(new Dimension(50, 30));
        btnCadastro.addActionListener(z -> {
            // Validar dados
            String RA = inputCadastroRA.getText();
            String senha = new String(inputCadastroSenha.getPassword());
            String nome = inputCadastroNome.getText();

            try{
                this.controlador.cadastrarUsuario(RA, senha, nome);
                JOptionPane.showMessageDialog(null, "Usuário cadastrado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Colocando componentes no painel com GridBagLayout
        panel.add(labelNome, criarGBC(0, 0, 1, true));
        panel.add(inputCadastroNome, criarGBC(1, 0, 1, true));
        panel.add(labelRA, criarGBC(0, 1, 1, true));
        panel.add(inputCadastroRA, criarGBC(1, 1, 1, true));
        panel.add(labelSenha, criarGBC(0, 2, 1, true));
        panel.add(inputCadastroSenha, criarGBC(1, 2, 1, true));
        panel.add(btnCadastro, criarGBC(0, 3, 2, false));

        return panel;
    }

    // Método especializado na criação de GridBagConstraints
    private GridBagConstraints criarGBC(int x, int y, int width, boolean flag) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Distribui o espaço igualmente
        if(flag){
            if(x == 0) gbc.insets = new Insets(5, 20, 5, 3);
            else gbc.insets = new Insets(5, 3, 5, 20);
            if(y == 3) gbc.insets.bottom = 15;
        }
        else
            gbc.insets = new Insets(5, 20, 5, 20);

        return gbc;
    }
}
