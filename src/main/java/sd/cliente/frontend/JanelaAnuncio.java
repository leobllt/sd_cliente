package sd.cliente.frontend;

// @Author Leonardo Bellato

import sd.cliente.backend.Anuncio;
import sd.cliente.backend.Categoria;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class JanelaAnuncio {
    // Componentes de entrada de dados
    JTextField inputTitulo;
    JTextField inputTexto;
    JComboBox<Categoria> comboCategorias;

    // Elementos gráficos
    JPanel painelPrincipal;
    String titulo;
    String texto;
    Categoria categoriaAtual;

    private JanelaAnuncio(String titulo, String texto, String categoriaAtual, Categoria[] categorias) {
        this.titulo = titulo;
        this.texto = texto;
        if(categoriaAtual != null)
            this.categoriaAtual = Arrays.stream(categorias).filter((c) -> c.getId().equals(categoriaAtual)).findFirst().get();
        else
            this.categoriaAtual = null;
        this.comboCategorias = new JComboBox<>(categorias);
        this.montarInterface();
    }

    private void montarInterface(){
        // Criando componentes visuais
        Font fonteComum = new Font("Arial", Font.PLAIN, 14);

        JLabel labelTituloJanela = new JLabel("Dados do anuncio: ");
        labelTituloJanela.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel labelTitulo = new JLabel("Titulo:");
        labelTitulo.setFont(fonteComum);

        this.inputTitulo = new JTextField(20);
        this.inputTitulo.setFont(fonteComum);
        this.inputTitulo.setText((titulo != null) ? titulo : "");

        JLabel labelDescricao = new JLabel("Texto:");
        labelDescricao.setFont(fonteComum);

        this.inputTexto = new JTextField(20);
        this.inputTexto.setFont(fonteComum);
        this.inputTexto.setText((texto != null) ? texto : "");

        JLabel labelCategoria = new JLabel("Categoria:");
        labelCategoria.setFont(fonteComum);

        this.comboCategorias.setSelectedItem(this.categoriaAtual);

        // Forçando alinhamento à esquerda
        labelTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelDescricao.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputTexto.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelCategoria.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboCategorias.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Criando o painel principal com BoxLayout (vertical)
        this.painelPrincipal = new JPanel();
        this.painelPrincipal.setLayout(new BoxLayout(this.painelPrincipal, BoxLayout.Y_AXIS)); // Layout vertical

        // Adicionando os componentes ao painelPrincipal
        painelPrincipal.add(labelTituloJanela);
        painelPrincipal.add(Box.createVerticalStrut(10)); // Espaçamento entre o título e os inputs

        painelPrincipal.add(labelTitulo);
        painelPrincipal.add(inputTitulo);
        painelPrincipal.add(Box.createVerticalStrut(10)); // Espaçamento entre o titulo e a descrição

        painelPrincipal.add(labelDescricao);
        painelPrincipal.add(inputTexto);

        painelPrincipal.add(labelCategoria);
        painelPrincipal.add(comboCategorias);
    }

    // Interação com usuário
    public static Anuncio executar(Anuncio anuncio, Categoria[] categorias) {
        JanelaAnuncio janelaAnuncio;

        if(anuncio != null)
            janelaAnuncio = new JanelaAnuncio(anuncio.getTitle(), anuncio.getText(), anuncio.getCategoriaId(), categorias);
        else
            janelaAnuncio = new JanelaAnuncio(null, null, null, categorias);

        int opcao = JOptionPane.showConfirmDialog(null, janelaAnuncio.painelPrincipal, "Dados da categoria",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);

        if (opcao == JOptionPane.OK_OPTION) {
            if (anuncio != null) {
                anuncio.setTitle(janelaAnuncio.inputTitulo.getText());
                anuncio.setText(janelaAnuncio.inputTexto.getText());
                anuncio.setCategoriaId(((Categoria) janelaAnuncio.comboCategorias.getSelectedItem()).getId());
                return anuncio;
            } else
                return new Anuncio(null, janelaAnuncio.inputTitulo.getText(), janelaAnuncio.inputTexto.getText(), null, ((Categoria) janelaAnuncio.comboCategorias.getSelectedItem()).getId());
        }
        else
            return null;
    }
}
