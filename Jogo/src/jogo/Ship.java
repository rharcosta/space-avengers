//Classe Nave
package jogo;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Ship extends Element implements KeyListener {  //Método para ativar o teclado

    BufferedImage image;
    public int vel = 5;
    public boolean shooting;
    public Missile tiro;

    //Apenas uma nave
    private static Ship singleton;

    //Verificar se já existe uma nave 
    public static Ship getInstance() {
        //Caso não exista uma nave
        if (singleton == null) {
            //Criação de uma nave
            singleton = new Ship();
        }
        // Se ela já existe ele irá retorná-la
        return singleton;
    }

    public Ship() {
        //Construtor da nave
        super(Dimensoes.xShip, Dimensoes.yShip, Dimensoes.widthShip, Dimensoes.heightShip, Dimensoes.vxShip, 0);
        tiro = null;
        try {
            //Pegar a imagem da nave
            image = ImageIO.read(new File("images/nave.png"));
        } catch (IOException ex) {
            System.out.println("Não foi possível encontrar a imagem");
            //Exceção
            ex.printStackTrace();
        }
    }

    public static void soundMissil() throws UnsupportedAudioFileException, IOException {
        try {
            AudioInputStream menu = AudioSystem.getAudioInputStream(Ship.class.getResource("music/ironMan6.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(menu);
            clip.start();
        } catch (Exception e) {
            System.out.println("Erro ao encontrar a musica");
        }
    }

    //Método atirar
    public void shoot() {
        //Se tiro for nulo ou já morreu ele dispara
        if (tiro == null || !tiro.vivo) {
            //Criação do tiro
            tiro = new Missile(x + width / 2 - Dimensoes.widthMissile, y);
            try {
                soundMissil();
            } catch (UnsupportedAudioFileException ex) {
                System.out.println("Formato não suportado");
            } catch (IOException ex) {
                System.out.println("Não foi possivel ler a pasta");
            }
        }
    }

    @Override
    //Método pintar
    public void paint(Graphics2D g2) {
        //Colocando a imagem em uma posição X e Y - Tamanho dela em X e Y - e ajustando ela
        g2.drawImage(image, (int) x, (int) y, (int) x + 100, (int) y + 100, 0, 0, image.getWidth(), image.getHeight(), null);

        //Se o tiro for diferente de nulo e vivo
        if (tiro != null && tiro.vivo) {
            g2.fillRect((int) tiro.x, (int) tiro.y, tiro.height, tiro.width);
        }
    }

    @Override
    //Método de atualizar
    public void update() {
        //Método de atualizar do pai 
        super.update();
        if ((x >= Dimensoes.Largura - width) || (x - width / 10 <= 0)) {
            this.x -= this.vx;
        }
        if (shooting) {
            this.shoot();
        }
        if (tiro != null) {
            tiro.update();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Muda de sistema operacional para sistema operacional
    }

    @Override
    //Chama o evento apenas um vez depois de chamada
    public void keyPressed(KeyEvent e) {
        //Chave para a tecla apertada
        int key = e.getKeyCode();

        // Tecla D ou seta para direita
        if ((key == KeyEvent.VK_D) || (key == KeyEvent.VK_RIGHT)) {
            this.vx = vel;

        } // Tecla A ou seta para esquerda
        else if ((key == KeyEvent.VK_A) || (key == KeyEvent.VK_LEFT)) {
            this.vx = -vel;

        }
        if (key == KeyEvent.VK_SPACE) {
            this.shooting = true;
        }
        if (key == KeyEvent.VK_ENTER) {
            System.exit(0);
        }

    }

    @Override
    //Chama o evento apenas um vez depois de chamada
    //Quando soltamos a tecla 
    public void keyReleased(KeyEvent e) {
        //Chave para a tecla apertada
        int key = e.getKeyCode();

        // Tecla D ou seta para direita
        if ((key == KeyEvent.VK_D) || (key == KeyEvent.VK_RIGHT)) {
            this.vx = 0;
        } // Tecla D ou seta para esquerda
        else if ((key == KeyEvent.VK_A) || (key == KeyEvent.VK_LEFT)) {
            this.vx = 0;
        }
        if (key == KeyEvent.VK_SPACE) {
            this.shooting = false;
        }
    }

}
