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
public class Rainha extends Peca {
    public Rainha(Cor cor, int x, int y)  {
        super(cor, x, y);
        tipo = Tipo.RAINHA;
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
            g.drawImage(pecasImg, x0, y0, x1, y1, 70, 20, 125, 60, null);
        } else {
            g.drawImage(pecasImg, x0, y0, x1, y1, 70, 72, 125, 112, null);
        }
    }
    
    @Override
    public boolean verificaJogada(int x, int y, boolean inimigo) {
        int dy = super.cor == Cor.PRETO ? y - super.quadrante.y : super.quadrante.y - y;
        int dx = Math.abs(x - super.quadrante.x);
        
        if(dy == 1 && dx <= 1) {
            movePeca = true;
        }else if(dy == 2 && dx <= 1) {
            movePeca = true;
        }
        return movePeca;
    }    
    
    @Override
    public String toString() {
        if(this.cor == Peca.Cor.PRETO){
            return "Rainha Preta";
        } else {
            return "Rainha Branca";
        }
    }
    @Override
    public Tipo getPeca() {
        return tipo;
    }
}
