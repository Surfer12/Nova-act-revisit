package com.fractal.browser.collective.visualization;

public class FractalMath {
    public double[] calculateMandelbrotPoint(double x, double y, double c, int maxIterations) {
        double zx = x;
        double zy = y;
        double zx2 = zx * zx;
        double zy2 = zy * zy;
        
        int iteration = 0;
        while (zx2 + zy2 < 4 && iteration < maxIterations) {
            zy = 2 * zx * zy + c;
            zx = zx2 - zy2 + x;
            zx2 = zx * zx;
            zy2 = zy * zy;
            iteration++;
        }
        
        return new double[]{zx, zy, iteration};
    }
} 