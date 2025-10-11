package borealiscards;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.interfaces.*;
import borealiscards.cards.BaseCard;
import borealiscards.cards.silent.RopeDart;
import borealiscards.patches.ParanoiaBoxPreventSkip;
import borealiscards.potions.BasePotion;
import borealiscards.relics.BaseRelic;
import borealiscards.ui.ModConfig;
import borealiscards.util.GeneralUtils;
import borealiscards.util.KeywordInfo;
import borealiscards.util.Sounds;
import borealiscards.util.TextureLoader;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.patches.CenterGridCardSelectScreen;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@SpireInitializer
public class BorealisCards implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        AddAudioSubscriber,
        PostInitializeSubscriber,
        PostUpdateSubscriber,
        StartGameSubscriber,
        OnCardUseSubscriber {
    public static boolean choosingTransformCard;
    public static ModInfo info;
    public static String modID; //Edit your pom.xml to change this
    static { loadModInfo(); }
    private static final String resourcesFolder = checkResourcesPath();
    public static final Logger logger = LogManager.getLogger(modID); //Used to output to the console.

    //This is used to prefix the IDs of various objects like cards and relics,
    //to avoid conflicts between different mods using the same name for things.
    public static String makeID(String id) {
        return modID + ":" + id;
    }

    //This will be called by ModTheSpire because of the @SpireInitializer annotation at the top of the class.
    public static void initialize() {
        new BorealisCards();
    }

    public BorealisCards() {
        BaseMod.subscribe(this); //This will make BaseMod trigger all the subscribers at their appropriate times.
        logger.info(modID + " subscribed to BaseMod.");
    }


    @Override
    public void receiveEditCards() { //somewhere in the class
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseCard.class) //In the same package as this class
                .setDefaultSeen(true) //And marks them as seen in the compendium
                .cards();
    }

    @Override
    public void receiveStartGame() {
        ArrayList<AbstractCard> cardsToRemove = new ArrayList<AbstractCard>();

        cardsToRemove.addAll(AbstractDungeon.commonCardPool.group.stream().filter(card ->
                card instanceof BaseCard
        ).collect(Collectors.toList()));
        cardsToRemove.addAll(AbstractDungeon.uncommonCardPool.group.stream().filter(card ->
                card instanceof BaseCard
        ).collect(Collectors.toList()));
        cardsToRemove.addAll(AbstractDungeon.rareCardPool.group.stream().filter(card ->
                card instanceof BaseCard
        ).collect(Collectors.toList()));
        cardsToRemove.addAll(AbstractDungeon.colorlessCardPool.group.stream().filter(card ->
                card instanceof BaseCard
        ).collect(Collectors.toList()));
        cardsToRemove.addAll(AbstractDungeon.curseCardPool.group.stream().filter(card ->
                card instanceof BaseCard
        ).collect(Collectors.toList()));

        for(AbstractCard c : cardsToRemove) {
            if(!ModConfig.ColorsRed && c.color == AbstractCard.CardColor.RED) {
                removeCard(c);
            }
            if(!ModConfig.ColorsGreen && c.color == AbstractCard.CardColor.GREEN) {
                removeCard(c);
            }
            if(!ModConfig.ColorsBlue && c.color == AbstractCard.CardColor.BLUE) {
                removeCard(c);
            }
            if(!ModConfig.ColorsPurple && c.color == AbstractCard.CardColor.PURPLE) {
                removeCard(c);
            }
            if(!ModConfig.ColorsGray && c.color == AbstractCard.CardColor.COLORLESS) {
                removeCard(c);
            }
            if(!ModConfig.ColorsBlack && c.color == AbstractCard.CardColor.CURSE) {
                removeCard(c);
            }
        }


    }

    private void removeCard(AbstractCard c) {
        AbstractDungeon.commonCardPool.removeCard(c);
        AbstractDungeon.uncommonCardPool.removeCard(c);
        AbstractDungeon.rareCardPool.removeCard(c);
        AbstractDungeon.colorlessCardPool.removeCard(c);
        AbstractDungeon.curseCardPool.removeCard(c);
        AbstractDungeon.srcCommonCardPool.removeCard(c);
        AbstractDungeon.srcUncommonCardPool.removeCard(c);
        AbstractDungeon.srcRareCardPool.removeCard(c);
        AbstractDungeon.srcColorlessCardPool.removeCard(c);
        AbstractDungeon.srcCurseCardPool.removeCard(c);
        // the great pool wall
    }

    private void killCompendium() {
        ArrayList<AbstractCard> cardsToRemove = new ArrayList<AbstractCard>();
        ArrayList<AbstractRelic> relicsToRemove = new ArrayList<AbstractRelic>();

        for(AbstractCard c : CardLibrary.getAllCards()) {
            if(c instanceof BaseCard) {
                cardsToRemove.add(c);
            }
        }

        for (AbstractRelic r : new ArrayList<AbstractRelic>() {{
            addAll(RelicLibrary.commonList);
            addAll(RelicLibrary.uncommonList);
            addAll(RelicLibrary.rareList);
            addAll(RelicLibrary.bossList);
            addAll(RelicLibrary.shopList);
            addAll(RelicLibrary.specialList);
            addAll(RelicLibrary.redList);
            addAll(RelicLibrary.greenList);
            addAll(RelicLibrary.blueList);
            addAll(RelicLibrary.whiteList); // Watcher Rushdown White. Ironclad we need to COOK
        }}) if(r instanceof BaseRelic) relicsToRemove.add(r);

        for(AbstractCard c : cardsToRemove) {
            if(!ModConfig.ColorsRed && c.color == AbstractCard.CardColor.RED) {
                CardLibrary.cards.remove(c.cardID);
            }
            if(!ModConfig.ColorsGreen && c.color == AbstractCard.CardColor.GREEN) {
                CardLibrary.cards.remove(c.cardID);
            }
            if(!ModConfig.ColorsBlue && c.color == AbstractCard.CardColor.BLUE) {
                CardLibrary.cards.remove(c.cardID);
            }
            if(!ModConfig.ColorsPurple && c.color == AbstractCard.CardColor.PURPLE) {
                CardLibrary.cards.remove(c.cardID);
            }
            if(!ModConfig.ColorsGray && c.color == AbstractCard.CardColor.COLORLESS) {
                CardLibrary.cards.remove(c.cardID);
            }
            if(!ModConfig.ColorsBlack && c.color == AbstractCard.CardColor.CURSE) {
                CardLibrary.cards.remove(c.cardID);
                ReflectionHacks.<HashMap>getPrivateStatic(CardLibrary.class, "curses").remove(c.cardID);

            }
        }

        for(AbstractRelic r : relicsToRemove) {
            if(!ModConfig.Relics) {
                ReflectionHacks.<HashMap>getPrivateStatic(RelicLibrary.class, "sharedRelics").remove(r.relicId);
                ReflectionHacks.<HashMap>getPrivateStatic(RelicLibrary.class, "redRelics").remove(r.relicId);
                ReflectionHacks.<HashMap>getPrivateStatic(RelicLibrary.class, "greenRelics").remove(r.relicId);
                ReflectionHacks.<HashMap>getPrivateStatic(RelicLibrary.class, "blueRelics").remove(r.relicId);
                ReflectionHacks.<HashMap>getPrivateStatic(RelicLibrary.class, "purpleRelics").remove(r.relicId);
                RelicLibrary.commonList.remove(r);
                RelicLibrary.uncommonList.remove(r);
                RelicLibrary.rareList.remove(r);
                RelicLibrary.bossList.remove(r);
                RelicLibrary.shopList.remove(r);
                RelicLibrary.specialList.remove(r);
                RelicLibrary.redList.remove(r);
                RelicLibrary.greenList.remove(r);
                RelicLibrary.blueList.remove(r);
                RelicLibrary.whiteList.remove(r);
            }
        }
    }

    @Override
    public void receiveEditRelics() { //somewhere in the class
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BaseRelic.class) //In the same package as this class
                .any(BaseRelic.class, (info, relic) -> { //Run this code for any classes that extend this class
                    if (relic.pool != null)
                        BaseMod.addRelicToCustomPool(relic, relic.pool); //Register a custom character specific relic
                    else
                        BaseMod.addRelic(relic, relic.relicType); //Register a shared or base game character specific relic

                    //If the class is annotated with @AutoAdd.Seen, it will be marked as seen, making it visible in the relic library.
                    //If you want all your relics to be visible by default, just remove this if statement.
                    if (info.seen)
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                });
    }

    @Override
    public void receivePostInitialize() {
        //This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TextureLoader.getTexture(imagePath("badge.png"));
        //Set up the mod information displayed in the in-game mods menu.
        //The information used is taken from your pom.xml file.

        //If you want to set up a config panel, that will be done here.
        //You can find information about this on the BaseMod wiki page "Mod Config and Panel".
        BaseMod.registerModBadge(badgeTexture, info.Name, GeneralUtils.arrToString(info.Authors), info.Description, new ModConfig());
        killCompendium();
        registerPotions();
    }

    /*----------Localization----------*/

    //This is used to load the appropriate localization files based on language.
    private static String getLangString()
    {
        return Settings.language.name().toLowerCase();
    }
    private static final String defaultLanguage = "eng";

    public static final Map<String, KeywordInfo> keywords = new HashMap<>();

    @Override
    public void receiveEditStrings() {
        /*
            First, load the default localization.
            Then, if the current language is different, attempt to load localization for that language.
            This results in the default localization being used for anything that might be missing.
            The same process is used to load keywords slightly below.
        */
        loadLocalization(defaultLanguage); //no exception catching for default localization; you better have at least one that works.
        if (!defaultLanguage.equals(getLangString())) {
            try {
                loadLocalization(getLangString());
            }
            catch (GdxRuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadLocalization(String lang) {
        //While this does load every type of localization, most of these files are just outlines so that you can see how they're formatted.
        //Feel free to comment out/delete any that you don't end up using.
        BaseMod.loadCustomStringsFile(CardStrings.class,
                localizationPath(lang, "CardStrings.json"));
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                localizationPath(lang, "CharacterStrings.json"));
        BaseMod.loadCustomStringsFile(EventStrings.class,
                localizationPath(lang, "EventStrings.json"));
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                localizationPath(lang, "OrbStrings.json"));
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                localizationPath(lang, "PotionStrings.json"));
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                localizationPath(lang, "PowerStrings.json"));
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                localizationPath(lang, "RelicStrings.json"));
        BaseMod.loadCustomStringsFile(UIStrings.class,
                localizationPath(lang, "UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords()
    {
        Gson gson = new Gson();
        String json = Gdx.files.internal(localizationPath(defaultLanguage, "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordInfo[] keywords = gson.fromJson(json, KeywordInfo[].class);
        for (KeywordInfo keyword : keywords) {
            keyword.prep();
            registerKeyword(keyword);
        }

        if (!defaultLanguage.equals(getLangString())) {
            try
            {
                json = Gdx.files.internal(localizationPath(getLangString(), "Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
                keywords = gson.fromJson(json, KeywordInfo[].class);
                for (KeywordInfo keyword : keywords) {
                    keyword.prep();
                    registerKeyword(keyword);
                }
            }
            catch (Exception e)
            {
                logger.warn(modID + " does not support " + getLangString() + " keywords.");
            }
        }
    }

    private void registerKeyword(KeywordInfo info) {
        BaseMod.addKeyword(modID.toLowerCase(), info.PROPER_NAME, info.NAMES, info.DESCRIPTION, info.COLOR);
        if (!info.ID.isEmpty())
        {
            keywords.put(info.ID, info);
        }
    }

    @Override
    public void receiveAddAudio() {
        loadAudio(Sounds.class);
    }

    private static final String[] AUDIO_EXTENSIONS = { ".ogg", ".wav", ".mp3" }; //There are more valid types, but not really worth checking them all here
    private void loadAudio(Class<?> cls) {
        try {
            Field[] fields = cls.getDeclaredFields();
            outer:
            for (Field f : fields) {
                int modifiers = f.getModifiers();
                if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers) && f.getType().equals(String.class)) {
                    String s = (String) f.get(null);
                    if (s == null) { //If no defined value, determine path using field name
                        s = audioPath(f.getName());

                        for (String ext : AUDIO_EXTENSIONS) {
                            String testPath = s + ext;
                            if (Gdx.files.internal(testPath).exists()) {
                                s = testPath;
                                BaseMod.addAudio(s, s);
                                f.set(null, s);
                                continue outer;
                            }
                        }
                        throw new Exception("Failed to find an audio file \"" + f.getName() + "\" in " + resourcesFolder + "/audio; check to ensure the capitalization and filename are correct.");
                    }
                    else { //Otherwise, load defined path
                        if (Gdx.files.internal(s).exists()) {
                            BaseMod.addAudio(s, s);
                        }
                        else {
                            throw new Exception("Failed to find audio file \"" + s + "\"; check to ensure this is the correct filepath.");
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error("Exception occurred in loadAudio: ", e);
        }
    }

    //These methods are used to generate the correct filepaths to various parts of the resources folder.
    public static String localizationPath(String lang, String file) {
        return resourcesFolder + "/localization/" + lang + "/" + file;
    }

    public static String audioPath(String file) {
        return resourcesFolder + "/audio/" + file;
    }
    public static String imagePath(String file) {
        return resourcesFolder + "/images/" + file;
    }
    public static String characterPath(String file) {
        return resourcesFolder + "/images/character/" + file;
    }
    public static String powerPath(String file) {
        return resourcesFolder + "/images/powers/" + file;
    }
    public static String relicPath(String file) {
        return resourcesFolder + "/images/relics/" + file;
    }

    /**
     * Checks the expected resources path based on the package name.
     */
    private static String checkResourcesPath() {
        String name = BorealisCards.class.getName(); //getPackage can be iffy with patching, so class name is used instead.
        int separator = name.indexOf('.');
        if (separator > 0)
            name = name.substring(0, separator);

        FileHandle resources = new LwjglFileHandle(name, Files.FileType.Internal);

        if (!resources.exists()) {
            throw new RuntimeException("\n\tFailed to find resources folder; expected it to be at  \"resources/" + name + "\"." +
                    " Either make sure the folder under resources has the same name as your mod's package, or change the line\n" +
                    "\t\"private static final String resourcesFolder = checkResourcesPath();\"\n" +
                    "\tat the top of the " + BorealisCards.class.getSimpleName() + " java file.");
        }
        if (!resources.child("images").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'images' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "images folder is in the correct location.");
        }
        if (!resources.child("localization").exists()) {
            throw new RuntimeException("\n\tFailed to find the 'localization' folder in the mod's 'resources/" + name + "' folder; Make sure the " +
                    "localization folder is in the correct location.");
        }

        return name;
    }

    /**
     * This determines the mod's ID based on information stored by ModTheSpire.
     */
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo)->{
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(), Collections.emptySet());
            return initializers.contains(BorealisCards.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;
        }
        else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    public static void registerPotions() {
        new AutoAdd(modID) //Loads files from this mod
                .packageFilter(BasePotion.class) //In the same package as this class
                .any(BasePotion.class, (info, potion) -> { //Run this code for any classes that extend this class
                    //These three null parameters are colors.
                    //If they're not null, they'll overwrite whatever color is set in the potions themselves.
                    //This is an old feature added before having potions determine their own color was possible.
                    BaseMod.addPotion(potion.getClass(), null, null, null, potion.ID, potion.playerClass);
                    //playerClass will make a potion character-specific. By default, it's null and will do nothing.
                });
    }

    @Override
    public void receivePostUpdate() {
        if(choosingTransformCard) {
            AbstractDungeon.overlayMenu.proceedButton.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.topPanel.mapHb.move(ParanoiaBoxPreventSkip.ReplayRewardSkipPositionPatch.HIDE_X, AbstractDungeon.topPanel.mapHb.cY);
        }
        if (choosingTransformCard && AbstractDungeon.gridSelectScreen.selectedCards.size() == 3) {
            for(AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractDungeon.player.masterDeck.removeCard(c);// 79
                AbstractDungeon.transformCard(c, false, AbstractDungeon.miscRng);// 80
                AbstractCard transCard = AbstractDungeon.getTransformedCard();// 81
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(transCard, c.current_x, c.current_y));// 82
            }
            choosingTransformCard = false;
            CenterGridCardSelectScreen.centerGridSelect = false;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    @Override
    public void receiveCardUsed(AbstractCard useCard) {
        if (useCard.cardID == Shiv.ID) {
            AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(AbstractDungeon.player.hand, AbstractDungeon.player.drawPile, (card -> card.cardID == RopeDart.ID), 42069));
        }
    }
}
