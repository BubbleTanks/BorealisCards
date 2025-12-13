package borealiscards.cards.defect;

import borealiscards.actions.ArcticCircleAction;
import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ArcticCircle extends BaseCard {
    public static final String ID = makeID(ArcticCircle.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CustomRarity.SHOP,
            CardTarget.SELF,
            -1
    );

    public ArcticCircle() {
        super(ID, info);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ArcticCircleAction(p, this.energyOnUse, this.upgraded, this.freeToPlayOnce));
    }
}
