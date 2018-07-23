
package business;

/**
 *
 * @author Jill Haake Moreau
 */
public class Annuity {
    //class constants
    //"final" cannot be changed
    //class constants are done in all caps
    public static final String AMTDESC = "Montly Deposit:";
    public static final String RESULTDESC = "Final Annuity Value:";

    //global variables (private so the form cannot access)
    private double amt, rate;
    private int term;
    private String errmsg;
    private double[] begbal, intearn, endbal;
    private boolean built;
    
    //constructor
    public Annuity( double a, double r, int t) {
        //saves parameters into global variables for the class 
        this.amt = a;
        this.rate = r;
        this.term = t;
        this.built = false;
        if (isValid()) {
            buildAnnuity();
        }
    }

    //decides if amount provided by form are valid   
    private boolean isValid() {
        boolean valuesok = true;
        this.errmsg = "";
        if (this.amt <= 0) {
            this.errmsg = "Bad amount, not positive. ";
            valuesok = false;
        }
        if (this.rate <= 0) {
            this.errmsg += "Bad Rate, not positive. ";
            valuesok = false;
        }
        if (this.term <= 0) {
            this.errmsg += "Bad Term, not positive. ";
            valuesok = false;
        }
        return valuesok;
    }
    
    //builds annuity schedule (??)
    private void buildAnnuity() {
        this.errmsg = "";
        try {
            this.begbal = new double[this.term];
            this.intearn = new double[this.term];
            this.endbal = new double[this.term];
            
            this.begbal[0] = 0.0;
            for (int i = 0; i < this.term; i++) {
                if (i > 0){
                    //if past first month: begbal = last month ending bal
                    this.begbal[i] = this.endbal[i-1];
                }
                this.intearn[i] = (this.begbal[i] + this.amt) * (this.rate / 12.0);
                this.endbal[i] = this.begbal[i] + this.intearn[i] + this.amt;
            }
            this.built = true;
            
        } catch (Exception e) {
            this.errmsg = "build error: " + e.getMessage();
            this.built = false;
        }//end catch
    }//end of buildAnnuity
    
    //allows form to see errors thrown
    public String getErrorMsg() {
        return this.errmsg;
    }//"encapsulation (how to send a private variable back to a form)
    
    //gets final result from built annuity
    public double getResult() {
        if (built) {
            return this.endbal[this.term - 1];
        }
        return -999;
    }//end of getResult

    public double getAmt() {
        return this.amt;
    }
    
    public double getRate() {
        return this.rate;
    }
    
    public int getTerm() {
        return this.term;
    }
    
    public double getBegBal (int mo) {
        if (!built) {
            buildAnnuity();
            if (!built) {
                return -1;
            }
        }
        if (mo < 1 || mo > this.term) {
            //month is out of range
            return -1;
        }
        return this.begbal[mo-1];
    }
    public double getIntEarned(int mo) {
        if (!built) {
            buildAnnuity();
            if (!built) {
                return -1;
            }
        }
        if (mo < 1 || mo > this.term) {
            //month is out of rant
            return -1;
        }
        return this.intearn[mo-1];
    }
    public double getEndBal(int mo) {
        if (!built) {
            buildAnnuity();
            if (!built) {
                return -1;
            }
        }
        if (mo < 1 || mo > this.term){
            //month is out of range
            return -1;
        }
        return this.endbal[mo-1];
    }
}//end of class
