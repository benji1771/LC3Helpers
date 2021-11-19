
public class BaseConverter {
    private String binary;
    public BaseConverter(String n){
        binary = toBinary(n);
    }
    public String getBin(){return binary;}
    public static int base(String n){
        if(n.length() < 2) return 2;
        String bina = n.substring(0, 1).toLowerCase();
        String hexa = n.substring(0,2).toLowerCase();
        if(bina.equals("b")){
            if(n.length() == 17)
                return 0;
            
        }else if(hexa.contains("x")){
            if(n.length() == 6 && hexa.equals("0x") || n.length() == 5 && hexa.charAt(0) == 'x'){
                return 1;
            }
        }
        return 2;
        
    }
    public String convert(String num, int source, int destination){
            return Integer.toString(Integer.parseInt(num, source), destination);
    }
    public String toBinary(String n){
        try {
        if(base(n) == 0){
            return n.substring(1,n.length());
        }else if(base(n) == 1){
            if(n.length() == 6)
                return convert(n.substring(2, n.length()), 16, 2);
            else
                return convert(n.substring(1,n.length()), 16, 2);
        }else{
            StringBuilder output = new StringBuilder(Integer.toBinaryString(Integer.parseInt(n)).toString());
            if(output.length() > 16)
                return output.substring(16);
            while(output.length() < 16)
                output.insert(0, "0");
            
            return output.toString();
        }
        } catch (Exception e) {
            return "Invalid Input";
        }
    }

    public String toHex(){
        try {
            return "0x" + convert(binary, 2, 16);
        } catch (Exception e) {
            return "Invalid Input";
        }
        
    }
    public String toUnsigned(){
        try {
            return convert(binary, 2, 10);
        } catch (Exception e) {
            return "Invalid Input";
        }
        
    }
    public String toSigned(){
        if(binary.charAt(0) == '1')
            return "-" + convert(binary.substring(1), 2, 10);
        else
            return convert(binary, 2, 10);
    }
    public String toOneComp(){
        if(binary.charAt(0) == '1'){
            StringBuilder sb = new StringBuilder(binary);
            for(int i = 0; i < sb.length(); i++){
                if(sb.charAt(i) == '0')
                    sb.setCharAt(i, '1');
                else
                    sb.setCharAt(i, '0');
            }
            return "-" + convert(sb.toString(), 2, 10);
        }
        return convert(binary, 2, 10);
    }

    public String toTwoComp(){
        String temp = toOneComp();
        if(temp.charAt(0) == '-'){
            int result = Integer.parseInt(temp.substring(1)) + 1;
            return "-" + Integer.toString(result);
        }
        return temp;
    }
}