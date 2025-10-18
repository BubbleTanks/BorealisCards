package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.patches.OverdriveBoosterPatch;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class OverdriveBooster extends BaseCard {
    public static final String ID = makeID(OverdriveBooster.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    public OverdriveBooster() {
        super(ID, info);
        setMagic(5,2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                OverdriveBoosterPatch.OVERDRIVEORBSLOT = OverdriveBoosterPatch.OVERDRIVEORBSLOT * 2;
                this.isDone = true;
            }
        });
        addToBot(new IncreaseMaxOrbAction(magicNumber));
    }
}
