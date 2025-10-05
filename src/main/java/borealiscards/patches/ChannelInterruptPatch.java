package borealiscards.patches;

import borealiscards.powers.HyperPropellantPower;
import borealiscards.powers.ShockAndAwePower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch2(
        clz = ChannelAction.class, method = "update"
)

public class ChannelInterruptPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> Prefix(ChannelAction __instance, AbstractOrb ___orbType, float ___duration) {
        if(AbstractDungeon.player.hasPower(HyperPropellantPower.POWER_ID)) {
            for(AbstractPower p : AbstractDungeon.player.powers) {
                if(p.ID == HyperPropellantPower.POWER_ID) p.flash();
            }
            __instance.isDone = true;
            return SpireReturn.Return();
        }
        if (___duration == Settings.ACTION_DUR_FAST) {
            for(AbstractPower p : AbstractDungeon.player.powers) {
                if (p.ID == ShockAndAwePower.POWER_ID) {
                    if (___orbType.ID == Lightning.ORB_ID) {
                        for (int i = p.amount; i > 0; i--) {
                            AbstractDungeon.actionManager.addToTop(new ChannelAction(AbstractOrb.getRandomOrb(true)));
                        }
                    }
                }
            }
        }

        return SpireReturn.Continue();
    }
}
