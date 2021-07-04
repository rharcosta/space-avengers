//Classe Míssel

package jogo;

public class Missile extends Element {

    
    boolean vivo;

    //Construtor do Missíl
    public Missile(double x, double y) { 
        super(x, y, Dimensoes.widthMissile, Dimensoes.heightMissile, 0, Dimensoes.vyMissile);
        vivo = true;  //Inicialização = vivo
    }

    @Override
    //Método de mover
    public void move() {  
        //Método de mover do pai 
        super.move();  
        //Se o tiro passar do painel
        if (y < 0) {  
            vivo = false;
        }
    }
}
