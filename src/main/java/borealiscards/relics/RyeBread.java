package borealiscards.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static borealiscards.BorealisCards.makeID;

public class RyeBread extends BaseRelic {
    public static final String ID = makeID(RyeBread.class.getSimpleName());

    public RyeBread() {
        super(ID, RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL) {
            addToTop(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}