package user;

public class Helper {

    private String xpath;

    public Helper(String xpath) {
        this.xpath = xpath;
    }

    public boolean isBracketsValid(){
        int circleCounter = 0;
        int squareCounter = 0;

        for(int i= 0; i<xpath.length(); i++){
            if(xpath.charAt(i) == '('){
                circleCounter++;
            }
            else if(xpath.charAt(i) == ')'){
                circleCounter--;
            }
            if(xpath.charAt(i) == '['){
                squareCounter++;
            } else if(xpath.charAt(i) == ']'){
                squareCounter--;
            }
        }

        return (circleCounter == 0)&&(squareCounter == 0);
    }

    public boolean isQuotesValid(){
        int counter = 0;
        for(int i= 0; i<xpath.length(); i++) {
            if (xpath.charAt(i) == '\'') {
                counter++;
            }
        }
        return counter%2==0;
    }

    public boolean isSlashValid(){
        return !xpath.endsWith("/");
    }
}
