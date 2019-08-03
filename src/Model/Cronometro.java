/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.Tabuleiro;
import java.io.Serializable;
/**
 *
 * @author Guilherme Sanches
 */
public class Cronometro implements Runnable, Serializable {
    protected int sec;
    protected int min;
    transient private Tabuleiro view;
    private final Temporizador Relogios[];
    private boolean on;
    
    public Cronometro(Tabuleiro view, Temporizador P, Temporizador B) {
        sec = 0;
        min = 0;
        on = true;
        this.view = view;
        Relogios = new Temporizador[2];
        Relogios[0] = B;
        Relogios[1] = P;
    }
    
    public void setView(Tabuleiro view) {
        this.view = view;
    }
    
    public void setOn(boolean b) {
        on = b;
    }
    
    public void setRelogios(Temporizador P, Temporizador B) {
        Relogios[0] = B;
        Relogios[1] = P;
    }
    
    public boolean getOn() {
        return on;
    }
    
    @Override
    public void run() {
        while(on) {
            // Atualiza cronometro
            if(sec == 59) {
                min++;
                sec = 0;
            }else sec++;
            if(Relogios[0].getOff() || Relogios[1].getOff()) {
                // Relogio estar ligado e desligado é um caso especial de fim de jogo
                if(Relogios[0].getOff()) {
                    Relogios[1].setOn(true); //Liga relogio adversario
                    Relogios[1].setOff(true);//Desliga relogio adversario
                }else if(Relogios[1].getOff()) {
                    Relogios[0].setOn(true); //Liga relogio adversario
                    Relogios[0].setOff(true);//Desliga relogio adversario
                } 
                on = false; // Encerra thread
            }
            view.getTtotal().setText(String.format( "%02d:%02d", min, sec ));
            try {                
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                System.out.println("ERRO");
            }  
       }
    }
}
