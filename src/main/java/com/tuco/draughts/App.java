package com.tuco.draughts;

import com.tuco.draughts.board.util.StandardBoardCreator;
import com.tuco.draughts.game.DraughtGameManager;
import com.tuco.draughts.game.DraughtsState;
import com.tuco.draughts.game.heuristic.DraughtsHeuristic;
import com.tuco.draughts.game.heuristic.HeuristicCalculator;
import com.tuco.draughts.movement.maker.AIMovementMaker;
import com.tuco.draughts.movement.maker.AlgorithmType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

class App {

    public static void main(String[] args) {
        start();
    }

    static class Params {
        static final int iterationsPerColor = 20;
        static final int statusPeriod = 2_000;
        static final double pawnValue = 1;
        static final double kingMin = 1.6, kingMax = 2.2, kingStep = 0.02;
        //        static final double safeFactor;
//        static final double distanceFactor;
//        static final double firstLineValue;
//        static final double defenderKingValue;
//        static final double kingMainDiagonal;
//        static final double triangleValue;
//        static final double oreoValue;
//        static final double bridgeValue;
//        static final double dogValue;
        static int allIterationsCounter = 0;
        static final double allIterationsCount = evaluateIterations();

        static double evaluateIterations() {
            return (kingMax - kingMin) / kingStep;
        }
    }

    public static void start() {
        LocalDateTime startDate = LocalDateTime.now();
        System.out.println("START time = " + startDate);
        HeuristicCalculator constantCalculator = HeuristicCalculator.builder()
                .PAWN_VALUE(1)
                .KING_VALUE(2)
                .build()
                .actType();

        DraughtsHeuristic constantHeuristic = new DraughtsHeuristic(constantCalculator);
        long start = System.nanoTime();

        Thread threadOne = new Thread(() -> {
            try {
                processOneTask(constantHeuristic, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread threadTwo = new Thread(() -> {
            try {
                processOneTask(constantHeuristic, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        TimerTask statusTask = new TimerTask() {
            @Override
            public void run() {
                presentStatus();
            }
        };

        Timer statusTimer = new Timer(true);
        statusTimer.scheduleAtFixedRate(statusTask, Params.statusPeriod, Params.statusPeriod);

        threadOne.start();
        threadTwo.start();

        try {
            threadOne.join();
            threadTwo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        statusTimer.cancel();

        LocalDateTime endDate = LocalDateTime.now();
        System.out.println("END time = " + endDate);
        long elapsedTime = System.nanoTime() - start;
        System.out.println("total time = " + elapsedTime / 1_000_000_000 / 60 + "m " + elapsedTime / 1_000_000_000 % 60 + "s");
    }

    private static void processOneTask(DraughtsHeuristic constantHeuristic, boolean isFirst) throws IOException {
        DraughtsHeuristic customHeuristic;

        AtomicInteger wonCount, loseCount, drawCount;

        Writer writer = Files.newBufferedWriter(Paths.get(isFirst ? "researchA.csv" : "researchB.csv"));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(
                "won",
                "lose",
                "draw",
                "pawn",
                "king"));

        for (double kingIteration = isFirst ? Params.kingMin : (Params.kingMax + Params.kingMin) / 2;
             kingIteration <= (isFirst ? (Params.kingMax + Params.kingMin) / 2 : Params.kingMax); kingIteration += Params.kingStep) {
            HeuristicCalculator heuristicCalculator = HeuristicCalculator.builder()
                    .PAWN_VALUE(Params.pawnValue)
                    .KING_VALUE(kingIteration)
                    .build()
                    .actType();
            customHeuristic = new DraughtsHeuristic(heuristicCalculator);

            wonCount = new AtomicInteger(0);
            loseCount = new AtomicInteger(0);
            drawCount = new AtomicInteger(0);

            //white is constant
            processOnePlayer(constantHeuristic, customHeuristic, loseCount, wonCount, drawCount);
            //white is complex
            processOnePlayer(customHeuristic, constantHeuristic, wonCount, loseCount, drawCount);

            Params.allIterationsCounter++;
            csvPrinter.printRecord(wonCount, loseCount, drawCount, Params.pawnValue, kingIteration);
        }
        csvPrinter.flush();
    }

    private static void processOnePlayer(DraughtsHeuristic whiteHeuristic, DraughtsHeuristic blackHeuristic, AtomicInteger whiteWons, AtomicInteger blackWons, AtomicInteger draws) {
        for (int colorIteration = 0; colorIteration < Params.iterationsPerColor; colorIteration++) {
            DraughtsState state = new DraughtsState(new StandardBoardCreator());

            AIMovementMaker whiteAI = new AIMovementMaker(state, AlgorithmType.MINMAX, whiteHeuristic);
            whiteAI.setDepthLimit(1.5);

            AIMovementMaker blackAI = new AIMovementMaker(state, AlgorithmType.MINMAX, blackHeuristic);
            blackAI.setDepthLimit(1.5);

            DraughtGameManager gameManager = DraughtGameManager.builder()
                    .state(state)
                    .playerWhite(whiteAI)
                    .playerBlack(blackAI)
                    .build();

            gameManager.play();

            switch (gameManager.getWinner()) {
                case BLACK:
                    blackWons.incrementAndGet();
                    break;
                case WHITE:
                    whiteWons.incrementAndGet();
                    break;
                case BOTH:
                    draws.incrementAndGet();
                    break;
            }
        }
    }

    private static void presentStatus() {
        System.out.format("%.2f%%%n", Params.allIterationsCounter / Params.allIterationsCount);
    }
}
