/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import View.Tabuleiro;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
/**
 *
 * @author jbatista
 * ESTA CLASSE contem o MODELO DE DADOS da sua aplicação !!!!!
 * COloque nela APENAS os dados: acesso a um banco de dados, queries de SQL, suas pecas de xadrez, 
 * etc..
 */
public class ModelTabuleiro extends Observable implements Observer, java.io.Serializable {

    private final ArrayList<Peca> pecasPretas;
    private final ArrayList<Peca> pecasBrancas;
    private Peca selecionada;
    private int Vitoria; // 0 brancas 1 pretas

    public void setVitoria(int Vitoria) {
        this.Vitoria = Vitoria;
    }

    public void setVezBrancas(int vezBrancas) {
        this.vezBrancas = vezBrancas;
    }

    public int getVitoria() {
        return Vitoria;
    }

    public int getVezBrancas() {
        return vezBrancas;
    }
    private int vezBrancas;
    private Cronometro Ttotal;
    private Temporizador TrodadaB;
    private Temporizador TrodadaP;

    public Cronometro getTtotal() {
        return Ttotal;
    }

    public Temporizador getTrodadaB() {
        return TrodadaB;
    }

    public Temporizador getTrodadaP() {
        return TrodadaP;
    } 

    public ModelTabuleiro()  {
        Vitoria = -1; //ninguem ganhou
        this.pecasPretas = new ArrayList<Peca>();
        this.pecasBrancas  = new ArrayList<Peca>();
        selecionada = null;        
        init();
    }
    
    private void init() {
        
        //Inicializa todas pecas
        for(int i = 0; i < 8; i++) {
            //inicializa peoes branco
            pecasBrancas.add(new Peao(Peca.Cor.BRANCO,i,6));            
            //inicializa peoes preto
            pecasPretas.add(new Peao(Peca.Cor.PRETO,i,1));
        }
        int j = 1;
        int i = 0;
        do {
           pecasBrancas.add(new Torre(Peca.Cor.BRANCO,i,7));
           pecasBrancas.add(new Cavalo(Peca.Cor.BRANCO,i + 1*j,7));
           pecasBrancas.add(new Bispo(Peca.Cor.BRANCO,i + 2*j,7));
           
           pecasPretas.add(new Torre(Peca.Cor.PRETO,i,0));
           pecasPretas.add(new Cavalo(Peca.Cor.PRETO,i + 1*j,0));
           pecasPretas.add(new Bispo(Peca.Cor.PRETO,i + 2*j,0));
           j *= -1;
           i += 7;
        }while(i <= 7);
        pecasBrancas.add(new Rainha(Peca.Cor.BRANCO,3,7));
        pecasBrancas.add(new Rei(Peca.Cor.BRANCO,4,7));
        pecasPretas.add(new Rainha(Peca.Cor.PRETO,3, 0));   
        pecasPretas.add(new Rei(Peca.Cor.PRETO,4,0));        
    }

    public Peca getSelecionada() {
        return selecionada;
    }

    public void setSelecionada(Peca selecionada) {
        this.selecionada = selecionada;
    }
     //Retorna true se peca tiver sido movida ou movimento for cancelado
    private boolean moveDiagonal(Peca P, int x, int y) {
        Peca aux = null;
        int xLoop = P.quadrante.x;
        int yLoop = P.quadrante.y;
        int i = x - P.quadrante.x > 0 ? 1 : -1;
        int j = y - P.quadrante.y > 0 ? 1 : -1;
        // Nao selecionar uma diagonal
        if(Math.abs(x - P.quadrante.x) != Math.abs(y - P.quadrante.y))
            return false;
                //verifica se ha uma peca entre a posicao inicial e a final
        while(xLoop != x && yLoop != y) {
            xLoop += i;
            yLoop += j;
            aux = findPeca(xLoop, yLoop);
            if(aux == null) {
                if(!P.verificaJogada(xLoop, yLoop, false)) {
                    return false; //Peca no caminho
                 }
            }else break;
        }
        //Se chegar ate a posicao final sem passar por obstaculos, move a peca
        if(xLoop == x && yLoop == y) {
            if(aux != null) {
                //Se a peca for inimiga apague
                if(aux.getCor() != P.getCor()) removePeca(aux);
                else return false;
            }                      
            P.setQuadrante(x, y);
            return true;
        }
        return false;
    }
    private boolean moveLados(Peca P, int x, int y) {
        Peca aux = null;        
        int xLoop = P.quadrante.x;
        int yLoop = P.quadrante.y;
        int i;
        int j;
        //Inicializa incremento dos eixos
        if(x == P.quadrante.x) i = 0;
        else i = x - P.quadrante.x > 0 ? 1 : -1;
        if(y == P.quadrante.y) j = 0;
        else j = y - P.quadrante.y > 0 ? 1 : -1;        
        // Nao andar em um dos eixos
        if(!(P.quadrante.x == x || P.quadrante.y == y)) {
            return false;
        }
        //verifica se ha uma peca entre a posicao inicial e a final
        while(xLoop != x || yLoop != y) {
            xLoop += i;
            yLoop += j;
            aux = findPeca(xLoop, yLoop);
            if(aux == null) {
                if(!P.verificaJogada(xLoop, yLoop, false)) {
                    return false; //Peca no caminho
                 }
            }else break;            
        }
        //Se chegar ate a posicao final sem passar por obstaculos, move a peca
        if(xLoop == x && yLoop == y) {
            if(aux != null) {
                if(aux.getCor() != P.getCor()) removePeca(aux);
                else return false;
            }
            P.setQuadrante(x, y);
            return true;                                  
        }
        return false;
    }
    public boolean movePeca(Peca P, int x, int y) {
        if(P.equals(findPeca(x, y))) {
            setSelecionada(null);
            return false;
        }
        switch(P.getPeca()) {
            case PEAO:
                Peca aux = findPeca(x, y);
                if(aux == null) {
                    if(P.verificaJogada(x, y, false)) {
                        P.setQuadrante(x, y);
                        return true;
                    }
                }else if(aux.getCor() != P.getCor() &&
                        P.verificaJogada(x,y,true)) {
                        removePeca(aux);
                        P.setQuadrante(x, y);
                        return true;
                }
                return false;
            case BISPO:
                return moveDiagonal(P, x, y);
            case TORRE:
                return moveLados(P, x, y);
            case CAVALO:
                if(!P.verificaJogada(x, y, true)) return false;
                aux = findPeca(x, y);
                if(aux == null) {
                    P.setQuadrante(x, y);
                    return true;  
                }else if(aux.getCor() != P.getCor()) {
                    removePeca(aux);
                    P.setQuadrante(x, y);
                    return true;
                }else return false;
            case RAINHA:
                if(P.quadrante.x == x || P.quadrante.y == y) 
                    return moveLados(P, x, y);
                return moveDiagonal(P, x, y);
            case REI:
                //So anda uma casa
                if(Math.abs(x - P.quadrante.x) <= 1 && Math.abs(y - P.quadrante.y) <= 1) {
                    if(P.quadrante.x == x || P.quadrante.y == y) 
                        return moveLados(P, x, y);
                    return moveDiagonal(P, x, y);            
                }
                return false;
        }
        return false;
    }    
    //Acha uma peca com base nas coordenadas
    public Peca findPeca(int x, int y) {
        Peca peca = null;
        
        //desenha pecas Brancas
        for(Peca p : pecasBrancas){
            if(p.inSquare(x,y)){
                return p;
            }
        }
        
        //desenha pecas pretas
        for(Peca p : pecasPretas){
            if(p.inSquare(x,y)){
                return p;
            }
        }
        
        return peca;
    }
    //Apaga a peca
    public void removePeca(Peca p) {
        if(p.cor == Peca.Cor.PRETO) {
            if(p.tipo == Peca.Tipo.REI)
                Vitoria = 0;
            pecasPretas.remove(p);            
        }            
        else {
            if(p.tipo == Peca.Tipo.REI)
                Vitoria = 1;
            pecasBrancas.remove(p);
        }        
    }    
    public void draw(Graphics2D g){
        //desenha pecas Brancas
        for(Peca p : pecasBrancas){
            p.draw(g);
        }
        
        //desenha pecas pretas
        for(Peca p : pecasPretas){
            p.draw(g);
        }
    }
    @Override
    public void update(Observable o, Object arg) {
        draw((Graphics2D) arg);
    }

    public void InicializaThreads(Tabuleiro view) {        
        TrodadaB = new Temporizador(view);
        TrodadaP = new Temporizador(view);
        Ttotal = new Cronometro(view, TrodadaB, TrodadaP);
    }

    public void atualizaThreads(Tabuleiro view) {
        Ttotal.setView(view);        
        TrodadaB.setView(view);
        TrodadaP.setView(view);
        Ttotal.setRelogios(TrodadaP, TrodadaB);
    }
}

    

