import java.util.ArrayList;
import java.util.List;

public class CommandExemple {

    public CommandExemple(List<String> texts, int number){
        this.texts = texts;
        this.number = number;
    }

    private List<String> texts;

    private int number;

    public List<String> gettexts(){
        return texts;
    }

    public int getnumber(){
        return number;
    }

    public void setStudents(List<String> texts){
        this.texts = new ArrayList<>(texts);
    }

    public void setNumber(int number){
        this.number = number;
    }

    public boolean equals(CommandExemple commande) {
        for (int i = 0; i < number; number++) {
            if(!commande.texts.get(i).equals(texts.get(i)))
                return false;
        }
        return true;
    }
}
