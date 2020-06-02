package sample;

public class CV {
    private String idUser;
    private String typeJob;
    private String firstName;
    private String lastName;
    private String vacancy1;
    private String vacancy2;
    private String vacancy3;
    private String description;
    private String city;
    private String sex;
    private String idUserFor;
    private String status;

    public CV() {
    }

    public CV(String idUser,String typeJob, String firstName, String lastName, String vacancy1, String vacancy2,
              String vacancy3, String description, String city, String sex,String idUserFor) {
        this.idUser = idUser;
        this.typeJob = typeJob;
        this.firstName = firstName;
        this.lastName = lastName;
        this.vacancy1 = vacancy1;
        this.vacancy2 = vacancy2;
        this.vacancy3 = vacancy3;
        this.description = description;
        this.city = city;
        this.sex = sex;
        this.idUserFor=idUserFor;
    }

    public String getTypeJob() {
        return typeJob;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdUserFor() {
        return idUserFor;
    }

    public void setIdUserFor(String idUserFor) {
        this.idUserFor = idUserFor;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setTypeJob(String typeJob) {
        this.typeJob = typeJob;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getVacancy1() {
        return vacancy1;
    }

    public void setVacancy1(String vacancy1) {
        this.vacancy1 = vacancy1;
    }

    public String getVacancy2() {
        return vacancy2;
    }

    public void setVacancy2(String vacancy2) {
        this.vacancy2 = vacancy2;
    }

    public String getVacancy3() {
        return vacancy3;
    }

    public void setVacancy3(String vacancy3) {
        this.vacancy3 = vacancy3;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
