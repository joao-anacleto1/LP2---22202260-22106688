package pt.ulusofona.lp2.deisiJungle;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Especie {

    protected String nome;
    protected char identificador;
    protected String imagem;
    protected int energiaInicial;
    protected int consumoEnergia;
    protected int ganhoEnergiaEmDescanso;
    protected int velocidadeMinima;
    protected int velocidadeMaxima;


    Especie() {}

    String buscarNome() {
        return this.nome;
    }

    char buscarIdentificador() {
        return this.identificador;
    }

    String buscarImagem() {
        return this.imagem;
    }

    int buscarEnergiaInicial(){return this.energiaInicial;}

    int buscarConsumoEnergia(){return this.consumoEnergia;}

    int buscarGanhoEnergiaEmDescanso(){return this.ganhoEnergiaEmDescanso;}

    int buscarVelocidadeMinima(){
        return this.velocidadeMinima;
    }

    int buscarVelocidadeMaxima(){
        return this.velocidadeMaxima;
    }

    public String[] buscaInfo(){

        String[] informacoesEspecies = new String[7];

        informacoesEspecies[0] = "" + this.identificador;
        informacoesEspecies[1] = nome;
        informacoesEspecies[2] = imagem;
        informacoesEspecies[3] = "" + energiaInicial;
        informacoesEspecies[4] = "" +consumoEnergia;
        informacoesEspecies[5] = "" + ganhoEnergiaEmDescanso;
        informacoesEspecies[6] = "" + velocidadeMinima + ".." + velocidadeMaxima;

        return informacoesEspecies;


    }

    public abstract boolean isHerbivoro();

    public abstract boolean isCarnivoro();

    public abstract boolean isOmnivoro();
}
