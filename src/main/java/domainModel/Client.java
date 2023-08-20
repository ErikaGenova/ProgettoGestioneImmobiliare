package domainModel;


public class Client {
    private final String fiscalCode;
    private String name;
    private String lastName;
    private int budget;

    public Client(String fiscalCode, String name, String lastName, int budget) {
        this.fiscalCode = fiscalCode;
        this.name = name;
        this.lastName = lastName;
        this.budget = budget;
    }

     public String getFiscalCode() {
        return fiscalCode;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getBudget() {
        return budget;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Client{" +
                "fiscalCode='" + fiscalCode + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", budget=" + budget +
                '}';
    }
}
