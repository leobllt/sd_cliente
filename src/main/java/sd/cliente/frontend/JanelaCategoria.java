package sd.cliente.frontend;

// @Author Leonardo Bellato

import sd.cliente.backend.Categoria;

import java.awt.Font;
import javax.swing.*;

public class JanelaCategoria {
    // Componentes de entrada de dados
    JTextField inputNome;
    JTextField inputDescricao;

    // Elementos gráficos
    JPanel painelPrincipal;
    String nome;
    String descricao;

    private JanelaCategoria(String nome, String descricao){
        this.nome = nome;
        this.descricao = descricao;
        this.montarInterface();
    }

    private void montarInterface(){
        // Criando componentes visuais
        Font fonteComum = new Font("Arial", Font.PLAIN, 14);

        JLabel labelTitulo = new JLabel("Dados da categoria: ");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setFont(fonteComum);

        this.inputNome = new JTextField(10);
        this.inputNome.setFont(fonteComum);
        this.inputNome.setText((nome != null) ? nome : "");

        JLabel labelDescricao = new JLabel("Descrição:");
        labelDescricao.setFont(fonteComum);

        this.inputDescricao = new JTextField(5);
        this.inputDescricao.setFont(fonteComum);
        this.inputDescricao.setText((descricao != null) ? descricao : "");

        // Criando o painel principal com BoxLayout (vertical)
        this.painelPrincipal = new JPanel();
        this.painelPrincipal.setLayout(new BoxLayout(this.painelPrincipal, BoxLayout.Y_AXIS)); // Layout vertical

        // Adicionando os componentes ao painelPrincipal
        painelPrincipal.add(labelTitulo);
        painelPrincipal.add(Box.createVerticalStrut(10)); // Espaçamento entre o título e os inputs

        painelPrincipal.add(labelNome);
        painelPrincipal.add(inputNome);
        painelPrincipal.add(Box.createVerticalStrut(10)); // Espaçamento entre o nome e a descrição

        painelPrincipal.add(labelDescricao);
        painelPrincipal.add(inputDescricao);
    }

    // Interação com usuário
    public static Categoria executar(Categoria categoria){
        JanelaCategoria janelaCategoria;

        if(categoria != null)
            janelaCategoria = new JanelaCategoria(categoria.getName(), categoria.getDescription());
        else
            janelaCategoria = new JanelaCategoria(null, null);

        int opcao = JOptionPane.showConfirmDialog(null, janelaCategoria.painelPrincipal, "Dados do servidor",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);

        if (opcao == JOptionPane.OK_OPTION) {
            if (categoria != null) {
                categoria.setName(janelaCategoria.inputNome.getText());
                categoria.setDescription(janelaCategoria.inputDescricao.getText());
                return categoria;
            } else
                return new Categoria(null, janelaCategoria.inputNome.getText(), janelaCategoria.inputDescricao.getText());
        }
        else
            return null;
    }
}
