package sd.cliente.frontend;

// @Author Leonardo Bellato

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import sd.cliente.backend.Anuncio;
import sd.cliente.backend.Categoria;
import sd.cliente.backend.Controlador;
import sd.cliente.backend.Usuario;

// Classe que permite logar e cadastrar
public class JanelaPrincipal extends JDialog {
    private final Controlador controlador;
    private DefaultListModel<Categoria> modelCategorias = new DefaultListModel<>();;
    private JList<Categoria> listaCategorias = new JList<>(modelCategorias);
    private JScrollPane categoriasPane = new JScrollPane(listaCategorias);
    private DefaultListModel<Anuncio> modelAnuncios = new DefaultListModel<>();;
    private JList<Anuncio> listaAnuncios = new JList<>(modelAnuncios);
    private JScrollPane anunciosPane = new JScrollPane(listaAnuncios);
    private Categoria[] categoriasCache;

    Font fontePrincipal;
    Font fonteSecundaria;

    public JanelaPrincipal(Controlador controlador) {
        super();
        this.controlador = controlador;
        this.montarInterface();
    }

    public void montarInterface() {
        // Configurações iniciais
        this.setModal(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null); // Posiciona em relação ao pai
        // Redefinindo o comportamento do fechamento da janela
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                aoFechar();
            }
        });

        // Definindo fontes
        fontePrincipal = new Font("Arial", Font.BOLD, 14);
        fonteSecundaria = new Font("Arial", Font.PLAIN, 14);

        // Criando layout
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 16));
        tabbedPane.setForeground(new Color(0, 100, 255));

        JPanel painelCategorias = criarPainelCategorias();
        tabbedPane.addTab("Anúncios", criarPainelAnuncios());
        tabbedPane.addTab("Categorias", painelCategorias);
        tabbedPane.addTab("Conta", criarPainelDados());
        this.setLayout(new BorderLayout());
        this.add(tabbedPane, BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    private JPanel criarPainelDados() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        // Labels e campos de texto
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setFont(fonteSecundaria);
        labelNome.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField inputNome = new JTextField(15);
        inputNome.setFont(fonteSecundaria);
        inputNome.setText(this.controlador.getUsuario().getNome());

        JLabel labelRA = new JLabel("R.A.:");
        labelRA.setFont(fonteSecundaria);
        labelRA.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField inputRA = new JTextField(15);
        inputRA.setFont(fonteSecundaria);
        inputRA.setText(this.controlador.getUsuario().getRA());

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(fonteSecundaria);
        labelSenha.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField inputSenha = new JTextField(15);
        inputSenha.setFont(fonteSecundaria);
        inputSenha.setText(this.controlador.getUsuario().getSenha());

        JLabel labelTipo = new JLabel("Tipo:");
        labelTipo.setFont(fonteSecundaria);
        labelTipo.setHorizontalAlignment(SwingConstants.LEFT);
        JTextField inputTipo = new JTextField(15);
        inputTipo.setFont(fonteSecundaria);
        inputTipo.setEditable(false);
        inputTipo.setText(this.controlador.getUsuario().isAdmin() ? "admin" : "comum");

        // Botões
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(fontePrincipal);
        btnBuscar.setBackground(new Color(0, 100, 255));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setPreferredSize(new Dimension(50, 30));
        btnBuscar.addActionListener(z -> {
            String RA = (!inputRA.getText().isBlank()) ? inputRA.getText() : null;
            try{
                Usuario usuarioBuscado = this.controlador.lerUsuario(RA);
                inputNome.setText(usuarioBuscado.getNome());
                inputRA.setText(usuarioBuscado.getRA());
                inputSenha.setText(usuarioBuscado.getSenha());
                inputTipo.setText(usuarioBuscado.isAdmin() ? "admin" : "comum");
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnEditar = new JButton("Editar");
        btnEditar.setFont(fontePrincipal);
        btnEditar.setBackground(new Color(0, 100, 255));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFocusPainted(false);
        btnEditar.setPreferredSize(new Dimension(50, 30));
        btnEditar.addActionListener(z -> {
            String RA = (!inputRA.getText().isBlank()) ? inputRA.getText() : null;
            String nome = (!inputNome.getText().isBlank()) ? inputNome.getText() : null;
            String senha = (!inputSenha.getText().isBlank()) ? inputSenha.getText() : null;

            try{
                this.controlador.editarUsuario(RA, nome, senha);
                JOptionPane.showMessageDialog(null, "Dados atualizados.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setFont(fontePrincipal);
        btnExcluir.setBackground(new Color(255, 0, 0));
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFocusPainted(false);
        btnExcluir.setPreferredSize(new Dimension(50, 30));
        btnExcluir.addActionListener(z -> {
            String RA = (!inputRA.getText().isBlank()) ? inputRA.getText() : null;

            try{
                this.controlador.deletarUsuario(RA);
                JOptionPane.showMessageDialog(null, "Conta deletada.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                if(RA.equals(this.controlador.getUsuario().getRA())) this.aoFechar();
                else{
                    inputNome.setText("");
                    inputRA.setText("");
                    inputSenha.setText("");
                    inputTipo.setText("");
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Colocando componentes no painel com GridBagLayout
        panel.add(labelNome, criarGBC(0, 0, 1, true));
        panel.add(inputNome, criarGBC(1, 0, 1, true));
        panel.add(labelRA, criarGBC(0, 1, 1, true));
        panel.add(inputRA, criarGBC(1, 1, 1, true));
        panel.add(labelSenha, criarGBC(0, 2, 1, true));
        panel.add(inputSenha, criarGBC(1, 2, 1, true));
        panel.add(labelTipo, criarGBC(0, 3, 1, true));
        panel.add(inputTipo, criarGBC(1, 3, 1, true));
        panel.add(btnBuscar, criarGBC(0, 4, 2, false));
        panel.add(btnEditar, criarGBC(0, 5, 2, false));
        panel.add(btnExcluir, criarGBC(0, 6, 2, false));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return panel;
    }

    private JPanel criarPainelCategorias() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        listaCategorias.setFont(new Font("Arial", Font.PLAIN, 14));
        categoriasPane.setPreferredSize(new Dimension(250, 150));
        categoriasPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        categoriasPane.setBorder(BorderFactory.createTitledBorder("Categorias Cadastradas"));
        // Get the existing TitledBorder and change its font size
        TitledBorder titledBorder = (TitledBorder) categoriasPane.getBorder();
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));

        // Botões
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setFont(fontePrincipal);
        btnAtualizar.setBackground(new Color(0, 100, 255));
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setPreferredSize(new Dimension(50, 30));
        btnAtualizar.addActionListener(z -> {
            JOptionPane.showMessageDialog(null, "Conteúdo atualizado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            atualizarCategoriasPane();
        });

        JButton btnInscrever = new JButton("Inscrever/Cancelar");
        btnInscrever.setFont(fontePrincipal);
        btnInscrever.setBackground(new Color(0, 100, 255));
        btnInscrever.setForeground(Color.WHITE);
        btnInscrever.setFocusPainted(false);
        btnInscrever.setPreferredSize(new Dimension(50, 30));
        btnInscrever.addActionListener(z -> {
            Categoria categoria = this.listaCategorias.getSelectedValue();

            try{
                if(categoria == null) throw new Exception("Nenhuma categoria selecionada.");

                if(categoria.getSubscribed().equals("true"))
                    this.controlador.cancelarInscricao(categoria.getId());
                else
                    this.controlador.inscrever(categoria.getId());
                JOptionPane.showMessageDialog(null, "Inscrito/Cancelado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarCategoriasPane();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setFont(fontePrincipal);
        btnAdicionar.setBackground(new Color(0, 100, 255));
        btnAdicionar.setForeground(Color.WHITE);
        btnAdicionar.setFocusPainted(false);
        btnAdicionar.setEnabled(this.controlador.getUsuario().isAdmin());
        btnAdicionar.setPreferredSize(new Dimension(50, 30));
        btnAdicionar.addActionListener(z -> {
            try{
                String aux = JOptionPane.showInputDialog("Digite o quantidade de categorias:");
                if(aux == null) throw new InterruptedException();

                int qtd = Integer.parseInt(aux);
                Categoria[] categorias = new Categoria[qtd];
                for (int i = 0; i < qtd; i++) {
                    Categoria res = JanelaCategoria.executar(null);
                    if(res != null)
                        categorias[i] = res;
                }

                Categoria[] temp = Arrays.stream(categorias).filter(c -> c != null).toArray(Categoria[]::new);
                if(temp.length != 0) {
                    this.controlador.cadastrarCategorias(temp);
                    JOptionPane.showMessageDialog(null, "Categoria(s) adicionada(s).", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    atualizarCategoriasPane();
                }
            }
            catch (InterruptedException ex){}
            catch (NumberFormatException nfe){
                JOptionPane.showMessageDialog(null,"Número inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnEditar = new JButton("Editar");
        btnEditar.setFont(fontePrincipal);
        btnEditar.setBackground(new Color(0, 100, 255));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFocusPainted(false);
        btnEditar.setEnabled(this.controlador.getUsuario().isAdmin());
        btnEditar.setPreferredSize(new Dimension(50, 30));
        btnEditar.addActionListener(z -> {
            try{
                if(this.listaCategorias.getSelectedValuesList().isEmpty()) throw new Exception("Nenhuma categoria selecionada.");

                Categoria[] cats = this.listaCategorias.getSelectedValuesList().toArray(new Categoria[this.listaCategorias.getSelectedValuesList().size()]);
                Categoria[] atualizadas = new Categoria[cats.length];
                int i = 0;
                for(Categoria categoria : cats) atualizadas[i++] = JanelaCategoria.executar(categoria);

                this.controlador.editarCategorias(Arrays.stream(atualizadas).filter(c -> c != null).toArray(Categoria[]::new));
                JOptionPane.showMessageDialog(null, "Categoria(s) editadas(s).", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarCategoriasPane();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setFont(fontePrincipal);
        btnExcluir.setBackground(new Color(255, 0, 0));
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFocusPainted(false);
        btnExcluir.setEnabled(this.controlador.getUsuario().isAdmin());
        btnExcluir.setPreferredSize(new Dimension(50, 30));
        btnExcluir.addActionListener(z -> {

            try{
                if(this.listaCategorias.getSelectedValuesList().isEmpty()) throw new Exception("Nenhuma categoria selecionada.");

                Categoria[] cats = this.listaCategorias.getSelectedValuesList().toArray(new Categoria[this.listaCategorias.getSelectedValuesList().size()]);
                String[] ids = new String[cats.length];
                int i = 0;
                for(Categoria cat: cats) ids[i++] = cat.getId();

                this.controlador.deletarCategorias(ids);
                JOptionPane.showMessageDialog(null, "Categoria(s) deletada(s).", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarCategoriasPane();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Colocando componentes no painel com GridBagLayout
        panel.add(categoriasPane, criarGBC(0, 0, 2, false));
        panel.add(btnAtualizar, criarGBC(0, 1, 2, false));
        panel.add(btnInscrever, criarGBC(0, 2, 2, false));
        panel.add(btnAdicionar, criarGBC(0, 3, 2, false));
        panel.add(btnEditar, criarGBC(0, 4, 2, false));
        panel.add(btnExcluir, criarGBC(0, 5, 2, false));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        atualizarCategoriasPane();

        return panel;
    }

    private JPanel criarPainelAnuncios() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        listaAnuncios.setFont(new Font("Arial", Font.PLAIN, 14));
        anunciosPane.setPreferredSize(new Dimension(250, 150));
        anunciosPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        anunciosPane.setBorder(BorderFactory.createTitledBorder("Anuncios inscritos"));
        // Get the existing TitledBorder and change its font size
        TitledBorder titledBorder = (TitledBorder) anunciosPane.getBorder();
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 14));

        // Botões
        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setFont(fontePrincipal);
        btnAtualizar.setBackground(new Color(0, 100, 255));
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setPreferredSize(new Dimension(50, 30));
        btnAtualizar.addActionListener(z -> {
            JOptionPane.showMessageDialog(null, "Conteúdo atualizado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            atualizarAnunciosPane();
        });

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setFont(fontePrincipal);
        btnAdicionar.setBackground(new Color(0, 100, 255));
        btnAdicionar.setForeground(Color.WHITE);
        btnAdicionar.setFocusPainted(false);
        btnAdicionar.setEnabled(this.controlador.getUsuario().isAdmin());
        btnAdicionar.setPreferredSize(new Dimension(50, 30));
        btnAdicionar.addActionListener(z -> {
            try{
                if(this.categoriasCache == null || this.categoriasCache.length == 0) throw new Exception("Nenhuma categoria cadastrada.");
                Anuncio res = JanelaAnuncio.executar(null, this.categoriasCache);
                if(res != null) {
                    this.controlador.cadastrarAnuncio(res);
                    JOptionPane.showMessageDialog(null, "Anúncio adicionado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    atualizarAnunciosPane();
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnEditar = new JButton("Editar");
        btnEditar.setFont(fontePrincipal);
        btnEditar.setBackground(new Color(0, 100, 255));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFocusPainted(false);
        btnEditar.setEnabled(this.controlador.getUsuario().isAdmin());
        btnEditar.setPreferredSize(new Dimension(50, 30));
        btnEditar.addActionListener(z -> {
            Anuncio anuncio = this.listaAnuncios.getSelectedValue();
            try{
                if(anuncio == null) throw new Exception("Nenhum anuncio cadastrado.");

                anuncio = JanelaAnuncio.executar(anuncio, this.categoriasCache);
                this.controlador.editarAnuncio(anuncio);
                JOptionPane.showMessageDialog(null, "Anúncio editado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarAnunciosPane();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setFont(fontePrincipal);
        btnExcluir.setBackground(new Color(255, 0, 0));
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFocusPainted(false);
        btnExcluir.setEnabled(this.controlador.getUsuario().isAdmin());
        btnExcluir.setPreferredSize(new Dimension(50, 30));
        btnExcluir.addActionListener(z -> {
            Anuncio anuncio = this.listaAnuncios.getSelectedValue();

            try{
                if(anuncio == null) throw new Exception("Nenhum anuncio cadastrado.");

                this.controlador.deletarAnuncio(anuncio.getId());
                JOptionPane.showMessageDialog(null, "Anúncio deletado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarAnunciosPane();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Colocando componentes no painel com GridBagLayout
        panel.add(anunciosPane, criarGBC(0, 0, 2, false));
        panel.add(btnAtualizar, criarGBC(0, 1, 2, false));
        panel.add(btnAdicionar, criarGBC(0, 2, 2, false));
        panel.add(btnEditar, criarGBC(0, 3, 2, false));
        panel.add(btnExcluir, criarGBC(0, 4, 2, false));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        atualizarAnunciosPane();

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

    private void atualizarAnunciosPane(){
        try {
            if(this.modelAnuncios != null && !this.modelAnuncios.isEmpty())
                this.modelAnuncios.clear();

            Anuncio[] anuncios = this.controlador.lerAnuncios();
            if(anuncios != null && anuncios.length > 0){
                for(Anuncio a: anuncios) {
                    a.setCategoriaNome(Arrays.stream(this.categoriasCache).filter((c) -> c.getId().equals(a.getCategoriaId())).findFirst().get().getName());
                    this.modelAnuncios.addElement(a);
                }
                this.anunciosPane.updateUI();
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarCategoriasPane(){
        try {
            if(this.modelCategorias != null && !this.modelCategorias.isEmpty())
                this.modelCategorias.clear();

            this.categoriasCache = this.controlador.lerCategorias();
            if(this.categoriasCache != null && this.categoriasCache.length > 0){
                for(Categoria c: categoriasCache) this.modelCategorias.addElement(c);
                this.categoriasPane.updateUI();
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aoFechar(){
        this.controlador.deslogar();
        this.controlador.desconectar();
        this.dispose();
    }
}
