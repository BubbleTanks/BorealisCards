package borealiscards.cards.watcher;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class Nihilism extends BaseCard {
    public static final String ID = makeID(Nihilism.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.PURPLE,
            CardType.ATTACK,
            CustomRarity.EXOTIC,
            CardTarget.ENEMY,
            1
    );

    public Nihilism() {
        super(ID, info);
        setDamage(18,5);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardGroup handCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        handCards.group.addAll(AbstractDungeon.player.hand.getPurgeableCards().group);
        handCards.group.remove(this);

        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));



        addToBot(new SelectCardsInHandAction(1, cardStrings.EXTENDED_DESCRIPTION[0], card -> handCards.contains(card), (cards) -> {
            for (AbstractCard c : cards) {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractCard b = null;
                        for (AbstractCard a : AbstractDungeon.player.masterDeck.group) {
                            if (a.uuid == c.uuid) {
                                AbstractDungeon.effectList.add(new PurgeCardEffect(a));
                                b = a;
                            }
                        }
                        AbstractDungeon.player.masterDeck.removeCard(b);
                        AbstractDungeon.player.hand.removeCard(c);

                        this.isDone = true;
                    }
                });
            }
        }));
    }
}
