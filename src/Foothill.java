import java.util.*;
import java.lang.Math;

/**
 * Assignment 6, CS1A, Anand Venkataraman, Fall 2016
 * @author Paul Hayter
 */
public class Foothill
{
   static Scanner myScanner = null;
   
   // class constants
   static final int MAX_BET = 50;
   static final double BAR_RATE = 0.40;
   static final double CHERRY_RATE = 0.30;
   static final double SPACE_RATE = 0.05;
   static final double SEVENS_RATE = 0.25; // reference only, not used
   private static final String BAR_STR = "BAR";
   private static final String CHERRY_STR = "cherry";
   private static final String SPACE_STR = "(space)";
   private static final String SEVEN_STR = "7";
   static final int CH_MULT = 5;
   static final int CH_CH_MULT = 15;
   static final int CH_CH_CH_MULT = 30;
   static final int BAR_BAR_BAR_MULT = 50;
   static final int SEV_SEV_SEV_MULT = 100;

   /**
    * Slot Machine Simulation using class TripleString. User enters amount to
    * bet with getBet() method which only accepts bets 0 to MAX_BET. A bet of 0
    * causes simulation to end. Simulator generates random slots and displays
    * result of bet. <br><br>Slot machine has symbols BAR, cherry, (space)
    * and 7 whose text representations are in the strings BAR_STR, CHERRY_STR,
    * SPACE_STR and SEVEN_STR, respectively. The frequency rate for these
    * symbols are defined with BAR_RATE, CHERRY_RATE, SPACE_RATE and SEVENS_RATE
    * respectively (and need to sum to 100%). The pay-out multipliers are set in
    * <br>CH_MULT for cherry *** +++ 
    * <br>CH_CH_MULT for cherry cherry ***
    * <br>CH_CH_CH_MULT for cherry cherry cherry
    * <br>BAR_BAR_BAR_MULT for BAR BAR BAR
    * <br>SEV_SEV_SEV_MULT for  7   7   7
    * <br>where *** means any non-cherry symbol and +++ means any symbol
    * @param args not used
    */
   public static void main(String [] args)
   {   
      myScanner = new Scanner(System.in);
      TripleString pullString = new TripleString();

      while (true)
      {
         int bet = getBet();
         if (bet == 0)
            break;
         pullString = pull();
         int winnings = bet * getPayMultiplier(pullString);
         display(pullString, winnings);
      }
      
      myScanner.close();
   }
   
   /**
    * Prompts user for a bet between 0 and MAX_BET. Gives error messages for 
    * null and out of range input. Accepts numeric inputs and casts to integer
    * which is returned if in range.
    * @return specified integer
    */
   public static int getBet()
   {
      while (true)
      {
         System.out.print("How much would you like to bet (1 - "
               + MAX_BET + ") or 0 to quit? ");
         String input = myScanner.nextLine();
         if (input.length() == 0)
         {
            System.out.println("Error: No bet entered. Please enter again.");
            continue;
         }
         int bet = (int) Double.parseDouble(input);
         if (bet >= 0 && bet <= MAX_BET)
            return bet;
         System.out.println("Error: Bet is out of range. Please enter again.");
      }
   }
   
   /**
    * Fills the three strings of a TripleString object each with a random
    * string given by randString()
    * @return specified TripleString object
    */
   public static TripleString pull()
   {
      TripleString pullVal = new TripleString();

      pullVal.setString1(randString());
      pullVal.setString2(randString());
      pullVal.setString3(randString());
      
      return pullVal;
   }
   
   /**
    * Randomly selects one of the four strings of the slot machine (i.e.,
    * BAR_STR, CHERRY_STR, SPACE_STR, SEVEN_STR) according to the frequency rate
    * defined in BAR_RATE, CHERRY_RATE, SPACE_RATE, and SEVENS_RATE,
    * respectively.
    * @return specified String
    */
   private static String randString()
   {
      final int RAND_SCALER = 1000;
      final int BAR_THRESH = (int) (RAND_SCALER * BAR_RATE);
      final int CHERRY_THRESH = (int) (RAND_SCALER * CHERRY_RATE) + BAR_THRESH;
      final int SPACE_THRESH = (int) (RAND_SCALER * SPACE_RATE) + CHERRY_THRESH;
      // a SEV_THRESH is not needed for the sole remaining case of SEVEN_STR
      
      // Generates [0, RAND_SCALER - 1] for randNumber
      int randNumber = (int) (RAND_SCALER * Math.random());
      if (randNumber < BAR_THRESH) 
         return BAR_STR;
      if (randNumber < CHERRY_THRESH) 
         return CHERRY_STR;
      if (randNumber < SPACE_THRESH) 
         return SPACE_STR;
      return SEVEN_STR;
   }
   
   /**
    * Computes pay multiplier based on TripleString thePull values. Multipliers
    * are defined in CH_MULT, CH_CH_MULT, CH_CH_CH_MULT, BAR_BAR_BAR_MULT and
    * SEV_SEV_SEV_MULT.
    * @param thePull a TripleString object with three random slot symbols
    * @return specified pay multiplier
    */
   static int getPayMultiplier (TripleString thePull)
   {
      if (thePull.getString1().equals(CHERRY_STR))
      {
         if (thePull.getString2().equals(CHERRY_STR))
         {
            if (thePull.getString3().equals(CHERRY_STR))
               return CH_CH_CH_MULT;
            else
               return CH_CH_MULT;
         }
         else
            return CH_MULT;
      }
      
      if (thePull.getString1().equals(BAR_STR)
            && thePull.getString2().equals(BAR_STR)
            && thePull.getString3().equals(BAR_STR))
         return BAR_BAR_BAR_MULT;
      
      if (thePull.getString1().equals(SEVEN_STR)
            && thePull.getString2().equals(SEVEN_STR)
            && thePull.getString3().equals(SEVEN_STR))
         return SEV_SEV_SEV_MULT;
      
      return 0;
   }
   
   /**
    * Displays the results of a slot machine "pull" or gambling try. Results 
    * include win or loss and pay-out if a win. The pay-out factor is determined
    * by getPayMultiplier().
    * @param thePull TripleString result of a slot machine "pull"
    * @param winnings the amount won with this "pull" (could be 0)
    */
   public static void display (TripleString thePull, int winnings )
   {
      System.out.println("whirrrrrr .... and your pull is ...");
      System.out.println(thePull.toString());
      
      if (winnings == 0)
         System.out.println("sorry, you lose.\n");
      else
         System.out.println("congratulations, you win: " + winnings + "\n");
   }
} // end class Foothill


/**
 * Class object contains three strings of length up to MAX_LEN each with
 * supporting set, get and toString methods.
 * @author Paul Hayter
 */
class TripleString 
{
   private String string1, string2, string3;
   
   // class constants
   public static final int MAX_LEN = 20;
   public final String TO_STRING_PADS = "   ";
   public static final String TRIPLESTRING_DEFAULT = "";
   
   TripleString()
   {
      string1 = TRIPLESTRING_DEFAULT;
      string2 = TRIPLESTRING_DEFAULT;
      string3 = TRIPLESTRING_DEFAULT;         
   }
   
   private static boolean validString( String str )
   {
      if (str != null && str.length() <= MAX_LEN)
         return true;
      return false;
   }
   
   // mutators
   boolean setString1( String str )
   {
      if (!validString(str))
         return false;
      string1 = str;
      return true;
   }
   
   boolean setString2( String str )
   {
      if (!validString(str))
         return false;
      string2 = str;
      return true;
   }
   
   boolean setString3( String str )
   {
      if (!validString(str))
         return false;
      string3 = str;
      return true;
   }
   
   // accessors
   String getString1(){ return string1; }
   String getString2(){ return string2; }
   String getString3(){ return string3; }
   
   @Override
   public String toString()
   {
      return string1 + TO_STRING_PADS + string2 + TO_STRING_PADS + string3;
   }
} // end class TripleString

/*********************** RUN ***************************************************
How much would you like to bet (1 - 50) or 0 to quit? 55
Error: Bet is out of range. Please enter again.
How much would you like to bet (1 - 50) or 0 to quit? -1
Error: Bet is out of range. Please enter again.
How much would you like to bet (1 - 50) or 0 to quit? 
Error: No bet entered. Please enter again.
How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
cherry   BAR   7
congratulations, you win: 5

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
7   7   7
congratulations, you win: 100

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
7   cherry   7
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   BAR   cherry
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
7   cherry   BAR
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   cherry   BAR
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
cherry   BAR   cherry
congratulations, you win: 5

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   cherry   cherry
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   7   BAR
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
cherry   BAR   BAR
congratulations, you win: 5

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
7   BAR   cherry
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   7   BAR
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
cherry   cherry   cherry
congratulations, you win: 30

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
7   7   BAR
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   BAR   BAR
congratulations, you win: 50

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
cherry   BAR   BAR
congratulations, you win: 5

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
7   BAR   BAR
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   cherry   cherry
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   BAR   7
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   7   BAR
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   BAR   7
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
7   (space)   7
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
7   BAR   7
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   BAR   7
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
7   BAR   7
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
7   cherry   cherry
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   cherry   BAR
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
7   cherry   cherry
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
7   cherry   BAR
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   7   cherry
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   7   cherry
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
cherry   cherry   BAR
congratulations, you win: 15

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   cherry   cherry
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   BAR   BAR
congratulations, you win: 50

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
cherry   cherry   7
congratulations, you win: 15

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   (space)   BAR
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
cherry   7   cherry
congratulations, you win: 5

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   cherry   BAR
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
cherry   BAR   7
congratulations, you win: 5

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
7   BAR   cherry
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
(space)   cherry   BAR
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
7   (space)   BAR
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   BAR   BAR
congratulations, you win: 50

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
cherry   7   BAR
congratulations, you win: 5

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
cherry   cherry   BAR
congratulations, you win: 15

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
cherry   cherry   BAR
congratulations, you win: 15

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
BAR   BAR   (space)
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
7   7   cherry
sorry, you lose.

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
cherry   (space)   cherry
congratulations, you win: 5

How much would you like to bet (1 - 50) or 0 to quit? 1
whirrrrrr .... and your pull is ...
cherry   BAR   cherry
congratulations, you win: 5

How much would you like to bet (1 - 50) or 0 to quit? 0
*******************************************************************************/