package borealiscards.relics;

import basemod.AutoAdd;
import basemod.helpers.CardModifierManager;
import borealiscards.cardmods.DoubleDamageOnce;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.UUID;

import static borealiscards.BorealisCards.makeID;

@AutoAdd.Ignore
public class MarketGardener extends BaseRelic {
    public static final String ID = makeID(MarketGardener.class.getSimpleName());
    private AbstractCard lastPlayedAttack;
    public MarketGardener() {
        super(ID, RelicTier.COMMON, LandingSound.CLINK);
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if(CardModifierManager.hasModifier(c, DoubleDamageOnce.ID)) this.grayscale = true;
        if(c.type == AbstractCard.CardType.ATTACK) lastPlayedAttack = c;
    }

    public void onVictory() {
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) if(CardModifierManager.hasModifier(c,DoubleDamageOnce.ID)) CardModifierManager.removeModifiersById(c,DoubleDamageOnce.ID,true);
        if(lastPlayedAttack != null ) {
            UUID markedUUID = lastPlayedAttack.uuid;
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) if (c.uuid == markedUUID) CardModifierManager.addModifier(c, new DoubleDamageOnce());
        }
        lastPlayedAttack = null;
        this.grayscale = false;
    }
}
