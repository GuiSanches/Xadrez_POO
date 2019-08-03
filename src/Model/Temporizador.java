/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.Tabuleiro;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guilherme Sanches
 */
public class Temporizador implements Runnable, Serializable {
    protected int sec;
    public int min;
    transient private Tabuleiro view;
    protected boolean on;
    protected boolean off;
    
    public Temporizador(Tabuleiro view) {
        min = 15;
        sec = 0;
        on = true;
        off = false;
        this.view = view;
    }
    
    public void setView(View.Tabuleiro t) {
        view = t;
    }
    
    public void setOff(boolean b) {
        off = b;
    }
    
    public void setOn(boolean b) {
        on = b;
    }
    
    public boolean getOn() {
        return on;
    }
    
    public boolean getOff() {
        return off;
    }

    @Override
    public void run() {
        synchronized(view.getTrodada()) {
            while(!off) {
                if(!on) {
                    try {
                        view.getTrodada().wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Temporizador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            // Atualiza temporizador
                if(sec == 0) {                    
                    if(min != 0) {
                        min--;
                        sec = 59;
                    } else {
                        off = true; //fim de jogo
                        on = false;
                    }
                } else sec--;
                if(!off) view.getTrodada().setText(String.format( "%02d:%02d", min, sec ));
                // Ao encerrar notifica outras threads que precissem da variavel
                try {
                    view.getTrodada().notifyAll();
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Temporizador.class.getName()).log(Level.SEVERE, null, ex);
                }    
            }
//            Apenas o temporizador marcado como off e not on pode marcar a vitoria para o adversario
//            Previnindo assim que as duas threads alterem o valor            
            if("Vez: Brancas".equals(view.getStatus().getText())  && on == off) {
                view.getTrodada().setText(String.format("A%02d:%02d", min, sec ));
                view.getStatus().setText("PRETAS GANHARAM");
            } else if(off == on) {              
                view.getStatus().setText("BRANCAS GANHARAM"); 
                view.getTrodada().setText(String.format("%02d:%02d", min, sec ));
            }
        }        
        
        
    }    
}
