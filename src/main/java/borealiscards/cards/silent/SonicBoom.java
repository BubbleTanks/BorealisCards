package borealiscards.cards.silent;

import borealiscards.cards.BaseCard;
import borealiscards.patches.rarities.CustomRarity;
import borealiscards.util.CardStats;
import borealiscards.vfx.ExpungeRotatedEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class SonicBoom extends BaseCard {
    public static final String ID = makeID(SonicBoom.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.GREEN,
            CardType.ATTACK,
            CustomRarity.EXOTIC,
            CardTarget.ENEMY,
            1
    );

    private boolean PlayedThisTurn() {
        int i = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (this == c) {
                i++;
                if(i>1) return true;
            }
        }
        return false;
    }

    public SonicBoom() {
        super(ID, info);
        setDamage(14,4);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExpungeRotatedEffect(m));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        if (PlayedThisTurn()) {
            addToBot(new SFXAction("ATTACK_WHIRLWIND"));
            addToBot(new VFXAction(new WhirlwindEffect(Color.GREEN.cpy(), true), 0.0F));
            addToBot(new SkipEnemiesTurnAction());
            addToBot(new PressEndTurnButtonAction());
        }
    }
}
