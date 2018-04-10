/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VRP;
import java.util.Random;
/**
 *
 * @author PATRÍCIA E RICARDO
 */
public class VNS {
    private int[] solucao;
	private MatrizCustos matrizCustos;
	private int N;
	Random gerador;
	
	public VNS() {
		gerador = new Random();
		N = 48;
		matrizCustos = new MatrizCustos(N);
		solucao = new int[N];
		//solucao = new int[] {36,25,6,22,39,32,35,29,9,16,1,14,3,0,18,11,38,27,31,24,10,13,20,30,19,8,34,28,23,4,33,17,7,12,21,5,37,15,2,26};
		//solucao = new int[] {0,27,5,11,8,4,25,28,2,1,19,9,3,14,17,16,13,21,10,18,24,6,22,26,7,23,15,12,20};
		//solucao = new int[] {28,6,27,43,40,45,17,33,22,24,2,18,3,29,37,19,34,41,38,39,1,44,42,46,36,23,14,9,11,30,4,32,7,21,20,16,26,31,8,13,5,25,35,10,15,47,12,0};
	}
	
	public void gerarSolucaoInicial(int source){
		
		solucao[0] = source; 
		for(int i = 1; i < N; i++){
			if(i != source){
				solucao[i] = i;
			}
		}
		
		solucao = trocaTodasPosicoes(solucao);
		solucao = Troca2PosAleatoria(solucao);
		solucao = trocaTodasPosicoes(solucao);
		solucao = Vizinhanca3_Or_OPT(solucao);
		solucao = trocaTodasPosicoes(solucao);
		solucao = Swap_Move(solucao);
		solucao = trocaTodasPosicoes(solucao);
		solucao = Vizinhanca2_OPT(solucao);
		solucao = trocaTodasPosicoes(solucao);
		
	}
	
	public boolean verificaElemVetor(int elem){
		for(int i = 0; i < N; i++){
			if(solucao[i] == elem){
				return true;
			}
		}
		return false;
	}
	
	public void printSolucao(){
		for(int i = 0; i < N; i++ ){
			System.out.print(solucao[i] + " ");
		}
	}
	
	public void printSolucaoVizinha(int[] vetor){
		for(int i = 0; i < N; i++ ){
			System.out.print(vetor[i] + " ");
		}
	}
	
	public int custoSolucao(){
		int custo = 0;
		
		for(int i = 0; i < N - 1; i++ ){
			int linha = solucao[i];
			int coluna = solucao[i+1];
			custo += matrizCustos.getElemPosIJ(linha, coluna);
			//System.out.println("Linha " + linha  + " Coluna: " + coluna + " Custo " + matrizCustos.getElemPosIJ(linha, coluna) );
		}
		
		custo += matrizCustos.getElemPosIJ(solucao[N-1], solucao[0]);
		//System.out.println("Linha " + solucao[N-1]  + " Coluna: " + solucao[0] + " Custo " + matrizCustos.getElemPosIJ(solucao[N-1], solucao[0]) );
		return custo;
		
	}
	
	public int custoSolucaoVizinha(int[] vetor){
		int custo = 0;
		
		for(int i = 0; i < N - 1; i++ ){
			int linha = vetor[i];
			int coluna = vetor[i+1];
			custo += matrizCustos.getElemPosIJ(linha, coluna);
		}
		
		custo += matrizCustos.getElemPosIJ(vetor[N-1], vetor[0]);
		
		return custo;
		
	}
	
	public void validarSolucao(String tipo, int[] solucao){
		for (int i = 0; i < N - 1; i++) {
			for (int j = i + 1; j < N; j++) {
				if(solucao[i] == solucao[j]){
					System.out.println("ERRO " + tipo + " i: " + solucao[i] + " j: " +  solucao[j]);
					printSolucao();
				}
			}
		}
	}
	
	public int[] Exchange(int[] vetor){
		int pos = gerador.nextInt(N-3);
		pos = (pos <= 2) ? 3 : pos;
		
		//2 3 4 5 6 7 8 1
		//2 7 8 5 6 3 4 1 
		
		int aux = vetor[pos-2];
		vetor[pos-2] = vetor[pos+2];
		vetor[pos+2] = aux;
		
		aux = vetor[pos-1];
		vetor[pos-1] = vetor[pos+1];
		vetor[pos+1] = aux;
		
		return vetor;
		
	}
	
	public int[] Troca2PosAleatoria(int[] vetor){
		int pos1 = gerador.nextInt(N-2) + 1;
		int pos2 = gerador.nextInt(N-2) + 1;
		while(pos1 == pos2){
			pos2 = gerador.nextInt(N-2) + 1;
		}
		
		int aux = vetor[pos1];
		vetor[pos1] = vetor[pos2];
		vetor[pos2] = aux;
		
		return vetor;
	}
	
	// Sorteia uma posição aleatoria no vetor para trocar
	public int[] Vizinhanca2_OPT(int[] vetor){//Modificada
		
		int pos = gerador.nextInt(N-2) + 1;
		int aux = 0;
		
		if(pos != N-1){
			aux = vetor[pos];
			vetor[pos] = vetor[pos+1];
			vetor[pos+1] = aux;
		}
		else{
			aux = vetor[pos];
			vetor[pos] = vetor[pos-1];
			vetor[pos-1] = aux;
		}
		return vetor;
	}
	
    public int[] VizinhancaOr_OPT(int[] vetor){
		
		int pos = gerador.nextInt(N-2) + 1;
		int aux = 0;
		
		if(pos >= N-2){
			aux = vetor[pos];
			vetor[pos] = vetor[pos-2];
			vetor[pos-2] = aux;
		}
		else{
			aux = vetor[pos];
			vetor[pos] = vetor[pos+2];
			vetor[pos+2] = aux;
		}
		return vetor;
	}
	
	// Sorteia uma posição aleatoria no vetor para trocar
		public int[] Vizinhanca3_Or_OPT(int[] vetor){//Modificada
			
			int pos = gerador.nextInt(N-1);
			pos = (pos == 0) ? 1 : pos;
			int aux = 0;
			
			if(pos == N-1){
				aux = vetor[N-1];
				vetor[N-1] = vetor[N-3];
				vetor[N-3] = aux;
				
				aux = vetor[N-2];
				vetor[N-4] = vetor[N-2];
				vetor[N-4] = aux;
			}
			else{
				if(pos + 2 < N-1){
					aux = vetor[pos];
					vetor[pos] = vetor[pos + 2];
					vetor[pos + 2] = aux;
					
					aux = vetor[pos + 1];
					vetor[pos + 1] = vetor[pos + 3];
					vetor[pos + 3] = aux;
				}
				else{
					aux = vetor[N-1];
					vetor[N-1] = vetor[pos - 2];
					vetor[pos - 2] = aux;
					
					aux = vetor[N-2];
					vetor[N-2] = vetor[N-4];
					vetor[N-4] = aux;
				}
			}
		
			return vetor;
	}
		
	public int[] VizinhancaTeste2(int[] vetor){
		int pos = gerador.nextInt(N-1);
		pos = (pos == 0) ? 1 : pos;
		int aux = 0;
		if(pos >= N-3){
			aux = vetor[N-1];
			vetor[N-1] = vetor[N-2];
			vetor[N-2] = aux;
			
			aux = vetor[N-3];
			vetor[N-3] = vetor[N-4];
			vetor[N-4] = aux;
		}
		else{
			aux = vetor[pos];
			vetor[pos] = vetor[pos + 1];
			vetor[pos + 1] = aux;
			
			aux = vetor[pos + 2];
			vetor[pos + 2] = vetor[pos + 3];
			vetor[pos + 3] = aux;
		}
		return vetor;
	}
		
	public int[] VizinhancaTeste1(int[] vetor){
		int pos = gerador.nextInt(N-1);
		pos = (pos == 0) ? 1 : pos;
		int aux = 0;
		int aux1;
		if(pos >= N-3){
			aux = vetor[N-1];
			aux1 = vetor[N-2];
			
			vetor[N-1] = vetor[N-4];
			vetor[N-2] = vetor[N-3];
			vetor[N-3] = aux;
			vetor[N-4] = aux1;
		}
		else{
			aux = vetor[pos];
			aux1 = vetor[pos+1];
			
			vetor[pos] = vetor[pos+2];
			vetor[pos+1] = vetor[pos+3];
			vetor[pos+2] = aux1;
			vetor[pos+3] = aux;
		}
		
		return vetor;
		
	}	
	
	public int[] trocaTodasPosicoes(int[] vetor){//Modificada
		
		int aux;
		for(int i = 1; i < N - 1; i += 2){
			aux = vetor[i];
			vetor[i] = vetor[i + 1];
			vetor[i + 1] = aux;
		}
		return vetor;
	}
	
	public int[] Swap_Move(int[] vetor){
	
		int pos = gerador.nextInt(N-2) + 1;
		int aux1 = 0;
		int aux2 = 0;
		int aux3 = 0;
		if(pos < N - 5){
			aux1 = vetor[pos];
			aux2 = vetor[pos + 1];
			aux3 = vetor[pos + 2];
			
			vetor[pos] = vetor[pos + 3];
			vetor[pos + 1] = vetor[pos + 4];
			vetor[pos + 2] = vetor[pos + 5];
			
			vetor[pos + 3] = aux1;
			vetor[pos + 4] = aux2;
			vetor[pos + 5] = aux3;
		}
		else{
			aux1 = vetor[N-1];
			aux2 = vetor[N-2];
			aux3 = vetor[N-3];
			
			vetor[N-1] = vetor[N-4];
			vetor[N-2] = vetor[N-5];
			vetor[N-3] = vetor[N-6];
			
			vetor[N-4] = aux1;
			vetor[N-5] = aux2;
			vetor[N-6] = aux3;
		}
		
		return vetor;
	}
	
	
	public int[] copiaVetorSolucao(){
		int[] vetor = new int[N]; 
		for(int i = 0; i < N; i++){
			vetor[i] = solucao[i];
		}
		return vetor;
	}
	
	public void copiaVetor(int[] vet1, int[] vet2){ 
		for(int i = 0; i < N; i++){
			vet2[i] = vet1[i];
		}
	}
	
	public void atualizaVetorSolucao(int[] vetor){
		for(int i = 0; i < N; i++){
			solucao[i] = vetor[i];
		}
	}
	
	public void vns(int source){
		gerarSolucaoInicial(source);
		System.out.println("Solucação Incial");
		printSolucao();
		System.out.println("");
		System.out.println("Custo Inicial: "+ custoSolucao());
		//System.out.println("");
		int k = 0;
		int[] solucaoVizinha = new int[N];
		for(int i = 0;i < 100000; i++){
			k = 1;
			while(k <= 9){
				int custoAtual = custoSolucao();
				if(k == 1){
					solucaoVizinha = Vizinhanca2_OPT(copiaVetorSolucao());
					//validarSolucao("Vizinhanca2_opt", solucaoVizinha);
					//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 1");
				}
				else if(k == 2){
					solucaoVizinha = VizinhancaOr_OPT(copiaVetorSolucao());
					//validarSolucao("VizinhancaOr_OPT", solucaoVizinha);
					//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 2");
				}
				else if(k == 3){
					solucaoVizinha = Troca2PosAleatoria(copiaVetorSolucao());
					//validarSolucao("Troca2PosAleatoria", solucaoVizinha);
					//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 3");
				}
				else if(k == 4){
					solucaoVizinha = Exchange(copiaVetorSolucao());
					//validarSolucao("Exchange", solucaoVizinha);
					//if(custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 4");
				}
				else if(k == 5){
					solucaoVizinha = Vizinhanca3_Or_OPT(copiaVetorSolucao());
					//validarSolucao("Vizinhanca3Or_OPT", solucaoVizinha);
					//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 5");
				}
				else if(k == 6){
					solucaoVizinha = VizinhancaTeste1(copiaVetorSolucao());
					//validarSolucao("Vizinhanca3Or_OPT", solucaoVizinha);
					//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 5");
				}
				else if(k == 7){
					solucaoVizinha = VizinhancaTeste2(copiaVetorSolucao());
					//validarSolucao("Vizinhanca3Or_OPT", solucaoVizinha);
					//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 5");
				}
				else if(k == 8){
					solucaoVizinha = Swap_Move(copiaVetorSolucao());
					//validarSolucao("Swap_Move", solucaoVizinha);
					//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 6");
				}
				else if(k == 9){
					solucaoVizinha = trocaTodasPosicoes(copiaVetorSolucao());
					//validarSolucao("trocaTodasPosicoes", solucaoVizinha);
					if(custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 9");
				}
				
				int novoCusto = custoSolucaoVizinha(solucaoVizinha);
				if(novoCusto < custoAtual){
					atualizaVetorSolucao(solucaoVizinha);
					k = 1;
				}
				else{
					k++;
				}
			}
		}
		
		System.out.println("");
		System.out.println("Solucação Final");
		this.printSolucao();
		System.out.println("");
		System.out.println("Custo Final: "+ this.custoSolucao());
	}
	
	/*
	
	public void vns(int source){
		gerarSolucaoInicial(source);
		System.out.println("Solucação Incial");
		printSolucao();
		System.out.println("");
		System.out.println("Custo Inicial: "+ custoSolucao());
		//System.out.println("");
		int k = 0;
		int[] solucaoVizinha = new int[N];
		int[] solucaoAux = new int[N];
		
		for(int i = 0;i < 1000000; i++){
			k = 1;
			while(k <= 9){
				int custoAtual = custoSolucao();
				if(k == 1){
					solucaoVizinha = Vizinhanca2_OPT(copiaVetorSolucao());
					//validarSolucao("Vizinhanca2_opt", solucaoVizinha);
					//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 1");
				}
				else if(k == 2){
					solucaoVizinha = VizinhancaOr_OPT(copiaVetorSolucao());
					//validarSolucao("VizinhancaOr_OPT", solucaoVizinha);
					//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 2");
				}
				else if(k == 3){
					solucaoVizinha = Troca2PosAleatoria(copiaVetorSolucao());
					//validarSolucao("Troca2PosAleatoria", solucaoVizinha);
					//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 3");
				}
				else if(k == 4){
					solucaoVizinha = Exchange(copiaVetorSolucao());
					//validarSolucao("Exchange", solucaoVizinha);
					//if(custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 4");
				}
				else if(k == 5){
					solucaoVizinha = Vizinhanca3_Or_OPT(copiaVetorSolucao());
					//validarSolucao("Vizinhanca3Or_OPT", solucaoVizinha);
					//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 5");
				}
				else if(k == 6){
					solucaoVizinha = VizinhancaTeste1(copiaVetorSolucao());
					//validarSolucao("Vizinhanca3Or_OPT", solucaoVizinha);
					//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 5");
				}
				else if(k == 7){
					solucaoVizinha = VizinhancaTeste2(copiaVetorSolucao());
					//validarSolucao("Vizinhanca3Or_OPT", solucaoVizinha);
					//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 5");
				}
				else if(k == 8){
					solucaoVizinha = Swap_Move(copiaVetorSolucao());
					//validarSolucao("Swap_Move", solucaoVizinha);
					//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 6");
				}
				else if(k == 9){
					solucaoVizinha = trocaTodasPosicoes(copiaVetorSolucao());
					//validarSolucao("trocaTodasPosicoes", solucaoVizinha);
					//if(custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 9");
				}
				
				int m = 1;
				while(m <= 7){
					if(m == 1){
						solucaoAux = Vizinhanca2_OPT(solucaoVizinha);
						//validarSolucao("Vizinhanca2_opt", solucaoVizinha);
						//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 1");
					}
					else if(m == 2){
						solucaoAux = VizinhancaOr_OPT(solucaoVizinha);
						//validarSolucao("VizinhancaOr_OPT", solucaoVizinha);
						//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 2");
					}
					else if(m == 3){
						solucaoAux = Troca2PosAleatoria(solucaoVizinha);
						//validarSolucao("Troca2PosAleatoria", solucaoVizinha);
						//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 3");
					}
					else if(m == 4){
						solucaoAux = Exchange(solucaoVizinha);
						//validarSolucao("Exchange", solucaoVizinha);
						//if(custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 4");
					}
					else if(m == 5){
						solucaoAux = Vizinhanca3_Or_OPT(solucaoVizinha);
						//validarSolucao("Vizinhanca3Or_OPT", solucaoVizinha);
						//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 5");
					}
					
					else if(m == 6){
						solucaoAux = Swap_Move(solucaoVizinha);
						//validarSolucao("Swap_Move", solucaoVizinha);
						//if(this.custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 6");
					}
					else if(m == 7){
						solucaoAux = trocaTodasPosicoes(solucaoVizinha);
						//validarSolucao("trocaTodasPosicoes", solucaoVizinha);
						//if(custoSolucaoVizinha(solucaoVizinha) <= custoAtual) System.out.println("Troca 9");
					}
					int custoSolucaoViz = custoSolucaoVizinha(solucaoVizinha);
					int custoSolucaoAux = custoSolucaoVizinha(solucaoAux);
					if(custoSolucaoAux < custoSolucaoViz){
						copiaVetor(solucaoAux, solucaoVizinha);
						m = 1;
					}
					else{
						m++;
					}
					
				}
				
				int custoSolucaoViz = custoSolucaoVizinha(solucaoVizinha);
				if(custoSolucaoViz < custoAtual){
					atualizaVetorSolucao(solucaoVizinha);
					k = 1;
				}
				else{
					k++;
				}
			}
		}
		
		System.out.println("");
		System.out.println("Solucação Final");
		this.printSolucao();
		System.out.println("");
		System.out.println("Custo Final: "+ custoSolucao());
	}*/
}
