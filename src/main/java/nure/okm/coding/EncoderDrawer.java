package nure.okm.coding;

import java.awt.*;

/**
 * Created by Alexander on 05.04.2015.
 */
public class EncoderDrawer {
  private Encoder encoder;
  private Point lastPoint;
  private int lineWidth;
  private int lineHeight;

  public EncoderDrawer(Encoder encoder) {
    this.encoder = encoder;
  }

  public void draw4B5B(Graphics2D g, Dimension d) {
    lineWidth = d.width / encoder.get4Bto5BArray().length;
    lineHeight = d.height / 4;
    lastPoint = new Point(1, d.height / 2);
    g.clearRect(0, 0, d.width, d.height);
    boolean toggleByOne = false;
    for (Byte bit : encoder.get4Bto5BArray()) {
      if (bit == 0) {
        drawLineRight(g);
      } else if (bit == 1 && toggleByOne) {
        toggleByOne = !toggleByOne;
        drawLineUpRight(g);
        drawLineDown(g);
      } else {
        toggleByOne = !toggleByOne;
        drawLineDownRight(g);
        drawLineUp(g);
      }
    }
  }

  public void drawMLT3(Graphics2D g, Dimension d) {
    lineWidth = d.width / encoder.getMLT3Array().length;
    lineHeight = d.height / 4;
    lastPoint = new Point(1, d.height / 2);
    int MAX_UP_STEPS = 2;
    int MAX_DOWN_STEPS = 4;
    g.clearRect(0, 0, (int)d.getWidth(), (int)d.getHeight());
    int counterOfSteps = 0;
    for (Byte element : encoder.getMLT3Array()) {
      if (element == 0) {
        drawLineRight(g);
      } else if (counterOfSteps < MAX_UP_STEPS) {
        counterOfSteps++;
        drawLineUpRight(g);
      } else {
        counterOfSteps++;
        drawLineDownRight(g);
        if (counterOfSteps == MAX_DOWN_STEPS) counterOfSteps = 0;
      }
    }
  }

  private void drawLineUpRight(Graphics2D g) {
    drawLineUp(g);
    drawLineRight(g);
  }

  private void drawLineDownRight(Graphics2D g) {
    drawLineDown(g);
    drawLineRight(g);
  }

  private void drawLineRight(Graphics2D g) {
    Point right = new Point(lastPoint.x + lineWidth, lastPoint.y);
    drawLine(lastPoint, right, g);
  }

  private void drawLineUp(Graphics2D g) {
    Point up = new Point(lastPoint.x, lastPoint.y - lineHeight);
    drawLine(lastPoint, up, g);
  }

  private void drawLineDown(Graphics2D g) {
    Point down = new Point(lastPoint.x, lastPoint.y + lineHeight);
    drawLine(lastPoint, down, g);
  }

  private void drawLine(Point one, Point two, Graphics2D g) {
    g.setColor(Color.black);
    g.drawLine(one.x, one.y, two.x, two.y);
    lastPoint = new Point(two);
  }

}
