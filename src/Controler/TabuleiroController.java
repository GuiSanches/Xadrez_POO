/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Model.ModelTabuleiro;
import Model.Peca;
import View.Tabuleiro;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 *
 * @author felipelageduarte
 */
public class TabuleiroController implements MouseListener, MouseMotionListener {

  private Tabuleiro view;
  private ModelTabuleiro model;  
  private String []jogada;  
  
    public void addView (Tabuleiro view){
        this.view = view;
    }
    
    public void addModel (ModelTabuleiro model){
        this.model = model;
    }    
    /*
      USe este metodo para iniciar o seu VIEW... neste caso, antes de motra-lo
    na tela, o posicionamos no centro dela....
    */
    public void runTabuleiro() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - view.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - view.getHeight()) / 2);
        view.setLocation(x, y);
        
        view.setVisible(true);
        // Primeira chamada ao abrir programa
        if(model.getTtotal() == null) {
            jogada = new String[]{"Pretas", "Brancas"};
            model.setVezBrancas(1);
            model.InicializaThreads(view);
        }else {
            model.atualizaThreads(view);
            view.getStatus().setText("Vez: " +jogada[model.getVezBrancas()]);
        }
        new Thread(model.getTtotal()).start();
        new Thread(model.getTrodadaB()).start();
        new Thread(model.getTrodadaP()).start();        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.SalvaJogo();
        if(model.getVitoria() == -1) {
          int x = e.getX();//pega as coordenadas do mouse
          int y = e.getY();
          view.getClickLabel().setText("x:"+x+"  y:"+y+"   -   Quadrante: ["+x/60+","+y/60+"]");
          //nao tiver selecionado uma peca
          if(model.getSelecionada() == null) {
              //peca clicada
            if(model.findPeca(x/60, y/60) != null) {
                //Alterna jogadas
                if(model.getVezBrancas() == 1){
                    if(model.findPeca(x/60, y/60).getCor() == Peca.Cor.BRANCO)
                        model.setSelecionada(model.findPeca(x/60, y/60));
                    }else if (model.findPeca(x/60, y/60).getCor() == Peca.Cor.PRETO) 
                        model.setSelecionada(model.findPeca(x/60, y/60));
            }            
            }else {
                //Peca movida com sucesso
                if(model.movePeca(model.getSelecionada(), x/60, y/60)) {
                    view.getMouseCoord1().setLocation(x, y);
                    model.setVezBrancas((model.getVezBrancas() + 1) % 2);                                        
                    //Fim de jogo
                    if(model.getVitoria() == 0 || model.getVitoria() == 1) {
                        model.getTrodadaB().setOff(true);                        
                        model.getTrodadaP().setOff(true);
                        model.getTrodadaB().setOn(false);
                        model.getTrodadaP().setOn(false);
                        if(model.getVitoria() == 0) 
                            view.getStatus().setText("BRANCAS GANHARAM");                                                      
                        else 
                            view.getStatus().setText("PRETAS GANHARAM");
                        model.getTtotal().setOn(false);
                    }else {
                        view.getStatus().setText("Vez: " +jogada[model.getVezBrancas()]);
                        model.getTrodadaB().setOn(model.getVezBrancas() == 1);
                        model.getTrodadaP().setOn(model.getVezBrancas() == 0);
                    }
                    model.setSelecionada(null);
                }            
                view.repaint();
            } 
       }               
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        if(!model.getTtotal().getOn()) 
            model.setVitoria(10);
        if(model.getVitoria() == -1) {
            int x = e.getX(); //pega as coordenadas do mouse
            int y = e.getY();
            view.getCoordenadaLabel().setText("x:"+x+"  y:"+y+"   -   Quadrante: ["+x/60+","+y/60+"]");
            if(model.getSelecionada() == null) {
                view.getMouseCoord1().setLocation(x, y);
            }
            view.getMouseCoord2().setLocation(x, y);
            view.repaint();
        }
    }
    public void SalvaJogo() {
        // Serialization  
        try {    
            //Saving of object in a file 
            FileOutputStream file = new FileOutputStream("xadrez.data"); 
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(model);
                        
            out.close(); 
            file.close(); 
  
        }
        catch(IOException ex){ 
            System.out.println("IOException is caught: " + ex.toString()); 
        }
    }
    
    public void RecarregaJogo() {
        // Deserialization 
        try
        {   
            // Reading the object from a file 
            FileInputStream file = new FileInputStream("xadrez.data"); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
            this.model = (Model.ModelTabuleiro)in.readObject();
            view.setVisible(false);
            view = new Tabuleiro(model, this);
            view.addController(this);
            this.model.addObserver(view);
            this.model.setSelecionada(null);
            this.runTabuleiro();
            
            in.close(); 
            file.close();
        }
          
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        } 
          
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        } 
    }
}
