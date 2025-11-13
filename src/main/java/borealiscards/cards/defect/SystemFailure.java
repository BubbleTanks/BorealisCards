package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SystemFailure extends BaseCard {
    public static final String ID = makeID(SystemFailure.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public SystemFailure() {
        super(ID, info);
        setMagic(2,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int orbsCount = AbstractDungeon.player.orbs.size();
        int orbsMax = AbstractDungeon.player.maxOrbs;
        if (orbsCount > 0) {
            for (int i = 0; i < orbsCount; ++i) {
                for (int j = 0; j < magicNumber - 1; ++j) {
                    addToBot(new AnimateOrbAction(1));
                    addToBot(new EvokeWithoutRemovingOrbAction(1));
                }
                addToBot(new AnimateOrbAction(1));
                addToBot(new EvokeOrbAction(1));
            }
            if (orbsMax > 1) addToBot(new DecreaseMaxOrbAction(orbsMax - 1));
        }
    }
}

