package sd.cliente.frontend;

// @Author Leonardo Bellato

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;
import sd.cliente.backend.Controlador;

// Classe que permite logar e cadastrar
public class JanelaAutenticacao extends JDialog {
    private final Controlador controlador;
    private boolean sucesso; // Define o sucesso ao tentar autenticar

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
        labelRA.setHorizontalAlignment(SwingConstants.RIGHT);
        JTextField inputLoginRA = new JTextField(15);
        inputLoginRA.setFont(fonteSecundaria);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(fonteSecundaria);
        JPasswordField inputLoginSenha = new JPasswordField(15);
        inputLoginSenha.setFont(fonteSecundaria);

        // Botão de login
        JButton btnLogin = new JButton("Entrar");
        btnLogin.setFont(fontePrincipal);
        btnLogin.setBackground(new Color(0, 100, 255));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(50, 30));
        btnLogin.addActionListener(_ -> {
            // Validar dados
            String RA = inputLoginRA.getText();
            String senha = new String(inputLoginSenha.getPassword());

            if(!validarRA(RA)){
                JOptionPane.showMessageDialog(null, "RA inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(senha.length() != 4){
                JOptionPane.showMessageDialog(null, "Senha inválida (deve conter 4 caracteres).", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            sucesso = controlador.logar(RA, senha);

            if (sucesso) {
                dispose();  // Fecha o diálogo
            } else {
                JOptionPane.showMessageDialog(null, "Login falhou.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Colocando componentes no painel com GridBagLayout
        panel.add(labelRA, criarGBC(0, 0, 1));
        panel.add(inputLoginRA, criarGBC(1, 0, 1));
        panel.add(labelSenha, criarGBC(0, 1, 1));
        panel.add(inputLoginSenha, criarGBC(1, 1, 1));
        panel.add(btnLogin, criarGBC(0, 2, 2));

        return panel;
    }

    private JPanel criarPainelCadastro() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        // Labels e campos de texto
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setFont(fonteSecundaria);
        JTextField inputCadastroNome = new JTextField(15);
        inputCadastroNome.setFont(fonteSecundaria);

        JLabel labelRA = new JLabel("R.A.:");
        labelRA.setFont(fonteSecundaria);
        labelRA.setHorizontalAlignment(SwingConstants.RIGHT);
        JTextField inputCadastroRA = new JTextField(15);
        inputCadastroRA.setFont(fonteSecundaria);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(fonteSecundaria);
        JPasswordField inputCadastroSenha = new JPasswordField(15);
        inputCadastroSenha.setFont(fonteSecundaria);

        // Botão de cadastro
        JButton btnCadastro = new JButton("Cadastro");
        btnCadastro.setFont(fontePrincipal);
        btnCadastro.setBackground(new Color(0, 100, 255));
        btnCadastro.setForeground(Color.WHITE);
        btnCadastro.setFocusPainted(false);
        btnCadastro.setPreferredSize(new Dimension(50, 30));
        btnCadastro.addActionListener(_ -> {
            // Validar dados
            String RA = inputCadastroRA.getText();
            String senha = new String(inputCadastroSenha.getPassword());
            String nome = inputCadastroNome.getText();

            if(!validarRA(RA)){
                JOptionPane.showMessageDialog(null, "RA inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(senha.length() != 4){
                JOptionPane.showMessageDialog(null, "Senha inválida (deve conter 4 caracteres).", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(nome.isBlank()){
                JOptionPane.showMessageDialog(null, "Nome inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (controlador.cadastrar(RA, senha, nome))
                JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "Cadastro falhou.", "Erro", JOptionPane.ERROR_MESSAGE);

        });

        // Colocando componentes no painel com GridBagLayout
        panel.add(labelNome, criarGBC(0, 0, 1));
        panel.add(inputCadastroNome, criarGBC(1, 0, 1));
        panel.add(labelRA, criarGBC(0, 1, 1));
        panel.add(inputCadastroRA, criarGBC(1, 1, 1));
        panel.add(labelSenha, criarGBC(0, 2, 1));
        panel.add(inputCadastroSenha, criarGBC(1, 2, 1));
        panel.add(btnCadastro, criarGBC(0, 3, 2));

        return panel;
    }

    // Método especializado na criação de GridBagConstraints
    private GridBagConstraints criarGBC(int x, int y, int width) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        if(x == 0)
            if(width == 2)
                gbc.insets = new Insets(10, 25, 20, 25);
            else
                gbc.insets = new Insets(10, 25, 10, 5);
        else
            gbc.insets = new Insets(10, 5, 10, 25);

        return gbc;
    }

    private boolean validarRA(String RA){
        if(RA.isBlank() || RA.length() != 7) return false;
        String regex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(RA).matches();
    }

    public boolean ok(){
        return this.sucesso;
    }
}
