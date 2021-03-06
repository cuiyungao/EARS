package org.um.feri.ears.problems.unconstrained.cec2010;


import java.util.Arrays;
import java.util.Random;

import org.um.feri.ears.problems.Problem;
import org.um.feri.ears.problems.unconstrained.cec2010.base.EllipticShifted;

/**
 * Problem function!
 * 
 * @author Niki Vecek
 * @version 1
 * 
 **/	
public class F1 extends Problem {
     
	int[] P;
	int type;
	EllipticShifted elliptic_shifted;
	// F1 CEC 2010
	// Shifted Elliptic Function
	public F1(int d) {
		type=Problem.SHIFTED;
		dim = d;
		elliptic_shifted = new EllipticShifted(dim);
		interval = new double[d];
		intervalL = new double[d];
		Arrays.fill(interval, 200);
		Arrays.fill(intervalL, -100);
		name = "F01 Shifted Elliptic Function";
		
		P = new int[dim];
		Random rand = new Random();
		int rand_place = 0;
		for (int i=dim-1; i>0; i--){
			rand_place = rand.nextInt(dim);
			P[i] = rand_place;			
		}
	}
	
	public double eval(double x[]) {
		double F = 0;
		F = elliptic_shifted.eval(x,P,0,dim);
		return F;
	}

	public double getOptimumEval() {
		return 0;
	}

	@Override
	public boolean isFirstBetter(double[] x, double eval_x, double[] y,
			double eval_y) {
		return eval_x < eval_y;
	}

}