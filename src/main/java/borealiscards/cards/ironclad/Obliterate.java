package borealiscards.cards.ironclad;

import borealiscards.actions.ObliterateAction;
import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

public class Obliterate extends BaseCard {
    public static final String ID = makeID(Obliterate.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.ATTACK,
            CustomRarity.EXOTIC,
            CardTarget.ENEMY,
            3
    );

    public Obliterate() {
        super(ID, info);
        setDamage(42);
        setInnate(true);
    }

    public void upgrade() {
        if (!upgraded) {
            this.target = CardTarget.ALL_ENEMY;
            super.upgrade();
            setInnate(true);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            for (int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); ++i) {
                AbstractMonster target3 = (AbstractMonster) AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                this.addToBot(new VFXAction(new WeightyImpactEffect(target3.hb.cX, target3.hb.cY)));
            }
            this.addToBot(new WaitAction(0.8F));
        } else {
            this.addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
            this.addToBot(new WaitAction(0.8F));
        }
        addToBot(new ObliterateAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), this));
    }
}


