package borealiscards.patches;

import borealiscards.SpireFields.ShockAndAweField;
import borealiscards.cards.defect.TryCatch;
import borealiscards.powers.HyperPropellantPower;
import borealiscards.powers.SentiencePower;
import borealiscards.powers.ShockAndAwePower;
import borealiscards.powers.TryCatchPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
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

        return SpireReturn.Continue();
    }

    @SpireInsertPatch(rloc = 1)
    public static void ForPowers(AbstractOrb ___orbType) {
        for(AbstractPower p : AbstractDungeon.player.powers) {
            if (p.ID == ShockAndAwePower.POWER_ID) {
                if (___orbType.ID == Lightning.ORB_ID && !ShockAndAweField.ShockField.aweShocked.get(___orbType)) {
                    for (int i = p.amount; i > 0; i--) {
                        AbstractOrb aweOrb = AbstractOrb.getRandomOrb(true);
                        ShockAndAweField.ShockField.aweShocked.set(aweOrb, true);
                        ShockAndAweField.ShockField.aweShocked.set(___orbType, true);
                        AbstractDungeon.actionManager.addToTop(new ChannelAction(aweOrb));
                    }
                }
            }
            if (p.ID == TryCatchPower.POWER_ID && !((TryCatchPower)p).TriedCatch) {
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, p));
                ((TryCatchPower)p).TriedCatch = true;
                ((TryCatch)((TryCatchPower)p).TryCatch).CaughtOrbs.add(___orbType.makeCopy());
                ((TryCatch)((TryCatchPower)p).TryCatch).updateTryDescription();
            }
            if (p.ID == SentiencePower.POWER_ID) {
                if (!ShockAndAweField.ShockField.sentient.get(___orbType)) {
                    for (int i = p.amount; i > 0; i--) {
                        AbstractOrb sentientOrb = ___orbType.makeCopy();
                        if (ShockAndAweField.ShockField.aweShocked.get(___orbType)) {
                            ShockAndAweField.ShockField.aweShocked.set(sentientOrb, true);
                        }
                        ShockAndAweField.ShockField.sentient.set(sentientOrb, true);
                        ShockAndAweField.ShockField.sentient.set(___orbType, true);
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(sentientOrb));
                    }
                }
            }
        }
    }
}
