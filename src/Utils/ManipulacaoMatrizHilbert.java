/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import VRP.MatrizCustos;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author PATRÍCIA E RICARDO
 */
public class ManipulacaoMatrizHilbert {
    public static MatrizHilbert matrizHilbert;
   
    
    public static ArrayList<Integer> getOrdemAtendimentoClientes(MatrizCustos matCustos){
        ArrayList<Integer> listaOrdemAtendimento = new ArrayList<>();
        
        //Começo a matriz com a ordem 3
        matrizHilbert = new MatrizHilbert(10);
        
        setarDimensoesCelulas(matrizHilbert);
        matrizHilbert.printDimensionCellsMat();
        //Enquanto nao encaixar a matriz no plano carteziano ( cada ponto deve estar sozinho dentro da célula da matriz)
        //while(true){
            //Aumenta a ordem da matriz
            //Calcula as coordenas que cada célula vai ocupar no plano carteziano
            
        //}
        return null;
    }
    
    public static void setarDimensoesCelulas(MatrizHilbert mat){
        float dimCelula = (float) 0.98; //Por enquanto é o valor padrão
        int dimensaoMatriz = mat.getDimensao();
        float posInicY = 0;
        for(int i = dimensaoMatriz - 1 ; i >= 0; i--){
            float posInicX = 0;
            for(int j = 0 ; j < dimensaoMatriz; j++){
                
                float posFimX = (float) (posInicX + dimCelula);
                float posFimY = (float) (posInicY + dimCelula);
                
                Celula cell = mat.getCellPos(i, j);
                cell.setPosInicX(posInicX);
                cell.setPosFimX(posFimX);
                cell.setPosInicY(posInicY);
                cell.setPosFimY(posFimY);
                
                posInicX += dimCelula;
            }
            posInicY += dimCelula;
        }
    }
}
