package borealiscards.patches;

import borealiscards.powers.HyperPropellantPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch2(
        clz = ChannelAction.class, method = "update"
)

public class ChannelInterruptPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> Prefix(ChannelAction __instance) {
        if(AbstractDungeon.player.hasPower(HyperPropellantPower.POWER_ID)) {
            for(AbstractPower p : AbstractDungeon.player.powers) {
                if(p.ID == HyperPropellantPower.POWER_ID) p.flash();
            }
            __instance.isDone = true;
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}
