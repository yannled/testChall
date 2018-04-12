import java.util.ArrayList;
import java.util.List;

public class CommandExemple {

    public CommandExemple(List<String> texts, String number){
        this.texts = texts;
        this.number = number;
    }

    public CommandExemple(){}

    private List<String> texts;

    private String number;

    public List<String> getTexts(){
        return texts;
    }

    public String getNumber(){
        return number;
    }

    public void setStudents(List<String> texts){
        this.texts = new ArrayList<>(texts);
    }

    public void setNumber(String number){
        this.number = number;
    }

    public boolean equals(CommandExemple commande) {
        int numberCommand = Integer.getInteger(number);
        for (int i = 0; i < numberCommand; numberCommand++) {
            if(!commande.texts.get(i).equals(texts.get(i)))
                return false;
        }
        return true;
    }
}
