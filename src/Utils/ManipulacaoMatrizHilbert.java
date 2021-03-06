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
import java.util.Arrays;
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
    HashMap<Long,Integer> mapOrdemAtendimento = new HashMap<>();
     private static final HashMap<Integer, Integer> mapOrdemMatrizHilbert;
    static
    {
        mapOrdemMatrizHilbert = new HashMap<Integer, Integer>();
        mapOrdemMatrizHilbert.put(8, 3);
        mapOrdemMatrizHilbert.put(16, 4);
        mapOrdemMatrizHilbert.put(32, 5);
        mapOrdemMatrizHilbert.put(64, 6);
        mapOrdemMatrizHilbert.put(128, 7);
        mapOrdemMatrizHilbert.put(256, 8);
        mapOrdemMatrizHilbert.put(512, 9);
        mapOrdemMatrizHilbert.put(1024, 10);
    }
    
    
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
        int menorOrdem = ObterOrdemMatriz(dimensaoMatriz);
        System.out.println("DimMatriz: " + dimensaoMatriz);
        System.out.println("MenorOrdem: " + menorOrdem);
        matrizHilbert = HilbertCurve.small().bits(menorOrdem).dimensions(2);
        
        for(Ponto ponto: mapPontos.values()){
            int linha = ponto.getPosY();
            int coluna = ponto.getPosX();
            long index = matrizHilbert.index(linha, coluna);
            mapOrdemAtendimento.put(index, ponto.getValor());
            //System.out.println("Ponto: " + ponto.getValor() + " Ordem: " + index);
        }
       
        //Ordenando o array de ordem de atendimento
        Long[] array = new Long[mapOrdemAtendimento.values().size()];
        int i = 0;
        for(Long index: mapOrdemAtendimento.keySet()){
            array[i] = index;
            i++;
        }
        Arrays.sort(array);
        
        for(Long index: array){
            System.out.print(mapOrdemAtendimento.get(index) + " ");
            listaOrdemAtendimento.add(mapOrdemAtendimento.get(index));
        }
        
        return listaOrdemAtendimento;
    }
    
    public int ObterOrdemMatriz(int dimensao){
        int[] listDimensao = new int[mapOrdemMatrizHilbert.values().size()];
        int i = 0;
        for(int dim: mapOrdemMatrizHilbert.keySet()){
            listDimensao[i] = dim;
            i++;
        }
        Arrays.sort(listDimensao);
        
        //Obetendo a menor ordem possível para a dimensão da matriz
        int menorOrdem = 3;
        for(Integer dim: listDimensao){
            if(dim >= dimensao){
                menorOrdem = mapOrdemMatrizHilbert.get(dim);
                break;
            }
        }
        
        return menorOrdem;
    }
    
    public void ReposicionarPontos(){
        //lerPontos("C:\\Users\\PATRÍCIA E RICARDO\\Documents\\NetBeansProjects\\TCC-Netbeans\\entrada2.txt");
        lerPontos("C:\\Users\\ricar\\OneDrive\\Documentos\\NetBeansProjects\\TCC-V2\\entrada.txt");
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
