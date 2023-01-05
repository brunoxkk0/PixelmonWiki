package io.github.adainish.pixelmonwiki.command;

import ca.landonjw.gooeylibs2.api.UIManager;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonFactory;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import io.github.adainish.pixelmonwiki.gui.WikiGUI;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class Command {
    public static LiteralArgumentBuilder <CommandSource> getCommand()
    {
        return Commands.literal("pwiki")
                .executes(cc -> {
                    try {
                        UIManager.openUIForcefully(cc.getSource().asPlayer(), WikiGUI.MainWikiPage(PokemonFactory.create(PixelmonSpecies.BULBASAUR.getValueUnsafe()), cc.getSource().asPlayer()));
                    } catch (Exception e) {
                        cc.getSource().sendFeedback(new StringTextComponent("Something went wrong while executing the command, please contact a member of Staff if this issue persists"), true);
                        return 1;
                    }
                    return 1;
                })
                .then(Commands.argument("pokemon", StringArgumentType.string())
                        .executes(context -> {
                            try {
                                String targetName = StringArgumentType.getString(context, "pokemon");
                                Species species;
                                if (!PixelmonSpecies.get(targetName).isPresent())
                                    species = PixelmonSpecies.BULBASAUR.getValueUnsafe();
                                else species = PixelmonSpecies.get(targetName).get().getValue().get();
                                Pokemon pokemon = PokemonFactory.create(species);
                                UIManager.openUIForcefully(context.getSource().asPlayer(), WikiGUI.MainWikiPage(pokemon, context.getSource().asPlayer()));
                            } catch (Exception e) {
                                context.getSource().sendFeedback(new StringTextComponent("Something went wrong while executing the command, please contact a member of Staff if this issue persists"), true);
                                e.printStackTrace();
                                return 1;
                            }
                            return 1;
                        })
                );
    }
}
