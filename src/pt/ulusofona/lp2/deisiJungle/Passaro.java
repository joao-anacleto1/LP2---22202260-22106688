package pt.ulusofona.lp2.deisiJungle;

class Passaro extends Especie {

    String nome = "Pássaro";
    char identificador = 'P';
    String imagem = "bird.png";
    int energiaInicial = 70;
    int consumoEnergia = 4;
    int ganhoEnergiaEmDescanso = 50;
    int velocidadeMinima = 5;
    int velocidadeMaxima = 6;

    public Passaro() {}

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
