/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVRP;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import VRP.MatrizCustos;
/**
 *
 * @author ricar
 */
public class mTSP {
    	//private int[] solucao;
	private Vector<int[]> solucaoTotal;
	private MatrizCustos matrizCustos;
	private int N;
	private int numVeiculos;
	Random gerador;
	
	public mTSP(int nVeiculos) {
		numVeiculos = nVeiculos;
		gerador = new Random();
		N = 33;
		matrizCustos = new MatrizCustos(33);
		//solucao = new int[N];
		/*int[] solucao1 = new int[] {0,10,9,38,32,44,14,43,16,46,50,26};
		int[] solucao2 = new int[] {0,47,5,13,23,42,22,6,25,7,30,27};
		int[] solucao3 = new int[] {0,3,40,39,18,41,36,11,45};
		int[] solucao4 = new int[] {0,31,1,15,49,8,29,33,20,28,19,34,35,2,21};
		int[] solucao5 = new int[] {0,37,48,4,24,12,17};
		*/
		
		int[] solucao1 = new int[] {0,5,2,20,15,9,3,8,4};
		int[] solucao2 = new int[] {0,31,24,23,26,22};
		int[] solucao3 = new int[] {0,17,11,29,19,7};
		int[] solucao4 = new int[] {0,10,12,1};
		int[] solucao5 = new int[] {0,28,27,30,16,25,32};
                int[] solucao6 = new int[] {0,13,6,18,1,14};
		
		solucaoTotal = new Vector<int[]>();
		solucaoTotal.add(solucao1);
		solucaoTotal.add(solucao2);
		solucaoTotal.add(solucao3);
		solucaoTotal.add(solucao4);
		solucaoTotal.add(solucao5);
                solucaoTotal.add(solucao6);
	}
	
	public void gerarSolucaoInicial() {
		
	}
	
	public void printSolucaoTotal() {
		for (int i = 0; i < solucaoTotal.size(); i++) {
			System.out.print("S-" + (i + 1) + ": ");
			for (int s : solucaoTotal.get(i)) {
				System.out.print(s + " ");
			}
			System.out.println();
		}
	}
	
	public boolean verificaElemVetor(int elem, int[] solucao){
		for(int i = 0; i < solucao.length; i++){
			if(solucao[i] == elem){
				return true;
			}
		}
		return false;
	}
	
	public void printSolucao(int[] solucao){
		for(int i = 0; i < solucao.length; i++ ){
			System.out.print(solucao[i] + " ");
		}
	}
	
	public void printSolucaoVizinha(ArrayList<Integer> vetor){
		for(int i = 0; i < vetor.size(); i++ ){
			System.out.print(vetor.get(i) + " ");
		}
	}
	
	public float custoSolucao(int[] solucao){
		float custo = 0;
		
		for(int i = 0; i < solucao.length - 1; i++ ){
			int linha = solucao[i];
			int coluna = solucao[i+1];
			custo += matrizCustos.getElemPosIJ(linha, coluna);
			//System.out.println("Linha " + linha  + " Coluna: " + coluna + " Custo " + matrizCustos.getElemPosIJ(linha, coluna) );
		}
		
	
		custo += matrizCustos.getElemPosIJ(solucao[solucao.length - 1], solucao[0]);
		//System.out.print("Solucao:  ");
		//printSolucao(solucao);
		//System.out.println(" = " + custo);
		//System.out.println("Linha " + solucao[N-1]  + " Coluna: " + solucao[0] + " Custo " + matrizCustos.getElemPosIJ(solucao[N-1], solucao[0]) );
		return custo;
		
	}
	
	public float custoSolucaoTotal(){
		float custo = 0;
		
		for (int[] solucao : solucaoTotal) {
			for(int i = 0; i < solucao.length - 1; i++ ){
				int linha = solucao[i];
				int coluna = solucao[i+1];
				custo += matrizCustos.getElemPosIJ(linha, coluna);
			}
			custo += matrizCustos.getElemPosIJ(solucao[solucao.length - 1], solucao[0]);
		}
	
		return custo;
	}
	
	
	public float custoSolucaoVizinha(ArrayList<Integer> vetor){
		float custo = 0;
		
		for(int i = 0; i < vetor.size() - 1; i++ ){
			int linha = vetor.get(i);
			int coluna = vetor.get(i+1);
			custo += matrizCustos.getElemPosIJ(linha, coluna);
		}
		
		custo += matrizCustos.getElemPosIJ(vetor.get(vetor.size()-1), vetor.get(0));
		
		return custo;
		
	}
	
	public void validarSolucao(String tipo, int[] solucao){
		for (int i = 0; i < solucao.length - 1; i++) {
			for (int j = i + 1; j < solucao.length; j++) {
				if(solucao[i] == solucao[j]){
					System.out.println("ERRO " + tipo + " i: " + solucao[i] + " j: " +  solucao[j]);
					printSolucao(solucao);
				}
			}
		}
	}
	
	public ArrayList<Integer> Exchange(ArrayList<Integer> vetor){
		if(vetor.size() > 5) {
			int pos = gerador.nextInt(vetor.size()-3);
			pos = (pos <= 2) ? 3 : pos;
			
			//2 3 4 5 6 7 8 1
			//2 7 8 5 6 3 4 1 
			
			int aux = vetor.get(pos-2);
			vetor.set(pos-2, vetor.get(pos+2));
			vetor.set(pos+2,aux);
			
			aux = vetor.get(pos-1);
			vetor.set(pos-1, vetor.get(pos+1));
			vetor.set(pos+1,aux);
		}
		return vetor;
		
	}
	
	public ArrayList<Integer> Troca2PosAleatoria(ArrayList<Integer> vetor){
		int pos1 = gerador.nextInt(vetor.size()-2) + 1;
		int pos2 = gerador.nextInt(vetor.size()-2) + 1;
		while(pos1 == pos2){
			pos2 = gerador.nextInt(vetor.size()-2) + 1;
		}
		
		int aux = vetor.get(pos1);
		vetor.set(pos1,vetor.get(pos2));
		vetor.set(pos2,aux);
		
		return vetor;
	}
	
	// Sorteia uma posição aleatoria no vetor para trocar
	public ArrayList<Integer> Vizinhanca2_OPT(ArrayList<Integer> vetor){//Modificada
		
		int pos = gerador.nextInt(vetor.size()-2) + 1;
		int aux = 0;
		
		if(pos != vetor.size()-1){
			aux = vetor.get(pos);
			vetor.set(pos,vetor.get(pos + 1));
			vetor.set(pos + 1,aux);
		}
		else{
			aux = vetor.get(pos);
			vetor.set(pos,vetor.get(pos-1));
			vetor.set(pos-1,aux);
		}
		return vetor;
	}
	
    public ArrayList<Integer> VizinhancaOr_OPT(ArrayList<Integer> vetor){
		
    	if(vetor.size() > 4) {
			int pos = gerador.nextInt(vetor.size()-2) + 1;
			int aux = 0;
			
			if(pos >= vetor.size()-2){
				aux = vetor.get(pos);
				vetor.set(pos, vetor.get(pos-2));
				vetor.set(pos-2,aux);
			}
			else{
				aux = vetor.get(pos);
				vetor.set(pos,vetor.get(pos+2));
				vetor.set(pos+2,aux);
			}
    	}
		return vetor;
	}
	
	// Sorteia uma posição aleatoria no vetor para trocar
		public ArrayList<Integer> Vizinhanca3_Or_OPT(ArrayList<Integer> vetor){//Modificada
			
			int N = vetor.size();
			if(N > 4) {
				int pos = gerador.nextInt(N-1);
				pos = (pos == 0) ? 1 : pos;
				int aux = 0;
				
				if(pos == N-1){
					aux = vetor.get(N-1);
					vetor.set(N-1, vetor.get(N-3));
					vetor.set(N-3,aux);
					
					aux = vetor.get(N-2);
					vetor.set(N-4, vetor.get(N-2));
					vetor.set(N-4,aux);
				}
				else{
					if(pos + 2 < N-1){
						aux = vetor.get(pos);
						vetor.set(pos, vetor.get(pos+2));
						vetor.set(pos+2,aux);
						
						aux = vetor.get(pos+1);
						vetor.set(pos+1, vetor.get(pos+3));
						vetor.set(pos+3,aux);
					}
					else{
						aux = vetor.get(N-1);
						vetor.set(N-1, vetor.get(pos-2));
						vetor.set(pos-2,aux);
						
						aux = vetor.get(N-2);
						vetor.set(N-2, vetor.get(N-4));
						vetor.set(N-4,aux);
					}
				}
			}
			return vetor;
	}
		
	public ArrayList<Integer> VizinhancaTeste2(ArrayList<Integer> vetor){
		int N = vetor.size();
		int pos = gerador.nextInt(N-1);
		pos = (pos == 0) ? 1 : pos;
		int aux = 0;
		if(pos >= N-3){
			aux = vetor.get(N-1);
			vetor.set(N-1, vetor.get(N-2));
			vetor.set(N-2,aux);
			
			aux = vetor.get(N-3);
			vetor.set(N-3, vetor.get(N-4));
			vetor.set(N-4,aux);
		}
		else{
			aux = vetor.get(pos);
			vetor.set(pos, vetor.get(pos+1));
			vetor.set(pos+1,aux);
			
			aux = vetor.get(pos+2);
			vetor.set(pos+2, vetor.get(pos+3));
			vetor.set(pos+3,aux);
		}
		return vetor;
	}
		
	public ArrayList<Integer> VizinhancaTeste1(ArrayList<Integer> vetor){
		int N = vetor.size();
		int pos = gerador.nextInt(N-1);
		pos = (pos == 0) ? 1 : pos;
		int aux = 0;
		int aux1;
		if(pos >= N-3){
			aux = vetor.get(N-1);
			aux1 = vetor.get(N-2);
			
			vetor.set(N-1, vetor.get(N-4));
			vetor.set(N-2, vetor.get(N-3));
			vetor.set(N-3,aux);
			vetor.set(N-4,aux1);
		}
		else{
			aux = vetor.get(pos);
			aux1 = vetor.get(pos+1);
			
			vetor.set(pos, vetor.get(pos+2));
			vetor.set(pos+1, vetor.get(pos+3));
			vetor.set(pos+2,aux1);
			vetor.set(pos+3,aux);
		}
		
		return vetor;
		
	}	
	
	public ArrayList<Integer> trocaTodasPosicoes(ArrayList<Integer> vetor){//Modificada
		
		int aux;
		for(int i = 1; i < vetor.size() - 1; i += 2){
			aux = vetor.get(i);
			vetor.set(i, vetor.get(i+1));
			vetor.set(i+1,aux);
		}
		return vetor;
	}
	
	public ArrayList<Integer> Swap_Move(ArrayList<Integer> vetor){
		
		int N = vetor.size();
		if(N > 9) {
			int pos = gerador.nextInt(N-2) + 1;
			int aux1 = 0;
			int aux2 = 0;
			int aux3 = 0;
			if(pos < N - 5){
				aux1 = vetor.get(pos);
				aux2 = vetor.get(pos+1);
				aux3 = vetor.get(pos+2);
				
				vetor.set(pos, vetor.get(pos+3));
				vetor.set(pos+1, vetor.get(pos+4));
				vetor.set(pos+2, vetor.get(pos+5));
				
				vetor.set(pos+3, aux1);
				vetor.set(pos+4, aux2);
				vetor.set(pos+5, aux3);
			}
			else{
				aux1 = vetor.get(N-1);
				aux2 = vetor.get(N-2);
				aux3 = vetor.get(N-3);
				
				vetor.set(N-1, vetor.get(N-4));
				vetor.set(N-2, vetor.get(N-5));
				vetor.set(N-3, vetor.get(N-6));
				
				vetor.set(N-4, aux1);
				vetor.set(N-5, aux2);
				vetor.set(N-6, aux3);
			}
		}
		return vetor;
	}
	
	
	public ArrayList<Integer> copiaVetorSolucao(int[] solucao){
		int N = solucao.length;
		ArrayList<Integer> vetor = new ArrayList<>(); 
		for(int i = 0; i < N; i++){
			vetor.add(solucao[i]);
		}
		return vetor;
	}
	
	public void copiaVetor(int[] vet1, int[] vet2){ 
		
		for(int i = 0; i < vet1.length; i++){
			vet2[i] = vet1[i];
		}
	}
	
	public void atualizaVetorSolucao(int[] solucao, ArrayList<Integer> solucaoVizinha){
		for(int i = 0; i < solucao.length; i++){
			solucao[i] = solucaoVizinha.get(i);
		}
	}
	
	public void vns(int source){
		//gerarSolucaoInicial(source);
		System.out.println("Solucação Incial");
		printSolucaoTotal();
		System.out.println("");
		System.out.println("Custo Inicial: "+ custoSolucaoTotal());
		//System.out.println("");
		int k = 0;
		ArrayList<Integer> solucaoVizinha = new ArrayList<>();
		for(int i = 0;i < 100000; i++){
			for (int[] solucao : solucaoTotal) {
				k = 1;
				while(k <= 9){
					float custoAtual = custoSolucao(solucao);
					if(k == 1){
						solucaoVizinha = Vizinhanca2_OPT(copiaVetorSolucao(solucao));
					}
					else if(k == 2){
						solucaoVizinha = VizinhancaOr_OPT(copiaVetorSolucao(solucao));
					}
					else if(k == 3){
						solucaoVizinha = Troca2PosAleatoria(copiaVetorSolucao(solucao));
					}
					else if(k == 4){
						solucaoVizinha = Exchange(copiaVetorSolucao(solucao));
					}
					else if(k == 5){
						solucaoVizinha = Vizinhanca3_Or_OPT(copiaVetorSolucao(solucao));
					}
					else if(k == 6){
						solucaoVizinha = VizinhancaTeste1(copiaVetorSolucao(solucao));
					}
					else if(k == 7){
						solucaoVizinha = VizinhancaTeste2(copiaVetorSolucao(solucao));
					}
					else if(k == 8){
						solucaoVizinha = Swap_Move(copiaVetorSolucao(solucao));
					}
					else if(k == 9){
						solucaoVizinha = trocaTodasPosicoes(copiaVetorSolucao(solucao));
					}
					
					
					float novoCusto = custoSolucaoVizinha(solucaoVizinha);
					if(novoCusto < custoAtual){
						//System.out.println("Eita");
						atualizaVetorSolucao(solucao,solucaoVizinha);
						k = 1;
					}
					else{
						k++;
					}
				}
			}
		}
		
		System.out.println("");
		System.out.println("Solucação Final");
		printSolucaoTotal();
		System.out.println("");
		System.out.println("Custo Final: "+ custoSolucaoTotal());
	}
}
