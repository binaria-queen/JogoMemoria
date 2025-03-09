import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicial extends JFrame {

    private static final long serialVersionUID = 1L;

    public TelaInicial() {
        setTitle("Tela Inicial - Jogo da Memória");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelFundo = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagemFundo = new ImageIcon("memory.png"); 
                g.drawImage(imagemFundo.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };

        painelFundo.setLayout(new BorderLayout());

        JPanel painelBotao = new JPanel();
        painelBotao.setOpaque(false); 
        painelBotao.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton botaoIniciar = new JButton("Iniciar Jogo");
        botaoIniciar.setFont(new Font("Arial", Font.BOLD, 18)); 
        botaoIniciar.setForeground(Color.WHITE);
        botaoIniciar.setBackground(new Color(45, 45, 45)); 
        botaoIniciar.setFocusPainted(false);
        botaoIniciar.setPreferredSize(new Dimension(200, 50)); 
        botaoIniciar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60), 2), 
                BorderFactory.createEmptyBorder(10, 20, 10, 20) 
        ));

        botaoIniciar.setRolloverEnabled(true);
        botaoIniciar.setOpaque(true);
        botaoIniciar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botaoIniciar.setBackground(new Color(70, 70, 70)); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botaoIniciar.setBackground(new Color(45, 45, 45));
            }
        });

        botaoIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botaoIniciar.setBackground(new Color(90, 90, 90));
                new Timer(200, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        botaoIniciar.setBackground(new Color(45, 45, 45)); 
                    }
                }).start();
                
                dispose();
                new JogoMemoria(); 
            }
        });

        painelBotao.add(botaoIniciar);
        painelFundo.add(painelBotao, BorderLayout.SOUTH);

        setContentPane(painelFundo);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaInicial();
    }
}
