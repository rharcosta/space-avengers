//Classe Alien 

package jogo;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Alien extends Element {

    BufferedImage image;
    public boolean vivo;
    public static int death;
    

    public Alien(double x, double y) {
        //Posição, tamanho e velocidade
        super(x, y, Dimensoes.widthAlien, Dimensoes.heightAlien, 2, 0);  
        //Alien inicializado vivo
        vivo = true;  
        try {
            image = ImageIO.read(new File("images/alien.png"));
        } catch (IOException ex) {
            System.out.println("Não foi possível encontrar a imagem");
            ex.printStackTrace();  //Exceção
        }
    }

    //Método de colisão
    public void collide(Missile tiro) { 
        if (tiro != null && tiro.vivo) {
            //Se o tiro estiver no alien
            if (tiro.x > x && tiro.x < x + width && tiro.y > y && tiro.y < y + height) {  
                tiro.vivo = false;  //tiro morto 
                vivo = false; //alien morto
                death+= 100;
               
            }

        }

    }
    
    @Override
    public void paint(Graphics2D g2) {
        //Colocando a imagem em uma posição X e Y - Tamanho dela em X e Y - e ajustando ela
        g2.drawImage(image, (int) x, (int) y, (int) x + 50, (int) y + 50, 0, 0, image.getWidth(), image.getHeight(), null);
    }

}
