package borealiscards.cards;

import borealiscards.orbs.Starlight;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HubbleWave extends BaseCard {
    public static final String ID = makeID(HubbleWave.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.BLUE,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public HubbleWave() {
        super(ID, info);
        setExhaust(true);
        setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int hubbleCards = AbstractDungeon.player.hand.size() / 2;
        for(int hubbleChannel = 0; hubbleChannel < hubbleCards; ++hubbleChannel) {
            addToBot(new ChannelAction(new Starlight()));
        }
    }
}
