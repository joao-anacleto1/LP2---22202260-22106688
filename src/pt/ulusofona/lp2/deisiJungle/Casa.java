package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;

public class Casa {

    int indexCasa;
    String tipoCasa;
    ArrayList<Jogador> jogadores = new ArrayList<>();

    public Casa(int indexCasa, String tipoCasa) {
        this.indexCasa = indexCasa;
        this.tipoCasa = tipoCasa;
    }

    boolean adicionarJogador(Jogador j) {
        if (jogadores.contains(j)) {
            return false;
        } else {
            jogadores.add(j);
            return true;
        }
    }

    boolean removerJogador(Jogador j) {
        if (jogadores.contains(j)) {
            return false;
        } else {
            jogadores.remove(j);
            return true;
        }
    }

    int[] buscaJogadoresIds() {
        int[] ids = new int[jogadores.size()];
        for (int l = 0; l < jogadores.size(); l++) {
            ids[l] = jogadores.get(l).id;
        }
        return ids;
    }


}
