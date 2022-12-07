package pt.ulusofona.lp2.deisiJungle;

public class Especie {

    String nome;
    char identificador;
    String imagem;
    int energiaInicial;
    int consumoEnergia;
    int ganhoEnergiaEmDescanso;

    int velocidadeMinima;
    int velocidadeMaxima;



    public Especie(String nome, char identificador, String imagem, int energiaInicial,int consumoEnergia
            , int ganhoEnergiaEmDescanso,int velocidadeMinima,int velocidadeMaxima) {

        this.nome = nome;
        this.identificador = identificador;
        this.imagem = imagem;
        this.energiaInicial = energiaInicial;
        this.consumoEnergia = consumoEnergia;
        this.ganhoEnergiaEmDescanso = ganhoEnergiaEmDescanso;
        this.velocidadeMinima = velocidadeMinima;
        this.velocidadeMaxima = velocidadeMaxima;

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

    int buscarVelocidadeMinima(){
        return this.velocidadeMinima;
    }

    int buscarVelocidadeMaxima(){
        return this.velocidadeMaxima;
    }

}
