package borealiscards.SpireFields;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ThisStupidFuckingExotic {
    @SpirePatch(clz = AbstractMonster.class, method = SpirePatch.CLASS)
    public static class BehaveYourself {
        public static SpireField<Boolean> stopFuckingPlaying = new SpireField<>(() -> false);
    }
}
