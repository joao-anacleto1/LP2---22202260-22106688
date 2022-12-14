package pt.ulusofona.lp2.deisiJungle;

class Leao extends Especie {

    String nome = "Leão";
    char identificador = 'L';
    String imagem = "lion.png";
    int energiaInicial = 80;
    int consumoEnergia = 2;
    int ganhoEnergiaEmDescanso = 10;
    int velocidadeMinima = 4;
    int velocidadeMaxima = 6;


    public Leao() {}

    @Override
    public boolean isHerbivoro() {
       return false;
    }

    @Override
    public boolean isCarnivoro() {
       return true;
    }

    @Override
    public boolean isOmnivoro() {
       return false;
    }
}
