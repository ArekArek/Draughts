package com.tuco.draughts.console;

import com.tuco.draughts.movement.maker.HumanMovementInformator;
import com.tuco.draughts.movement.util.Movement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ConsoleMovementInformator implements HumanMovementInformator {

    private final static Logger LOG = LogManager.getLogger(ConsoleMovementInformator.class);

    @Override
    public void choosePosition(List<Movement> movements) {
        LOG.info("Insert position");
    }

    @Override
    public void wrongPositionChosen() {
        LOG.info("Incorrect position inserted");
    }
}