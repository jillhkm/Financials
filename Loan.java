/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author jilln
 */
public class Loan {
    //class constants
    public static final String AMTDESC = "Loan Amount:";
    public static final String RESULTDESC = "Montly Fee:";
    
    private double amt, rate, mopmt;
    private int term;
    private String errmsg;
    private boolean built;
    private double[] BegBal, IntCharge, EndBal;
    
    //constructor
    public Loan(double a, double r, int t) {
        this.amt = a;
        this.rate = r;
        this.term = t;
        this.built = false;
        this.mopmt = 0;
        this.errmsg = "";
        
        if(isValid()){
            buildLoan();
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
    
    public double getAmt() {
        return this.amt;
    }
    
    public double getRate() {
        return this.rate;
    }
    
    public int getTerm() {
        return this.term;
    }
    
    public String getErrorMsg() {
        return this.errmsg;
    }
    
    private void buildLoan() {
        this.errmsg = "";
        try {
            double morate = this.rate / 12.0;
            double denom = Math.pow((1+morate), this.term) - 1;
            this.mopmt = (morate + morate / denom) * this.amt;
            //calculate arry values...
            
            this.BegBal = new double[this.term];
            this.IntCharge = new double[this.term];
            this.EndBal = new double[this.term];
            
            this.BegBal[0] = this.amt;
            for(int i = 0; i <this.term; i++) {
                if (i > 0) {
                    this.BegBal[i] = this.EndBal[i-1];
                }
                this.IntCharge[i] = this.BegBal[i] * morate;
                this.EndBal[i] = (this.BegBal[i] + this.IntCharge[i]) - this.mopmt;
            }
            
            this.built = true;
        } catch (Exception e) {
            this.errmsg = "Build error: " + e.getMessage();
            this.built = false;
        }
    }//end of buildLoan
    
    public double getResult() {
        if (!built) {
            buildLoan();
            if (!built) {
                return -1;
            }
        }
        return this.mopmt;
    }
    
    public double getBegBal (int mo) {
    if (!built) {
        buildLoan();
        if (!built) {
            return -1;
        }
    }
    if (mo < 1 || mo > this.term) {
        //month is out of range
        return -1;
    }
    return this.BegBal[mo-1];
    }
    
    public double getIntCharge(int mo) {
        if (!built) {
            buildLoan();
            if (!built) {
                return -1;
            }
        }
        if (mo < 1 || mo > this.term) {
            //month is out of rant
            return -1;
        }
        return this.IntCharge[mo-1];
    }
    
    public double getEndBal(int mo) {
       if (!built) {
           buildLoan();
           if (!built) {
               return -1;
           }
       }
       if (mo < 1 || mo > this.term){
           //month is out of range
           return -1;
       }
       return this.EndBal[mo-1];
    }
    
}//end of class
