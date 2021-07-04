//Classe-Pai

package jogo;

import java.awt.Graphics2D;

public abstract class Element {

    public double x;    
    public double y;   //Lugar no espaço
    public int width;  //Largura
    public int height; //Altura
    public double vx;  //Velocidade
    public double vy;
    
    //Construtor do Elemento
    public Element(double x, double y, int width, int height, double vx, double vy) {  
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vx = vx;
        this.vy = vy;

    }

    //Método mover
    public void move() { 
        x += vx;
        y += vy;
    }

    //Método de espaço e visual
    public void paint(Graphics2D g2) { 
        g2.fillRect((int) x - width / 2, (int) y - height / 2, width, height);
    }

    //Método de atualização
    public void update() {  
        move();
    }
}
