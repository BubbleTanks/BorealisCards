package borealiscards.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;

import static borealiscards.BorealisCards.makeID;

public class Neloprephine extends BaseRelic {
    public static final String ID = makeID(Neloprephine.class.getSimpleName());

    public Neloprephine() {
        super(ID, RelicTier.SHOP, LandingSound.CLINK);
    }

    public void atBattleStart() {
        boolean isEliteOrBoss = AbstractDungeon.getCurrRoom().eliteTrigger;

        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m.type == AbstractMonster.EnemyType.BOSS) {
                isEliteOrBoss = true;
            }
        }

        if (isEliteOrBoss) {
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, 4)));
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}