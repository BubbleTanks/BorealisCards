package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.powers.PublicVoidPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PublicVoid extends BaseCard {
    public static final String ID = makeID(PublicVoid.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public PublicVoid() {
        super(ID, info);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PublicVoidPower(p, 1)));
    }
}

// wawa :3
