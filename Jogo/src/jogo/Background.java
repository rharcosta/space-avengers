//Classe do Papel de Parede

package jogo;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Background {

    private BufferedImage image;
    private int y;

    public Background() {
        try {
            image = ImageIO.read(new File("images/space4.jpg"));
        } catch (IOException ex) {
            System.out.println("Não foi possível encontrar a imagem");
            //Exceção
            ex.printStackTrace();  
        }
        y = 0;
    }
    
    //Método pintar
    public void paint(Graphics2D g2) {  
        //Colocando a imagem em uma posição X e Y - Tamanho dela em X e Y - e ajustando ela
        g2.drawImage(image, 0, y - Dimensoes.Largura, image.getWidth(), image.getHeight(), null);
        g2.drawImage(image, 0, y, image.getWidth(), -image.getHeight(), null);
        g2.drawImage(image, 0, y, image.getWidth(), image.getHeight(), null);
        y += 3; //Velocidade do espaço
        if (y > Dimensoes.Largura){
            y = 0;
        }
    }
}
