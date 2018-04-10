
package Utils;

import java.util.ArrayList;
import java.util.HashMap;
import org.davidmoten.hilbert.HilbertCurve;
import org.davidmoten.hilbert.SmallHilbertCurve;
/**
 *
 * @author RICARDO
 */
public class MatrizHilbert {
    public int ordem;
    public int dimensao;
    private SmallHilbertCurve hilbertCurve;
    public ArrayList<Celula> listaCelulas;
    private static final HashMap<Integer, Integer> mapOrdemMatrizHilbert;
    static
    {
        mapOrdemMatrizHilbert = new HashMap<Integer, Integer>();
        mapOrdemMatrizHilbert.put(3, 8);
        mapOrdemMatrizHilbert.put(4, 16);
        mapOrdemMatrizHilbert.put(5, 32);
        mapOrdemMatrizHilbert.put(6, 64);
        mapOrdemMatrizHilbert.put(7, 128);
        mapOrdemMatrizHilbert.put(8, 256);
        mapOrdemMatrizHilbert.put(9, 512);
        mapOrdemMatrizHilbert.put(10, 1024);
    }

    public MatrizHilbert(int ordem) {
        hilbertCurve = HilbertCurve.small().bits(ordem).dimensions(2);
        listaCelulas = new ArrayList<>();
        this.ordem = ordem;
        inicializarMatriz();
    }
    
    private void inicializarMatriz(){
        int dimensaoMatriz = mapOrdemMatrizHilbert.get(ordem);
        this.dimensao = dimensaoMatriz;
        //Criando a matriz
        for(int i = 0; i < dimensaoMatriz ; i++){
            for(int j = 0; j < dimensaoMatriz; j++){
                int index = (int) hilbertCurve.index(j, i);
                //System.out.println("C: " + j + " L: " + i + ": " + index);
                Celula cell = addCell2(j,i);
                cell.setIndexFractal(index);
            }  
        } 
    }
    
    public int getIndexPos(int l, int c){
        return (int) hilbertCurve.index(c, l); 
    }
    
    public void printMapHilbertCurve(){
        listaCelulas.forEach((cell) -> {
            int l = cell.getLinha();
            int c = cell.getColuna();
            int index = (int) hilbertCurve.index(c,l);
            System.out.println("L: " + l + " C: " + c + ": " + index);
        });
    }
    
    public void printDimensionCell(int l, int c){
        Celula cell =  getCellPos(l, c);
        if(cell != null){
            System.out.println("L: " + l + " C: " + c );
            System.out.println("posInicX: " + cell.getPosInicX() + " posFimX: " + cell.getPosFimX() + " posInicY: " + cell.getPosInicY() + " posFimY: " + cell.getPosFimY());
        }
    }
    
       public void printDimensionCellsMat(){
         for(int i = 0; i < dimensao ; i++){
            for(int j = 0; j < dimensao; j++){
                printDimensionCell(i, j);
            }
         }
    }
    
    public Celula addCell(int l, int c, int indexFractal){
        Celula cell = new Celula(l, c, indexFractal);
        listaCelulas.add(cell);
        return cell;
    }
    
    public Celula addCell2(int c, int l){
        Celula cell = new Celula(c, l);
        listaCelulas.add(cell);
        return cell;
    }
    
    public Celula getCellPos(int l, int c){
        for(Celula cell: listaCelulas){
            if(cell.linha == l && cell.coluna == c){
                return cell;
            }
        }
        return null;
    }
    
    public void setIndexPos(int l, int c, int indexFractal){
        if(getCellPos(l, c) != null){
           Celula cell = getCellPos(l, c);
           cell.setIndexFractal(indexFractal);
        }
    }

    public int getDimensao() {
        return dimensao;
    }
    
    
}
