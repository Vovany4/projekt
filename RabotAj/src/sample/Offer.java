package sample;

public class Offer {
    private String idOffer;
    private String vacancy ;
    private String price ;
    private String headOffer ;
    private String typeJob ;
    private String city ;
    private String description ;
    private String gender ;
    private String status ;
    private String  username ;
    private String  idusers  ;

    public Offer(String vacancy, String price, String headOffer, String typeJob, String city, String description, String gender, String status) {
        this.vacancy = vacancy;
        this.price = price;
        this.headOffer = headOffer;
        this.typeJob = typeJob;
        this.city = city;
        this.description = description;
        this.gender = gender;
        this.status = status;
    }

    public Offer(String idOffer, String vacancy, String price, String headOffer, String typeJob,
                 String city, String description, String gender, String status, String username, String idusers) {
        this.idOffer = idOffer;
        this.vacancy = vacancy;
        this.price = price;
        this.headOffer = headOffer;
        this.typeJob = typeJob;
        this.city = city;
        this.description = description;
        this.gender = gender;
        this.status = status;
        this.username = username;
        this.idusers = idusers;
    }

    public Offer() {}

    public String getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(String idOffer) {
        this.idOffer = idOffer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdusers() {
        return idusers;
    }

    public void setIdusers(String idusers) {
        this.idusers = idusers;
    }

    public String getVacancy() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHeadOffer() {
        return headOffer;
    }

    public void setHeadOffer(String headOffer) {
        this.headOffer = headOffer;
    }

    public String getTypeJob() {
        return typeJob;
    }

    public void setTypeJob(String typeJob) {
        this.typeJob = typeJob;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
