/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRP;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
/**
 *
 * @author PATRÍCIA E RICARDO
 */
public class MatrizCustos {
   private float [][] matrizCustos; 
	private int N;
	public MatrizCustos(int n) {
		N = n;
		matrizCustos = new float[N][N];
		//gerarMatrizCustos(400);
		lerMatrizTeste("C:\\Users\\RICARDO\\eclipse-workspace\\TCC\\M-" + n + ".csv");
	}
	
	public void gerarMatrizCustos(int tam){
		Random gerador = new Random();
		
		FileWriter arq;
		try {
			arq = new FileWriter("M-" + tam  +".csv");
		
			PrintWriter gravarArq = new PrintWriter(arq);
			
			int num;
			
			for(int i = 0; i < tam; i++ ){
				for(int j = 0; j < tam; j++ ){
					if(i != j){
						num = gerador.nextInt(90) + 10;
					}
					else{
						num = 0;
					}
					
					if(j != (tam - 1)){
						gravarArq.printf("%d;",num);
					}
					else{
						gravarArq.printf("%d",num);
					}
				}
				gravarArq.printf("\n");
			}
			arq.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printMatrizCustos(){
		for(int i = 0; i < N; i++ ){
			for(int j = 0; j < N; j++ ){
				System.out.print(matrizCustos[i][j] + " ");
			}
			System.out.println("\n");
		}
	}
	
	public float[][] getMatriz(){
		return this.matrizCustos;
	}
	
	public float getElemPosIJ(int i, int j){
		return matrizCustos[i][j];
	}
	
	public void insereLinhaMatriz(int linha, String[] listaNumeros){
		int coluna = 0;
		for (String num : listaNumeros) {
			matrizCustos[linha][coluna] = Float.parseFloat(num.replace(",", "."));
			coluna++;
		}
	}
	
	public void lerMatrizTeste(String nomeArq){
		 try {
		      FileReader arq = new FileReader(nomeArq);
		      BufferedReader lerArq = new BufferedReader(arq);
		      int i = 0;
		      String linha = lerArq.readLine();
		      String[] linhaNumeros = null;
		      
		      while (linha != null) {
		    	  
		    	linhaNumeros = linha.split(";");
		    	insereLinhaMatriz(i, linhaNumeros);  
		        linha = lerArq.readLine(); // lê da segunda até a última linha
		        i++;
		      }
		 
		      arq.close();
		    } catch (IOException e) {
		        System.err.printf("Erro na abertura do arquivo: %s.\n",
		          e.getMessage());
		    }
	}
	
	public void gravarMatriz(String nomeArq){
		 try {
		      FileReader arq = new FileReader(nomeArq);
		      BufferedReader lerArq = new BufferedReader(arq);
		      
		      FileWriter arqgravar = new FileWriter("m-48.csv");
			  PrintWriter gravarArq = new PrintWriter(arqgravar);
			    
		      int i = 0;
		      int j = 0;
		      int jAux = 0;
		      String linha = lerArq.readLine();
		      String[] linhaNumeros = null;
		      
		      while (linha != null) {
		    	  
		    	linhaNumeros = linha.split("\\s");
		    	//System.out.println(linhaNumeros[0]);
		    	while(i < (linhaNumeros.length - 1) ){
		    		if(j == jAux){
		    			gravarArq.printf("%d\n",Integer.parseInt(linhaNumeros[i]));
		    			j++;
		    			jAux = 0;
		    		}
		    		else{
			    		gravarArq.printf("%d;",Integer.parseInt(linhaNumeros[i]));
			    		System.out.println(Integer.parseInt(linhaNumeros[i]));
			    		//i++;
			    		jAux++;
		    		}
		    		i++;
		    	}
		    
		    	
		    	//insereLinhaMatriz(i, linhaNumeros);  
		        linha = lerArq.readLine(); // lê da segunda até a última linha
		        i=0;
		      }
		      arqgravar.close();
		      arq.close();
		    } catch (IOException e) {
		        System.err.printf("Erro na abertura do arquivo: %s.\n",
		          e.getMessage());
		    }
	}
}
