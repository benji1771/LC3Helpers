import java.util.Scanner;

public class lc3Program {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Binary Representation is: " + ins(sc));
        }
    }
    static String ins(Scanner input){
        System.out.print("Enter type of instruction(ADD,AND...): ");
        String its = input.next();
        if(its.substring(0,2).toUpperCase().equals("BR")){
            return br(input, its);
        }
        switch(its.toUpperCase()){
            case "ADD": return add(input);
            case "AND": return and(input);
            case "NOT": return not(input);
            case "JMP": return jmp(input);
            case "JSR": return jsr(input);
            case "JSRR": return jsrr(input);
            case "LD": return ld(input);
            case "LDI": return ldi(input);
            case "LDR": return ldr(input);
            case "LEA": return lea(input);
            case "ST": return st(input);
            case "STI": return sti(input);
            case "STR": return str(input);
            case "TRAP": return trap(input);
            case "RTI": return rti(input);
            case "RET": return ret(input);
            default: return "Invalid Command";
        }
        
    }

    
    
    static String getRegister(Scanner input){
        String n = input.next();
        switch(n.toUpperCase()){
            case "R0": return "000";
            case "R1": return "001";
            case "R2": return "010";
            case "R3": return "011";
            case "R4": return "100";
            case "R5": return "101";
            case "R6": return "110";
            case "R7": return "111";
            default: return "(Invalid Register)";
        }
    }
    private static String ret(Scanner input) {
        StringBuilder sb = new StringBuilder();
        sb.append("1100000111000000");
        return sb.toString();
        
    }
    private static String rti(Scanner input) {
        return "1000000000000000";
    }
    private static String trap(Scanner input) {
        StringBuilder sb = new StringBuilder();
        sb.append("11110000");
        sb.append(trapHelper(input));
        return sb.toString();
    }

    private static String trapHelper(Scanner input){
        System.out.print("Enter TRAP Value(Hex or Trap Code): ");
        String s = input.next().toLowerCase();
        if(s.charAt(0) == 'x'){
            return trapHelperHelper(s.substring(1));
        }else if(s.equals("halt")){
            return trapHelperHelper("25");
        }else if(s.equals("in")){
            return trapHelperHelper("23");
        }else if(s.equals("out")){
            return trapHelperHelper("21");
        }else if(s.equals("getc")){
            return trapHelperHelper("20");
        }else if(s.equals("puts")){
            return trapHelperHelper("22");
        }else{
            return "Invalid Trap Code";
        }
    }

    public static String trapHelperHelper(String n){
        int num = Integer.parseInt(n, 16);
        if(num < 0)
            return Integer.toBinaryString(num).substring(32 - 8);
        else{
            String res = Integer.toBinaryString(num);
            while(res.length() < 8){
                res = "0" + res;
            }
            while(res.length() > 8){
                res = res.substring(1);
            }
            return res;
        }
    }
    private static String str(Scanner input) {
        StringBuilder sb = new StringBuilder();
        sb.append("0111");
        System.out.print("Enter Source Register: ");
        sb.append(getRegister(input));
        System.out.print("Enter Base Register: ");
        sb.append(getRegister(input));
        sb.append(twosComplement(6, input));
        return sb.toString();
    }
    private static String sti(Scanner input) {
        StringBuilder sb = new StringBuilder();
        sb.append("1011");
        System.out.print("Enter Source Register: ");
        sb.append(getRegister(input));
        sb.append(twosComplement(9, input));
        return sb.toString();
    }
    private static String st(Scanner input) {
        StringBuilder sb = new StringBuilder();
        sb.append("0011");
        System.out.print("Enter Source Register: ");
        sb.append(getRegister(input));
        sb.append(twosComplement(9, input));
        return sb.toString();
    }
    private static String lea(Scanner input) {
        StringBuilder sb = new StringBuilder();
        sb.append("1110");
        System.out.print("Enter Destination Register: ");
        sb.append(getRegister(input));
        sb.append(twosComplement(9, input));
        return sb.toString();
    }
    private static String ldr(Scanner input) {
        StringBuilder sb = new StringBuilder();
        sb.append("0110");
        System.out.print("Enter Destination Register: ");
        sb.append(getRegister(input));
        System.out.print("Enter Base Register: ");
        sb.append(getRegister(input));
        sb.append(twosComplement(6, input));
        return sb.toString();
    }
    private static String ldi(Scanner input) {
        StringBuilder sb = new StringBuilder();
        sb.append("1010");
        System.out.print("Enter Destination Register: ");
        sb.append(getRegister(input));
        sb.append(twosComplement(9, input));
        return sb.toString();
    }
    private static String ld(Scanner input) {
        StringBuilder sb = new StringBuilder();
        sb.append("0010");
        System.out.print("Enter Destination Register: ");
        sb.append(getRegister(input));
        sb.append(twosComplement(9, input));

        return sb.toString();
    }
    private static String jsrr(Scanner input) {
        StringBuilder sb = new StringBuilder();
        sb.append("0100000");
        System.out.print("Enter Base R: ");
        sb.append(getRegister(input));
        sb.append("000000");
        return sb.toString();
    }
    private static String jsr(Scanner input) {
        StringBuilder sb = new StringBuilder();
        sb.append("01001");
        sb.append(twosComplement(11, input));
        return sb.toString();
    }
    private static String jmp(Scanner input) {
        StringBuilder sb = new StringBuilder();
        sb.append("1100000");
        System.out.print("Enter BaseR: ");
        sb.append(getRegister(input));
        sb.append("000000");
        return sb.toString();
    }
    private static String br(Scanner input, String its) {
        StringBuilder sb = new StringBuilder();
        sb.append("0000");
        if(its.length() > 2){
            String temp = its.substring(2).toLowerCase();
            switch(temp){
                case "nzp": sb.append("111");
                case "nz": sb.append("110");
                case "np": sb.append("101");
                case "zp": sb.append("011");
                case "n": sb.append("100");
                case "z": sb.append("010");
                case "p": sb.append("001");
                default: return "Invalid BR command";
            }
        }else{
            sb.append("000");
        }
        sb.append(twosComplement(9, input));
        return sb.toString();
    }
    private static String not(Scanner input) {
        StringBuilder sb = new StringBuilder();
        sb.append("1001");
        System.out.print("Enter Destination: ");
        sb.append(getRegister(input));
        System.out.print("Enter Register: ");
        sb.append(getRegister(input));
        sb.append("111111");
        return sb.toString();
    }
    private static String and(Scanner input) {
        StringBuilder sb = new StringBuilder();
        sb.append("0101");
        System.out.print("Enter Destination Register(R0, R1, R2...): ");
        sb.append(getRegister(input));
        System.out.print("Enter Register: ");
        sb.append(getRegister(input));
        System.out.print("Enter next type 'R' or '#' or 'X': ");
        String temp = input.next().toUpperCase();
        if(temp.charAt(0) == 'R'){
            sb.append("0");
            sb.append(getRegister(input));
        }else if(temp.charAt(0) == '#' || temp.charAt(0) == 'X'){
            sb.append("1");
            sb.append(twosComplement(5, input));
        }else{
            return "Invalid Input";
        }
        return sb.toString();
    }
    private static String add(Scanner input) {
        StringBuilder sb = new StringBuilder();
        sb.append("0001");
        System.out.print("Enter Destination Register(R0, R1, R2...): ");
        sb.append(getRegister(input));
        System.out.print("Enter Register: ");
        sb.append(getRegister(input));
        System.out.print("Enter next type 'R' or '#' or 'X': ");
        String temp = input.next().toUpperCase();
        if(temp.charAt(0) == 'R'){
            sb.append("0");
            sb.append(getRegister(input));
        }else if(temp.charAt(0) == '#' || temp.charAt(0) == 'X'){
            sb.append("1");
            sb.append(twosComplement(5, input));
        }else{
            return "Invalid Input";
        }
        return sb.toString();
    }
    private static String twosComplement(int i, Scanner input) {
        System.out.print("Enter Number(Offset or Immediate) In Decimal(#number) or Hex(xnumber): ");
        String s = input.next();
        if(s.substring(0,1).toLowerCase().equals("#")){
            int num = Integer.parseInt(s.substring(1));
            if(num < 0)
                return Integer.toBinaryString(num).substring(32 - i);
            else{
                String res = Integer.toBinaryString(num);
                while(res.length() < i){
                    res = "0" + res;
                }
                while(res.length() > i){
                    res = res.substring(1);
                }
                return res;
            }
            
        }else if(s.substring(0,1).toLowerCase().equals("x")){
            int num = Integer.parseInt(s.substring(1), 16);
            if(num < 0)
                return Integer.toBinaryString(num).substring(32 - i);
            else{
                String res = Integer.toBinaryString(num);
                while(res.length() < i){
                    res = "0" + res;
                }
                while(res.length() > i){
                    res = res.substring(1);
                }
                return res;
            }
        }else{
            return "Invalid Offset/Number";
        }
        
        
    }
}