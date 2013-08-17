EARS - Evolutionary Algorithms Rating System

EARS in action http://earatingsystem.appspot.com

What it is included:
- some banchmarks with problem functions (Sphere, ...).
- some already implemented test Alorithms
- some simple test experiments


How to use it!

- All projects are Eclipse java projects.
- Download it use git in eclipse.
- In same workspace create new java project.
- Add Properties -> Java Build Path -> Projects -> EARS
- Include your algorithm in the project.
- Modify algorithm to work with EARS

Example:
public class RandomWalkAlgorithm extends Algorithm { //needs to me extended 
	private Individual i; //EARS Individual includes solution vector and its fitness value
  private boolean debug = true;
  
	public RandomWalkAlgorithm() { 
	  super();
	  setDebug(debug);  //EARS prints some debug info
		ai = new AlgorithmInfo("","","RWSi+","Random Walk+");  //EARS add algorithm name
		au =  new Author("robi", "N/A"); //EARS author info
	}

	@Override  
	public Individual run(Task taskProblem) throws StopCriteriaException{ //EARS main evaluation loop 
		Individual ii;

		i = taskProblem.getRandomIndividual(); //EARS Helper for creating random solution, it takes one evaluation (eval++)
	  //user can use its own representation for example double[] and in fase of evaluation calls taskProblem.eval that creates individual
		System.out.println(taskProblem.getNumberOfEvaluations()+" "+i); //prints number of evaluations

		while (!taskProblem.isStopCriteria()) {   //EARS user needs to take care about number of evaluations
			ii = taskProblem.getRandomIndividual();
			if (taskProblem.isFirstBetter(ii, i)) { //EARS primary function it takes care if we are searching minimum or maximum, if solution is valit etc.
			  i = ii;
				if (debug) System.out.println(taskProblem.getNumberOfEvaluations()+" "+i);
			}
		}
		return i;

	}
	
  @Override
  public void resetDefaultsBeforNewRun() {
    i=null;
  }

}

- Next step is to run your algorithm.
- 
Example:
public class Main4Run {
	public static void main(String[] args) {
		Task t = new Task(EnumStopCriteria.EVALUATIONS, 3000, 0.0001, new ProblemSphere(5)); //run problem Sphere Dimension 5, 3000 evaluations
		RandomWalkAlgorithm test = new RandomWalkAlgorithm();
		try {
			System.out.println(test.run(t)); //prints best result afrer 3000 runs
		} catch (StopCriteriaException e) {
			e.printStackTrace();
		}
	}
}

-For rating you need more than one algorithm (player) and more than one task (banchmark)).

Example:
public class MainBenchMarkTest {
    public static void main(String[] args) {
        Util.rnd.setSeed(System.currentTimeMillis());
        RatingBenchmark.debugPrint = true; //prints one on one results
        ArrayList<Algorithm> players = new ArrayList<Algorithm>();
        players.add(new ES1p1sAlgorithm()); //EARS exampels
        players.add(new TLBOAlgorithm()); //EARS examples
        players.add(new RandomWalkAlgorithm());
        ResultArena ra = new ResultArena(100); 
        RatingRPUOed2 suopm = new RatingRPUOed2(); //Create banchmark
        for (Algorithm al:players) {
          ra.addPlayer(al.getID(), 1500, 350, 0.06,0,0,0); //init rating 1500
          suopm.registerAlgorithm(al);
        }
        suopm.run(ra, 50); //repeat competition 50X
        ArrayList<Player> list = new ArrayList<Player>();
        list.addAll(ra.recalcRangs()); //new rangs
        for (Player p: list) System.out.println(p); //print rangs
    }
}
-Thats it.

Tips:
If you have special representation create your own individual by extending EARS Individual.
class MyIndividual extends Individual
