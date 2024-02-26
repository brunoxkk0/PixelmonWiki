package io.github.adainish.pixelmonwiki.util;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.api.pokedex.PlayerPokedex;
import com.pixelmonmod.pixelmon.api.pokemon.Element;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonFactory;
import com.pixelmonmod.pixelmon.api.pokemon.ability.Ability;
import com.pixelmonmod.pixelmon.api.pokemon.drops.ItemWithChance;
import com.pixelmonmod.pixelmon.api.pokemon.drops.PokemonDropInformation;
import com.pixelmonmod.pixelmon.api.pokemon.egg.EggGroup;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.pokemon.stats.BattleStatsType;
import com.pixelmonmod.pixelmon.api.pokemon.stats.evolution.Evolution;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.spawning.SpawnInfo;
import com.pixelmonmod.pixelmon.api.spawning.SpawnSet;
import com.pixelmonmod.pixelmon.api.util.helpers.BiomeHelper;
import com.pixelmonmod.pixelmon.api.world.WeatherType;
import com.pixelmonmod.pixelmon.api.world.WorldTime;
import com.pixelmonmod.pixelmon.battles.attacks.Effectiveness;
import com.pixelmonmod.pixelmon.battles.attacks.ImmutableAttack;
import com.pixelmonmod.pixelmon.entities.SpawnLocationType;
import com.pixelmonmod.pixelmon.entities.npcs.registry.DropItemRegistry;
import com.pixelmonmod.pixelmon.enums.technicalmoves.Gen8TechnicalRecords;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.*;

public class InfoUtil {

    public static Pokemon returnPokemonFromDexNumber(int number) {
        Species species = PixelmonSpecies.MISSINGNO.getValueUnsafe();
        if (PixelmonSpecies.fromDex(number).isPresent()) {
            species = PixelmonSpecies.fromDex(number).get();
        }
        return PokemonFactory.create(species);
    }

    public static String returnPokemonNameString(Pokemon pokemon) {
        String translatedString = TranslateUtil.getTranslatedString(pokemon.getTranslationKey());
        return "&b[%dex%] &7".replace("%dex%", String.valueOf(pokemon.getSpecies().getDex())) + translatedString;
    }

    public static ITextComponent returnPokemonNameComponent(Pokemon pokemon) {
        String translatedString = TranslateUtil.getTranslatedString(pokemon.getTranslationKey());
        return new StringTextComponent("&b[%dex%] &7".replace("%dex%", String.valueOf(pokemon.getSpecies().getDex())) + translatedString);
    }

    public static ITextComponent returnPokemonDescription(Pokemon pokemon, ServerPlayerEntity player) {
        String translatedString = TranslateUtil.getTranslatedString(pokemon.getSpecies().getDescTranslationKey());
        return new StringTextComponent("&7" + translatedString);
    }

    public static List<ITextComponent> returnPokemonEggMoves(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();

        for (ImmutableAttack attack : pokemon.getForm().getMoves().getEggMoves()) {
            String translatedString = TranslateUtil.getTranslatedString(attack.getTranslationKey());
            StringTextComponent translationTextComponent = new StringTextComponent("&7" + translatedString);
            iTextComponents.add(translationTextComponent);
        }

        return iTextComponents;
    }

    public static List<ITextComponent> returnPokemonHMMoves(Pokemon pokemon) {

        List<ITextComponent> iTextComponents = new ArrayList<>();

        try{
            for (ImmutableAttack attack : pokemon.getForm().getMoves().getHMMoves()) {
                String translatedString = TranslateUtil.getTranslatedString(attack.getTranslationKey());
                StringTextComponent translationTextComponent = new StringTextComponent("&7" + translatedString);
                iTextComponents.add(translationTextComponent);
            }
        }catch (Exception ignored){}

        return iTextComponents;
    }

    public static List<ITextComponent> returnPokemonTMMoves(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();

        try{
            for (ImmutableAttack attack : pokemon.getForm().getMoves().getTMMoves()) {
                String translatedString = TranslateUtil.getTranslatedString(attack.getTranslationKey());
                StringTextComponent translationTextComponent = new StringTextComponent("&7" + translatedString);
                iTextComponents.add(translationTextComponent);
            }
        }catch (Exception ignored){}

        return iTextComponents;
    }

    public static List<ITextComponent> returnPokemonLevelUpMoves(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();

        try{
            for (ImmutableAttack attack : pokemon.getForm().getMoves().getAllLevelUpMoves()) {
                String translatedString = TranslateUtil.getTranslatedString(attack.getTranslationKey());
                StringTextComponent translationTextComponent = new StringTextComponent("&7" + translatedString);
                iTextComponents.add(translationTextComponent);
            }
        }catch (Exception ignored){}

        return iTextComponents;
    }

    public static List<ITextComponent> returnEvolutions(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();

        try{
            for (Species species : pokemon.getForm().getPreEvolutions()) {
                String translatedString = TranslateUtil.getTranslatedString(species.getTranslationKey());
                StringTextComponent translationTextComponent = new StringTextComponent("&7" + translatedString);
                iTextComponents.add(translationTextComponent);
            }

            for (Evolution evolution : pokemon.getForm().getEvolutions()) {
                String translatedString = TranslateUtil.getTranslatedString(evolution.to.create().getTranslationKey());
                StringTextComponent translationTextComponent = new StringTextComponent("&7" + translatedString);
                iTextComponents.add(translationTextComponent);
            }
        }catch (Exception ignored){}

        return iTextComponents;
    }

    public static List<ITextComponent> returnAbilities(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();
        for (Ability ability : pokemon.getForm().getAbilities().getAll()) {
            String translatedString = TranslateUtil.getTranslatedString(ability.getTranslationKey());
            StringTextComponent translationTextComponent = new StringTextComponent("&7" + translatedString);
            iTextComponents.add(translationTextComponent);
        }
        return iTextComponents;
    }

    public static List<ITextComponent> returnEVYield(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();
        for (BattleStatsType statType : BattleStatsType.EV_IV_STATS) {
            String colourPrefix = "&";
            switch (statType) {
                case HP:
                    colourPrefix += "a";
                    break;
                case SPECIAL_DEFENSE:
                    colourPrefix += "e";
                    break;
                case ATTACK:
                    colourPrefix += "4";
                    break;
                case SPECIAL_ATTACK:
                    colourPrefix += "5";
                    break;
                case DEFENSE:
                    colourPrefix += "6";
                    break;
                case SPEED:
                    colourPrefix += "b";
                    break;
                default:
                    colourPrefix += "d";
                    break;
            }
            String groupStat = TranslateUtil.getTranslatedString(statType.getTranslationKey());
            groupStat = colourPrefix + groupStat + " " + pokemon.getForm().getEVYields().getYield(statType);
            iTextComponents.add(new StringTextComponent(groupStat));
        }
        return iTextComponents;
    }

    public static String getColourFormatFromElement(Element element) {
        String typingPrefix = "&";
        switch (element) {
            case NORMAL: {
                typingPrefix += "f";
                break;
            }
            case FIRE:
                typingPrefix += "4";
                break;
            case WATER:
                typingPrefix += "1";
                break;
            case ELECTRIC:
                typingPrefix += "e";
                break;
            case GRASS:
                typingPrefix += "2";
                break;
            case ICE:
            case MYSTERY:
                typingPrefix += "b";
                break;
            case FIGHTING:
            case STEEL:
                typingPrefix += "6";
                break;
            case POISON:
            case GHOST:
                typingPrefix += "5";
                break;
            case GROUND:
            case ROCK:
                typingPrefix += "8";
                break;
            case FLYING:
            case DARK:
                typingPrefix += "7";
                break;
            case PSYCHIC:
            case FAIRY:
                typingPrefix += "d";
                break;
            case BUG:
                typingPrefix += "a";
                break;
            case DRAGON:
                typingPrefix += "9";
                break;
        }
        return typingPrefix;
    }

    public static List<ITextComponent> returnCatchRates(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();
        iTextComponents.add(new StringTextComponent("&7" + pokemon.getForm().getCatchRate()));
        return iTextComponents;
    }

    public static List<ITextComponent> returnEggGroups(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();
        iTextComponents.add(new StringTextComponent("&bEgg Groups:"));
        for (EggGroup group : pokemon.getForm().getEggGroups()) {
            String translatedString = TranslateUtil.getTranslatedString(group.getTranslationKey());
            StringTextComponent translationTextComponent = new StringTextComponent("&7" + translatedString);
            iTextComponents.add(translationTextComponent);
        }
        return iTextComponents;
    }

    public static List<ITextComponent> returnPokemonTypes(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();
        iTextComponents.add(new StringTextComponent("&eTypings:"));
        for (Element pokemonElement : pokemon.getForm().getTypes()) {
            String translatedString = TranslateUtil.getTranslatedString(pokemonElement.getTranslationKey());

            StringTextComponent translationTextComponent = new StringTextComponent(getColourFormatFromElement(pokemonElement) + translatedString);
            iTextComponents.add(translationTextComponent);
        }

        return iTextComponents;
    }

    public static List<ITextComponent> returnEffectiveness(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();
        List<String> addedList = new ArrayList<>();

        try{
            for (Element element : Element.values()) {
                for (Element pokemonElement : pokemon.getForm().getTypes()) {
                    Effectiveness effectiveness = Element.getEffectiveness(element, pokemonElement);
                    String text = TranslateUtil.getTranslatedString(element.getTranslationKey());
                    if (addedList.contains(text))
                        continue;
                    if (text.equalsIgnoreCase("type.mystery"))
                        continue;
                    StringTextComponent translatedEffectiveness = new StringTextComponent(getColourFormatFromElement(element) + text + " x" + effectiveness.value);
                    addedList.add(text);
                    iTextComponents.add(translatedEffectiveness);
                }
            }
        }catch (Exception ignored){}

        return iTextComponents;
    }

    public static List<ITextComponent> returnPokemonTransferMoves(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();

        try{
            for (ImmutableAttack attack : pokemon.getForm().getMoves().getTransferMoves()) {
                String translatedString = TranslateUtil.getTranslatedString(attack.getTranslationKey());
                StringTextComponent translationTextComponent = new StringTextComponent("&7" + translatedString);
                iTextComponents.add(translationTextComponent);
            }
        }catch (Exception ignored){}

        return iTextComponents;
    }

    public static List<ITextComponent> returnPokemonTutorMoves(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();

        try{
            for (ImmutableAttack attack : pokemon.getForm().getMoves().getTutorMoves()) {
                String translatedString = TranslateUtil.getTranslatedString(attack.getTranslationKey());
                StringTextComponent translationTextComponent = new StringTextComponent("&7" + translatedString);
                iTextComponents.add(translationTextComponent);
            }
        }catch (Exception ignored){}

        return iTextComponents;
    }

    public static List<ITextComponent> returnPokemonTRMoves(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();

        try{
            for (Gen8TechnicalRecords attack : pokemon.getForm().getMoves().getTRMoves()) {
                String translatedString = TranslateUtil.getTranslatedString(attack.getAttack().getTranslationKey());
                StringTextComponent translationTextComponent = new StringTextComponent("&7" + translatedString);
                iTextComponents.add(translationTextComponent);
            }
        }catch (Exception ignored){}

        return iTextComponents;
    }

    public static List<PlayerPokedex.PokedexSpawnData> returnDexSpawnData(Pokemon pokemon) {
        List<PlayerPokedex.PokedexSpawnData> spawnData = Lists.newArrayList();
        Species species = pokemon.getSpecies();
        List<SpawnSet> spawns = PixelmonSpecies.getLegendaries().stream().anyMatch((e) -> species.getDex() == e) ? PixelmonSpawning.legendaries : PixelmonSpawning.standard;
        if (species != null) {
            Iterator<SpawnSet> var6 = spawns.iterator();

            label85:
            while (true) {
                SpawnSet spawn;
                do {
                    if (!var6.hasNext()) {
                        break label85;
                    }

                    spawn = var6.next();
                } while (!spawn.id.equalsIgnoreCase(species.getName()));

                Iterator<SpawnInfo> var8 = spawn.spawnInfos.iterator();

                do {
                    SpawnInfo info;
                    do {
                        do {
                            do {
                                if (!var8.hasNext()) {
                                    continue label85;
                                }

                                info = var8.next();
                            } while (info == null);
                        } while (info.condition == null);
                    } while (info.condition.biomes == null);

                    PlayerPokedex.PokedexSpawnData data;
                    for (ResourceLocation resourceLocation : info.condition.biomes) {
                        data = new PlayerPokedex.PokedexSpawnData(BiomeHelper.getLocalizedBiomeName(resourceLocation).getString());
                        Iterator var13;
                        if (info.condition.times != null) {
                            var13 = info.condition.times.iterator();

                            if (var13.hasNext()) {
                                do {
                                    WorldTime time = (WorldTime) var13.next();
                                    data.addTime(time);
                                } while (var13.hasNext());
                            }
                        }

                        if (info.condition.cachedWeathers != null) {
                            var13 = info.condition.cachedWeathers.iterator();

                            while (var13.hasNext()) {
                                WeatherType weather = (WeatherType) var13.next();
                                data.addWeather(weather);
                            }
                        }

                        if (info.condition.maxY != null) {
                            data.setMaxY(info.condition.maxY);
                        }

                        if (info.condition.minY != null) {
                            data.setMinY(info.condition.minY);
                        }
                        spawnData.add(data);
                    }
                } while (true);
            }
        }
        return spawnData;
    }

    public static List<ITextComponent> returnPokemonDrops(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();
        List<ItemWithChance> itemWithChancesList = returnDropInfoFromSpecies(pokemon.getSpecies());
        if (itemWithChancesList.isEmpty()) {
            iTextComponents.add(new StringTextComponent("No drops available as data could not be loaded"));
            return iTextComponents;
        }
        for (ItemWithChance itemWithChance : itemWithChancesList) {
            String translatedString = TranslateUtil.getTranslatedString(itemWithChance.getItemStack().getTranslationKey());
            StringTextComponent translationTextComponent = new StringTextComponent("&7" + translatedString);
            iTextComponents.add(translationTextComponent);
        }

        return iTextComponents;
    }

    public static List<String> getBaseStatsFromSpecies(Pokemon pokemon) {
        List<String> strings = new ArrayList<>();
        for (BattleStatsType bt : BattleStatsType.EV_IV_STATS) {
            String colourPrefix = "&";
            switch (bt) {
                case HP:
                    colourPrefix += "a";
                    break;
                case SPECIAL_DEFENSE:
                    colourPrefix += "e";
                    break;
                case ATTACK:
                    colourPrefix += "4";
                    break;
                case SPECIAL_ATTACK:
                    colourPrefix += "5";
                    break;
                case DEFENSE:
                    colourPrefix += "6";
                    break;
                case SPEED:
                    colourPrefix += "b";
                    break;
                default:
                    colourPrefix += "d";
                    break;
            }
            String st = colourPrefix + "%bt%&7: ".replace("%bt%", TranslateUtil.getTranslatedString(bt.getTranslationKey())) + pokemon.getForm().getBattleStats().getStat(bt);
            strings.add(st);
        }

        return strings;
    }

    public static List<ItemWithChance> returnDropInfoFromSpecies(Species species) {

        List<ItemWithChance> itemWithChances = new ArrayList<>();

        Set<PokemonDropInformation> dropList = DropItemRegistry.pokemonDrops.getOrDefault(species, new HashSet<>());

        for (PokemonDropInformation pokemonDropInformation : dropList) {
            itemWithChances.addAll(pokemonDropInformation.getDrops());
        }

        return itemWithChances;
    }


    public static List<ITextComponent> returnPokemonBaseStats(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();
        getBaseStatsFromSpecies(pokemon).forEach((string) -> {
            iTextComponents.add(new StringTextComponent(string));
        });
        return iTextComponents;
    }

    public static List<ITextComponent> returnPokemonSpawnTimes(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();
        List<String> addedList = new ArrayList<>();
        List<PlayerPokedex.PokedexSpawnData> data = returnDexSpawnData(pokemon);
        for (PlayerPokedex.PokedexSpawnData spawnData : data) {
            for (WorldTime time : spawnData.getTimes()) {
                String text = TranslateUtil.getTranslatedString(time.getTranslationKey());
                if (addedList.contains(text))
                    continue;
                iTextComponents.add(new StringTextComponent("&7" + text));
                addedList.add(text);
            }

        }
        return iTextComponents;
    }

    public static List<ITextComponent> returnPokemonSpawnBiomes(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();
        List<String> addedList = new ArrayList<>();
        List<PlayerPokedex.PokedexSpawnData> data = returnDexSpawnData(pokemon);
        for (PlayerPokedex.PokedexSpawnData spawnData : data) {
            String text = spawnData.getBiome();
            if (addedList.contains(text))
                continue;
            iTextComponents.add(new StringTextComponent("&7" + text));
            addedList.add(text);
        }
        return iTextComponents;
    }

    public static List<ITextComponent> returnPokemonSpawnLocationTypes(Pokemon pokemon) {
        List<ITextComponent> iTextComponents = new ArrayList<>();

        for (SpawnLocationType locationType : pokemon.getForm().getSpawn().getSpawnLocations()) {
            iTextComponents.add(new StringTextComponent("&7" + locationType.name()));
        }

        return iTextComponents;
    }
}
