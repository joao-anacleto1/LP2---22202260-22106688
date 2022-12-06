package pt.ulusofona.lp2.deisiJungle;

import java.util.Random;

public class Especie {

    String nome;
    char identificador;
    String imagem;
    int energiaInicial;
    int consumoEnergia;
    int ganhoEnergiaEmDescanso;
    int velocidade;



    public Especie(String nome, char identificador, String imagem, int energiaInicial,int consumoEnergia
            , int ganhoEnergiaEmDescanso) {

        this.nome = nome;
        this.identificador = identificador;
        this.imagem = imagem;
        this.energiaInicial = energiaInicial;
        this.consumoEnergia = consumoEnergia;
        this.ganhoEnergiaEmDescanso = ganhoEnergiaEmDescanso;
    }

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

    int buscarVelocidade(){
        return this.velocidade;
        //Random ran = new Random();
        //return ran.nextInt(max) + min;
    }

}
