package org.firstinspires.ftc.teamcode.processor;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class ColorDetectionPipeline extends OpenCvPipeline {
    @Override
    public Mat processFrame(Mat input) {
        Mat hsv = new Mat();
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_BGR2HSV);

        Scalar lowerOrange = new Scalar(65.2, 145.9, 45.3);
        Scalar upperOrange = new Scalar(199.8, 189.8, 114.8);

        Mat orangeMask = new Mat();
        Core.inRange(hsv, lowerOrange, upperOrange, orangeMask);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();

        Imgproc.findContours(orangeMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        for (MatOfPoint contour : contours)                                                        {
            double area = Imgproc.contourArea(contour);
            if (area > 300) {
                Rect rect = Imgproc.boundingRect(contour);
                Imgproc.rectangle(input, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);
            }
        }

        return input;
    }
}