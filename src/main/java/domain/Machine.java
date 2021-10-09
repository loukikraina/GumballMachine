package domain;

import java.io.Serializable;

public class Machine implements Serializable {
//    private int tfpaisa;
//    private int fpaisa;
//    private int oruppee;
//    private int fruppee;
//    private int ragulla;
//    private int kheer;
//    private int kajukatli;
//    private int jalebi;
//    private double pragulla;
//    private double pkheer;
//    private double pkajukatli;
//    private double pjalebi;
    private double[] sweetsCost = new double[4];
    private String[] sweetsName = new String[4];
    private int[] sweetsStock = new int[4];
    private int[] denominations = new int[4]; //[fr,or,fp,tfp]

    public String returncashsuccess(double cash,int choice,int quantity){
        choice -=1;
        //System.out.println(Double.toString(cash) +" "+ Integer.toString(choice)+ " "+ Integer.toString(quantity) );
        double a = cash - (this.sweetsCost[choice]*quantity);
        //System.out.println(Double.toString(a)+" "+Double.toString(this.sweetsCost[choice]*quantity));
        if(this.sweetsStock[choice]<quantity){
            return "Sorry your transaction can't be completed, due to low stock then ordered by you i.e. " + Integer.toString(this.sweetsStock[choice]) +"\n" +
                    "We are very sorry for the inconvenience caused. Your deposited cash will be returned to you.\n" +
                    this.returncashfail(cash);
        }

        else if(a<0){
            return "Sorry your transaction can't be completed, you have deposited less cash than expected by the machine.\n" +
                    "Extra amount required: " + Double.toString(a*-1) +
                    "\nWe are very sorry for the inconvenience caused. Your deposited cash will be returned to you.\n" +
                    this.returncashfail(cash);
        }
        else if(a==0){
            this.sweetsStock[choice] -= quantity;
            this.adddenomination(cash,0 , 0,0,0);
            return "Hooray, transaction completed.\n" +
                    "Here is your " + Integer.toString(quantity) + " " + this.sweetsName[choice] + "\n" +
                    "Enjoy and have a great day ahead";

        }
        else {
            int tfp, fp, or, fr;
            fr = (int) (a / 5);
            if (this.denominations[0] < fr)
                fr = this.denominations[0];
            a -= (fr*5);
            or = (int) (a / 1);
            if (this.denominations[1] < or)
                or = this.denominations[1];
            a -= (or*1);
            fp = (int) (a / (0.5));
            if (this.denominations[2] < fp)
                fp = this.denominations[2];
            a -= (fp*0.5);
            tfp = (int) (a / (0.25));
            if (this.denominations[3] < tfp)
                tfp = this.denominations[3];

            if ((cash - (sweetsCost[choice]*quantity)) == ((tfp * 0.25) + (fp * 0.50) + (fr * 5) + (or * 1))) {
                this.sweetsStock[choice] -= quantity;
                this.adddenomination(cash,fr,or,fp,tfp);
                return "Hooray, transaction completed.\n" +
                        "Here is your " + Integer.toString(quantity) + " " + this.sweetsName[choice] + " " +
                        "And your change: \n" +
                        Integer.toString(fr) + " 5 Ruppee coin\n" +
                        Integer.toString(or) + " 1 Ruppee coin\n" +
                        Integer.toString(fp) + " 50 Paisa coin\n" +
                        Integer.toString(tfp) + " 25 Paisa coin\n" +
                        "Enjoy and have a great day ahead\n";
            }
            else{
                return "Sorry your transaction can't be completed, expected change denominations are lacking.\n" +
                        "We are very sorry for the inconvenience caused. Your deposited cash will be returned to you.\n" +
                        this.returncashfail(cash);
            }
        }



    }
    public void adddenomination(double a, int fr1, int or1, int fp1, int tfp1){
        int tfp, fp, or, fr;
        fr = (int) (a / 5);
        a = a % 5;
        or = (int) (a / 1);
        a = a % 1;
        fp = (int) (a / (0.5));
        a = a % (0.5);
        tfp = (int) (a / (0.25));
        this.denominations[0] += (fr-fr1);
        this.denominations[1] += (or-or1);
        this.denominations[2] += (fp-fp1);
        this.denominations[3] += (tfp-tfp1);
    }

    public String returncashfail(double a){
        int tfp, fp, or, fr;
        fr = (int) (a / 5);
        a = a % 5;
        or = (int) (a / 1);
        a = a % 1;
        fp = (int) (a / (0.5));
        a = a % (0.5);
        tfp = (int) (a / (0.25));
        return "Here is your money \n"+
                Integer.toString(fr) + " 5 Ruppee coin\n" +
                Integer.toString(or) + " 1 Ruppee coin\n" +
                Integer.toString(fp) + " 50 Paisa coin\n" +
                Integer.toString(tfp) + " 25 Paisa coin\n" +
                "Enjoy and have a great day ahead";

    }

    public void setSweetsCost(double[] sweetsCost) {
        this.sweetsCost = sweetsCost;
    }

    public void setSweetsName(String[] sweetsName) {
        this.sweetsName = sweetsName;
    }

    public void setSweetsStock(int[] sweetsStock) {
        this.sweetsStock = sweetsStock;
    }

    public void setDenominations(int[] denominations) {
        this.denominations = denominations;
    }

    public double[] getSweetsCost() {
        return sweetsCost;
    }

    public String[] getSweetsName() {
        return sweetsName;
    }

    public int[] getSweetsStock() {
        return sweetsStock;
    }

    public int[] getDenominations() {
        return denominations;
    }
}
