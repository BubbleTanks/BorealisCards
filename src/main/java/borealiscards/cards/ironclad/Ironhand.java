package borealiscards.cards.ironclad;

import borealiscards.actions.IronhandAction;
import borealiscards.cards.BaseCard;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

public class Ironhand extends BaseCard {
    public static final String ID = makeID(Ironhand.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            3
    );

    public void applyPowers() {
        baseDamage = misc;
        super.applyPowers();
        initializeDescription();
    }

    public void onLoadedMisc() {
        baseDamage = misc;
    }

    public Ironhand() {
        super(ID, info);
        misc = 8;
        setDamage(misc);
        setCostUpgrade(2);
    }

    public void tookDamage() {
        if (AbstractDungeon.player.hand.group.contains(this)) {
            addToTop(new IronhandAction(uuid));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        }

        addToBot(new WaitAction(0.8F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }
}
