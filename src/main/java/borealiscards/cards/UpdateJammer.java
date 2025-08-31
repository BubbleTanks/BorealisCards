package borealiscards.cards;

import borealiscards.powers.UpdateJammerPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UpdateJammer extends BaseCard {
    public static final String ID = makeID(UpdateJammer.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            4
    );

    public UpdateJammer() {
        super(ID, info);
        setCostUpgrade(3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new UpdateJammerPower(p)));
    }
}

// wawa :3
