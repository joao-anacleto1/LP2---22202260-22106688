package pt.ulusofona.lp2.deisiJungle;

class Tartaruga extends Especie {

    String nome = "Tartaruga";
    char identificador = 'T';
    String imagem = "turtle.png";
    int energiaInicial = 150;
    int consumoEnergia = 1;
    int ganhoEnergiaEmDescanso = 5;
    int velocidadeMinima = 1;
    int velocidadeMaxima = 3;

    public Tartaruga() {}

    @Override
    public boolean isHerbivoro() {
        return false;
    }

    @Override
    public boolean isCarnivoro() {
        return false;
    }

    @Override
    public boolean isOmnivoro() {
        return true;
    }
}
