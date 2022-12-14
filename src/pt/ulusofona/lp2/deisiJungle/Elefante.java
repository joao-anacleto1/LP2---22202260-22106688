package pt.ulusofona.lp2.deisiJungle;

class Elefante extends Especie {

    String nome = "Elefante";
    char identificador = 'E';
    String imagem = "elephant.png";
    int energiaInicial = 180;
    int consumoEnergia = 4;
    int ganhoEnergiaEmDescanso = 10;
    int velocidadeMinima = 1;
    int velocidadeMaxima = 6;

    public Elefante() {}

    @Override
    public boolean isHerbivoro() {
        return true;
    }

    @Override
    public boolean isCarnivoro() {
        return false;
    }

    @Override
    public boolean isOmnivoro() {
        return false;
    }

}
