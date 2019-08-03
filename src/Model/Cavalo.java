/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.Peca.pecasImg;
import java.awt.Graphics2D;

/**
 *
 * @author Guilherme Sanches
 */
public class Cavalo extends Peca {
    private int [][]posicoes;

    @SuppressWarnings("empty-statement")
    public Cavalo(Peca.Cor cor, int x, int y) {
        super(cor, x, y);
        tipo = Tipo.CAVALO;
        posicoes = new int[][]{{1, 2}, {1, -1}, {0, -2}, {-1, -1}, {-2, 0}, {-1, 1}, {0, 2}, {1, 1}};
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
            g.drawImage(pecasImg, x0-5, y0, x1, y1, 255, 20, 300, 60, null);
        } else {
            g.drawImage(pecasImg, x0-5, y0, x1, y1, 255, 72, 300, 112, null);
        }
    }
    
    @Override
    public boolean verificaJogada(int x, int y, boolean inimigo) {
        int x0 = quadrante.x;
        int y0 = quadrante.y;  
        for(int i = 0; i < 8; i++) {
            x0 += posicoes[i][0];
            y0 += posicoes[i][1];
            if(x0 == x && y0 == y) {
                return true;
            }                          
        }
        return false;
    }
    
    @Override
    public String toString() {
        if(this.cor == Peca.Cor.PRETO){
            return "Cavalo Preto";
        } else {
            return "Cavalo Branco";
        }
    }

    @Override
    public Tipo getPeca() {
        return tipo;
    }
}
