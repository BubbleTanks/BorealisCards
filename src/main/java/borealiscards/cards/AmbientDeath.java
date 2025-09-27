package borealiscards.cards;

import borealiscards.powers.AmbientDeathPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AmbientDeath extends BaseCard {
    public static final String ID = makeID(AmbientDeath.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            -2
    );

    public AmbientDeath() {
        super(ID, info);
        setMagic(1,1);

    }

    @Override
    public void triggerOnManualDiscard() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new AmbientDeathPower(AbstractDungeon.player, magicNumber)));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }
}
