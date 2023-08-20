package domainModel;


public class PrivateOwner extends Advertiser {
    private String name;
    private String lastName;

    public PrivateOwner(int id, int bankAccount, String name, String lastName) {
        super(id, bankAccount);
        this.name = name;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public void displayInformation() {
        System.out.println("Private Owner Information:");
        System.out.println("ID: " + getId());
        System.out.println("Bank Account: " + getBankAccount());
        System.out.println("Name: " + name);
        System.out.println("Last Name: " + lastName);
    }
}
