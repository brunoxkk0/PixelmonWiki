package io.github.adainish.pixelmonwiki.util;

import net.minecraft.util.text.TranslationTextComponent;

public class TranslateUtil {

    public static String getTranslatedString(String key) {
        TranslationTextComponent translationTextComponent = new TranslationTextComponent(key);
        return translationTextComponent.getString();
    }

}
