package borealiscards.patches;

import borealiscards.cards.silent.Toxicology;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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

public class ToxicologyPatch {
    @SpireInsertPatch (
            rloc = 16
    )
    public static void Insert(ApplyPowerAction __instance, AbstractCreature target, AbstractCreature source, AbstractPower ___powerToApply) {
        if (source != null && source.isPlayer && target != source && ___powerToApply.ID.equals("Poison")) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.cardID == Toxicology.ID) {
                    c.flash();
                    ___powerToApply.amount += c.magicNumber;
                    __instance.amount += c.magicNumber;
                }
            }
        }
    }
}
