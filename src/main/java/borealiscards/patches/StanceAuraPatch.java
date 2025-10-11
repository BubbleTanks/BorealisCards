package borealiscards.patches;

import borealiscards.stances.TensionStance;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;

@SpirePatch2(clz = StanceAuraEffect.class, method = SpirePatch.CONSTRUCTOR)
public class StanceAuraPatch {
    @SpirePostfixPatch
    public static void StanceAuraTension(String stanceId, @ByRef Color[] ___color) {
        if (stanceId.equals(TensionStance.STANCE_ID)) {
            ___color[0] = new Color(MathUtils.random(0.6F, 0.7F), MathUtils.random(0.6F, 0.7F), MathUtils.random(0.0F, 0.1F), 0.0F);
        }
    }
}
