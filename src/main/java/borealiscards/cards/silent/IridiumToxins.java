package borealiscards.cards.silent;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.powers.IridiumToxinsPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IridiumToxins extends BaseCard {
    public static final String ID = makeID(IridiumToxins.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.POWER,
            CustomRarity.EXOTIC,
            CardTarget.SELF,
            3
    );

    public IridiumToxins() {
        super(ID, info);
        setMagic(1,0);
        setCostUpgrade(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new IridiumToxinsPower(p, magicNumber), magicNumber));
    }
}
