package borealiscards.patches;

import borealiscards.powers.SadomasochismPower;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch2(
        clz = ApplyPowerAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {
                AbstractCreature.class,
                AbstractCreature.class,
                AbstractPower.class,
                int.class,
                boolean.class,
                AbstractGameAction.AttackEffect.class
        }
)

public class SadomasochismPatch {
    @SpireInsertPatch (
            rloc = 16
    )
    public static void Insert(ApplyPowerAction __instance, AbstractCreature target, AbstractCreature source, @ByRef AbstractPower[] ___powerToApply) {
        if (source != null && source.isPlayer && target != source && ___powerToApply[0].ID.equals("Poison")) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p.ID == SadomasochismPower.POWER_ID) {
                    AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, p.amount));
                }
            }
        }
    }
}
