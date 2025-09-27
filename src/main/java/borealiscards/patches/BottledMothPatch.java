package borealiscards.patches;

import borealiscards.SpireFields.BottledMothFieldHandler;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

@SpirePatch2(clz = CardGroup.class, method = "initializeDeck")
public class BottledMothPatch {
    @SpireInstrumentPatch
    public static ExprEditor addCheckForMoth() {
        return new ExprEditor() {
            public void edit(FieldAccess f) throws CannotCompileException {
                if (f.getClassName().equals(AbstractCard.class.getName()) && f.getFieldName().equals("inBottleFlame")) {
                    f.replace("$_ = $proceed($$) || ((Boolean)" + BottledMothFieldHandler.BottledMothField.class.getName() +".inBottleMoth.get(c)).booleanValue();");
                }
            }
        };
    }
}