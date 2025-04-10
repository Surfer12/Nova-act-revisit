package com.fractal.browser.util;

import java.util.List;

public class FractalMath {
    public static Complex mandelbrot(Complex z, Complex c) {
        return z.multiply(z).add(c);
    }
    
    public static double convergenceRate(List<Complex> sequence) {
        // Implementation
        return 0.0;
    }
}