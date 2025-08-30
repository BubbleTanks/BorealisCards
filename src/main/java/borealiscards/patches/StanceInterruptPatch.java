package borealiscards.patches;

import borealiscards.powers.CruelSunPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch2(
        clz = ChangeStanceAction.class, method = "update"
)

public class StanceInterruptPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> Prefix(ChangeStanceAction __instance) {
        if(AbstractDungeon.player.hasPower(CruelSunPower.POWER_ID)) {
            for(AbstractPower p : AbstractDungeon.player.powers) {
                if(p.ID == CruelSunPower.POWER_ID) p.flash();
            }
            __instance.isDone = true;
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}
