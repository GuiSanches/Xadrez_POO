/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author jbatista
 */
public abstract class Peca implements java.io.Serializable{
    
    protected final static String imgPath = "img/pecas.png";
    protected static BufferedImage pecasImg = null;    
    protected Cor cor;
    protected Tipo tipo;
    protected Point quadrante;
    protected boolean movePeca;
    public enum Cor {
        PRETO,
        BRANCO
    }
    public enum Tipo {
       PEAO,
       TORRE,
       CAVALO,
       BISPO,
       RAINHA,
       REI
    }
    
    public Peca(Cor cor, int x, int y) {
        movePeca = false;
        this.cor = cor;
        this.quadrante = new Point(x,y);
        if(pecasImg == null){
            try {
                pecasImg = ImageIO.read(new File(imgPath));
            } catch (IOException ex) {
                Logger.getLogger(Peca.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean inSquare(int x, int y){
        if(x == quadrante.x && y == quadrante.y) return true;
        else return false;
    }

    public Cor getCor() {
        return cor;
    } 
    
    public void setQuadrante(int x, int y){
        quadrante.setLocation(x, y);
    }
    public abstract boolean verificaJogada(int x, int y, boolean inimigo);
    public abstract void draw(Graphics2D g);
    public abstract Tipo getPeca();
}
