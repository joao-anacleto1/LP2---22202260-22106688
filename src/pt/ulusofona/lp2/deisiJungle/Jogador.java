package pt.ulusofona.lp2.deisiJungle;

public class Jogador {

    int id;
    int energia;
    String nome;
    Especie especie;
    int posicaoAtual;

    public Jogador(int id, String nome, Especie especie,int posicaoAtual) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.energia = especie.buscarEnergiaInicial();
        this.posicaoAtual = posicaoAtual;
    }

    boolean validarNome() {
        if (nome == null) {
            return false;
        }
        return nome.length() > 0;
    }

    int buscarId() {
        return id;
    }

    int buscarPosicaoAtual() {
        return posicaoAtual;
    }

    int buscarEnergia() {
        return energia;
    }

    void removeEnergia(int valor) {
        this.energia -= valor;
    }

    void atualizarPosicao(int casaPretendida) {
        this.posicaoAtual = casaPretendida;
    }

    String buscarNomeJogador(){
        return nome;
    }

    String buscarNomeEspecie(){
        return especie.buscarNome();
    }

    void ingereErva(){

        if(especie.isHerbivoro() || especie.isOmnivoro()){
            this.energia += 20;
        }else {
            this.energia -= 20;
        }
    }

    void ingereAgua(){
        if(especie.isCarnivoro() || especie.isHerbivoro()){
            this.energia += 15;
        }else{
            this.energia += ((this.energia * 0.20)) + this.energia;
        }
    }


}
