package ru.mirea.golubev.pkmn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CardImport {


    public static Card importCard(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));


        if (lines.size() < 12) {
            throw new IllegalArgumentException("Недостаточное количество строк в файле: " + filePath);
        }

        PokemonStage stage = PokemonStage.valueOf(lines.get(0));
        String name = lines.get(1);
        int hp = Integer.parseInt(lines.get(2));
        EnergyType type = EnergyType.valueOf(lines.get(3));


        Card evolvesFrom = null;
        if (!lines.get(4).equals("-")) {
            evolvesFrom = importCard(lines.get(4));
        }

        List<AttackSkill> attackSkills = new ArrayList<>();
        for (String attackData : lines.get(5).split(",")) {
            String[] parts = attackData.trim().split("/");
            attackSkills.add(new AttackSkill(parts[1].trim(), "", parts[0].trim(), Integer.parseInt(parts[2].trim())));
        }

        EnergyType weakness = EnergyType.valueOf(lines.get(6));
        EnergyType resistance = EnergyType.valueOf(lines.get(7));
        String retreatCost = lines.get(8);
        String gameSet = lines.get(9);
        char regulationMark = lines.get(10).charAt(0);


        String[] ownerData = lines.get(11).split("/");
        if (ownerData.length == 4) {
            Student owner = new Student(ownerData[1].trim(), ownerData[0].trim(), ownerData[2].trim(), ownerData[3].trim());
            return new Card(stage, name, hp, type, evolvesFrom, attackSkills, weakness, resistance, retreatCost, gameSet, regulationMark, owner);
        } else {
            throw new IllegalArgumentException("Неверный формат данных о владельце карты: " + lines.get(11));
        }
    }
}