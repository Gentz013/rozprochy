package logic;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jan on 4/19/2015.
 */
public class ServerManager implements IServerManager {

    private final List<Player> playersWaitingForGame = new ArrayList<Player>();
    private final Queue<Player> playerWaitingForAnotherPlayer = new LinkedList<Player>();
    private final Map<Player, Player> gameSessions = new HashMap<Player, Player>();
    private final AtomicInteger computerPlayersCounter = new AtomicInteger(0);

    @Override
    public void register(Player player) throws RemoteException, PlayerRejectedException {

        synchronized (playerWaitingForAnotherPlayer){
            if(playerWaitingForAnotherPlayer.contains(player)){
                throw new PlayerRejectedException("Player with the same username already exists");
            }

            synchronized (gameSessions){
                if(gameSessions.containsKey(player) || gameSessions.containsValue(player)){
                    throw new PlayerRejectedException("Player with the same username already exists");
                }

                synchronized (playersWaitingForGame){
                    if(playersWaitingForGame.contains(player)){
                        throw new PlayerRejectedException("Player with the same username already exists");
                    }

                    playersWaitingForGame.add(player);
                }
            }
        }
    }




    @Override
    public void chooseGame(Player player, int gametype) throws RemoteException, PlayerRejectedException {

        synchronized (playersWaitingForGame) {

            if(!playersWaitingForGame.contains(player)){
                throw new PlayerRejectedException("Player not found");
            }

            if (gametype > 0) {
                System.out.println("AI chosen by player: " + player.getNickname());
                //vs AI, let's create computer player
                Player bot = new Player("bot" + computerPlayersCounter.incrementAndGet());
                bot.setMoveListener(new AIMoveListener(this, bot));
                initializeGameDetails(player, bot);

            } else {
                //vs Player
                System.out.println("Another player chosen by player: " + player.getNickname());
                synchronized (playerWaitingForAnotherPlayer){
                    if(playerWaitingForAnotherPlayer.isEmpty()){
                        playerWaitingForAnotherPlayer.add(player);
                    } else {
                        Player enemy = playerWaitingForAnotherPlayer.remove();
                        initializeGameDetails(enemy, player);
                        //init game details


                    }
                }

            }

            playersWaitingForGame.remove(player);
        }

    }

    private void initializeGameDetails(Player playerOne, Player playerTwo) {
        setPlayerCellOrSafelyDisconnect(playerOne, playerTwo, -1);
        setPlayerCellOrSafelyDisconnect(playerTwo, playerOne, 1);

        synchronized (gameSessions) {
            gameSessions.put(playerOne, playerTwo);
        }

        try {
            BoardData gameBoard = new BoardData();
            playerOne.getMoveListener().onPlayerMove(playerTwo, gameBoard);
        } catch (RemoteException e) {
            System.err.println("connection problem");
            synchronized (gameSessions) {
                gameSessions.remove(playerOne);
            }

        }
    }

    private void setPlayerCellOrSafelyDisconnect(Player playerToSetCell, Player playerToInform, int cellType) {
        try {
            playerToSetCell.getMoveListener().setPlayerCell(cellType);
        } catch (RemoteException e) {
            System.err.println("Cannot set player's cell type");

            try {
                playerToInform.getMoveListener().onError("Problem with starting the game - disconnect and try again");
            } catch (RemoteException e1) {
                System.err.println("Problem with player's connection");
            }
        }
    }

    @Override
    public void doMove(Player player, BoardData gameBoard) throws RemoteException, PlayerRejectedException {
        Player enemy = null;
        Player playerOne, playerTwo;


        synchronized(gameSessions){
            if(gameSessions.containsKey(player)){
                enemy = gameSessions.get(player);
                playerOne = player;
                playerTwo = enemy;
            } else {
                for(Map.Entry<Player, Player> entry : gameSessions.entrySet()) {
                    if(entry.getValue().equals(player)) {
                        enemy = entry.getKey();
                        break;
                    }
                }

                if(enemy == null) {
                    throw new PlayerRejectedException("Player does not play any game");
                }

                playerOne = enemy;
                playerTwo = player;
            }
        }

        int gameIsFinished = gameBoard.ifGameWon(playerOne, playerTwo);

        IMoveListener playerMoveListener = player.getMoveListener();
        IMoveListener rivalIMoveListener = enemy.getMoveListener();

        System.out.println(gameIsFinished);
        if(gameIsFinished != 0) {
            System.out.println("GAME ENDED!");
            synchronized(gameSessions) {
                gameSessions.remove(playerOne);
            }

            Player winner = gameBoard.getWinner();

            if(winner == null) {
                playerMoveListener.onTie(gameBoard);
                rivalIMoveListener.onTie(gameBoard);
            } else if(winner.equals(enemy)) {
                playerMoveListener.onLoss(gameBoard);
                rivalIMoveListener.onVictory(gameBoard);
            } else {
                playerMoveListener.onVictory(gameBoard);
                rivalIMoveListener.onLoss(gameBoard);
            }
        } else {
            rivalIMoveListener.onPlayerMove(player, gameBoard);
        }

    }
}

