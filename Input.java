import javax.swing.*;

/**
 * Input class - to have popup boxes for users to type data
 * Modified from North Toronto Collegiate Institute, Gerry Heffernan: https://ntci.on.ca/compsci/hef/ics3/ch1/1_4.html
 * @author Joel A Bianchi
 * @version 5/13/25
 * Renamed prompt --> question
 */
public class Input{

    /**
	 * Generates a pop-up question on the Screen
	 * @param questionText String prompt for the user
	 * 						(Hint: Use new line escape characters (\n) in your String
	 * 						if it is too long to fit neatly into the box.)
	 * @return a String with the Adventurer's answer 
	 */     
    public static String question(String questionText) {
        System.out.println(questionText);
        String entry = JOptionPane.showInputDialog(questionText);
        if (entry == null){
            return null;
        }
        System.out.println(entry);
        return entry;
    }

    public static String getString(String s){
        return question(s);
    }

    public static int getInt(String s){
        return Integer.parseInt(getString(s));
    }

    public static long getLong(String s){
        return Long.parseLong(getString(s));
    }

    public static float getFloat(String s){
        return Float.parseFloat(getString(s));
    }

    public static double getDouble(String s){
        return Double.parseDouble(getString(s));
    }

    public static char getChar(String s){
        String entry = question(s);
        if (entry.length() >= 1){
            return entry.charAt(0);
        } else {
            return '\n';
        }
    }

    // /**
	//  * Generates a pop-up message that can be closed
	//  * @param questionText String prompt for the user
	//  * 						(Hint: Use new line escape characters (\n) in your String
	//  * 						if it is too long to fit neatly into the box.
	//  */
	// public void message(String messageText){
    //     System.out.println(messageText);
	// 	JOptionPane.showMessageDialog(null, messageText, "Important message!", JOptionPane.PLAIN_MESSAGE);
	// }

} // end of Input class
