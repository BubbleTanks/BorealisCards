package borealiscards.cards;

import borealiscards.powers.ShockAndAwePower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShockAndAwe extends BaseCard {
    public static final String ID = makeID(ShockAndAwe.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public ShockAndAwe() {
        super(ID, info);
        setInnate(false,true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ShockAndAwePower(p, 1)));
    }
}

// wawa :3
