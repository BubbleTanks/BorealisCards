package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.powers.HeartbeatPower;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Heartbeat extends BaseCard {
    public static final String ID = makeID(Heartbeat.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.POWER,
            CustomRarity.SHOP,
            CardTarget.SELF,
            1
    );

    public Heartbeat() {
        super(ID, info);
        setInnate(false,true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new HeartbeatPower(p)));
    }
}
