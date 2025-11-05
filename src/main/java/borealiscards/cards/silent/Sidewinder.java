package borealiscards.cards.silent;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Sidewinder extends BaseCard {
    public static final String ID = makeID(Sidewinder.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.ATTACK,
            CustomRarity.SHOP,
            CardTarget.ENEMY,
            1
    );

    public Sidewinder() {
        super(ID, info);
        setDamage(11,3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardGroup cardsDraw = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        CardGroup cardsDiscard = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        cardsDraw.group.addAll(AbstractDungeon.player.drawPile.group);
        cardsDiscard.group.addAll(AbstractDungeon.player.discardPile.group);
        AbstractDungeon.player.drawPile.group.removeAll(cardsDraw.group);
        AbstractDungeon.player.discardPile.group.removeAll(cardsDiscard.group);

        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new MoveCardsAction(AbstractDungeon.player.discardPile, cardsDraw, 99999));
        addToBot(new MoveCardsAction(AbstractDungeon.player.drawPile, cardsDiscard, 99999));
    }
}
