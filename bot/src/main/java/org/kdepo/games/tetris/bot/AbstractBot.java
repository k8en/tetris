package org.kdepo.games.tetris.bot;

import org.kdepo.games.tetris.bot.model.BotAction;
import org.kdepo.games.tetris.bot.model.BotThink;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBot implements BotThink {

    protected List<BotAction> botActionList;

    public AbstractBot() {
        botActionList = new ArrayList<>();
    }

    public BotAction getNextAction() {
        BotAction nextAction = null;
        if (!botActionList.isEmpty()) {
            nextAction = botActionList.get(0);
            botActionList.remove(0);
        }
        return nextAction;
    }
}
