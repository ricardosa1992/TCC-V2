/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author PATRÍCIA E RICARDO
 */
public class Celula {
    public float dimensao;
    public float posInicX;
    public float posFimX;
    public float posInicY;
    public float posFimY;
    public int indexFractal;
    public int linha;
    public int coluna;

    public Celula(int l, int c,int indexFractal) {
        this.indexFractal = indexFractal;
        this.linha = l;
        this.coluna = c;
    }
    
    public Celula(int c, int l) {
        this.linha = l;
        this.coluna = c;
    }

    public float getDimensao() {
        return dimensao;
    }

    public void setDimensao(float dimensao) {
        this.dimensao = dimensao;
    }

    public float getPosInicX() {
        return posInicX;
    }

    public void setPosInicX(float posInicX) {
        this.posInicX = posInicX;
    }

    public float getPosFimX() {
        return posFimX;
    }

    public void setPosFimX(float posFimX) {
        this.posFimX = posFimX;
    }

    public float getPosInicY() {
        return posInicY;
    }

    public void setPosInicY(float posInicY) {
        this.posInicY = posInicY;
    }

    public float getPosFimY() {
        return posFimY;
    }

    public void setPosFimY(float posFimY) {
        this.posFimY = posFimY;
    }

    public int getIndexFractal() {
        return indexFractal;
    }

    public void setIndexFractal(int indexFractal) {
        this.indexFractal = indexFractal;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }
    
}
