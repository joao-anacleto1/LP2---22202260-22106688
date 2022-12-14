package pt.ulusofona.lp2.deisiJungle;

class Tarzan extends Especie {

    String nome = "Tarzan";
    char identificador = 'Z';
    String imagem = "tarzan.png";
    int energiaInicial = 70;
    int consumoEnergia = 2;
    int ganhoEnergiaEmDescanso = 20;
    int velocidadeMinima = 1;
    int velocidadeMaxima = 6;

    public Tarzan() {}

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