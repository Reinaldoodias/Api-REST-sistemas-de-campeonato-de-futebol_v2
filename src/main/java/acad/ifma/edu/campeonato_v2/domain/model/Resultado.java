package acad.ifma.edu.campeonato_v2.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.annotation.Transient;

@Entity
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int numGolsMandante;
    private int numGolsVisitante;

    public Resultado() {}
    public Resultado(int numGolsMandante, int numGolsVisitante) {
        this.numGolsMandante = numGolsMandante;
        this.numGolsVisitante = numGolsVisitante;
    }

    @Transient
    public int getPontuacaoMandante(){
        if(this.numGolsMandante > this.numGolsVisitante){
            return 3;
        }
        else if(this.numGolsMandante < this.numGolsVisitante){
            return 0;
        }
        return 1;
    }
    @Transient
    public int getPontuacaoVisitante(){
        if(this.numGolsVisitante > this.numGolsMandante){
            return 3;
        }else if(this.numGolsVisitante < this.numGolsMandante){
            return 0;
        }
        return 1;
    }
    @Transient
    public boolean jogoSaiuEmpate(){
        return this.numGolsMandante == this.numGolsVisitante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumGolsMandante() {
        return numGolsMandante;
    }

    public void setNumGolsMandante(int numGolsMandante) {
        this.numGolsMandante = numGolsMandante;
    }

    public int getNumGolsVisitante() {
        return numGolsVisitante;
    }

    public void setNumGolsVisitante(int numGolsVisitante) {
        this.numGolsVisitante = numGolsVisitante;
    }



}
