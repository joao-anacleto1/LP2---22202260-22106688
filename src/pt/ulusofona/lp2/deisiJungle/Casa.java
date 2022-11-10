package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;

public class Casa {

    int indexCasa;
    String tipoCasa;
    String imagemCasa;
    ArrayList<Jogador> jogadores = new ArrayList<>();

    public Casa(int indexCasa, String tipoCasa, String imagemCasa) {
        this.indexCasa = indexCasa;
        this.tipoCasa = tipoCasa;
        this.imagemCasa = imagemCasa;
    }

    boolean adicionarJogador(Jogador j) {
        for (Jogador jogador : this.jogadores) {
            if (jogador.buscarId() == j.buscarId()) {
                return false;
            }
        }
        this.jogadores.add(j);
        return true;
    }


    boolean removerJogador(Jogador j) {
        for (int i = 0; i < this.jogadores.size(); i++) {
            if (this.jogadores.get(i).buscarId() == j.buscarId()) {
                this.jogadores.remove(i);
                return true;
            }
        }
        return false;
    }

    int[] buscaJogadoresIds() {
        int[] ids = new int[jogadores.size()];
        for (int l = 0; l < jogadores.size(); l++) {
            ids[l] = jogadores.get(l).id;
        }
        return ids;
    }

    int buscarIndexCasa() {
        return indexCasa;
    }

    String buscarTipoCasa() {
        return tipoCasa;
    }

    String buscarImagemCasa() {
        return imagemCasa;
    }

    ArrayList<Integer> buscaIds() {
        ArrayList<Integer> guardarIds = new ArrayList<>();
        for (int k = 0; k < jogadores.size(); k++) {
            guardarIds.add(jogadores.get(k).buscarId());
        }
        return guardarIds;
    }


}
