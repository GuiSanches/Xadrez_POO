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
public class Rei extends Peca{
    public Rei(Cor cor, int x, int y)  {
        super(cor, x, y);
        tipo = Tipo.REI;
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
            g.drawImage(pecasImg, x0, y0, x1, y1, 15, 20, 60, 60, null);
        } else {
            g.drawImage(pecasImg, x0, y0, x1, y1, 15, 72, 60, 112, null);
        }
    }
    
    @Override
    public boolean verificaJogada(int x, int y, boolean inimigo) {
        return true;
    }    
    
    @Override
    public String toString() {
        if(this.cor == Peca.Cor.PRETO){
            return "Rei Preto";
        } else {
            return "Rei Branco";
        }
    }
    @Override
    public Tipo getPeca() {
        return tipo;
    }
}
