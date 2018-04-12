/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import VRP.MatrizCustos;
import MatrizCustos.Ponto;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.davidmoten.hilbert.HilbertCurve;
import org.davidmoten.hilbert.SmallHilbertCurve;

/**
 *
 * @author PATRÍCIA E RICARDO
 */
public class ManipulacaoMatrizHilbert {
    SmallHilbertCurve matrizHilbert;
    int qtdlinhas = 0;
    HashMap<Integer,Ponto> mapPontos = new HashMap<>();
    HashMap<Integer,Long> mapOrdemAtendimento = new HashMap<>();

    public ManipulacaoMatrizHilbert() {
    }
    
    public  ArrayList<Integer> getOrdemAtendimentoClientes(){
        ArrayList<Integer> listaOrdemAtendimento = new ArrayList<>();
        
        //Reposiciono as Coordenadas de cada Ponto
        ReposicionarPontos();
        
        //Obtendo a maior posição x ou Y, esse valor vai dizer qual é a dimensão da matriz de Hilbert
        int maiorPosX = ObterMaiorPosX();
        int maiorPosY = ObterMaiorPosY();
        
        int dimensaoMatriz = (maiorPosX > maiorPosY) ? maiorPosX : maiorPosY;
        matrizHilbert = HilbertCurve.small().bits(dimensaoMatriz).dimensions(2);
        
        for(Ponto ponto: mapPontos.values()){
            int linha = ponto.getPosY();
            int coluna = ponto.getPosX();
            long index = matrizHilbert.index(linha, coluna);
            mapOrdemAtendimento.put(ponto.getValor(), index);
            System.out.println("Ponto: " + ponto.getValor() + " Ordem: " + index);
        }
        
        return null;
    }
    
    public void ReposicionarPontos(){
        lerPontos("C:\\Users\\PATRÍCIA E RICARDO\\Documents\\NetBeansProjects\\TCC-Netbeans\\entrada2.txt");
        int maiorPosX = ObterMaiorPosX();
        int maiorPosY = ObterMaiorPosY();
        int menorPosX = 0;
        int menorPosY = 0;
        boolean achou = false;
        System.out.println(maiorPosY);
       
        //Reposicionando as cordenadas Y dos pontos 
        for(int i = 0; i <= maiorPosY; i++ ){
            for(int j = 0; j <= maiorPosX; j++){
                for (Ponto ponto: mapPontos.values()){
                    if(ponto.getPosY() == i){
                        ponto.setPosY(menorPosY);
                        achou = true;
                    }
                }
            }
            if(achou)
                menorPosY++;
            achou = false;
        }
        
        //Reposicionando as cordenadas X dos pontos 
        achou = false;
        for(int i = 0; i <= maiorPosX; i++ ){
            for(int j = 0; j <= maiorPosY; j++){
                for (Ponto ponto: mapPontos.values()){
                    if(ponto.getPosX() == i){
                        ponto.setPosX(menorPosX);
                        achou = true;
                    }
                }
            }
            if(achou)
                menorPosX++;
            achou = false;
        }
        
        //printMapPontos();
    }
    
    public void printMapPontos() {
        for (int i = 1; i <= qtdlinhas; i++) {
            Ponto p = mapPontos.get(i);
            System.out.println("Ponto: " + i + " CoordX: " + p.getPosX() + " CoordY: " + p.getPosY());
        }
    }
    
    public int ObterMaiorPosX(){
        int maiorPosX = 0;
         for (Map.Entry<Integer, Ponto> entry : mapPontos.entrySet()) {
            Ponto coordenadas = entry.getValue();
            int coordX = coordenadas.getPosX();
            if(coordX > maiorPosX){
                maiorPosX = coordX;
            }
        }
         return maiorPosX;
    }
    
     public int ObterMaiorPosY(){
        int maiorPosY = 0;
         for (Map.Entry<Integer, Ponto> entry : mapPontos.entrySet()) {
            Ponto coordenadas = entry.getValue();
            int coordY = coordenadas.getPosY();
            if(coordY > maiorPosY){
                maiorPosY = coordY;
            }
        }
         return maiorPosY;
    }
    
    public void lerPontos(String nomeArq){
        try {
             FileReader arq = new FileReader(nomeArq);
             BufferedReader lerArq = new BufferedReader(arq);
             String linha = lerArq.readLine();
             String[] linhaNumeros = null;

             while (linha != null) {

               linhaNumeros = linha.split(";");
               int ponto = Integer.parseInt(linhaNumeros[0]);
               int coordX = Integer.parseInt(linhaNumeros[1]);
               int coordY = Integer.parseInt(linhaNumeros[2]);
               mapPontos.put(ponto, new Ponto(ponto,coordX,coordY));
               linha = lerArq.readLine(); // l� da segunda at� a �ltima linha
               qtdlinhas++;
             }

             arq.close();
           } catch (IOException e) {
               System.err.printf("Erro na abertura do arquivo: %s.\n",
                 e.getMessage());
           }
    }
}
