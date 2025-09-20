package borealiscards.patches;

import borealiscards.orbs.Starlight;
import borealiscards.powers.OrbStarlightPower;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoopPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.EmotionChip;
import com.megacrit.cardcrawl.relics.GoldPlatedCables;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

import java.util.ArrayList;

public class StarlightOrbPatches {

    @SpirePatch2(clz = AbstractCreature.class, method = "applyStartOfTurnPowers")
    public static class StarlightOrbStartOfTurn {
        @SpirePrefixPatch
        public static void StartTurn(AbstractCreature __instance) {
            if(__instance.isPlayer && AbstractDungeon.player.maxOrbs > 0) {
                int starlightCount = 0;
                for (AbstractOrb o : AbstractDungeon.player.orbs) {
                    if (o.ID == Starlight.ORB_ID) {
                        starlightCount += o.passiveAmount;
                    }
                }
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (p.ID == OrbStarlightPower.POWER_ID) {
                        starlightCount += OrbStarlightPower.STARLIGHTORB.passiveAmount;
                    }
                }
                if(AbstractDungeon.player.orbs.get(0).ID == Starlight.ORB_ID) {
                    for (AbstractPower p : AbstractDungeon.player.powers) {
                        if (p.ID == LoopPower.POWER_ID) {
                            starlightCount += OrbStarlightPower.STARLIGHTORB.passiveAmount;
                        }
                    }
                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        if (r.relicId == GoldPlatedCables.ID) {
                            starlightCount += OrbStarlightPower.STARLIGHTORB.passiveAmount;
                        }
                    }
                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        if (r.relicId == EmotionChip.ID && LostHealthThisTurnPatch.hurtLastTurn) {
                            starlightCount += OrbStarlightPower.STARLIGHTORB.passiveAmount;
                        }
                    }
                }
                if(starlightCount > 0) AbstractDungeon.actionManager.addToBottom(new ScryAction(starlightCount));
            }
        }
    }

    @SpirePatch2(clz = AbstractOrb.class, method = "getRandomOrb")
    public static class StarlightOrbChaos {
        @SpireInsertPatch(rloc = 3, localvars = {"orbs"})
        public static void StarlightOrbChaosInsert(ArrayList<AbstractOrb> orbs) {
            orbs.add(new Starlight());
        }
    }

    @SpirePatch2(clz = OrbFlareEffect.class, method = "setColor")
    public static class StarlightOrbColor {
        @SpirePostfixPatch
        public static void StarlightOrbPrefix(AbstractOrb ___orb, @ByRef Color[] ___color, @ByRef Color[] ___color2) {
            if(___orb.ID == Starlight.ORB_ID) {
                ___color[0] = Color.ROYAL;
                ___color2[0] = Color.VIOLET;
            }
        }
    }
}
