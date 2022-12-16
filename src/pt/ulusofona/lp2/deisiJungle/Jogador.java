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

        if(especie.eHerbivoro() || especie.eOmnivoro()){
            this.energia += 20;
        }else {
            this.energia -= 20;
        }
    }

    void ingereAgua(){
        if(especie.eCarnivoro() || especie.eHerbivoro()){
            this.energia += 15;
        }else{
            this.energia += ((this.energia * 0.20)) + this.energia;
        }
    }

    void ingereCarne(int turno){

        Carne carne = new Carne();

        if (!carne.eToxica(turno)){
            if (especie.eCarnivoro() || especie.eOmnivoro()){
                this.energia += 50;
            }
            this.energia -= (this.energia/2); // carne toxica
        }
    }

    void ingereCogumelosMagicos(){

        if (especie.eCarnivoro() || especie.eHerbivoro() || especie.eOmnivoro()){



        }

    }

    void ingereCachoDeBananas(CachoDeBananas cachoDeBananas){

        for(int i = 3 ; i >= cachoDeBananas.bananasNoCacho ; i--){

            if (especie.eCarnivoro() || especie.eHerbivoro() || especie.eOmnivoro()){
                this.energia += 40;

                if (cachoDeBananas.buscarBananasNoCacho() == 2){
                    this.energia -= 40;
                }
                if (cachoDeBananas.buscarBananasNoCacho() == 3){
                    this.energia -= 40;
                }
            }
        }
    }
}
/*
        if (especie.eCarnivoro() || especie.eHerbivoro() || especie.eOmnivoro()){

           if (cachoDeBananas.buscarBananasNoCacho() >= 1 || cachoDeBananas.buscarBananasNoCacho() <= 3){
               this.energia += 40;

               if (cachoDeBananas.buscarBananasNoCacho() == 2){
                   this.energia -= 40;
               }
               if (cachoDeBananas.buscarBananasNoCacho() == 3){
                   this.energia -= 40;
               }
           }
        }

 */


