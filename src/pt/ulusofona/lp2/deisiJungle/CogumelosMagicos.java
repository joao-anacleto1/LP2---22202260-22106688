package pt.ulusofona.lp2.deisiJungle;

import java.util.Random;

class CogumelosMagicos extends Alimento {

    int nrCogumelo;

    public CogumelosMagicos(){
        this.identificadorAlimento = 'm';
        this.nomeAlimento = "Cogumelo Magico";
        this.imagem = "mushroom.png";
        Random r = new Random();
        int min = 10;
        int max = 50;
        this.nrCogumelo = r.nextInt(max - min) + min;
        this.tooltip = "Cogumelo Magico : +- " + nrCogumelo + "% energia";
    }

    int buscarNrCogumelo(){
        return this.nrCogumelo;
    }

    public void setNrCogumelo(int nrCogumelo) {
        this.nrCogumelo = nrCogumelo;
    }

    public void alteraCogumelos(int valor){
        this.nrCogumelo = valor;
    }
}