/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Xadrez;

import Controler.TabuleiroController;
import Model.ModelTabuleiro;
import View.Tabuleiro;
/**
 *
 * @author felipelageduarte
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
          
        ModelTabuleiro model = new ModelTabuleiro();
        // Cria o Controller do TAbuleiro.... todos eventos tratados aqui..
        TabuleiroController tabuleiroController = new TabuleiroController();
        // cria o View (Janela do TAbuleiro)
        Tabuleiro viewTabuleiro = new Tabuleiro(model, tabuleiroController);
        
        /*
        Se o seu projeto tivesse um MODEL, ou seja, um 'modulo de dados'
        entao seria a hora de cria-lo aqui... */        
        /*
        ATENCAO: a classe ModelTAbuleiro extends Observable !!!!!!
        Isso significa que a classe tem a disposicao o metodo addObserver()
        AHA: o observer no nosso caso é exatamente a nossa classe view -> Tabuleiro !!!
        a ideia é a seguinte: SEMPRE QUE HOUVER NECESSIDADE, em funcao de uma alteracao 
        dos dados, que isso se REFLITA visualmente na INTERFACE GRAFICA (o nosso view 
        Tabuleiro), então, o observable (ModelTAbuleiro) NOTIFICARÁ por meio do método
        notifyObservers(), no nosso caso o VIEW Tabuleiro !!!!
        
        Para isso devemos dizer qual é o observer do Observable
        */
        
        model.addObserver(viewTabuleiro);
        
        // define o tratamento de eventos dos atributos do view para o controller
        viewTabuleiro.addController(tabuleiroController);
        // associa o view ao seu repectivo controller
        tabuleiroController.addView(viewTabuleiro);
        tabuleiroController.addModel(model);
        // finalmente, executa o view.....
        tabuleiroController.runTabuleiro();
      }
    });
  }
}
