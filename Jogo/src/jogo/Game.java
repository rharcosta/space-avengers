package jogo;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Game extends JFrame {

    Thread t;

    public Game() {
        setUndecorated(true);
        setTitle("Space Invaders");  //Título do programa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //Fecha o programa
        setPreferredSize(new Dimension(Dimensoes.Largura, Dimensoes.Altura)); //Dimensão do JPanel 

        Universe universo = Universe.getInstance(this);  //Criação do universo
        getContentPane().add(universo); //Adicionando o painel no conteudo do frame
        pack();   //O tamanho do frame aumenta de acordo com o painel
        setVisible(true);  //Ativando a visibilidade do panel 
        addKeyListener(universo.ship);  //Teclas que movimentam e atiram a ship 

        t = new Thread(universo);
        t.start();
        universo.requestFocus();
        setLocationRelativeTo(null);

    }


}
