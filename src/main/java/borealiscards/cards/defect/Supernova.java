package borealiscards.cards.defect;

import borealiscards.cards.BaseCard;
import borealiscards.orbs.Horizon;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Supernova extends BaseCard {
    public static final String ID = makeID(Supernova.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CustomRarity.EXOTIC,
            CardTarget.SELF,
            1
    );

    public Supernova() {
        super(ID, info);
        setInnate(false,true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChannelAction(new Horizon()));
    }
}
