package com.tuco.draughts;

import com.tuco.draughts.board.util.Draughts64BoardCreator;
import com.tuco.draughts.console.ConsoleMovementInformator;
import com.tuco.draughts.console.ConsolePositionLoader;
import com.tuco.draughts.game.DraughtGameManager;
import com.tuco.draughts.game.DraughtsState;
import com.tuco.draughts.game.heuristic.Heuristic;
import com.tuco.draughts.game.util.ChangeTurnListener;
import com.tuco.draughts.movement.maker.AIMovementDescription;
import com.tuco.draughts.movement.maker.AIMovementMaker;
import com.tuco.draughts.movement.maker.AlgorithmType;
import com.tuco.draughts.movement.maker.HumanMovementMaker;
import com.tuco.draughts.movement.maker.MovementMaker;

class App {

    public static void playGame() {
        DraughtsState state = new DraughtsState(new Draughts64BoardCreator());

        AIMovementMaker aiSimple = new AIMovementMaker(state, AlgorithmType.ALPHABETA, Heuristic.SIMPLE);
        aiSimple.setDepthLimit(1.5);
        AIMovementMaker aiComplex = new AIMovementMaker(state, AlgorithmType.ALPHABETA, Heuristic.COMPLEX);
        aiComplex.setDepthLimit(2);

        MovementMaker human = new HumanMovementMaker(state, new ConsolePositionLoader(), new ConsoleMovementInformator());

        DraughtGameManager gameManager = DraughtGameManager.builder()
                .state(state)
                .playerWhite(aiSimple)
                .playerBlack(aiComplex)
                .generalChangeTurnListener(new ChangeTurnListener() {
                    @Override
                    public void beforeTurn(DraughtGameManager manager) {
                        System.out.println(manager.getState());
                    }

                    @Override
                    public void afterAITurn(AIMovementDescription movementDescription) {
                        System.out.println(movementDescription.getDepthReached());
                        System.out.println(movementDescription.getDuration());
                    }
                })
                .build();

        gameManager.play();

        System.out.println("\n\nGAME IS OVER!!!\n");

        switch (gameManager.getWinner()) {
            case WHITE:
                System.out.println("\n\nWHITE WIN!!!\n");
                break;
            case BLACK:
                System.out.println("\n\nBLACK WIN!!!\n");
                break;
            case BOTH:
                System.out.println("\n\nDRAW!!!\n");
                break;
            default:
                System.out.println("\n\nunknown\n");
                break;
        }
        System.out.println(state);
    }

    public static void main(String[] args) {
        playGame();
    }
}
