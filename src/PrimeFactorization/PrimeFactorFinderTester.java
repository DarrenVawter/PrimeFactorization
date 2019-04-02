package PrimeFactorization;

import java.util.ArrayList;

public class PrimeFactorFinderTester {

    //construct int arrays for potential primes and the carry column
    private static ArrayList<Integer> p, q, c, t;
    private static int nDigits;

    public static void main(String[] args) {

        //TODO
        /** KNOWN BUGS

         findpair with p = 5 needs solvings

         ...just debug it dude

         */

        //pick random primes
        int p1 = 199, p2 = 7;

        //compute product of primes
        int n = p1 * p2;

        //find # digits in product
        nDigits = 0;
        for(int i=1;n/i>0;i*=10){
            nDigits++;
        }

        //instantiate array lists
        p = new ArrayList<>(nDigits);
        q = new ArrayList<>(nDigits);
        c = new ArrayList<>(nDigits);
        t = new ArrayList<>(nDigits);

        //get dem factors bois
        FindPrimeFactorization(n);

    }

    private static void FindPrimeFactorization(int n) {

        //populate initial target array
        for(int col=0;col<nDigits;col++){
            t.add(  (int)   (   ( n % (Math.pow(10,col+1) ) )  /   ( Math.pow(10,col) )  )   );
        }
        //'-1' or 'X' at the end of the target array should not be touched if the solution is correct
        t.add(-1);

        //populate initial p array
        for(int col=0;col<nDigits;col++){
            p.add(0);
        }

        //populate initial q array
        for(int col=0;col<nDigits;col++){
            q.add(0);
        }

        System.out.println("");
        if(FindCombo(0)) {
            for (int i = nDigits - 1; i >= 0; i--) {
                System.out.print(p.get(i));
            }
            System.out.println("");
            for (int i = nDigits - 1; i >= 0; i--) {
                System.out.print(q.get(i));
            }
        }else{
            System.out.println("No prime-pair factorization found.");
        }


    }

    //TODO : if p is 5 this kinda sucks... (not a function)

    private static Integer GetPair(int pArg, int q1, int t) throws NoValidPairsException {

       if(p.get(0)==9 && p.get(1)==9 ) {
            System.out.println(pArg+" "+q1+" "+t);
        }

        t-=pArg*q1;

        //simulated borrowing
        while(t<0){
            t+=10;
        }

        if(t<0) {
            throw new NoValidPairsException("No pair Exists.");
        }

        switch (pArg) {
            case 0:
                if(t==0){
                    return 0;
                }else{
                    throw new NoValidPairsException("No pair Exists.");
                }
            case 1:
                switch(t) {
                    default:
                        return t;
                }
            case 3:
                switch (t) {
                    case 0:
                        return 0;
                    case 1:
                        return 7;
                    case 2:
                        return 4;
                    case 3:
                        return 1;
                    case 4:
                        return 8;
                    case 5:
                        return 5;
                    case 6:
                        return 2;
                    case 7:
                        return 9;
                    case 8:
                        return 6;
                    case 9:
                        return 3;
                    default:
                        System.out.println("SYSTEM ERROR");
                        System.exit(-1);
                }
                break;
            case 5:
                switch (t) {
                    case 5:
                        //TODO yikes
                        break;
                    default:
                        throw new NoValidPairsException("No pair exists.");
                }
                break;
            case 7:
                switch (t) {
                    case 0:
                        return 0;
                    case 1:
                        return 3;
                    case 2:
                        return 6;
                    case 3:
                        return 9;
                    case 4:
                        return 2;
                    case 5:
                        return 5;
                    case 6:
                        return 8;
                    case 7:
                        return 1;
                    case 8:
                        return 4;
                    case 9:
                        return 7;
                    default:
                        System.out.println("SYSTEM ERROR");
                        System.exit(-1);
                }
                break;
            case 9:
                switch (t) {
                    case 0:
                        return 0;
                    case 1:
                        return 9;
                    case 2:
                        return 8;
                    case 3:
                        return 7;
                    case 4:
                        return 6;
                    case 5:
                        return 5;
                    case 6:
                        return 4;
                    case 7:
                        return 3;
                    case 8:
                        return 2;
                    case 9:
                        return 1;
                    default:
                        System.out.println("SYSTEM ERROR");
                        System.exit(-1);
                }
                break;
            case 2:
            case 4:
            case 6:
            case 8:
                throw new NoValidPairsException("No pair exists.");
            default:
                System.out.println("SYSTEM ERROR");
                System.exit(-1);
        }

        //should never reach here
        System.out.println("SYSTEM ERROR");
        System.exit(-1);
        return -2;

    }

    private static boolean FindCombo(int col){


        //base case
        if(col==nDigits) {
            for (int i = 0; i < nDigits; i++) {
                if (t.get(i) != 0) {
                    return false;
                }
            }
            return true;
        }

        for(p.set(col,0);p.get(col)<10;p.set(col,p.get(col)+1)) {

          //  if(p.get(0)==9 && p.get(1)==9 ) {
                //TODO REMOVE DEBUGS
                System.out.println("A");
                System.out.println("P:" + p);
                System.out.println("Q:" + q);
                System.out.println("T:" + t);
                System.out.println("");
            //}

            //set q value according to p
            try {
                if (col == 0) {
                    q.set(col, GetPair(p.get(col), 0, t.get(col)));
                } else {
                    q.set(col, GetPair(p.get(col), q.get(0), t.get(col)));
                }
            }catch(NoValidPairsException e){
                continue;
            }

            //decrement by carry over amount
            int tDec = 0;
            for(int i=0;i<=col;i++) {
                tDec += p.get(col) * (Math.pow(10, i)) * q.get(i);
                if (i != col) {
                    tDec += q.get(col) * (Math.pow(10, i)) * p.get(i);
                }
            }

            //do not allow modification of X value
            if( tDec >= Math.pow(10,(nDigits-col))){
                return false;
            }

            //dec tar vals
            for(int i=col;i<nDigits;i++){
                t.set(i, t.get(i) - (int)   (   ( tDec % (Math.pow(10,i-col+1) ) )  /   ( Math.pow(10,i-col) )  )   );
            }

            //borrow from left
            for(int i=col;i<nDigits-1;i++){
                while(t.get(i)<0){
                    t.set(i,t.get(i)+10);
                    t.set(i+1,t.get(i+1)-1);
                }
            }

            //allows us to skip the next combos if dec was invalid and re-incs tar values
            boolean skip = false;
            if(t.get(nDigits-1)<0){
                skip = true;
            }


            //this will echo down from the top of the call stack if the entire
            if(FindCombo(col+1) && !skip){
                return true;
            }

            //replace tar vals (combo was not a success if this line is reached
            for(int i=col;i<nDigits;i++){
                t.set(i, t.get(i) + (int)   (   ( tDec % (Math.pow(10,i-col+1) ) )  /   ( Math.pow(10,i-col) )  )   );
            }
            //undo borrow from left
            for(int i=col;i<nDigits-1;i++){
                while(t.get(i)>10){
                    t.set(i,t.get(i)-10);
                    t.set(i+1,t.get(i+1)+1);
                }
            }

        }

        //if no p-values worked, this FindCombo has failed
        return false;

    }

}
