package org.firstinspires.ftc.teamcode;


import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.apriltag.AprilTagDetectorJNI;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

public class MyAprilTagPipeline extends OpenCvPipeline {

    private ArrayList<AprilTagDetection> latestDetections = new ArrayList<>();

    //Intrinsics / Pixels

    private static final double fx = 578.272;
    private static final double fy = 578.272;
    private static final double cx = 402.145;
    private static final double cy = 221.506;

    // Tag size (meters)
    private static final double TAG_SIZE = 0.166;

    private long nativeDetectorPtr;
    private Mat gray = new Mat();

    @Override
    public void init(Mat firstFrame) {

        nativeDetectorPtr = AprilTagDetectorJNI.createApriltagDetector(
                AprilTagDetectorJNI.TagFamily.TAG_36h11.string, 3, 3);
        // AprilTag detector for family TAG_36h11 with 3 threads and quad decimation 3
    }

    @Override
    public Mat processFrame(Mat input) {

        Imgproc.cvtColor(input, gray, Imgproc.COLOR_RGBA2GRAY); // Grayscale for better detection

        latestDetections = AprilTagDetectorJNI.runAprilTagDetectorSimple(
                nativeDetectorPtr, gray, TAG_SIZE, fx, fy, cx, cy);

        for (AprilTagDetection detection: latestDetections) {

            //Create Box around the thing
            for (int i = 0; i < 4; i++) {
                Imgproc.line(input, detection.corners[i], detection.corners[(i + 1) % 4],
                        new Scalar(0, 255, 0), 2);
        }

            int left = input.cols() - 140;  // away from the right edge
            int up = input.rows() / 2;    // vertically centered
            Imgproc.putText(input, "ID: " + detection.id,
                    new org.opencv.core.Point(left, up),  // use the fixed side position here!
                    Imgproc.FONT_HERSHEY_SIMPLEX, 1,
                    new Scalar(255, 0, 0), 2);

            if (detection.id == 21) {
                int x = input.cols() - 180;  // away from the right edge
                int y = input.rows() - 225;    // vertically centered
                Imgproc.putText(input, "Motif: GPP",
                        new org.opencv.core.Point(x, y),  // use the fixed side position here!
                        Imgproc.FONT_HERSHEY_SIMPLEX, 0.5,
                        new Scalar(0, 0, 255), 1);
            }

             if (detection.id == 22) {
                int x = input.cols() - 180;  // away from the right edge
                int y = input.rows() - 225;    // vertically centered
                Imgproc.putText(input, "Motif: PGP",
                        new org.opencv.core.Point(x, y),  // use the fixed side position here!
                        Imgproc.FONT_HERSHEY_SIMPLEX, 0.5,
                        new Scalar(0, 0, 255), 1);
            }

            if (detection.id == 23) {
                int x = input.cols() - 180;  // away from the right edge
                int y = input.rows() - 225;    // vertically centered
                Imgproc.putText(input, "Motif: PPG",
                        new org.opencv.core.Point(x, y),  // use the fixed side position here!
                        Imgproc.FONT_HERSHEY_SIMPLEX, 0.5,
                        new Scalar(0, 0, 255), 1);
            }


        }


        if (!latestDetections.isEmpty()) {
            for (AprilTagDetection detection : latestDetections) {
                System.out.println("Detected tag ID: " + detection.id);
            }
        }

        else {
            System.out.println("No tag detected");
        }



        return input;
    }

    public ArrayList<AprilTagDetection> getLatestDetections() {
        return latestDetections;
    }

}