package jogo;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Universe extends JPanel implements Runnable {

    //Criação das variáveis
    //Criação de uma lista com uma lista dentro (matriz)
    private ArrayList<List<Alien>> alien = new ArrayList();
    private static Universe singleton;
    private Background background;
    private boolean gameOver = false;
    public Ship ship;
    public Game game;
    public int cont = 0;
    public int AlienCount = Dimensoes.linesAliens * Dimensoes.columnsAliens;

    //Verificar se já existe um universo
    public static synchronized Universe getInstance(Game game) {
        //Caso não exista um universo
        if (singleton == null) {
            //Criação de um universo
            singleton = new Universe(game);
        }
        //Se ele já existe ele irá retorná-la
        return singleton;
    }

    //Metodo construtor
    public Universe(Game game) {
        initAlien();
        this.game = game;
        this.background = new Background();
        this.ship = Ship.getInstance();
        //Método do teclado
        this.addKeyListener(ship);
        //Cor do painel
        this.setBackground(Color.black);
        //Organização
        this.setLayout(new FlowLayout());
        this.setVisible(true);
        this.requestFocus();

    }

    public void initAlien() {
        //Linhas
        for (int i = 0; i < Dimensoes.linesAliens; i++) {
            //Adicionando os aliens
            alien.add(new ArrayList<>());
            //Colunas
            for (int j = 0; j < Dimensoes.columnsAliens; j++) {
                //Andando em X e Y
                alien.get(i).add(new Alien(70 * j + 70, 50 * i + 50));
            }
        }
    }

    //Método de atualizar
    public void update() {
        cont++;
        ship.update();
        //Percorra a matriz
        for (List<Alien> arr : alien) {
            for (Alien i : arr) {
                i.update();
                if (i.y >= Ship.getInstance().y - 30) {
                    gameOver = true;
                }
            }
        }

        if (cont % 400 == 0) {  //após certo tempo
            //Sorteamento de um alien aleatório
            int r1 = (int) (Math.random() * (3 - 0 + 1) + 0);
            int r2 = (int) (Math.random() * (11 - 0 + 1) + 0);

            while (r1 > alien.size() - 1 || alien.get(r1).isEmpty()) {
                r1 = (int) (Math.random() * (3 - 0 + 1) + 0);
            }

            while (r2 > alien.get(r1).size() - 1) {
                r2 = (int) (Math.random() * (11 - 0 + 1) + 0);
            }

            //Velocidade
            alien.get(r1).get(r2).vy = 2;
            alien.get(r1).get(r2).vx = 3;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        //Limpa a tela
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Tiro
        g.setColor(Color.blue);
        g.fillRect(600, 400, 5, 25);

        //Pintando
        background.paint(g2);
        ship.paint(g2);

        //SCORE
        g.setColor(Color.yellow);
        g.setFont(new Font("Swis721 BlkOul BT", Font.PLAIN, 24));
        g.drawString("SCORE: " + Alien.death, 10, 25);

        //Percorra a matriz
        for (List<Alien> arr : alien) {
            for (Alien i : arr) {
                //Pintando todos os Aliens
                i.paint(g2);
            }
        }

    }

    private void sleep() {
        // Evitar que outro programa mate a Thread enquanto ela está dormindo
        try {

            Thread.sleep(20); //20 milissegundos- 50 fps (roda 50 vezes por segundo)
        } catch (InterruptedException ex) {
            Logger.getLogger(Universe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public static void soundGame() throws UnsupportedAudioFileException, IOException{
        try{
            AudioInputStream menu = AudioSystem.getAudioInputStream(Ship.class.getResource("music/avenger_remix.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(menu);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }catch(Exception e){
            System.out.println("Erro ao encontrar a musica");
        }
    }
    
    @Override
    public void run() {
        //Thread vai executar o update e o repaint
        boolean b = false;
        try {
            soundGame();
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("Formato não suportado");
        } catch (IOException ex) {
            System.out.println("Não foi possivel ler a pasta");
        }
        while (!gameOver) {
            //Percorra a matriz
            for (List<Alien> arr : alien) {
                for (Alien i : arr) {
                    //Caso ele colida com a parede
                    if (i.x <= 0 || i.x >= Dimensoes.Largura - i.width) {
                        b = true;
                    }
                }
            }
            //Percorra a matriz
            for (List<Alien> arr : alien) {
                for (Alien i : arr) {
                    if (b) {
                        i.y += 5;    //O tanto que ele desce
                        i.vx *= -1;  //Ir para trás ou para frente
                    }
                }
            }
            b = false;
            //Percorra a matriz
            for (List<Alien> arr : alien) {
                for (Alien i : arr) {

                    i.collide(ship.tiro);
                }
            }

            for (int i = 0; i < alien.size(); i++) {
                //Remove o Alien caso ele seja atingido
                alien.get(i).removeIf(m -> !m.vivo);
            }

            if ((Alien.death/100) == AlienCount) {
                int resWon = JOptionPane.showConfirmDialog(this, "Score final: " + Alien.death + "\nClique em Ok para sair", "VOCÊ GANHOU!", JOptionPane.OK_CANCEL_OPTION);
                if (resWon == JOptionPane.OK_OPTION) {
                    System.exit(0);
                } else if (resWon == JOptionPane.OK_CANCEL_OPTION) {
                    System.exit(0);
                }
            }
            this.requestFocus();

            update();
            //Pintar a tela a cada interação
            repaint(); 
            sleep(); 

        }

        int resLose = JOptionPane.showConfirmDialog(this, "Seu score foi de: " + Alien.death + " \nClique em OK para sair", "VOCÊ PERDEU!", JOptionPane.OK_CANCEL_OPTION);
        if (resLose == JOptionPane.OK_OPTION) {
            System.exit(0);
        } else if (resLose == JOptionPane.OK_CANCEL_OPTION) {
            System.exit(0);
        }
    }
}
