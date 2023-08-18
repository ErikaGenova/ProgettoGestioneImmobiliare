package domainModel;



public class EstateAgency extends Advertiser {
    private String name;
    private int agencyFee;

    public EstateAgency(String id, int bankAccount, String name, int agencyFee) {
        super(id, bankAccount);
        this.name = name;
        this.agencyFee = agencyFee;

    }

    public String getName() {
        return name;
    }

    public int getAgencyFee() {
        return agencyFee;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAgencyFee(int agencyFee) {
        this.agencyFee = agencyFee;
    }

    @Override
    public void displayInformation() {
        System.out.println("Estate Agency Information:");
        System.out.println("ID: " + getId());
        System.out.println("Bank Account: " + getBankAccount());
        System.out.println("Name: " + name);
        System.out.println("Agency Fee: " + agencyFee);
    }
}