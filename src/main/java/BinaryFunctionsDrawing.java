import java.awt.*;
import java.util.List;

/**
 * Created by andreyzaytsev on 31/10/2016.
 */
public class BinaryFunctionsDrawing {

    public Graphics drawFunction(Graphics g, java.util.List<StringBuilder> minForm, int width, int height, Boolean isDNF) {
        g.setColor(Color.BLACK);
        drawVariable(g, width, height, "a", 0);
        drawVariable(g, width, height, "b", width / 12);
        drawVariable(g, width, height, "c", width / 6);
        drawVariable(g, width, height, "d", width / 4);

        drawExpression(g, minForm, width, height, isDNF);

        drawOperators(g, minForm.size(), width, height, isDNF);

        return g;
    }

    public Graphics drawVariable(Graphics g, int width, int height, String variableName, int posX) {
        g.setColor(Color.BLACK);
        g.drawString("!" + variableName, width / 18 + posX, height / 40);
        g.drawString(variableName, width / 30 + posX, height / 40);
        g.drawLine(width / 20 + posX, 0, width / 20 + posX, height);
        g.drawLine(width / 20 + posX, height / 25, width / 12 + posX, height / 25);
        g.drawLine(width / 12 + posX, height / 25, width / 12 + posX, height / 20);
        g.drawRect(width / 14 + posX, height / 20, width / 50, height / 30);
        g.drawLine(width / 12 + posX, height / 12, width / 12 + posX, height);
        g.drawOval(width / 13 + posX, height / 13, width / 90, height / 90);
        g.setColor(Color.WHITE);
        g.fillOval(width / 13 + posX, height / 13, width / 90, height / 90);
        return g;
    }

    public void drawExpression(Graphics g, List<StringBuilder> minForm, int width, int height, Boolean isDNF) {
        g.setColor(Color.BLACK);
        int position = height / 10;
        for (StringBuilder vector : minForm) {
            for (int i = 0; i < vector.length(); i++) {
                if (vector.charAt(i) != 'X') {
                    drawVariableLine(g, vector.charAt(i), i, width, height, position, isDNF);
                }
                position += height / 35;
            }
            position += height / 35;
        }
    }

    public void drawVariableLine(Graphics g, char var, int varNum, int width, int height, int posY, Boolean isDNF) {
        int posX = 0;
        switch (varNum) {
            case 0:
                posX = 0;
                break;
            case 1:
                posX = width / 12;
                break;
            case 2:
                posX = width / 6;
                break;
            case 3:
                posX = width / 4;
                break;
        }
        if (isDNF) {
            if (var == '1') {
                g.drawLine(width / 20 + posX, posY, width / 2, posY);
            } else {
                g.drawLine(width / 12 + posX, posY, width / 2, posY);
            }
        } else {
            if (var == '0') {
                g.drawLine(width / 12 + posX, posY, width / 2, posY);
            } else {
                g.drawLine(width / 20 + posX, posY, width / 2, posY);
            }
        }
    }

    public void drawOperators(Graphics g, int length, int width, int height, Boolean isDNF) {
        g.setColor(Color.black);
        int posY = 0;
        for (int i = 0; i < length; i++) {
            if (isDNF) {
                g.drawString("&", width / 2 + width / 50, height / 12 + height / 25 + posY);
            } else {
                g.drawString("1", width / 2 + width / 50, height / 12 + height / 25 + posY);
            }
            g.drawRect(width / 2, height / 12 + posY, width / 25, 4 * height / 35);
            g.drawLine(width / 2 + width / 25, height / 12 + posY + height / 25, width / 2 + 2 * width / 25, height / 12 + posY + height / 25);
            posY += 5 * height / 35;
        }

        if (isDNF) {
            g.drawString("1", width / 2 + 2 * width / 25 + width / 50, height / 12 + height / 25);
        } else {
            g.drawString("&", width / 2 + 2 * width / 25 + width / 50, height / 12 + height / 25);
        }
        g.drawRect(width / 2 + 2 * width / 25, height / 12, width / 25, length * 5 * height / 35);
        g.drawLine(width / 2 + 3 * width / 25, height / 12 + (length / 2) * 4 * height / 25, width, height / 12 + (length / 2) * 4 * height / 25);
    }

}
