package pt.ulusofona.lp2.deisiJungle;

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

    public abstract boolean isHerbivoro();

    public abstract boolean isCarnivoro();

    public abstract boolean isOmnivoro();
}
