package borealiscards.cards.defect;

import borealiscards.actions.WaterCoolingAction;
import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WaterCooling extends BaseCard {
    public static final String ID = makeID(WaterCooling.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            -1
    );

    public WaterCooling() {
        super(ID, info);
        setMagic(0,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new WaterCoolingAction(p, this.upgraded, this.freeToPlayOnce, this.energyOnUse));
    }
}
