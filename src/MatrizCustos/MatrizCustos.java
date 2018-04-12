package MatrizCustos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class MatrizCustos {
	
	int qtdlinhas = 0;
	HashMap<Integer,Ponto> mapPontos = new HashMap<>();
	ArrayList<ItemMatriz> matriz = new ArrayList<>();
	
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
		    	qtdlinhas++; 
		        linha = lerArq.readLine(); // l� da segunda at� a �ltima linha
		      }
		 
		      arq.close();
		    } catch (IOException e) {
		        System.err.printf("Erro na abertura do arquivo: %s.\n",
		          e.getMessage());
		    }
	}
	
	
	public void calcularMatrizDistancia() {
		for (Ponto ponto : mapPontos.values()) {
			for (Ponto pontoSec : mapPontos.values()) {
				if(ponto.valor != pontoSec.valor) {
					float dist = 0;
					//System.out.println("Ponto1 " + ponto.valor + " CoordX: " + ponto.posX + " CoordY: " + ponto.posY);
					//System.out.println("Ponto2 " + pontoSec.valor + " CoordX: " + pontoSec.posX + " CoordY: " + pontoSec.posY);
					
					if(ponto.posX > pontoSec.posX && ponto.posY > pontoSec.posY)
						dist = (float) Math.sqrt((ponto.posX - pontoSec.posX) + (ponto.posY - pontoSec.posY));
					else if(pontoSec.posX > ponto.posX && ponto.posY > pontoSec.posY)
						dist = (float) Math.sqrt((pontoSec.posX - ponto.posX) + (ponto.posY - pontoSec.posY));
					else if(pontoSec.posX > ponto.posX && pontoSec.posY > ponto.posY)
						dist = (float) Math.sqrt((pontoSec.posX - ponto.posX) + (pontoSec.posY - ponto.posY));
					else if(ponto.posX > pontoSec.posX && pontoSec.posY > ponto.posY)
						dist = (float) Math.sqrt((ponto.posX - pontoSec.posX) + (pontoSec.posY - ponto.posY));
					else if(ponto.posX == pontoSec.posX)
						if(pontoSec.posY > ponto.posY)
							dist = (float) Math.sqrt((pontoSec.posY - ponto.posY));
						else
							dist = (float) Math.sqrt((ponto.posY - pontoSec.posY));
					else if(ponto.posY == pontoSec.posY)
						if(pontoSec.posX > ponto.posX)
							dist = (float) Math.sqrt((pontoSec.posX - ponto.posX));
						else
							dist = (float) Math.sqrt((ponto.posX - pontoSec.posX));
					
					//System.out.println("Dist: " + dist);
					insereItemMatriz(ponto.valor, pontoSec.valor, dist);
				}
			}
		}
	}
	
	public void insereItemMatriz(int i, int j, float valor ){
		ItemMatriz item1 = new ItemMatriz(i,j,valor);
		//ItemMatriz item2 = new ItemMatriz(j,i,valor);
		matriz.add(item1);	

	}
	
	public float getValorPosIJ(int i, int j) {
		for (ItemMatriz itemMatriz : matriz) {
			if(itemMatriz.i == i && itemMatriz.j == j) {
				return itemMatriz.valor;
			}
		}
		return 0;
		
	}
	
	
	public void printMatriz() {
		for (int i = 1; i <= qtdlinhas; i++) {
			for (int j = 1; j <= qtdlinhas; j++) {
				float valor = getValorPosIJ(i, j);
				System.out.print(valor + " ");
			}
			System.out.println("");
		}
	}
	
	public void printMapPontos() {
		for (int i = 1; i <= qtdlinhas; i++) {
			Ponto p = mapPontos.get(i);
			System.out.println("Ponto: " + i + " CoordX: " + p.posX + " CoordY: " + p.posY);
		}
	}
	
	public void printPosIguais() {
		for (int i = 1; i <= qtdlinhas; i++) {
			for (int j = 1; j <= qtdlinhas; j++) {
				System.out.println("I: " + i + " J: " + j);
				float vlr1 = getValorPosIJ(i, j);
				float vlr2 = getValorPosIJ(j, i);
				System.out.print("P1: " + i + " P2: " + j + " Valor: " + vlr1 + " ");
			    System.out.print("P2: " + j + " P1: " + i + " Valor: " + vlr2);
			    System.out.println(""); 
			}
			//System.out.println("");
		}
	}
	
	public void gravarMatriz(String nomeArq){
		 try {
		    
		      FileWriter arqgravar = new FileWriter(nomeArq);
			  PrintWriter gravarArq = new PrintWriter(arqgravar);
			  int jAux = qtdlinhas;
			  
			  for (int i = 1; i <= qtdlinhas; i++) {
					for (int j = 1; j <= qtdlinhas; j++) {
						
						float vlr1 = getValorPosIJ(i, j); 
						System.out.println("I: " + i + " J: " + j + " Valor: " + vlr1);
						if(j < jAux) {
							gravarArq.printf("%f;",vlr1);
						}
						else
							gravarArq.printf("%f\n",vlr1);
					}
				}
		     
		      arqgravar.close();
		    } catch (IOException e) {
		        System.err.printf("Erro na abertura do arquivo: %s.\n",
		          e.getMessage());
		    }
	}
	
	
}
