package Model;

import Data.*;

import GUI.Tools.Draw;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zy on 27/09/2017.
 */
public class CanvasInteraction {

    private Canvas permanentCanvas;
    private Canvas temporaryCanvas;

    private CanvasLog log;

    private CanvasStatus status;

    private Draw draw;

    /**
     * initialize the model
     * @param permanentCanvas
     * @param temporaryCanvas
     */
    public CanvasInteraction(Canvas permanentCanvas, Canvas temporaryCanvas) {
        this.permanentCanvas = permanentCanvas;
        this.temporaryCanvas = temporaryCanvas;

        log = new CanvasLog(CanvasHelper.canvasToMatrix(permanentCanvas));

        status = new CanvasStatus();

        Canvas[] tmpCanvas = new Canvas[2];
        tmpCanvas[Draw.PERMANENT_LAYER] = permanentCanvas;
        tmpCanvas[Draw.TEMPORARY_LAYER] = temporaryCanvas;
        draw = new Draw(tmpCanvas);

    }

    /**
     * draw some pixels without adding new log
     * this method is used by undo
     * @param difference
     */
    public void drawFreeWithoutLogging(PixelsDifference difference) {
        draw.drawFree(difference.getPixels(), Draw.PERMANENT_LAYER);
    }

    /**
     * draw some pixels based on PixelsDifference
     * @param difference
     */
    public void drawFree(PixelsDifference difference) {
        drawFreeWithoutLogging(difference);
        updateLog();
    }

    /**
     * draw some pixels based on a Pixel list
     * @param pixels
     */
    public void drawFree(ArrayList<Pixel> pixels) {
        draw.drawFree(pixels, Draw.PERMANENT_LAYER);
        updateLog();
    }

    /**
     * start drawing (press the LMB, Left Mouse Button)
     * @param start
     * @param color
     * @param lineStyle
     */
    public void startDrawFree(Coord start, Color color , int lineStyle) {
        status.drawFree(start);
        System.out.print("startDrawFree"+start+color+"|"+lineStyle+"\n");
    }

    /**
     * continue drawing ( moving the mouse while the LMB is pressed)
     * @param current
     * @param color
     * @param lineStyle
     */
    public void continueDrawFree(Coord current, Color color, int lineStyle) {
        if (status.status() != CanvasStatus.DRAW_FREE)
            return;

        draw.drawLine(status.stratCoord(), current, color, lineStyle, Draw.PERMANENT_LAYER);
        status.drawFree(current);
        System.out.print("continueDrawFree"+current+color+"|"+lineStyle+"\n");
    }

    /**
     * finish drawing (release the LMB)
     * @param end
     * @param color
     * @param lineStyle
     */
    public void stopDrawFree(Coord end, Color color, int lineStyle) {
        if (status.status() != CanvasStatus.DRAW_FREE)
            return;
        draw.drawLine(status.stratCoord(), end, color, lineStyle, Draw.PERMANENT_LAYER);
        status.nothing();
        System.out.print("stopDrawFree"+end+color+"|"+lineStyle+"\n");
        updateLog();
    }

    /**
     * start drawing (press the LMB, Left Mouse Button)
     * @param start
     * @param end
     * @param color
     * @param lineStyle
     */
    public void startDrawLine(Coord start, Coord end, Color color, int lineStyle) {
        draw.drawLine(start, end, color, lineStyle, Draw.TEMPORARY_LAYER);
    }

    /**
     * continue drawing ( moving the mouse while the LMB is pressed)
     * @param start
     * @param end
     * @param color
     * @param lineStyle
     */
    public void continueDrawLine(Coord start, Coord end, Color color, int lineStyle) {
        draw.clearTemporaryLayer();
        draw.drawLine(start, end, color, lineStyle, Draw.TEMPORARY_LAYER);
    }

    /**
     * finish drawing (release the LMB)
     * @param start
     * @param end
     * @param color
     * @param lineStyle
     */
    public void stopDrawLine(Coord start, Coord end, Color color, int lineStyle) {
        draw.clearTemporaryLayer();
        draw.drawLine(start, end, color, lineStyle, Draw.PERMANENT_LAYER);
        updateLog();
    }

    /**
     * start drawing (press the LMB, Left Mouse Button)
     * @param start
     * @param end
     * @param color
     * @param lineStyle
     * @param isFilled
     */
    public void startDrawRectangle(Coord start, Coord end, Color color, int lineStyle, boolean
            isFilled) {
        draw.drawRectangle(start, end, color, lineStyle, isFilled, Draw.TEMPORARY_LAYER);
    }

    /**
     * continue drawing ( moving the mouse while the LMB is pressed)
     * @param start
     * @param end
     * @param color
     * @param lineStyle
     * @param isFilled
     */
    public void continueDrawRectangle(Coord start, Coord end, Color color, int lineStyle, boolean
            isFilled) {
        draw.clearTemporaryLayer();
        draw.drawRectangle(start, end, color, lineStyle, isFilled, Draw.TEMPORARY_LAYER);
    }

    /**
     * finish drawing (release the LMB)
     * @param start
     * @param end
     * @param color
     * @param lineStyle
     * @param isFilled
     */
    public void stopDrawRectangle(Coord start, Coord end, Color color, int lineStyle, boolean
            isFilled) {
        draw.clearTemporaryLayer();
        draw.drawRectangle(start, end, color, lineStyle, isFilled, Draw.PERMANENT_LAYER);
        updateLog();
    }

    /**
     * start drawing (press the LMB, Left Mouse Button)
     * @param start
     * @param end
     * @param color
     * @param lineStyle
     * @param isFilled
     */
    public void startDrawOval(Coord start, Coord end, Color color, int lineStyle, boolean
            isFilled) {
        draw.drawOval(start, end, color, lineStyle, isFilled, Draw.TEMPORARY_LAYER);
    }

    /**
     * continue drawing ( moving the mouse while the LMB is pressed)
     * @param start
     * @param end
     * @param color
     * @param lineStyle
     * @param isFilled
     */
    public void continueDrawOval(Coord start, Coord end, Color color, int lineStyle, boolean
            isFilled) {
        draw.clearTemporaryLayer();
        draw.drawOval(start, end, color, lineStyle, isFilled, Draw.TEMPORARY_LAYER);
    }

    /**
     * finish drawing (release the LMB)
     * @param start
     * @param end
     * @param color
     * @param lineStyle
     * @param isFilled
     */
    public void stopDrawOval(Coord start, Coord end, Color color, int lineStyle, boolean
            isFilled) {
        draw.clearTemporaryLayer();
        draw.drawOval(start, end, color, lineStyle, isFilled, Draw.PERMANENT_LAYER);
        updateLog();
    }

    /**
     * start drawing (press the LMB, Left Mouse Button)
     * @param start
     * @param end
     * @param color
     * @param lineStyle
     * @param isFilled
     */
    public void startDrawCircle(Coord start, Coord end, Color color, int lineStyle, boolean
            isFilled) {
        draw.drawCircle(start, end, color, lineStyle, isFilled, Draw.TEMPORARY_LAYER);
    }

    /**
     * continue drawing ( moving the mouse while the LMB is pressed)
     * @param start
     * @param end
     * @param color
     * @param lineStyle
     * @param isFilled
     */
    public void continueDrawCircle(Coord start, Coord end, Color color, int lineStyle, boolean
            isFilled) {
        draw.clearTemporaryLayer();
        draw.drawCircle(start, end, color, lineStyle, isFilled, Draw.TEMPORARY_LAYER);
    }

    /**
     * finish drawing (release the LMB)
     * @param start
     * @param end
     * @param color
     * @param lineStyle
     * @param isFilled
     */
    public void stopDrawCircle(Coord start, Coord end, Color color, int lineStyle, boolean
            isFilled) {
        draw.clearTemporaryLayer();
        draw.drawCircle(start, end, color, lineStyle, isFilled, Draw.PERMANENT_LAYER);
        updateLog();
    }

    public void selectArea(Coord start, Coord end) {

    }

    public void unselectArea() {

    }

    // check if currentMouse is in the selected area, or close to, or far away from
    public int getLocationStatus(Coord currentMouse) {
        return 0;
    }

    /**
     * draw text
     * @param start
     * @param content
     * @param font
     * @param size
     * @param color
     */
    public void drawText(Coord start, String content, String font, int size, Color color) {
        draw.drawText(start, content, font, size, color, Draw.PERMANENT_LAYER);
        updateLog();
    }

    // TBD
    public void moveArea() {

    }

    // TBD
    public void resizeArea() {

    }

    /**
     * undo
     */
    public void undo() {


        int[][] tmp = log.undo();

        if (tmp != null)
            for (int i = 0; i < tmp.length; i++)
                for (int j = 0; j < tmp[i].length; j++)
                    permanentCanvas.getGraphicsContext2D().getPixelWriter().setColor(i, j,
                            CanvasHelper.intToColor(tmp[i][j]));

        /* todo advanced logging
        PixelsDifference lastOperation = log.popLastOperation();

        if (lastOperation != null)
            drawFreeWithoutLogging(lastOperation);
            */

    }

    private void updateLog(){
        log.updateCanvas(CanvasHelper.canvasToMatrix(permanentCanvas));
    }

    public static void main(String[] args) {

        System.out.println("test");

    }
}
