package borealiscards.cards.ironclad;

import borealiscards.actions.GodOfWarAction;
import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.watcher.ExpungeVFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GodOfWar extends BaseCard {
    public static final String ID = makeID(GodOfWar.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.ATTACK,
            CustomRarity.EXOTIC,
            CardTarget.ENEMY,
            20
    );

    public GodOfWar() {
        super(ID, info);
        misc = 15;
        setDamage(misc);
        setCostUpgrade(15);
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null) {
            this.configureCostsOnNewCard();
        }
        setExhaust(true);
    }

    public void onLoadedMisc() {
        baseDamage = misc;
    }

    public void configureCostsOnNewCard() {
        for(AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.type == CardType.ATTACK) {
                updateCost(-1);
            }
        }
    }

    public void triggerOnCardPlayed(AbstractCard c) {
        if (c.type == CardType.ATTACK) {
            this.updateCost(-1);
        }
    }

    public void applyPowers() {
        super.applyPowers();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExpungeVFXAction(m));
        addToBot(new GodOfWarAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this));
    }
}


