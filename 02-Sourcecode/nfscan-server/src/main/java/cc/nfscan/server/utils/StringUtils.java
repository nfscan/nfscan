package cc.nfscan.server.utils;

import java.util.InputMismatchException;

/**
 * Utility Class which defines some useful methods for string handling
 *
 * @author Paulo Miguel Almeida <a href="http://github.com/PauloMigAlmeida">@PauloMigAlmeida</a>
 */
public class StringUtils {

    /**
     * Checks whether a string contains only number or not using a regex.
     * I like this approach since it doesn't need to parse it into a numerical variable
     * which may not fit into any particularly type
     *
     * @param maybeNumeric a string you want to test
     * @return true if it's a number or false if it isn't
     */
    public static boolean isNumeric(String maybeNumeric) {
        return maybeNumeric != null && maybeNumeric.matches("[0-9]+");
    }

    /**
     * Check whether a string is a valid CNPJ or not using a modulus 11 calculation
     * @param CNPJ a string that you want to verify if it's a valid CNPJ
     * @return true if it's a valid CNPJ or not if it isn't
     */
    public static boolean validateCNPJ(String CNPJ) {
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
                CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
                CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
                CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
                CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
                (CNPJ.length() != 14))
            return (false);

        char dig13, dig14;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char) ((11 - r) + 48);

            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char) ((11 - r) + 48);

            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
                return (true);
            else return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    /**
     * Reverse the characters of a string value
     *
     * @param str
     * @return a reversed string
     */
    public static String reverseString(String str){
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        return sb.toString();
    }

    /**
     * Appends values to given StringBuilder parameter
     *
     * @param sb
     * @param strings
     */
    public static void appendVarArgs(StringBuilder sb, String... strings){
        for(String str : strings){
            sb.append(str);
        }
    }
}
