import javax.swing.*;
import java.awt.*;

public class Ball extends JComponent implements Runnable {
    private static final long serialVersionUID = 1L;
    private double x = 375; // Initial x position
    private double y = 0; // Initial y position
    private double xVelocity = 0; // Initial x velocity
    private double yVelocity = 0; // Initial y velocity
    private final double gravity = 0.1; // כוח משיכה
    private final double elasticity = 0.9; // גמישות
    private final int diameter = 30; // קוטר הכדור
    private final int bottomBoundary = 235; // הגבול התחתון החדש
    private final int topBoundary = 0;
    private final int leftWall = 86;
    private final int rightWall =700;
    private GamePanel gamePanel;
    private Thread animator;
    private Point center= new Point((int)x+15,(int)y+15);

    public Ball(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        animator = new Thread(this);
        animator.start();



    }

    @Override
    public void run() {
        while (true) {
            moveBall();
            gamePanel.repaint();
            try {
                Thread.sleep(10); // שינה של 10 מילישניות
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void moveBall() {
        yVelocity += gravity; // עדכון מהירות הכדור בהתחשב בכוח המשיכה
        x += xVelocity;
        center.setX((int)x+15);
        y += yVelocity;
        center.setY((int)y+15);
        if(xVelocity>15){
            xVelocity=15;
        }
        if(yVelocity>15){
            yVelocity=15;
        }
// התנגשות עם הקיר מעל השער השמאלי
//        if((x <= 100)&&y<=235-120){
//            xVelocity = -xVelocity * elasticity;// הכדור קופץ חזרה בהתאם לגמישות
//            x = 100;
//            center.setX((int)x+15);
//
//        }
//        // התנגשות עם הקיר מעל השער הימני
//        if((x>=800 - diameter-100)&&y<=120 ){
//            xVelocity = -xVelocity * elasticity*0.3;// הכדור קופץ חזרה בהתאם לגמישות
//            x =800 - diameter-100;
//            center.setX((int)x+15);
//        }

//         בדיקת התנגשות עם גבולות המסך
        if (x <= 40 || x >= 786 - diameter-40) {
            xVelocity = -xVelocity * elasticity; // הכדור קופץ חזרה בהתאם לגמישות
            if (x <= 40) {
                x = 40;
                center.setX((int)x+15);
            } else {
                x = 786 - diameter-40;
                center.setX((int)x+15);

            }
        }
        if ((x <= 110&&y<=120) || (x >= 786 - diameter-110&&y<=120)) {
            xVelocity = -xVelocity * elasticity; // הכדור קופץ חזרה בהתאם לגמישות
            if (x <= 110) {
                x = 110;
                center.setX((int)x+15);
            } else {
                x = 786 - diameter-110;
                center.setX((int)x+15);
            }
        }
        //גול של player1
        if(center.getX()<=110){
            gamePanel.goal(1);
            x=375;
            center.setX((int)x+15);
            y=0;
            center.setY((int)y+15);
            xVelocity=-3;
            yVelocity=3;
        }
        //גול של player2
        if(center.getX()>=786-110){
            gamePanel.goal(2);
            x=375;
            center.setX((int)x+15);
            y=0;
            center.setY((int)y+15);
            xVelocity=3;
            yVelocity=3;
        }
        //התנגשות עם הראש של player1
        double incline,yCalculation,xCalculation;
        if (gamePanel.getPlayer1().getCenterOfTheHead().distance(center)<=30)
        {
            xCalculation=gamePanel.getPlayer1().getCenterOfTheHead().getX()- center.getX();
            yCalculation=gamePanel.getPlayer1().getCenterOfTheHead().getY()- center.getY();
            if(xCalculation!=0) {
                incline = yCalculation / xCalculation;
                // כשהכדור מגיע מלמעלה מימין
                if(xCalculation<0&&yCalculation>0) {
                    if (incline <= -1) {
                        yVelocity = 6 / (-incline + 1) * incline;
                        xVelocity = 6 / (-incline + 1);

                    }
                    if (incline > -1 && incline < 0) {
                        incline = 1 / incline;
                        xVelocity = 6 / -(-incline + 1) * incline;
                        yVelocity = 6 / (incline + 1);
                    }
                }
                //כשהכדור מגיע מלמטה מימין
                if(yCalculation<0&&xCalculation<0){
                    if (incline >= 1) {
                        yVelocity = 6 / (incline +1) * incline;
                        xVelocity = 6 / (incline + 1);

                    }
                    if (incline < 1 && incline > 0) {
                        incline = 1 / incline;
                        xVelocity = 6 / (incline + 1) * incline;
                        yVelocity = 6 / (incline + 1);
                    }
                }
                //כשהכדור מגיע מלמטה משמאל
                if(yCalculation<0&&xCalculation>0){
                    if (incline <= -1) {
                        yVelocity = 6 / -(-incline +1) * incline;
                        xVelocity = 6 / (incline + 1);

                    }
                    if (incline > -1 && incline < 0) {
                        incline = 1 / incline;
                        xVelocity = 6 / (-incline + 1) * incline;
                        yVelocity = 6 / (-incline + 1);
                    }
                }
                //כשהכדור מגיע מלמעלה משמאל
                if(yCalculation>0&&xCalculation>0){
                    if (incline >= 1) {
                        yVelocity = 6 / -(incline +1) * incline;
                        xVelocity = 6 / -(incline + 1);

                    }
                    if (incline < 1 && incline > 0) {
                        incline = 1 / incline;
                        xVelocity = 6 / -(incline + 1) * incline;
                        yVelocity = 6 / -(incline + 1);
                    }
                }
                //כשהכדור מגיע ישר מהצד
                if(incline==0){
                    if (xCalculation<0) {
                        xVelocity = -6;
                        yVelocity = 0;
                    }else {
                        xVelocity = 6;
                        yVelocity = 0;
                    }
                }
            }else{
                if(yCalculation>=0) {
                    xVelocity = 0;
                    yVelocity = -6;
                }else {
                    xVelocity = 0;
                    yVelocity = 6;
                }
            }
        }
        //התנגשות עם הראש של player2

        if (gamePanel.getPlayer2().getCenterOfTheHead().distance(center)<=30)
        {
            xCalculation=gamePanel.getPlayer2().getCenterOfTheHead().getX()- center.getX();
            yCalculation=gamePanel.getPlayer2().getCenterOfTheHead().getY()- center.getY();
            if(xCalculation!=0) {
                incline = yCalculation / xCalculation;
                // כשהכדור מגיע מלמעלה מימין
                if(xCalculation<0&&yCalculation>0) {
                    if (incline <= -1) {
                        yVelocity = 6 / (-incline + 1) * incline;
                        xVelocity = 6 / (-incline + 1);

                    }
                    if (incline > -1 && incline < 0) {
                        incline = 1 / incline;
                        xVelocity = 6 / -(-incline + 1) * incline;
                        yVelocity = 6 / (incline + 1);
                    }
                }
                //כשהכדור מגיע מלמטה מימין
                if(yCalculation<0&&xCalculation<0){
                    if (incline >= 1) {
                        yVelocity = 6 / (incline +1) * incline;
                        xVelocity = 6 / (incline + 1);

                    }
                    if (incline < 1 && incline > 0) {
                        incline = 1 / incline;
                        xVelocity = 6 / (incline + 1) * incline;
                        yVelocity = 6 / (incline + 1);
                    }
                }
                //כשהכדור מגיע מלמטה משמאל
                if(yCalculation<0&&xCalculation>0){
                    if (incline <= -1) {
                        yVelocity = 6 / -(-incline +1) * incline;
                        xVelocity = 6 / (incline + 1);

                    }
                    if (incline > -1 && incline < 0) {
                        incline = 1 / incline;
                        xVelocity = 6 / (-incline + 1) * incline;
                        yVelocity = 6 / (-incline + 1);
                    }
                }
                //כשהכדור מגיע מלמעלה משמאל
                if(yCalculation>0&&xCalculation>0){
                    if (incline >= 1) {
                        yVelocity = 6 / -(incline +1) * incline;
                        xVelocity = 6 / -(incline + 1);

                    }
                    if (incline < 1 && incline > 0) {
                        incline = 1 / incline;
                        xVelocity = 6 / -(incline + 1) * incline;
                        yVelocity = 6 / -(incline + 1);
                    }
                }
                //כשהכדור מגיע ישר מהצד
                if(incline==0){
                    if (xCalculation<0) {
                        xVelocity = -6;
                        yVelocity = 0;
                    }else {
                        xVelocity = 6;
                        yVelocity = 0;
                    }
                }
            }else{
                if(yCalculation>=0) {
                    xVelocity = 0;
                    yVelocity = -6;
                }else {
                    xVelocity = 0;
                    yVelocity = 6;
                }
            }
        }
//        // בדיקת התנגשות בplayer1
//        //בדיקת התנגשות בגוף בצד ימין
//        boolean isThereCollision=false;
//        Point temp;
//        for(int i=0;i<30;i++){
//            temp=new Point(gamePanel.getPlayer1().getUpperRight().getX(),gamePanel.getPlayer1().getUpperRight().getY()+i);
//            if(temp.distance(center)<=15)
//            {isThereCollision = true;}
//
//            if(i<15) {
//                temp = new Point(gamePanel.getPlayer1().getUpperRight().getX() - i, gamePanel.getPlayer1().getUpperRight().getY());
//                if(temp.distance(center)<=15)
//                { isThereCollision = true;}
//                temp = new Point(gamePanel.getPlayer1().getUpperRight().getX() - i, gamePanel.getPlayer1().getUpperRight().getY()+30);
//                if(temp.distance(center)<=15)
//                { isThereCollision = true;}
//
//            }
//        }
//        if(isThereCollision)
//        {
//            x=gamePanel.getPlayer1().getUpperRight().getX()+diameter;
//            center.setX((int)x+15);
//            xVelocity=5;
//            yVelocity=-10;
//        }
//        isThereCollision=false;
//        //בדיקת התנגשות בגוף בצד שמאל
//        for(int i=0;i<30;i++){
//            temp=new Point(gamePanel.getPlayer1().getLowerLeft().getX(),gamePanel.getPlayer1().getLowerLeft().getY()-i);
//            if(temp.distance(center)<=15)
//            {isThereCollision = true;}
//
//            if(i<15) {
//                temp = new Point(gamePanel.getPlayer1().getLowerLeft().getX() + i, gamePanel.getPlayer1().getLowerLeft().getY());
//                if(temp.distance(center)<=15)
//                { isThereCollision = true;}
//                temp = new Point(gamePanel.getPlayer1().getLowerLeft().getX() - i, gamePanel.getPlayer1().getLowerLeft().getY()-30);
//                if(temp.distance(center)<=15)
//                { isThereCollision = true;}
//
//            }
//        }
//        if(isThereCollision){
//            x=gamePanel.getPlayer1().getLowerLeft().getX();
//            center.setX((int)x+15);
//            xVelocity=-5;
//            yVelocity=2;
//        }
//        // בדיקת התנגשות בplayer2
//        //בדיקת התנגשות בגוף בצד ימין
//        isThereCollision=false;
//        for(int i=0;i<30;i++){
//            temp=new Point(gamePanel.getPlayer2().getUpperRight().getX(),gamePanel.getPlayer2().getUpperRight().getY()+i);
//            if(temp.distance(center)<=15)
//            {isThereCollision = true;}
//
//            if(i<15) {
//                temp = new Point(gamePanel.getPlayer2().getUpperRight().getX() - i, gamePanel.getPlayer2().getUpperRight().getY());
//                if(temp.distance(center)<=15)
//                { isThereCollision = true;}
//                temp = new Point(gamePanel.getPlayer2().getUpperRight().getX() - i, gamePanel.getPlayer2().getUpperRight().getY()+30);
//                if(temp.distance(center)<=15)
//                { isThereCollision = true;}
//
//            }
//        }
//        if(isThereCollision)
//        {
//            x=gamePanel.getPlayer2().getUpperRight().getX()+diameter+1;
//            center.setX((int)x+15);
//            xVelocity=5;
//            yVelocity=-10;
//        }
//        isThereCollision=false;
//        //בדיקת התנגשות בגוף בצד שמאל
//        for(int i=0;i<30;i++){
//            temp=new Point(gamePanel.getPlayer2().getLowerLeft().getX(),gamePanel.getPlayer2().getLowerLeft().getY()-i);
//            if(temp.distance(center)<=15)
//            {isThereCollision = true;}
//
//            if(i<15) {
//                temp = new Point(gamePanel.getPlayer2().getLowerLeft().getX() + i, gamePanel.getPlayer2().getLowerLeft().getY());
//                if(temp.distance(center)<=15)
//                { isThereCollision = true;}
//                temp = new Point(gamePanel.getPlayer2().getLowerLeft().getX() - i, gamePanel.getPlayer2().getLowerLeft().getY()-30);
//                if(temp.distance(center)<=15)
//                { isThereCollision = true;}
//
//            }
//        }
//        if(isThereCollision){
//            x=gamePanel.getPlayer2().getLowerLeft().getX()-1;
//            center.setX((int)x+15);
//            xVelocity=-5;
//            yVelocity=2;
//        }
//        isThereCollision=false;

//        if(gamePanel.getPlayer1().getLowerLeft().getX()>=x&&gamePanel.getPlayer1().getLowerLeft().getY()<=y+15&&gamePanel.getPlayer1().getUpperRight().getX()>=x&&gamePanel.getPlayer1().getLowerLeft().getY()<=y+15){
//            if(center.getX()>gamePanel.getPlayer1().getUpperRight().getX()+15){
//                x=gamePanel.getPlayer1().getLowerLeft().getX();
//                center.setX((int)x+15);
//                xVelocity=5;
//                yVelocity=2;
//            }else{
//                x=gamePanel.getPlayer1().getUpperRight().getX()-30;
//                center.setX((int)x+15);
//                xVelocity=-5;
//                yVelocity=2;
//            }
//        }

        //בדיקת התנגשות בתחתית
        if (y >= bottomBoundary - diameter) {
            y = bottomBoundary - diameter;
            center.setY((int)y+15);
            yVelocity = -yVelocity * elasticity; // הכדור קופץ חזרה בהתאם לגמישות
        }
        //בדיקת התנגשות בתקרה
        if(y<=0){
            y=0;
            center.setY((int)y+15);
            yVelocity = -yVelocity * elasticity; // הכדור קופץ חזרה בהתאם לגמישות


        }

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.RED);
        g2d.fillOval((int) x, (int) y, diameter, diameter);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600); // Set preferred size to match the frame
    }
}
