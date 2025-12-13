package borealiscards.cards.silent;

import borealiscards.cards.BaseCard;
import borealiscards.powers.AcrimonyPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Caustics extends BaseCard {
    public static final String ID = makeID(Caustics.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public Caustics() {
        super(ID, info);
        setMagic(1,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeTempCardInHandAction(new Shiv(), 2));
        for(AbstractMonster m1 : AbstractDungeon.getMonsters().monsters) {
            if (!m1.isDead && !m1.isDying) {
                addToBot(new ApplyPowerAction(m1, p, new AcrimonyPower(m1, magicNumber)));
            }
        }
    }
}


