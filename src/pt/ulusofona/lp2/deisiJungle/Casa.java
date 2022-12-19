package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;

public class Casa {

    int indexCasa;
    String tipoCasa;
    String imagemCasa;
    ArrayList<Jogador> jogadores = new ArrayList<>();
    Alimento alimento;

    public Casa(int indexCasa, String tipoCasa, String imagemCasa, Alimento alimento) {
        this.indexCasa = indexCasa;
        this.tipoCasa = tipoCasa;
        this.imagemCasa = imagemCasa;
        this.alimento = alimento;
    }

    public void receberAlimento(Alimento a){
        if(a != null){
            this.alimento = a;
            this.tipoCasa = a.nomeAlimento;
            this.imagemCasa = a.imagem;
        }
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

    String buscaIdsString() {
        String resultado = "";
        for (int j = 0; j < jogadores.size(); j++) {
            if (j == jogadores.size() - 1) {
                resultado += "" + jogadores.get(j).buscarId();
            } else {
                resultado += "" + jogadores.get(j).buscarId() + ",";
            }
        }
        return resultado;
    }

    boolean casaVazia() {
        return jogadores.isEmpty();
    }

    ArrayList<Jogador> ordernarIds() {
        ArrayList<Jogador> jogadoresIds;
        jogadoresIds = jogadores;
        int n = jogadoresIds.size();
        Jogador temp = null;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (jogadoresIds.get(j - 1).buscarId() > jogadoresIds.get(j).buscarId()) {
                    //swap elements
                    temp = jogadoresIds.get(j - 1);
                    jogadoresIds.set(j - 1, jogadoresIds.get(j));
                    jogadoresIds.set(j, temp);
                }

            }
        }

        return jogadoresIds;
    }

}
