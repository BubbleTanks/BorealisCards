package borealiscards.cards;

import borealiscards.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ExpungeVFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;

import java.util.ArrayList;

public class DevastatingStrike extends BaseCard implements StartupCard {
    public static final String ID = makeID(DevastatingStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.RED,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );

    private static final int DAMAGE = 65;

    public DevastatingStrike() {
        super(ID, info);
        cardsToPreview = new BloodSurge();
        setDamage(DAMAGE);
        setExhaust(true);
        tags.add(CardTags.STRIKE);
    }

    @Override // Deals damage
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new ExpungeVFXAction(m));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override // Checks for Blood Surge
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            ArrayList<AbstractCard> cards = new ArrayList<>();
            cards.addAll(p.hand.group);
            cards.addAll(p.discardPile.group);
            cards.addAll(p.drawPile.group);

            for(AbstractCard c : cards) {
                if (c instanceof BloodSurge) {
                    canUse = false;
                    cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
                    break;
                }
            }

            return canUse;
        }
    }

    @Override // Adds Blood Surge to draw and discard piles
    public boolean atBattleStartPreDraw() {
        BloodSurge surge1 = new BloodSurge();
        BloodSurge surge2 = new BloodSurge();
        if (upgraded) {
            surge1.upgrade();
            surge2.upgrade();
        }
        addToBot(new MakeTempCardInDiscardAction(surge1, 1));
        addToBot(new MakeTempCardInDrawPileAction(surge2, 1, true, true));
        return true;

    }

    @Override // Grave logic
    public void upgrade() {
        super.upgrade();
        cardsToPreview.upgrade();
        GraveField.grave.set(this,true);
    }
}


