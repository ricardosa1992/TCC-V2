
public class Main {

	public static void main(String[] args) {
		MatrizCustos matriz = new MatrizCustos();
		matriz.lerPontos("entrada.txt");
		matriz.calcularMatrizDistancia();
		//matriz.printMatriz();
		//matriz.printPosIguais();
		matriz.gravarMatriz("matriz.csv");

	}

}
