package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;

public class GameManager {

    Especie elefante = new Especie("Elefante", "E", "elephant.png");
    Especie leao = new Especie("Leão", "L", "lion.png");
    Especie tartaruga = new Especie("Tartaruga", "T", "turtle.png");
    Especie passaro = new Especie("Pássaro", "P", "bird.png");
    Especie tarzan = new Especie("Tarzan", "Z", "tarzan.png");

    ArrayList<Especie> especies = new ArrayList<>() {
        {
            add(elefante);
            add(leao);
            add(tartaruga);
            add(passaro);
            add(tarzan);
        }
    };


    public GameManager() {
    }

    public String[][] getSpecies() {

        String[][] specie = new String[especies.size()][];

        for (int i = 0; i < especies.size(); i++) {
            specie[i] = new String[]{
                    especies.get(i).buscarIdentificador(),
                    especies.get(i).buscarNome(),
                    especies.get(i).buscarImagem()};
        }
        return specie;
    }
}
