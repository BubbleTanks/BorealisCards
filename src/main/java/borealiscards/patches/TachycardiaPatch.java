package borealiscards.patches;

import borealiscards.powers.AcrimonyPower;
import borealiscards.powers.TachycardiaPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch2(clz = GameActionManager.class, method = "incrementDiscard")
public class TachycardiaPatch {
    @SpireInsertPatch(rloc = 5)
    public static void TachycardiaRloc() {
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p.ID == TachycardiaPower.POWER_ID) {
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new AcrimonyPower(m, p.amount)));
                }
            }
        }
    }
}
