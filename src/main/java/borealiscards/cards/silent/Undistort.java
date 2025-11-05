package borealiscards.cards.silent;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.powers.UndistortPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Undistort extends BaseCard {
    public static final String ID = makeID(Undistort.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.ATTACK,
            CustomRarity.EXOTIC,
            CardTarget.SELF,
            2
    );

    public Undistort() {
        super(ID, info);
        setMagic(6);
        setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new ApplyPowerAction(p, p, new UndistortPower(p, 1)));
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new DiscardAction(p, p, magicNumber, false));
    }
}
