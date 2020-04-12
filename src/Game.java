//Java GUI based TicTacToe game
//Rathin Kamble
//12-04-2020

//This game is programmed such that the CPU will play as the 'X' and the user can
// play as the 'O' and implements the minimax() method. You can use the Alpha-Beta
// prunning algorithm in this code too.

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game {

    JFrame frame;
    JFrame resframe;
    JFrame preframe;
    JLabel result;
    JButton Obtn = new JButton("You first");
    JButton Xbtn = new JButton("Player AI first");
    private String[][] board = {{"","",""},{"","",""},{"","",""}};
    private Boxes canvas;
    private final int BOXSIZE = 200;
    private int width = BOXSIZE;
    private int height = BOXSIZE;
    private String playerAI = "X";
    private String opponent = "O";
    private String currentPlayer;
    private boolean flag = true;

    public Game(){
        preGame();
    }

    // Makes a frame which gives you option who starts the game first and display the game
    public void preGame(){
        preframe = new JFrame("Choose");
        preframe.setLocation(700,320);
        preframe.setSize(400,400);
        preframe.setVisible(true);
        preframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        preframe.setResizable(false);
        preframe.getContentPane().setLayout(null);

        Obtn.setBounds(145,100,120,25);
        preframe.getContentPane().add(Obtn);

        Xbtn.setBounds(145,200,120,25);
        preframe.getContentPane().add(Xbtn);

        Obtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPlayer = opponent;
                displayGame();
            }
        });

        Xbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPlayer = playerAI;
                displayGame();
            }
        });


    }

    //Displays the basic structure of game board and starts the game
    public void displayGame(){
        frame = new JFrame("TicTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(610,610);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        canvas = new Boxes();
        frame.getContentPane().add(canvas);

        startGame();

    }

    public static void main(String args []){
        new Game();
    }

    public void Update(){canvas.repaint();}

    public void startGame(){
        bestMove();
    }

    public void checkWinner(){


        if(areMovesLeft(board) == false){
            resframe = new JFrame("Result");
            resframe.setLocation(620,520);
            resframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            resframe.setSize(500,150);
            resframe.setVisible(true);
            result = new JLabel("TIED");
            result.setFont(new Font("Calibri", Font.BOLD, 20));
            Border border = BorderFactory.createLineBorder(Color.BLACK);
            result.setBorder(border);
            result.setPreferredSize(new Dimension(150,100));
            result.setHorizontalAlignment(JLabel.CENTER);
            result.setVerticalAlignment(JLabel.CENTER);
            resframe.add(result);
        }
        else if (scorer(board) == +5){
            resframe = new JFrame("Result");
            resframe.setLocation(620,520);
            resframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            resframe.setSize(500,150);
            resframe.setVisible(true);
            result = new JLabel("X WON");
            result.setFont(new Font("Calibri", Font.BOLD, 20));
            Border border = BorderFactory.createLineBorder(Color.BLACK);
            result.setBorder(border);
            result.setPreferredSize(new Dimension(150,100));
            result.setHorizontalAlignment(JLabel.CENTER);
            result.setVerticalAlignment(JLabel.CENTER);
            resframe.add(result);
        }
        else if(scorer(board) == -5){
            resframe = new JFrame("Result");
            resframe.setLocation(620,520);
            resframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            resframe.setSize(500,150);
            resframe.setVisible(true);
            result = new JLabel("O WON");
            result.setFont(new Font("Calibri", Font.BOLD, 20));
            Border border = BorderFactory.createLineBorder(Color.BLACK);
            result.setBorder(border);
            result.setPreferredSize(new Dimension(150,100));
            result.setHorizontalAlignment(JLabel.CENTER);
            result.setVerticalAlignment(JLabel.CENTER);
            resframe.add(result);
        }
    }

    public boolean areMovesLeft(String board[][]){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board[i][j] == ""){
                    return true;
                }
            }
        }
        return false;
    }

    //Used to indicate which move is better to the AI
    public int scorer(String board[][]){

        //Checks if rows are filled
        for(int i = 0; i < 3; i++){
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2]){
                if(board[i][0].equals(playerAI)){
                    return +5;
                }
                else if(board[i][0].equals(opponent)){
                    return -5;
                }
            }
        }

        //Checks if columns are filled
        for(int j = 0; j < 3; j++){
            if(board[0][j] == board[1][j] && board[1][j] == board[2][j]){
                if(board[0][j].equals(playerAI)){
                    return +5;
                }
                else if(board[0][j].equals(opponent)){
                    return -5;
                }
            }
        }

        //Checks if diagonals are filled
        if((board[0][0] == board[1][1])&& (board[1][1] == board[2][2])){
            if(board[0][0].equals(playerAI)){
                return +5;
            }
            else if(board[0][0].equals(opponent)){
                return -5;
            }
        }

        if((board[0][2] == board[1][1]) && (board[1][1] == board[2][0])){
            if(board[0][2].equals(playerAI)){
                return +5;
            }
            else if(board[0][2].equals(opponent)){
                return -5;
            }
        }

        //If tie it returns 0
        return 0;
    }

    class Move {
        int row;
        int col;
    }

    //Calls minimax() function to detect which move is the best and hence plays it on the board
    public void bestMove(){
        int bestVal = -1000;
        Move move = new Move();
        move.row = -1;
        move.col = -1;

        if(currentPlayer == opponent && flag){
            Update();
            flag = false;
        }
        else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == "") {
                        board[i][j] = playerAI;
                        int eval = minimax(board, 0, false);
                        board[i][j] = "";

                        if (eval > bestVal) {
                            move.row = i;
                            move.col = j;
                            bestVal = eval;
                        }
                    }
                }
            }
            board[move.row][move.col] = playerAI;
            Update();
            checkWinner();
            currentPlayer = opponent;
        }
    }

    //Returns the value which has the best chance to win the game by evaluating the move
    //placed by the opponent on the board
    public int minimax(String board[][], int depth, boolean isMaximazing){
        int score = scorer(board);

        if(score == +5){return score;}
        if(score == -5){return score;}
        if(areMovesLeft(board) == false){return 0;}

        if(isMaximazing){
            int maxEval = -1000;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if(board[i][j] == ""){
                        board[i][j] = playerAI;
                        maxEval = Math.max(maxEval,minimax(board,depth+1,false));
                        board[i][j] = "";
                    }
                }
            }
            return maxEval;
        }
        else{
            int minEval = 1000;
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if(board[i][j] == ""){
                        board[i][j] = opponent;
                        minEval = Math.min(minEval,minimax(board,depth+1,true));
                        board[i][j] = "";
                    }
                }
            }
            return minEval;
        }
    }

    //Class used to draw the TicTacToe grid, 'X's and 'O's
    class Boxes extends JPanel implements MouseListener{
        public Boxes(){
            addMouseListener(this);
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;

            g2.setStroke(new BasicStroke(2));

            //To draw the vertical and horizontal lines of the grid
            for(int i = 0; i < 2; i++){
                g2.drawLine(0,(i+1)*height,600,(i+1)*height);
            }
            for(int i = 0; i < 2; i++){
                g2.drawLine((i+1)*width,0,(i+1)*width,600);
            }


            g2.setStroke(new BasicStroke(3));
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    int x = width * i + width/2;
                    int y = height * j + height/2;
                    String letter = board[i][j];
                    if(letter == "X"){
                        int fixer = width/4;
                        g2.drawLine(x-fixer,y-fixer,x+fixer,y+fixer);
                        g2.drawLine(x+fixer, y-fixer, x-fixer, y+fixer);
                    }
                    else if(letter == "O"){
                        g2.drawOval(x-width/4,y-height/4,width/2,height/2);
                    }
                }
            }

        }

        @Override
        public void mouseClicked(MouseEvent e){}

        //Checks if the current player is the opponent(You) and if so draws an 'O' on the
        // board, checks the winner and sets the current player as CPU
        @Override
        public void mousePressed(MouseEvent e) {
            if(currentPlayer == opponent){
                try {
                    int x = e.getX() / BOXSIZE;
                    int y = e.getY() / BOXSIZE;
                    if(board[x][y] == ""){
                        board[x][y] = opponent;
                        currentPlayer = playerAI;
                        Update();
                        checkWinner();
                        bestMove();
                    }
                }catch (Exception z){}
            }
        }

        @Override
        public void mouseReleased(MouseEvent e){}

        @Override
        public void mouseEntered(MouseEvent e){}

        @Override
        public void mouseExited(MouseEvent e){}

    }

}
