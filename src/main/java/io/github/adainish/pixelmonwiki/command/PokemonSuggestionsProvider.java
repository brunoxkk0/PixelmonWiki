package io.github.adainish.pixelmonwiki.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import net.minecraft.command.CommandSource;

import java.util.concurrent.CompletableFuture;

public class PokemonSuggestionsProvider implements SuggestionProvider <CommandSource> {

    @Override
    public CompletableFuture <Suggestions> getSuggestions(CommandContext <CommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        PixelmonSpecies.getAll().stream().map(Species::getName).forEach(builder::suggest);
        return builder.buildFuture();
    }
}
