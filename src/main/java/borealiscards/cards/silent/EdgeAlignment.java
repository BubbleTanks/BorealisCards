package borealiscards.cards.silent;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.powers.EdgeAlignmentPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EdgeAlignment extends BaseCard {
    public static final String ID = makeID(EdgeAlignment.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.POWER,
            CustomRarity.SHOP,
            CardTarget.SELF,
            1
    );

    public EdgeAlignment() {
        super(ID, info);
        cardsToPreview = new Shiv();
        setMagic(2,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new EdgeAlignmentPower(p, magicNumber)));
    }
}
