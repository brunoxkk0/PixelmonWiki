package io.github.adainish.pixelmonwiki.util;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.drops.ItemWithChance;
import com.pixelmonmod.pixelmon.api.pokemon.drops.PokemonDropInformation;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.entities.npcs.registry.DropItemRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PokemonJsonHelper {
    public static List <String> getBaseStatsFromSpecies(Pokemon pokemon)
    {
        List<String> strings = new ArrayList <>();
        for (BattleStatsType bt:BattleStatsType.EV_IV_STATS) {
            String st = "%bt% :".replace("%bt%", TranslateUtil.getTranslatedString(bt.getTranslationKey())) + pokemon.getForm().getBattleStats().getStat(bt);
            strings.add(st);
        }

        return strings;
    }

    public static List<ItemWithChance> returnDropInfoFromSpecies(Species species)
    {
        List<ItemWithChance> itemWithChances = new ArrayList <>();
        Set <PokemonDropInformation> dropList = DropItemRegistry.pokemonDrops.get(species);
        for (PokemonDropInformation pokemonDropInformation:dropList) {
            itemWithChances.addAll(pokemonDropInformation.getDrops());
        }
        return itemWithChances;
    }
}
