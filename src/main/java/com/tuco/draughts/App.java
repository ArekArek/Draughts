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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

class App {

    public static void main(String[] args) {
        start();
    }

    static class Params {
        static final int iterationsPerColor = 640;
        static final double algorithmDepth = 0.5;
        static final int statusPeriod = 10_000;
        static final double pawnValue = 1;
        static final double kingMin = 0.0, kingMax = 12.0, kingStep = 0.02;
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

        static HeuristicCalculator constantCalculator = HeuristicCalculator.builder()
                .PAWN_VALUE(1)
                .KING_VALUE(2)
                .build()
                .actType();

        static DraughtsHeuristic constantHeuristic = new DraughtsHeuristic(constantCalculator);
    }

    public static void start() {
        LocalDateTime startDate = LocalDateTime.now();
        System.out.println("START time = " + startDate);

        long start = System.nanoTime();

        Thread threadOne = new Thread(() -> {
            try {
                processOneTask(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread threadTwo = new Thread(() -> {
            try {
                processOneTask(false);
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

    private static void processOneTask(boolean isFirst) throws IOException {
        DraughtsHeuristic customHeuristic;

        Writer writer = Files.newBufferedWriter(Paths.get(isFirst ? "researchA.csv" : "researchB.csv"));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(
                "wonPercent",
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

            //white is constant
            double whitePercent = processOnePlayer(customHeuristic, true);
            //white is complex
            double blackPercent = processOnePlayer(customHeuristic, false);

            Params.allIterationsCounter++;
            csvPrinter.printRecord(String.format("%.2f%%", (whitePercent + blackPercent) / 2), Params.pawnValue, String.format("%.2f", kingIteration));
        }
        csvPrinter.println();
        csvPrinter.print(LocalDate.now());
        csvPrinter.print("iterations per color = " + Params.iterationsPerColor);
        csvPrinter.print("depth  = " + Params.algorithmDepth);
        csvPrinter.print("enemy  = " + Params.constantCalculator.toString());
        csvPrinter.flush();
    }

    private static double processOnePlayer(DraughtsHeuristic complexHeuristic, boolean isWhite) {
        int whiteWons = 0;
        int blackWons = 0;
        for (int colorIteration = 0; colorIteration < Params.iterationsPerColor; colorIteration++) {
            DraughtsState state = new DraughtsState(new StandardBoardCreator());

            AIMovementMaker whiteAI;
            AIMovementMaker blackAI;
            if (isWhite) {
                whiteAI = new AIMovementMaker(state, AlgorithmType.MINMAX, complexHeuristic);
                blackAI = new AIMovementMaker(state, AlgorithmType.MINMAX, Params.constantHeuristic);
            } else {
                whiteAI = new AIMovementMaker(state, AlgorithmType.MINMAX, Params.constantHeuristic);
                blackAI = new AIMovementMaker(state, AlgorithmType.MINMAX, complexHeuristic);
            }
            whiteAI.setDepthLimit(Params.algorithmDepth);
            blackAI.setDepthLimit(Params.algorithmDepth);

            DraughtGameManager gameManager = DraughtGameManager.builder()
                    .state(state)
                    .playerWhite(whiteAI)
                    .playerBlack(blackAI)
                    .build();

            gameManager.play();

            switch (gameManager.getWinner()) {
                case BLACK:
                    blackWons++;
                    break;
                case WHITE:
                    whiteWons++;
                    break;
            }
        }
        double allWons = whiteWons + blackWons;
        return (isWhite ? whiteWons : blackWons) / allWons;
    }

    private static void presentStatus() {
        System.out.format("%.2f%%%n", Params.allIterationsCounter * 100 / Params.allIterationsCount);
    }
}
