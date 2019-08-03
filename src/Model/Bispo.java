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
public class Bispo extends Peca{

    public Bispo(Cor cor, int x, int y)  {
        super(cor, x, y);
        tipo = Tipo.BISPO;        
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
            g.drawImage(pecasImg, x0-5, y0, x1, y1, 190, 20, 240, 60, null);
        } else {
            g.drawImage(pecasImg, x0-5, y0, x1, y1, 190, 72, 240, 112, null);
        }
    }
    
    @Override
    public boolean verificaJogada(int x, int y, boolean inimigo) {
        return Math.abs(x - super.quadrante.x) == Math.abs(y - super.quadrante.y);      
    }   
    
    @Override
    public String toString() {
        if(this.cor == Peca.Cor.PRETO){
            return "Bispo Preto";
        } else {
            return "Bispo Branco";
        }
    }
    @Override
    public Tipo getPeca() {
        return this.tipo;
    }
    
    
}
