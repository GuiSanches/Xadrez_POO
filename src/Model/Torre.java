/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Graphics2D;

/**
 *
 * @author Guilherme Sanches
 */
public class Torre extends Peca{
    public Torre(Cor cor, int x, int y)  {
        super(cor, x, y);
        tipo = Tipo.TORRE;
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
            g.drawImage(pecasImg, x0, y0, x1, y1, 135, 20, 175, 60, null);
        } else {
            g.drawImage(pecasImg, x0, y0, x1, y1, 135, 72, 175, 112, null);
        }
    }
    
    @Override
    public boolean verificaJogada(int x, int y, boolean inimigo) {
        return quadrante.x == x || quadrante.y == y;
    }
    
    @Override
    public String toString() {
        if(this.cor == Peca.Cor.PRETO){
            return "Torre Preta";
        } else {
            return "Torre Branca";
        }
    }    

    @Override
    public Tipo getPeca() {
        return tipo;
    }
}
