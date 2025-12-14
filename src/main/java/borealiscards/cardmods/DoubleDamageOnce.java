package borealiscards.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import borealiscards.BorealisCards;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DoubleDamageOnce extends AbstractCardModifier {
    public static String ID = BorealisCards.makeID(DoubleDamageOnce.class.getSimpleName());

    @Override
    public AbstractCardModifier makeCopy() {
        return new DoubleDamageOnce();
    }

    public float modifyDamageFinal(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if(type == DamageInfo.DamageType.NORMAL) return damage * 2;
        return damage;
    }

    public Color getGlow(AbstractCard card) {
        if(Loader.isModLoaded("mintyspire")) return Color.PURPLE.cpy();
        return null;
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                CardModifierManager.removeModifiersById(card, DoubleDamageOnce.ID, true);
                this.isDone = true;
            }
        });
    }
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card,ID);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
