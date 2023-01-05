package io.github.adainish.pixelmonwiki.gui;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.template.LineType;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonFactory;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.registries.PixelmonItems;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import io.github.adainish.pixelmonwiki.util.InfoUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class WikiGUI
{
    public static GooeyButton filler = GooeyButton.builder()
            .display(new ItemStack(Blocks.GRAY_STAINED_GLASS_PANE, 1))
            .build();

    public static Species getSafeSpeciesFromDex(int dex) {
        if (PixelmonSpecies.fromDex(dex).isPresent())
            return PixelmonSpecies.fromDex(dex).get();
        else return PixelmonSpecies.MISSINGNO.getValueUnsafe();
    }

    public static Pokemon getPreviousPokemon(Pokemon pokemon)
    {
        int dexNumber = pokemon.getSpecies().getDex();
        if (dexNumber == 1)
            return pokemon;
        else dexNumber--;
        return PokemonFactory.create(getSafeSpeciesFromDex(dexNumber));
    }

    public static Pokemon getNextPokemon(Pokemon pokemon)
    {
        int dexNumber = pokemon.getSpecies().getDex() + 1;
        return PokemonFactory.create(getSafeSpeciesFromDex(dexNumber));
    }

    public static List <Button> pokemonButtons(Pokemon pokemon, ServerPlayerEntity playerEntity)
    {
        List <Button> buttons = new ArrayList <>();


        Pokemon currentPokemon;
        List<String> description = new ArrayList <>();
        for (int i = 0; i < 3; i++) {
            if (i == 0)
            {

                currentPokemon = getPreviousPokemon(pokemon);
                description.add(InfoUtil.returnPokemonDescription(currentPokemon, playerEntity).getString());
                Pokemon finalCurrentPokemon = currentPokemon;
                GooeyButton button = GooeyButton.builder()
                        .display(SpriteItemHelper.getPhoto(currentPokemon))
                        .title(InfoUtil.returnPokemonNameString(currentPokemon))
                        .onClick(buttonAction -> {
                            UIManager.openUIForcefully(playerEntity, MainWikiPage(finalCurrentPokemon, playerEntity));
                        })
                        .lore(description)
                        .build();
                buttons.add(button);
            } else if (i == 1)
            {
                description.clear();
                currentPokemon = pokemon;
                description.add(InfoUtil.returnPokemonDescription(currentPokemon, playerEntity).getString());
                Pokemon finalCurrentPokemon = currentPokemon;
                GooeyButton button = GooeyButton.builder()
                        .display(SpriteItemHelper.getPhoto(currentPokemon))
                        .title(InfoUtil.returnPokemonNameString(currentPokemon))
                        .onClick(buttonAction -> {
                            UIManager.openUIForcefully(playerEntity, MainWikiPage(finalCurrentPokemon, playerEntity));
                        })
                        .lore(description)
                        .build();
                buttons.add(button);
            } else {
                currentPokemon = getNextPokemon(pokemon);
                description.clear();
                description.add(InfoUtil.returnPokemonDescription(currentPokemon, playerEntity).getString());
                Pokemon finalCurrentPokemon = currentPokemon;
                GooeyButton button = GooeyButton.builder()
                        .display(SpriteItemHelper.getPhoto(currentPokemon))
                        .title(InfoUtil.returnPokemonNameString(currentPokemon))
                        .onClick(buttonAction -> {
                            UIManager.openUIForcefully(playerEntity, MainWikiPage(finalCurrentPokemon, playerEntity));
                        })
                        .lore(description)
                        .build();
                buttons.add(button);
            }
        }

        return buttons;
    }

    public static GooeyButton EffectivenessButton(Pokemon pokemon) {
        List<String> lore = new ArrayList <>();
        for (ITextComponent component:InfoUtil.returnEffectiveness(pokemon)) {
            lore.add(component.getString());
        }
        return GooeyButton.builder()
                .display(new ItemStack(PixelmonItems.rumble_badge))
                .title("Effectiveness")
                .lore(lore)
                .build();
    }

    public static GooeyButton LevelMovesButton(Pokemon pokemon) {
        List<String> lore = new ArrayList <>();
        for (ITextComponent component:InfoUtil.returnPokemonLevelUpMoves(pokemon)) {
            lore.add(component.getString());
        }
        return GooeyButton.builder()
                .display(new ItemStack(PixelmonItems.tm_gen1))
                .title("Level up Moves")
                .lore(lore)
                .build();
    }

    public static GooeyButton TMHMMovesButton(Pokemon pokemon) {
        List<String> lore = new ArrayList <>();
        for (ITextComponent component:InfoUtil.returnPokemonHMMoves(pokemon)) {
            lore.add(component.getString());
        }
        for (ITextComponent component:InfoUtil.returnPokemonTMMoves(pokemon)) {
            lore.add(component.getString());
        }
        for (ITextComponent component:InfoUtil.returnPokemonTRMoves(pokemon)) {
            lore.add(component.getString());
        }
        return GooeyButton.builder()
                .display(new ItemStack(PixelmonItems.tm_gen1))
                .title("TM/HM/TR Moves")
                .lore(lore)
                .build();
    }

    public static GooeyButton TutorMovesButton(Pokemon pokemon) {
        List<String> lore = new ArrayList <>();
        for (ITextComponent component:InfoUtil.returnPokemonTutorMoves(pokemon)) {
            lore.add(component.getString());
        }
        return GooeyButton.builder()
                .display(new ItemStack(PixelmonItems.tm_gen1))
                .title("Tutor Moves")
                .lore(lore)
                .build();
    }

    public static GooeyButton TransferMovesButton(Pokemon pokemon) {
        List<String> lore = new ArrayList <>();
        for (ITextComponent component:InfoUtil.returnPokemonTransferMoves(pokemon)) {
            lore.add(component.getString());
        }
        return GooeyButton.builder()
                .display(new ItemStack(PixelmonItems.tm_gen1))
                .title("Transfer Moves")
                .lore(lore)
                .build();
    }


    public static GooeyButton DropsButton(Pokemon pokemon) {
        List<String> lore = new ArrayList <>();

        for (ITextComponent component:InfoUtil.returnPokemonDrops(pokemon)) {
            lore.add(component.getString());
        }

        return GooeyButton.builder()
                .display(new ItemStack(Items.DIAMOND))
                .title("Drops")
                .lore(lore)
                .build();
    }


    public static GooeyButton EvolutionsButton(Pokemon pokemon) {
        List<String> lore = new ArrayList <>();
        for (ITextComponent component:InfoUtil.returnEvolutions(pokemon)) {
            lore.add(component.getString());
        }
        return GooeyButton.builder()
                .display(new ItemStack(PixelmonItems.up_grade))
                .title("Evolutions")
                .lore(lore)
                .build();
    }


    public static GooeyButton EVYieldButton(Pokemon pokemon) {
        List<String> lore = new ArrayList <>();
        for (ITextComponent component:InfoUtil.returnEVYield(pokemon)) {
            lore.add(component.getString());
        }
        return GooeyButton.builder()
                .display(new ItemStack(PixelmonItems.power_weight))
                .title("EV Yield")
                .lore(lore)
                .build();
    }


    public static GooeyButton AbilitiesButton(Pokemon pokemon) {
        List<String> lore = new ArrayList <>();
        for (ITextComponent textComponent:InfoUtil.returnAbilities(pokemon)) {
            lore.add(textComponent.getString());
        }
        return GooeyButton.builder()
                .display(new ItemStack(PixelmonItems.ability_capsule))
                .title("Abilities")
                .lore(lore)
                .build();
    }

    public static GooeyButton CatchRateButton(Pokemon pokemon) {
        List<String> lore = new ArrayList <>();
        for (ITextComponent textComponent:InfoUtil.returnCatchRates(pokemon)) {
            lore.add(textComponent.getString());
        }
        return GooeyButton.builder()
                .display(new ItemStack(PixelmonItems.poke_ball))
                .title("Catch Rate")
                .lore(lore)
                .build();
    }

    public static GooeyButton SpawnLocationsButton(Pokemon pokemon) {
        List<String> lore = new ArrayList <>();
        for (ITextComponent component:InfoUtil.returnPokemonSpawnLocationTypes(pokemon)) {
            lore.add(component.getString());
        }
        return GooeyButton.builder()
                .display(new ItemStack(Items.PAPER))
                .title("Spawn Location")
                .lore(lore)
                .build();
    }

    public static GooeyButton SpawnTimesButton(Pokemon pokemon) {
        List<String> lore = new ArrayList <>();
        for (ITextComponent component:InfoUtil.returnPokemonSpawnTimes(pokemon)) {
            lore.add(component.getString());
        }
        return GooeyButton.builder()
                .display(new ItemStack(Items.CLOCK))
                .title("Spawn Time")
                .lore(lore)
                .build();
    }



    public static GooeyButton BiomesButton(Pokemon pokemon) {
        List<String> lore = new ArrayList <>();
        for (ITextComponent component:InfoUtil.returnPokemonSpawnBiomes(pokemon)) {
            lore.add(component.getString());
        }
        return GooeyButton.builder()
                .display(new ItemStack(Items.OAK_SAPLING))
                .title("Biomes")
                .lore(lore)
                .build();
    }

    public static GooeyButton BaseStatButton(Pokemon pokemon) {
        List<String> lore = new ArrayList <>();

        for (ITextComponent component:InfoUtil.returnPokemonBaseStats(pokemon)) {
            lore.add(component.getString());
        }

        return GooeyButton.builder()
                .display(new ItemStack(PixelmonItems.weakness_policy))
                .title("Base Stats")
                .lore(lore)
                .build();
    }

    public static GooeyButton TypeButton(Pokemon pokemon) {
        List<String> lore = new ArrayList <>();
        for (ITextComponent component:InfoUtil.returnPokemonTypes(pokemon)) {
            lore.add(component.getString());
        }
        for (ITextComponent component:InfoUtil.returnEggGroups(pokemon)) {
            lore.add(component.getString());
        }
        //add lvl range
        return GooeyButton.builder()
                .display(new ItemStack(PixelmonItems.marsh_badge))
                .title("Type")
                .lore(lore)
                .build();
    }

    public static LinkedPage MainWikiPage(Pokemon pokemon, ServerPlayerEntity playerEntity) {
        PlaceholderButton placeHolderButton = new PlaceholderButton();

        Template template = null;
        template = ChestTemplate.builder(5)
                .border(0, 0, 5, 9, filler)
                .set(1, 3, TypeButton(pokemon))
                .set(1, 4, BaseStatButton(pokemon))
                .set(1, 5, BiomesButton(pokemon))
                .set(1, 6, SpawnTimesButton(pokemon))
                .set(1, 7, SpawnLocationsButton(pokemon))

                .set(2, 3, CatchRateButton(pokemon))
                .set(2, 4, AbilitiesButton(pokemon))
                .set(2, 5, EVYieldButton(pokemon))
                .set(2, 6, DropsButton(pokemon))
                .set(2, 7, EvolutionsButton(pokemon))

                .set(3, 3, TutorMovesButton(pokemon))
                .set(3, 4, TMHMMovesButton(pokemon))
                .set(3, 5, LevelMovesButton(pokemon))
                .set(3, 6, EffectivenessButton(pokemon))
                .set(3, 7, TransferMovesButton(pokemon))
                .line(LineType.VERTICAL, 1, 1, 3, placeHolderButton)
                .build();

        return PaginationHelper.createPagesFromPlaceholders(template, pokemonButtons(pokemon, playerEntity), LinkedPage.builder().title("Wikipedia").template(template));
    }
}
