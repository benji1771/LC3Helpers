import java.util.Scanner;

public class Decoder {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Binary/Hex: ");
        while(sc.hasNextLine()){
            
            System.out.println("LC3 Instruction: " + decode(sc.nextLine()));
            System.out.print("Enter Binary/Hex: ");
        }
    }
    public static String decode(String n){
        String bin = twosComplement(n);
        String command = bin.substring(0, 4);
        switch(command){
            case "0001": return add(bin.substring(4));
            case "0101": return and(bin.substring(4));
            case "0000": return br(bin.substring(4));
            case "1100": return jmpRet(bin.substring(4));
            case "0100": return js(bin.substring(4));
            case "0010": return ld(bin.substring(4));
            case "1010": return ldi(bin.substring(4));
            case "0110": return ldr(bin.substring(4));
            case "1110": return lea(bin.substring(4));
            case "1001": return not(bin.substring(4));
            case "1000": return rti(bin.substring(4));
            case "0011": return st(bin.substring(4));
            case "1011": return sti(bin.substring(4));
            case "0111": return str(bin.substring(4));
            case "1111": return trap(bin.substring(4));
            default: return "Invalid Input";
        }

    }
    private static String trap(String substring) {
        String s = substring;
        StringBuilder sb = new StringBuilder();
        sb.append("TRAP ");
        String temp = getHex(s.substring(4));
        if(temp.equals("x25") || temp.equals("x23") || temp.equals("x21") || temp.equals("x20") || temp.equals("x22")){
            sb.append(temp);
            return sb.toString();
        }else{
            return "Invalid Trap";
        } 
        
    }
    private static String str(String substring) {
        String s = substring;
        StringBuilder sb = new StringBuilder();
        sb.append("STR ");
        sb.append(getRegister(s.substring(0,3)) + " ");
        s = s.substring(3);
        sb.append(getRegister(s.substring(0,3)) + " ");
        s = s.substring(3);
        sb.append(getHex(s));
        return sb.toString();
    }
    private static String sti(String substring) {
        String s = substring;
        StringBuilder sb = new StringBuilder();
        sb.append("STI ");
        sb.append(getRegister(s.substring(0,3)) + " ");
        s = s.substring(3);
        sb.append(getHex(s));
        return sb.toString();
    }
    private static String st(String substring) {
        String s = substring;
        StringBuilder sb = new StringBuilder();
        sb.append("ST ");
        sb.append(getRegister(s.substring(0,3)) + " ");
        s = s.substring(3);
        sb.append(getHex(s));
        return sb.toString();

    }
    private static String rti(String substring) {
        return "RTI";
    }
    private static String not(String substring) {
        String s = substring;
        StringBuilder sb = new StringBuilder();
        sb.append("NOT ");
        sb.append(getRegister(s.substring(0, 3)) + " ");
        s = s.substring(3);
        sb.append(getRegister(s.substring(0, 3)) + " ");
        s = s.substring(3);
        return sb.toString();
    }
    private static String lea(String substring) {
        String s = substring;
        StringBuilder sb = new StringBuilder();
        sb.append("LEA ");
        sb.append(getRegister(s.substring(0,3)) + " ");
        s = s.substring(3);
        sb.append(getHex(s));
        return sb.toString();
    }
    private static String ldr(String substring) {
        String s = substring;
        StringBuilder sb = new StringBuilder();
        sb.append("LDR ");
        sb.append(getRegister(s.substring(0,3)) + " ");
        s = s.substring(3);
        sb.append(getRegister(s.substring(0,3)) + " ");
        s = s.substring(3);
        sb.append(getHex(s));
        return sb.toString();
    }
    private static String ldi(String substring) {
        String s = substring;
        StringBuilder sb = new StringBuilder();
        sb.append("LDI ");
        sb.append(getRegister(s.substring(0,3)) + " ");
        s = s.substring(3);
        sb.append(getHex(s));
        return sb.toString();
    }
    private static String ld(String substring) {
        String s = substring;
        StringBuilder sb = new StringBuilder();
        sb.append("LD ");
        sb.append(getRegister(s.substring(0,3)) + " ");
        s = s.substring(3);
        sb.append(getHex(s));
        return sb.toString();
    }
    private static String js(String substring) {
        String s = substring;
        StringBuilder sb = new StringBuilder();
        if(s.charAt(0) == '1'){
            sb.append("JSR ");
            s = s.substring(1);
            sb.append(getHex(s));
        }else{
            sb.append("JSRR ");
            sb.append(getRegister(s.substring(3, 6)));
        }
        return sb.toString();
    }
    private static String jmpRet(String substring) {
        String s = substring;
        StringBuilder sb = new StringBuilder();
        if(s.substring(3, 6).equals("111")){
            sb.append("RET");
        }else{
            sb.append("JMP ");
            sb.append(getRegister(s.substring(3, 6)));
        }
        return sb.toString();
    }
    private static String br(String substring) {
        String s = substring;
        StringBuilder sb = new StringBuilder();
        sb.append("BR");
        sb.append(brHelper(s.substring(0, 3)) + " ");
        s = s.substring(3);
        sb.append(getHex(s));
        return null;
    }
    private static String getHex(String s) {
        int num = Integer.parseInt(s,2);
        return "x" + Integer.toHexString(num);
    }
    private static String brHelper(String substring) {
        switch(substring){
            case "000": return "";
            case "001": return "p";
            case "010": return "z";
            case "100": return "n";
            case "011": return "zp";
            case "101": return "np";
            case "111": return "nzp";
            default: return "Invalid BR";
        }
    }
    private static String and(String substring) {
        String s = substring;
        StringBuilder sb = new StringBuilder();
        sb.append("AND ");
        sb.append(getRegister(s.substring(0, 3)) + " ");
        s = s.substring(3);
        sb.append(getRegister(s.substring(0, 3)) + " ");
        s = s.substring(3);
        if(s.charAt(0) == '0'){
            sb.append(getRegister(s.substring(3)));
        }else{
            sb.append("#");
            sb.append(getNumber(s.substring(1)));
        }
        return sb.toString();
    }
    private static String add(String substring) {
        String s = substring;
        StringBuilder sb = new StringBuilder();
        sb.append("ADD ");
        sb.append(getRegister(s.substring(0, 3)) + " ");
        s = s.substring(3);
        sb.append(getRegister(s.substring(0, 3)) + " ");
        s = s.substring(3);
        if(s.charAt(0) == '0'){
            sb.append(getRegister(s.substring(3)));
        }else{
            sb.append("#");
            sb.append(getNumber(s.substring(1)));
        }
        return sb.toString();
    }
    private static Object getNumber(String substring) {
        return Integer.toString(Integer.parseInt(substring));
    }
    private static Object getRegister(String substring) {
        switch(substring){
            case "000": return "R0";
            case "001": return "R1";
            case "010": return "R2";
            case "011": return "R3";
            case "100": return "R4";
            case "101": return "R5";
            case "110": return "R6";
            case "111": return "R7";
        }
        return null;
    }
    private static String twosComplement(String n) {
        //System.out.print("Enter Number(Offset or Immediate) In Decimal(#number) or Hex(xnumber): ");
        String s = n.toLowerCase();
        if(s.substring(0,1).toLowerCase().equals("#")){
            int num = Integer.parseInt(s.substring(1));
            if(num < 0)
                return Integer.toBinaryString(num).substring(32 - 16);
            else{
                String res = Integer.toBinaryString(num);
                while(res.length() < 16){
                    res = "0" + res;
                }
                while(res.length() > 16){
                    res = res.substring(1);
                }
                return res;
            }
            
        }else if(s.substring(0,1).toLowerCase().equals("x")){
            int num = Integer.parseInt(s.substring(1), 16);
            if(num < 0)
                return Integer.toBinaryString(num).substring(32 - 16);
            else{
                String res = Integer.toBinaryString(num);
                while(res.length() < 16){
                    res = "0" + res;
                }
                while(res.length() > 16){
                    res = res.substring(1);
                }
                return res;
            }
        }else if(s.substring(0,1).toLowerCase().equals("b") && s.length() == 17){
            return s.substring(1);
        }else{
            return "Invalid Input";
        }
        
        
    }
}
