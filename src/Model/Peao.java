/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Graphics2D;

/**
 *
 * @author jbatista
 */
public class Peao  extends Peca{
    private boolean comeco;

    public Peao(Cor cor, int x, int y)  {
        super(cor, x, y);
        tipo = Tipo.PEAO;
        comeco = true;        
    }

    @Override
    public void draw(Graphics2D g) {
        int squareWidth = g.getClip().getBounds().width / 8;
        int squareHeight = g.getClip().getBounds().height / 8;
        
        int x0 = quadrante.x * squareWidth;
        int y0 = quadrante.y * squareHeight;
        int x1 = x0 + squareWidth;
        int y1 = y0 + squareHeight;
        
        if(this.cor == Peca.Cor.PRETO){
            g.drawImage(pecasImg, x0, y0, x1, y1, 320, 20, 360, 60, null);
        } else {
            g.drawImage(pecasImg, x0, y0, x1, y1, 320, 72, 360, 112, null);
        }
    }
    
    @Override
    public String toString() {
        if(this.cor == Peca.Cor.PRETO){
            return "Peao Preto";
        } else {
            return "Peao Branco";
        }
    }
    
    @Override
    public boolean verificaJogada(int x, int y, boolean inimigo) {
        int dy = super.cor == Cor.PRETO ? y - super.quadrante.y : super.quadrante.y - y;
        int dx = Math.abs(x - super.quadrante.x);
        
        if(x == super.quadrante.x && y == super.quadrante.y) {
            return true;
        }
        if(inimigo) {
            return dx == 1 && dy == 1;
        }else if(dx == 0) {
            if(dy <= 2 && comeco) {
                comeco = false;
                return true;
            }else if(dy == 1) 
                return true;
        }
        return false;
    }    

    @Override
    public Tipo getPeca() {
        return tipo;
    }
}
