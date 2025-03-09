import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class JogoMemoria extends JFrame {
    private static final long serialVersionUID = 1L;

    private JButton[] botoes;
    private String[] valores;
    private JButton primeiroBotao, segundoBotao;
    private int paresEncontrados = 0;
    private int fase = 1;
    private Map<String, Color> coresUsadas = new HashMap<>();
    private Color corAtual = new Color(255, 165, 0); //

    private final Map<String, Color> mapaCores = Map.ofEntries(
            Map.entry("Red", Color.RED), Map.entry("Vermelho", Color.RED),
            Map.entry("Blue", Color.BLUE), Map.entry("Azul", Color.BLUE),
            Map.entry("Green", Color.GREEN), Map.entry("Verde", Color.GREEN),
            Map.entry("Yellow", Color.YELLOW), Map.entry("Amarelo", Color.YELLOW),
            Map.entry("Black", Color.BLACK), Map.entry("Preto", Color.BLACK),
            Map.entry("White", Color.WHITE), Map.entry("Branco", Color.WHITE),
            Map.entry("Purple", new Color(128, 0, 128)), Map.entry("Roxo", new Color(128, 0, 128)),
            Map.entry("Orange", Color.ORANGE), Map.entry("Laranja", Color.ORANGE)
        );

    public JogoMemoria() {
        setTitle("Jogo da Memória");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 4, 10, 10));
        getContentPane().setBackground(new Color(50, 50, 50));

        botoes = new JButton[16];
        iniciarFase();

        setVisible(true);
    }

    private void iniciarFase() {
        valores = gerarValoresFase();
        paresEncontrados = 0;
        getContentPane().removeAll();

        for (int i = 0; i < 16; i++) {
            botoes[i] = criarBotao(i);
            add(botoes[i]);
        }

        revalidate();
        repaint();
    }

    private JButton criarBotao(int indice) {
        JButton botao = new JButton("?");
        botao.setFont(new Font("Arial", Font.BOLD, 24));
        botao.setBackground(new Color(70, 130, 180));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createBevelBorder(1));
        botao.addActionListener(new AcaoBotao(indice));
        return botao;
    }

    private String[] gerarValoresFase() {
        ArrayList<String> lista = new ArrayList<>();

        if (fase == 1) { // Fase de números
            for (int i = 1; i <= 8; i++) {
                lista.add(String.valueOf(i));
                lista.add(String.valueOf(i));
            }
        } else if (fase == 2) { // Fase de cores
            String[][] cores = {
                {"Red", "Vermelho"}, {"Blue", "Azul"}, {"Green", "Verde"}, {"Yellow", "Amarelo"},
                {"Black", "Preto"}, {"White", "Branco"}, {"Purple", "Roxo"}, {"Orange", "Laranja"}
            };
            for (String[] par : cores) {
                lista.add(par[0]);
                lista.add(par[1]);
            }
        } else if (fase == 3) { // Fase de charadas
            Map<String, String> charadas = Map.of(
                "Tem dentes, mas não morde.", "Pente",
                "Quanto mais se tira, maior fica.", "Buraco",
                "Anda com os pés na cabeça.", "Piolho",
                "É cheio de buracos, mas segura água.", "Esponja",
                "Não tem boca, mas conta tudo.", "Relógio",
                "Pode ser alto ou baixo, mas sempre está de pé.", "Som",
                "Tem chaves, mas não abre portas.", "Teclado",
                "Tem pescoço, mas não tem cabeça.", "Garrafa"
            );
            for (Map.Entry<String, String> par : charadas.entrySet()) {
                lista.add(par.getKey());
                lista.add(par.getValue());
            }
        }

        Collections.shuffle(lista);
        return lista.toArray(new String[0]);
    }

    private class AcaoBotao implements ActionListener {
        private int indice;

        public AcaoBotao(int indice) {
            this.indice = indice;
        }

        public void actionPerformed(ActionEvent e) {
            if (botoes[indice].getText().equals("?")) {
                if (primeiroBotao == botoes[indice]) return;

                String valor = valores[indice];
                botoes[indice].setText(valor);

                if (fase == 2 && mapaCores.containsKey(valor)) {
                    botoes[indice].setBackground(mapaCores.get(valor));
                    botoes[indice].setForeground(valor.equals("Black") || valor.equals("Preto") ? Color.WHITE : Color.BLACK);
                } else {
                    botoes[indice].setBackground(new Color(34, 177, 76));
                }

                if (primeiroBotao == null) {
                    primeiroBotao = botoes[indice];
                } else {
                    segundoBotao = botoes[indice];
                    verificarPares();
                }
            }
        }
    }

    private void verificarPares() {
        if (primeiroBotao != null && segundoBotao != null) {
            if (saoPares(primeiroBotao.getText(), segundoBotao.getText())) {
                primeiroBotao.setEnabled(false);
                segundoBotao.setEnabled(false);
                paresEncontrados++;

                if (fase == 3) {
                    Color cor = coresUsadas.get(primeiroBotao.getText());
                    if (cor == null) {
                        cor = corAtual;
                        corAtual = corAtual.darker(); 
                        coresUsadas.put(primeiroBotao.getText(), cor);
                        coresUsadas.put(segundoBotao.getText(), cor);
                    }
                    primeiroBotao.setBackground(cor);
                    segundoBotao.setBackground(cor);
                } else if (fase == 2) { 
                    String valor = primeiroBotao.getText();
                    if (mapaCores.containsKey(valor)) {
                        Color cor = mapaCores.get(valor);
                        primeiroBotao.setBackground(cor);
                        segundoBotao.setBackground(cor);
                    }
                }

               
                if (paresEncontrados == 8) {
                    if (fase < 3) {
                        JOptionPane.showMessageDialog(this, "Parabéns! Você passou para a próxima fase!");
                        fase++;
                        iniciarFase();
                    } else {
                        JOptionPane.showMessageDialog(this, "Você completou todas as fases! 🏆");
                        System.exit(0);
                    }
                }
            } else {
                JButton primeiro = primeiroBotao;
                JButton segundo = segundoBotao;

                Timer timer = new Timer(600, e -> {
                    primeiro.setText("?");
                    segundo.setText("?");
                    primeiro.setBackground(new Color(70, 130, 180));
                    segundo.setBackground(new Color(70, 130, 180));
                    primeiro.setForeground(Color.WHITE);
                    segundo.setForeground(Color.WHITE);
                });
                timer.setRepeats(false);
                timer.start();
            }
        }

        primeiroBotao = null;
        segundoBotao = null;
    }
    private boolean saoPares(String a, String b) {
        if (fase == 1) {
            return a.equals(b);
        } else if (fase == 2) {
            return mapaCores.containsKey(a) && mapaCores.containsKey(b) && mapaCores.get(a).equals(mapaCores.get(b));
        } else if (fase == 3) {
            Map<String, String> charadas = Map.of(
                "Tem dentes, mas não morde.", "Pente",
                "Quanto mais se tira, maior fica.", "Buraco",
                "Anda com os pés na cabeça.", "Piolho",
                "É cheio de buracos, mas segura água.", "Esponja",
                "Não tem boca, mas conta tudo.", "Relógio",
                "Pode ser alto ou baixo, mas sempre está de pé.", "Som",
                "Tem chaves, mas não abre portas.", "Teclado",
                "Tem pescoço, mas não tem cabeça.", "Garrafa"
            );
            return charadas.getOrDefault(a, "").equals(b) || charadas.getOrDefault(b, "").equals(a);
        }
        return false;
    }

    public static void main(String[] args) {
        new JogoMemoria();
    }
}
